package com.example.wanted.order;

import com.example.wanted.member.Member;
import com.example.wanted.product.Product;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPostDto {
    @NotNull
    private long buyerId;
    @NotNull
    private long productId;
    private Order.OrderStatus orderStatus;
}
