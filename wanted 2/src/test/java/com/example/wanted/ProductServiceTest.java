package com.example.wanted;

import com.example.wanted.product.Product;
import com.example.wanted.product.ProductRepository;
import com.example.wanted.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenProductIsCreated_thenProductStatusStepNumberShouldBeOne() {
        // given
        Product product = new Product();
        product.setProductName("New Product");
        product.setPrice(100);

        // when
        Product createdProduct = productService.createProduct(product);

        // then
        assertThat(createdProduct.getProductStatus().getStepNumber()).isEqualTo(1);
    }
}
