package com.example.wanted;

import com.example.wanted.exception.BusinessLogicException;
import com.example.wanted.exception.ExceptionCode;
import com.example.wanted.member.Member;
import com.example.wanted.member.MemberRepository;
import com.example.wanted.member.MemberService;
import com.example.wanted.order.Order;
import com.example.wanted.order.OrderRepository;
import com.example.wanted.order.OrderService;
import com.example.wanted.product.Product;
import com.example.wanted.product.ProductRepository;
import com.example.wanted.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }

    @DisplayName("거래 생성 시, 거래 상태값은 1(진행중)이어야 함")
    @Test
    public void whenOrderIsCreated_thenOrderStatusStepNumberShouldBeOne() {
        // given
        Member member = new Member();
        member.setEmail("123@123.com");
        member.setMemberName("John Doe");
        member.setPassword("1234");

        memberService.createMember(member);

        Product product = new Product();
        product.setProductName("New Product");
        product.setPrice(100);
        product.setSeller(member);

        productService.createProduct(product);

        Order order = new Order();
        order.addMember(member);
        order.addProduct(product);

        // when
        Order createdOrder = orderService.createOrder(order);

        // then
        assertThat(createdOrder.getOrderStatus().getStepNumber()).isEqualTo(1);
    }

    @DisplayName("product 상태값이 2(예약중) 이상인 경우, 거래 불가여야 함")
    @Test
    public void OrderIsNotAvailable_WhenProductStatusStepNumberIsGreaterThenTwo() {
        // given
        Member member = new Member();
        member.setEmail("123@123.com");
        member.setMemberName("John Doe");
        member.setPassword("1234");

        memberService.createMember(member);


        Product product = new Product();
        product.setProductName("New Product");
        product.setPrice(100);
        product.setSeller(member);

        productService.createProduct(product);

        Order order = new Order();
        order.addMember(member);
        order.addProduct(product);

        // when
        Order createdOrder = orderService.createOrder(order);

        // then
        Product updatedProduct = productRepository.findById(product.getProductId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));

        assertThat(updatedProduct.getProductStatus().getStepNumber()).isEqualTo(2);
    }

    @DisplayName("구매 내역 조회(내가 구매자)")
    @Test
    public void getPurchaseList_whenIAmBuyer() {
        // given
        //seller 설정
        Member seller = new Member();
        seller.setMemberId(1L);
        seller.setEmail("123@seller.com");
        seller.setMemberName("Seller");
        seller.setPassword("sell123");

        memberService.createMember(seller);

        //buyer 설정
        Member buyer = new Member();
        buyer.setMemberId(2L);
        buyer.setEmail("123@buyer.com");
        buyer.setMemberName("Buyer");
        buyer.setPassword("buy123");

        memberService.createMember(buyer);

        //buyer 설정
        Member member3 = new Member();
        member3.setMemberId(3L);
        member3.setEmail("123@member3.com");
        member3.setMemberName("Member3");
        member3.setPassword("member123");

        memberService.createMember(member3);

        //product 설정
        Product product1 = new Product();
        product1.setProductId(1L);
        product1.setProductName("New Product1");
        product1.setPrice(100);
        product1.setSeller(seller);

        productService.createProduct(product1);

        //product2 설정
        Product product2 = new Product();
        product2.setProductId(2L);
        product2.setProductName("New Product2");
        product2.setPrice(200);
        product2.setSeller(seller);

        productService.createProduct(product2);

        //product2 설정
        Product product3 = new Product();
        product3.setProductId(3L);
        product3.setProductName("New Product3");
        product3.setPrice(300);
        product3.setSeller(seller);

        productService.createProduct(product3);

        //order 설정
        Order order1 = new Order();
        order1.setOrderId(1L);
        order1.addMember(buyer);
        order1.addProduct(product1);

        Order createdOrder1 = orderService.createOrder(order1);

        //order 설정
        Order order2 = new Order();
        order2.setOrderId(2L);
        order2.addMember(member3);
        order2.addProduct(product2);

        Order createdOrder2 = orderService.createOrder(order2);

        //order 설정
        Order order3 = new Order();
        order3.setOrderId(2L);
        order3.addMember(buyer);
        order3.addProduct(product3);

        Order createdOrder3 = orderService.createOrder(order3);


        // when
        List<Order> getPurchasedOrdersByBuyer = orderService.findPurchasedOrders(buyer.getMemberId());
        List<Order> getPurchasedOrdersByNotBuyer = orderService.findPurchasedOrders(member3.getMemberId());

        // then
        assertThat(getPurchasedOrdersByBuyer.stream()
                .anyMatch(orderByBuyer -> orderByBuyer.getOrderId().equals(createdOrder1.getOrderId())))
                .isTrue();

        assertThat(getPurchasedOrdersByNotBuyer.stream()
                .anyMatch(orderByNotBuyer -> orderByNotBuyer.getOrderId().equals(createdOrder1.getOrderId())))
                .isFalse();

        assertThat(getPurchasedOrdersByBuyer.stream()
                .anyMatch(orderByBuyer -> orderByBuyer.getOrderId().equals(createdOrder3.getOrderId())))
                .isTrue();
    }

    @DisplayName("CreateOrder 값 확인")
    @Test
    public void getCreateOrder() {
        // given
        Member seller = new Member();
        seller.setMemberId(1L);
        seller.setEmail("123@seller.com");
        seller.setMemberName("Seller");
        seller.setPassword("sell123");

        memberService.createMember(seller);

        Member buyer = new Member();
        buyer.setMemberId(2L);
        buyer.setEmail("123@buyer.com");
        buyer.setMemberName("Buyer");
        buyer.setPassword("buy123");

        memberService.createMember(buyer);


        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("New Product");
        product.setPrice(100);
        product.setSeller(seller);

        productService.createProduct(product);

        Order order = new Order();
        order.addMember(buyer);
        order.addProduct(product);

        // when
        Order createdOrder = orderService.createOrder(order);

        // then
        assertThat(createdOrder.getProduct().getSeller().getMemberId()).isEqualTo(1L);
        assertThat(createdOrder.getBuyer().getMemberId()).isEqualTo(2L);
        assertThat(createdOrder.getProduct().getProductId()).isEqualTo(1L);

    }
}
