package com.example.wanted;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String obtainAccessToken(String username,
                                     String password) throws Exception{

        System.out.println("Sending login request for username : " + username);
        System.out.println("Sending login request for password : " + password);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .content("{\"username\" : \"" + username + "\" , \"password\" : \"" + password +"\" }"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        System.out.println("Login response : "+ response);

        return response;
    }

    @Test
    @DisplayName("로그인 테스트")
    public void loginTest() throws Exception{
        //member 등록
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{\"memberName\" : \"testUser\",\"email\":\"testUser@email.com\",\"password\":\"testPassword\"}"))
                .andExpect(status().isCreated());

        //로그인
        String token = obtainAccessToken("testUser@email.com",
                "testPassword");
    }

    @Test
    @DisplayName("회원은 제품 등록과 구매가 가능함")
    public void testMemberFunctions() throws Exception{
        //member-seller 등록
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"memberName\" : \"testSellerUser\",\"email\":\"testSellerUser@email.com\",\"password\":\"sellerPassword\"}"))
                .andExpect(status().isCreated());

        //member-buyer 등록
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"memberName\" : \"testBuyerUser\",\"email\":\"testBuyerUser@email.com\",\"password\":\"buyerPassword\"}"))
                .andExpect(status().isCreated());

        //로그인
        String tokenSeller = obtainAccessToken("testSellerUser@email.com",
                "sellerPassword");
        String tokenBuyer = obtainAccessToken("testBuyerUser@email.com",
                "buyerPassword");

        //product 등록
        mockMvc.perform(post("/products")
                        .header("Authorization","Bearer "+ tokenSeller)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productName\" : \"커피\",\"productStatus\" : \"PRODUCT_AVAILABLE\",\"price\" : 500,\"seller\" : {\"memberId\" : 1, \"name\": \"Seller\" }}"))
                .andExpect(status().isCreated());

        //order 등록
        mockMvc.perform(post("/orders")
                .header("Authorization", "Bearer " + tokenBuyer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\" : 1, \"OrderStatus\" : \"IN_PROGRESS\", \"buyerId\" : 2}"))
                .andExpect(status().isCreated());
    }
}
