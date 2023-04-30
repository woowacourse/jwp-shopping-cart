package cart.controller;

import cart.entity.Cart;
import cart.repository.CartRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CartRepository cartRepository;

    @Test
    @DisplayName("post 요청을 보냈을 때, 주어진 멤버의 base64 정보와 상품 Id로 장바구니에 상품을 담을 수 있다.")
    void saveCart() throws Exception {
        // given
        Cart cart = new Cart("test@gmail.com", 1L);
        ObjectMapper objectMapper = new ObjectMapper();
        when(cartRepository.save(cart)).thenReturn(cart);

        // when & then
        mockMvc.perform(post("/cart")
                        .header("authorization", httpBasic("test@gmail.com", "test1234!"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(Map.of("product_id", 1L)))
                ).andDo(print())
                .andExpect(status().isCreated());

        verify(cartRepository, times(1)).save(cart);
    }

    private String httpBasic(String email, String password) {
        String format = String.format("%s:%s", email, password);
        return String.format("Basic %s", Base64.getEncoder().encodeToString(format.getBytes()));
    }
}