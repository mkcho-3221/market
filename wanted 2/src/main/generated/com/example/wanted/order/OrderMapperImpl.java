package com.example.wanted.order;

import com.example.wanted.member.Member;
import com.example.wanted.order.Order.OrderStatus;
import com.example.wanted.product.Product;
import com.example.wanted.product.Product.ProductStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-22T10:53:30+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order orderPostDtoToOrder(OrderPostDto orderPostDto) {
        if ( orderPostDto == null ) {
            return null;
        }

        Order order = new Order();

        order.setBuyer( orderPostDtoToMember( orderPostDto ) );
        order.setProduct( orderPostDtoToProduct( orderPostDto ) );
        order.setOrderStatus( orderPostDto.getOrderStatus() );

        return order;
    }

    @Override
    public Order orderPatchDtoToOrder(OrderPatchDto orderPatchDto) {
        if ( orderPatchDto == null ) {
            return null;
        }

        Order order = new Order();

        order.setOrderId( orderPatchDto.getOrderId() );
        order.setOrderStatus( orderPatchDto.getOrderStatus() );

        return order;
    }

    @Override
    public OrderResponseDto orderToOrderResponseDto(Order product) {
        if ( product == null ) {
            return null;
        }

        long sellerId = 0L;
        long buyerId = 0L;
        long productId = 0L;
        ProductStatus productStatus = null;
        long orderId = 0L;
        OrderStatus orderStatus = null;

        Long memberId = productProductSellerMemberId( product );
        if ( memberId != null ) {
            sellerId = memberId;
        }
        Long memberId1 = productBuyerMemberId( product );
        if ( memberId1 != null ) {
            buyerId = memberId1;
        }
        productId = productProductProductId( product );
        productStatus = productProductProductStatus( product );
        orderId = product.getOrderId();
        orderStatus = product.getOrderStatus();

        OrderResponseDto orderResponseDto = new OrderResponseDto( orderId, orderStatus, productId, sellerId, buyerId, productStatus );

        return orderResponseDto;
    }

    protected Member orderPostDtoToMember(OrderPostDto orderPostDto) {
        if ( orderPostDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberId( orderPostDto.getBuyerId() );

        return member;
    }

    protected Product orderPostDtoToProduct(OrderPostDto orderPostDto) {
        if ( orderPostDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setProductId( orderPostDto.getProductId() );

        return product;
    }

    private Long productProductSellerMemberId(Order order) {
        if ( order == null ) {
            return null;
        }
        Product product = order.getProduct();
        if ( product == null ) {
            return null;
        }
        Member seller = product.getSeller();
        if ( seller == null ) {
            return null;
        }
        Long memberId = seller.getMemberId();
        if ( memberId == null ) {
            return null;
        }
        return memberId;
    }

    private Long productBuyerMemberId(Order order) {
        if ( order == null ) {
            return null;
        }
        Member buyer = order.getBuyer();
        if ( buyer == null ) {
            return null;
        }
        Long memberId = buyer.getMemberId();
        if ( memberId == null ) {
            return null;
        }
        return memberId;
    }

    private long productProductProductId(Order order) {
        if ( order == null ) {
            return 0L;
        }
        Product product = order.getProduct();
        if ( product == null ) {
            return 0L;
        }
        long productId = product.getProductId();
        return productId;
    }

    private ProductStatus productProductProductStatus(Order order) {
        if ( order == null ) {
            return null;
        }
        Product product = order.getProduct();
        if ( product == null ) {
            return null;
        }
        ProductStatus productStatus = product.getProductStatus();
        if ( productStatus == null ) {
            return null;
        }
        return productStatus;
    }
}
