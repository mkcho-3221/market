package com.example.wanted.product;

import com.example.wanted.product.Product.ProductStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-28T09:42:27+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product productPostDtoToProduct(ProductPostDto productPostDto) {
        if ( productPostDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setProductName( productPostDto.getProductName() );
        if ( productPostDto.getProductStatus() != null ) {
            product.setProductStatus( Enum.valueOf( ProductStatus.class, productPostDto.getProductStatus() ) );
        }
        product.setPrice( productPostDto.getPrice() );
        product.setSeller( productPostDto.getSeller() );

        return product;
    }

    @Override
    public Product productPatchDtoToProduct(ProductPatchDto productPatchDto) {
        if ( productPatchDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setProductId( productPatchDto.getProductId() );
        product.setProductName( productPatchDto.getProductName() );
        product.setProductStatus( productPatchDto.getProductStatus() );
        product.setPrice( productPatchDto.getPrice() );

        return product;
    }

    @Override
    public ProductResponseDto productToProductResponseDto(Product product) {
        if ( product == null ) {
            return null;
        }

        long productId = 0L;
        String productName = null;
        String productStatus = null;
        int price = 0;

        if ( product.getProductId() != null ) {
            productId = product.getProductId();
        }
        productName = product.getProductName();
        if ( product.getProductStatus() != null ) {
            productStatus = product.getProductStatus().name();
        }
        price = product.getPrice();

        ProductResponseDto productResponseDto = new ProductResponseDto( productId, productName, productStatus, price );

        return productResponseDto;
    }
}
