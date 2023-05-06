package cart.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.service.CartService;
import cart.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;

    @MockBean
    private UserService userService;

    @DisplayName("GET /carts/me/products 요청 시")
    @Nested
    class getCartsMeProducts {

        @DisplayName("Basic Token을 헤더에 담아 요청하면 Status OK를 반환한다.")
        @Test
        void shouldResponseStatusOkWhenRequestWithBasicToken() throws Exception {
            mockMvc.perform(get("/carts/me/products")
                            .header("Authorization", "Basic ZW1haWxAdGVzdC50ZXN0OjEyMzQ="))
                    .andExpect(status().isOk());
        }

        @DisplayName("Authorization 헤더가 없으면 Status Unauthorized를 반환한다.")
        @Test
        void shouldResponseStatusUnauthorizedWhenRequestWithoutBasicToken() throws Exception {
            mockMvc.perform(get("/carts/me/products"))
                    .andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("POST /carts/me/{productId} 요청 시")
    @Nested
    class postCartsMeProductId {

        @DisplayName("ID와 Basic Token이 올바르면 Status OK를 반환한다.")
        @Test
        void shouldResponseStatusOkWhenRequestWithCorrectIdAndBasicToken() throws Exception {
            mockMvc.perform(post("/carts/me/1")
                            .header("Authorization", "Basic ZW1haWxAdGVzdC50ZXN0OjEyMzQ="))
                    .andExpect(status().isOk());
        }

        @DisplayName("Authorization 헤더가 없으면 Status Unauthorized를 반환한다.")
        @Test
        void shouldResponseStatusUnauthorizedWhenRequestWithoutBasicToken() throws Exception {
            mockMvc.perform(post("/carts/me/1"))
                    .andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("DELETE /carts/me/{productId} 요청 시")
    @Nested
    class deleteCartsMeProductId {

        @DisplayName("ID와 Basic Token이 올바르면 Status OK를 반환한다.")
        @Test
        void shouldResponseStatusOkWhenRequestWithCorrectIdAndBasicToken() throws Exception {
            mockMvc.perform(delete("/carts/me/1")
                            .header("Authorization", "Basic ZW1haWxAdGVzdC50ZXN0OjEyMzQ="))
                    .andExpect(status().isOk());
        }

        @DisplayName("Authorization 헤더가 없으면 Status Unauthorized를 반환한다.")
        @Test
        void shouldResponseStatusUnauthorizedWhenRequestWithoutBasicToken() throws Exception {
            mockMvc.perform(delete("/carts/me/1"))
                    .andExpect(status().isUnauthorized());
        }
    }
}
