package com.example.wanted.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPatchDto {
    private long productId;
    @NotBlank
    private String productName;
    @NotBlank
    private Product.ProductStatus productStatus;
    @NotBlank
    private int price;
}