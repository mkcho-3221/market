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

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("거래 생성 시, 거래 상태값은 1(진행중)이어야 함")
    @Test
    public void whenOrderIsCreated_thenOrderStatusStepNumberShouldBeOne() {
        // given
        Member member = new Member();
        member.setEmail("123@123.com");
        member.setUserName("John Doe");
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
        member.setUserName("John Doe");
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

//    @DisplayName("구매 내역 조회(내가 구매자)")
//    @Test
//    public void getPurchaseList_whenIAmBuyer() {
//        // given
//        Member seller = new Member();
//        seller.setEmail("123@seller.com");
//        seller.setUserName("Seller");
//        seller.setPassword("sell123");
//
//        memberService.createMember(seller);
//
//        Member buyer = new Member();
//        buyer.setEmail("123@buyer.com");
//        buyer.setUserName("Buyer");
//        buyer.setPassword("buy123");
//
//        memberService.createMember(buyer);
//
//
//        Product product = new Product();
//        product.setProductName("New Product");
//        product.setPrice(100);
//        product.setSeller(seller);
//
//        productService.createProduct(product);
//
//        Order order = new Order();
//        order.addBuyer(buyer);
//        order.addProduct(product);
//
//        Order createdOrder = orderService.createOrder(order);
//
//        // when
//        List<Order> getPurchasedOrders = orderService.findPurchasedOrders(buyer.getMemberId());
//
//        // then
//        Product updatedProduct = productRepository.findById(product.getProductId())
//                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));
//
//        assertThat(updatedProduct.getSeller()).isEqualTo(seller);
//        assertThat(updatedProduct.getOrder().getBuyer()).isEqualTo(buyer);
//
//    }

    @DisplayName("PostOrder 값 확인")
    @Test
    public void getPurchaseList_whenIAmBuyer() {
        // given
        Member seller = new Member();
        seller.setMemberId(1L);
        seller.setEmail("123@seller.com");
        seller.setUserName("Seller");
        seller.setPassword("sell123");

        memberService.createMember(seller);

        Member buyer = new Member();
        buyer.setMemberId(2L);
        buyer.setEmail("123@buyer.com");
        buyer.setUserName("Buyer");
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
