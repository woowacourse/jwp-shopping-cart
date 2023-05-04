package cart.presentation.controller;

import cart.business.service.CartService;
import cart.business.service.ProductService;
import cart.presentation.controller.CartController;
import cart.presentation.dto.CartItemIdDto;
import cart.presentation.dto.ProductIdDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CartService cartService;
    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("/cart로 POST 요청을 보낼 수 있다")
    void test_create_request() throws Exception {
        // given
        willDoNothing().given(cartService).addCartItem(any());

        String content = objectMapper.writeValueAsString(new ProductIdDto(1));

        // when
        mockMvc.perform(post("/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/cart로 GET 요청을 보낼 수 있다")
    void test_read_request() throws Exception {
        // given
        given(cartService.readAllCartItem(any())).willReturn(Collections.emptyList());
        given(productService.readById(any())).willReturn(null);

        // when
        mockMvc.perform(get("/cart"))

                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/cart로 DELETE 요청을 보낼 수 있다")
    void test_delete_request() throws Exception {
        // given
        willDoNothing().given(cartService).removeCartItem(any());
        String content = objectMapper.writeValueAsString(new CartItemIdDto(1));

        // when
        mockMvc.perform(delete("/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

                // then
                .andExpect(status().isOk());
    }
}
