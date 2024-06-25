package com.example.wanted.order;

import com.example.wanted.exception.BusinessLogicException;
import com.example.wanted.exception.ExceptionCode;
import com.example.wanted.member.Member;
import com.example.wanted.member.MemberRepository;
import com.example.wanted.product.Product;
import com.example.wanted.product.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public Order findVerifyOrder(long orderId){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        Order findOrder = optionalOrder.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));

        return findOrder;
    }

    public Order createOrder(Order order){

        Member buyer = memberRepository.findById(order.getBuyer().getMemberId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Product product = productRepository.findById(order.getProduct().getProductId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));

        if(product.getProductStatus().getStepNumber() >= 2){
            throw new BusinessLogicException(ExceptionCode.PRODUCT_NOT_AVAILABLE);
        }

        order.setBuyer(buyer);

        order.setOrderStatus(Order.OrderStatus.IN_PROGRESS);
        product.setProductStatus(Product.ProductStatus.PRODUCT_RESERVED);
        productRepository.save(product);

        Order createdOrder = orderRepository.save(order);

        return createdOrder;
    }


    public Order updateOrder(Order order){

        Order findOrder = findVerifyOrder(order.getOrderId());

        Optional.ofNullable(findOrder.getOrderStatus())
                .ifPresent(orderStatus -> findOrder.setOrderStatus(Order.OrderStatus.COMPLETED));

        Order updatedOrder = orderRepository.save(findOrder);

        return updatedOrder;
    }


    public Order findOrder(long orderId){

        return findVerifyOrder(orderId);
    }

    public Page<Order> findOrders(int page, int size){

        Page<Order> orders = orderRepository.findAll(
                PageRequest.of(page, size, Sort.by("orderId").descending()));

        return orders;
    }

    public void deleteOrder(long orderId){

        Order findOrder = findVerifyOrder(orderId);

        orderRepository.delete(findOrder);
    }


    //구매한 목록
    public List<Order> findPurchasedOrders(long buyerId){

        return orderRepository.findByBuyerMemberId(buyerId);
    }

    //예약목록
    public List<Order> findReservedOrders(long memberId){

        return orderRepository.findReservedOrdersByMemberId(memberId);
    }

    //거래 완료
    public Order approveOrder(Long orderId, Long sellerId){
        Order order = findVerifyOrder(orderId);

        if (!order.getProduct().getSeller().getMemberId().equals(sellerId)) {
            throw new BusinessLogicException(ExceptionCode.PERMISSION_DENIED);
        }

        order.setOrderStatus(Order.OrderStatus.COMPLETED);
        order.getProduct().setProductStatus(Product.ProductStatus.PRODUCT_SOLD);

        return orderRepository.save(order);
    }
}
