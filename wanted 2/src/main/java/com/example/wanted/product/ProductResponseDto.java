package com.example.wanted.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponseDto {
    private long productId;
    private String productName;
    private String productStatus;
    private int price;
}
