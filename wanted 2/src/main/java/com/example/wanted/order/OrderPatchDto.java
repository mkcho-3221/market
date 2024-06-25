package com.example.wanted.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPatchDto {
    @NotBlank
    private long orderId;
    private Order.OrderStatus orderStatus;
}
