package com.example.wanted;

import com.example.wanted.member.Member;
import com.example.wanted.member.MemberService;
import com.example.wanted.order.Order;
import com.example.wanted.order.OrderMapper;
import com.example.wanted.order.OrderPostDto;
import com.example.wanted.order.OrderResponseDto;
import com.example.wanted.product.Product;
import com.example.wanted.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderResponseDtoTest {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;

    @DisplayName("OrderResponseDto 확인")
    @Test
    public void testOrderToOrderResponseDto(){
        //get
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

        //when
        OrderResponseDto responseDto = orderMapper.orderToOrderResponseDto(order);

        //then
        assertThat(responseDto.getSellerId()).isEqualTo(1L);
        assertThat(responseDto.getBuyerId()).isEqualTo(2L);
        assertThat(responseDto.getProductId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("OrderResponseDto 확인")
    public void testOrderToOrderResponseDto2() {
        // Given
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

        // When
        OrderResponseDto responseDto = orderMapper.orderToOrderResponseDto(order);

        // Then
        System.out.println("OrderResponseDto: " + responseDto); // 디버깅용 출력
        assertThat(responseDto.getSellerId()).isEqualTo(1L);
    }

}
