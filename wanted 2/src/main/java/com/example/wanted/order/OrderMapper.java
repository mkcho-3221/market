package com.example.wanted.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "buyerId", target = "buyer.memberId")
    @Mapping(source = "productId", target = "product.productId")
//    @Mapping(source = "product.seller.memberId", target = "sellerId")//추가
    Order orderPostDtoToOrder(OrderPostDto orderPostDto);
    Order orderPatchDtoToOrder(OrderPatchDto orderPatchDto);

    @Mapping(source = "product.seller.memberId", target = "sellerId")//추가
    @Mapping(source = "buyer.memberId", target = "buyerId")
    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.productStatus", target = "productStatus")
    OrderResponseDto orderToOrderResponseDto(Order product);

}
