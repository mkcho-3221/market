package com.example.wanted;

import com.example.wanted.order.Order;
import com.example.wanted.order.OrderMapper;
import com.example.wanted.order.OrderPostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderPostDtoTest {
    @Autowired
    private OrderMapper orderMapper;
    @Test
    public void testOrderPostDtoToOrderMapping(){

        OrderPostDto postDto = new OrderPostDto();
        postDto.setBuyerId(1L);
        postDto.setProductId(2L);

        Order order = orderMapper.orderPostDtoToOrder(postDto);

        assertThat(order.getBuyer().getMemberId()).isEqualTo(1L);
        assertThat(order.getProduct().getProductId()).isEqualTo(2L);
    }
}
