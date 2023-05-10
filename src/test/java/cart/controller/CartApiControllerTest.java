package cart.controller;

import cart.dto.ProductResponseDto;
import cart.service.AuthService;
import cart.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartApiController.class)
class CartApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private AuthService authService;

    @DisplayName("GET /cart-products 성공 테스트")
    @Test
    void getCartItemsTest() throws Exception {
        when(cartService.getCartProducts(0)).thenReturn(List.of(
                new ProductResponseDto(1, "image1", "name1", 1000),
                new ProductResponseDto(2, "image2", "name2", 2000)
        ));

        this.mockMvc.perform(get("/cart-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic YUBhLmNvbTpwYXNzd29yZDE="))
                .andExpect(status().isOk());
    }

    @DisplayName("POST /cart/{product} 성공 테스트")
    @Test
    void addProductToCartTest() throws Exception {
        this.mockMvc.perform(post("/cart/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic YUBhLmNvbTpwYXNzd29yZDE="))
                .andExpect(status().isCreated());
    }

    @DisplayName("DELETE /cart/{product} 성공 테스트")
    @Test
    void deleteProductInCartTest() throws Exception {
        this.mockMvc.perform(delete("/cart/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic YUBhLmNvbTpwYXNzd29yZDE="))
                .andExpect(status().isNoContent());
    }

    @DisplayName("올바른 Basic Authorization 헤더값이 들어오지 않을 때 실패한다")
    @Test
    void throwExceptionWhenInvalidAuthorizationHeader() throws Exception {
        this.mockMvc.perform(get("/cart-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic 123456789="))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Basic Authorization 헤더값이 들어오지 않을 때 실패한다")
    @Test
    void throwExceptionWhenNotBasicAuthorizationHeader() throws Exception {
        this.mockMvc.perform(get("/cart-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer 123456789="))
                .andExpect(status().isUnauthorized());
    }
}
