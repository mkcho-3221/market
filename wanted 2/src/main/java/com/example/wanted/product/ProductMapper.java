package com.example.wanted.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "productId", ignore = true)
    Product productPostDtoToProduct(ProductPostDto productPostDto);
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "order", ignore = true)
    Product productPatchDtoToProduct(ProductPatchDto productPatchDto);
    ProductResponseDto productToProductResponseDto(Product product);
}
