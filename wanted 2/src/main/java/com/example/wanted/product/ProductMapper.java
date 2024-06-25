package com.example.wanted.product;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product productPostDtoToProduct(ProductPostDto productPostDto);
    Product productPatchDtoToProduct(ProductPatchDto productPatchDto);
    ProductResponseDto productToProductResponseDto(Product product);
}
