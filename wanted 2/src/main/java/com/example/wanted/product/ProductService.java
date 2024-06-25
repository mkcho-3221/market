package com.example.wanted.product;

import com.example.wanted.exception.BusinessLogicException;
import com.example.wanted.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findVerifyProduct(long productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);

        Product findProduct = optionalProduct.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));

        return findProduct;
    }

    public Product createProduct(Product product){

        Product createdProduct = productRepository.save(product);

        return createdProduct;
    }

    public Product updateProduct(Product product){

        Product findProduct = findVerifyProduct(product.getProductId());

        Optional.ofNullable(findProduct.getProductName())
                .ifPresent(productName -> findProduct.setProductName(productName));
        Optional.ofNullable(findProduct.getPrice())
                .ifPresent(price -> findProduct.setPrice(price));
        Optional.ofNullable(findProduct.getProductStatus())
                .ifPresent(productStatus -> findProduct.setProductStatus(productStatus));

        Product updatedProduct = productRepository.save(findProduct);

        return updatedProduct;
    }

    public Product findProduct(long productId){

        return findVerifyProduct(productId);
    }

    public Page<Product> findProducts(int page, int size){

        Page<Product> products = productRepository.findAll(
                PageRequest.of(page, size, Sort.by("productId").descending()));

        return products;
    }

    public void deleteProduct(long productId){

        Product findProduct = findVerifyProduct(productId);

        productRepository.delete(findProduct);
    }
}