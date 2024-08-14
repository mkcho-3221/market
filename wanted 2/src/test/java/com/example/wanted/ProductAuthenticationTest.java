package com.example.wanted;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductAuthenticationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("비회원 제품 상세조회 성공")
    public void noneMemberGetProductTest() throws Exception{
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비회원 제품 목록조회 성공")
    public void noneMemberGetProductsTest() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비회원 제품 등록 실패")
    public void noneMemberCreateProductTest() throws Exception {
        mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer someInvalidToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productName\" : \"커피\",\"productStatus\" : \"PRODUCT_AVAILABLE\",\"price\" : 500 }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("비회원 제품 수정 실패")
    public void noneMemberUpdateProductTest() throws Exception {
        mockMvc.perform(patch("/products/1")
                        .header("Authorization", "Bearer someInvalidToken"))
                .andExpect(status().isUnauthorized());
    }
}
