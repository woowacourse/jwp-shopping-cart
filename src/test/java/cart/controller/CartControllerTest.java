package cart.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import cart.service.JwpCartService;

@WebMvcTest
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwpCartService jwpCartService;

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addToCart() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                    .request(HttpMethod.POST, "/cart-items")
                    .header("Authorization", "Basic ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk")
                    .content("1")
            )
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("장바구니의 상품을 조회한다.")
    void findAllCartItems() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                    .request(HttpMethod.GET, "/cart-items")
                    .header("Authorization", "Basic ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk")
                    .content("1")
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("장바구니의 상품을 삭제한다.")
    void deleteCartProduct() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                    .request(HttpMethod.DELETE, "/cart-items/1")
                    .header("Authorization", "Basic ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk")
                    .content("1")
            )
            .andDo(print())
            .andExpect(status().isOk());
    }
}
