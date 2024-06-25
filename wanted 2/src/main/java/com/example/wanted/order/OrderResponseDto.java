package com.example.wanted.order;

import com.example.wanted.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderResponseDto {
    private long orderId;
    private Order.OrderStatus orderStatus;
    private long productId;
    private long sellerId;
    private long buyerId;
    private Product.ProductStatus productStatus;
}
