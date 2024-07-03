package com.example.wanted.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/products")
@Validated
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    //제품 등록
    @PostMapping
    public ResponseEntity postProduct(@Valid @RequestBody ProductPostDto postDto){

        Product product = productMapper.productPostDtoToProduct(postDto);

        Product response = productService.createProduct(product);

        return new ResponseEntity(productMapper.productToProductResponseDto(response), HttpStatus.CREATED);
    }

    //제품 세부 내역 업데이트
    @PatchMapping("/{product-id}")
    public ResponseEntity patchProduct(@PathVariable("product-id") long productId,
                                       @Valid @RequestBody ProductPatchDto patchDto){

        patchDto.setProductId(productId);

        Product response = productService.updateProduct(productMapper.productPatchDtoToProduct(patchDto));

        return new ResponseEntity(productMapper.productToProductResponseDto(response), HttpStatus.OK);
    }

    //제품 조회
    @GetMapping("/{product-id}")
    public ResponseEntity getProduct(@PathVariable("product-id") long productId){

        Product response = productService.findProduct(productId);

        return new ResponseEntity(productMapper.productToProductResponseDto(response), HttpStatus.OK);
    }

    //전체 제품 조회
    @GetMapping
    public ResponseEntity getProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size){

        Page<Product> products = productService.findProducts(page, size);

        List<ProductResponseDto> response = products.stream()
                .map(product -> productMapper.productToProductResponseDto(product))
                .collect(Collectors.toList());

        return new ResponseEntity(response, HttpStatus.OK);
    }

    //제품 삭제
    @DeleteMapping("/{product-id}")
    public ResponseEntity deleteProduct(@PathVariable("product-id") @Positive long productId){

        productService.deleteProduct(productId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
