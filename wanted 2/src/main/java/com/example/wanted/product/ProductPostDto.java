package com.example.wanted.product;

import com.example.wanted.member.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPostDto{
    @NotBlank
    private String productName;
    @NotBlank
    private String productStatus;
    @NotNull
    private int price;
    @NotNull
    private Member seller;
}
