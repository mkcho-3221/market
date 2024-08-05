package com.example.wanted;

import com.example.wanted.member.Member;
import com.example.wanted.member.MemberRepository;
import com.example.wanted.member.MemberService;
import com.example.wanted.order.*;
import com.example.wanted.product.Product;
import com.example.wanted.product.ProductRepository;
import com.example.wanted.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderResponseDtoTest {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp(){
        orderRepository.deleteAll();
        productRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("OrderResponseDto 확인1")
    @Test
    @Transactional
    public void testOrderToOrderResponseDto(){
        //get
        Member seller = new Member();
        seller.setEmail("123@seller.com");
        seller.setMemberName("Seller");
        seller.setPassword("sell123");

        Member savedSellerMember = memberService.createMember(seller);

        Member buyer = new Member();
        buyer.setEmail("123@buyer.com");
        buyer.setMemberName("Buyer");
        buyer.setPassword("buy123");

        Member savedBuyerMember = memberService.createMember(buyer);

        Product product = new Product();
        product.setProductName("New Product");
        product.setPrice(100);
        product.setSeller(seller);

        Product savedProduct = productService.createProduct(product);

        Order order = new Order();
        order.addMember(buyer);
        order.addProduct(product);

        //when
        OrderResponseDto responseDto = orderMapper.orderToOrderResponseDto(order);

        //then
        assertThat(responseDto.getSellerId()).isEqualTo(savedSellerMember.getMemberId());
        assertThat(responseDto.getBuyerId()).isEqualTo(savedBuyerMember.getMemberId());
        assertThat(responseDto.getProductId()).isEqualTo(savedProduct.getProductId());
    }

    @Test
    @DisplayName("OrderResponseDto 확인2")
    @Transactional
    public void testOrderToOrderResponseDto2() {
        // Given
        Member seller = new Member();
        seller.setEmail("123@seller.com");
        seller.setMemberName("Seller");
        seller.setPassword("sell123");

        Member savedSellerMember = memberService.createMember(seller);

        Member buyer = new Member();
        buyer.setEmail("123@buyer.com");
        buyer.setMemberName("Buyer");
        buyer.setPassword("buy123");

        Member savedBuyerMember = memberService.createMember(buyer);

        Product product = new Product();
        product.setProductName("New Product");
        product.setPrice(100);
        product.setSeller(seller);

        Product savedProduct = productService.createProduct(product);

        Order order = new Order();
        order.addMember(buyer);
        order.addProduct(product);

        // When
        OrderResponseDto responseDto = orderMapper.orderToOrderResponseDto(order);

        // Then
        assertThat(responseDto.getSellerId()).isEqualTo(savedSellerMember.getMemberId());
    }
}
