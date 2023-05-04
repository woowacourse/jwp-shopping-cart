package cart.presentation.controller;

import cart.business.service.CartService;
import cart.business.service.MemberService;
import cart.business.service.ProductService;
import cart.presentation.dto.CartItemIdDto;
import cart.presentation.dto.ProductIdDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

    private static final String EMAIL = "dntjd991223@naver.com";
    private static final String PASSWORD = "1234";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CartService cartService;
    @MockBean
    private ProductService productService;
    @MockBean
    private MemberService memberService;

    private static String auth;
    private static String encodedAuth;

    @BeforeAll
    static void setup() {
        auth = EMAIL + ":" + PASSWORD;
        encodedAuth = "Basic " + new String(Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    @DisplayName("/carts로 POST 요청을 보낼 수 있다")
    void test_create_request() throws Exception {
        // given
        String content = objectMapper.writeValueAsString(new ProductIdDto(1));

        willDoNothing().given(cartService).addCartItem(any());
        willDoNothing().given(memberService).validateExists(any());

        // when
        mockMvc.perform(post("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .header("Authorization", encodedAuth))

                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/carts로 GET 요청을 보낼 수 있다")
    void test_read_request() throws Exception {
        // given
        given(cartService.readAllCartItem(any())).willReturn(Collections.emptyList());
        given(productService.readById(any())).willReturn(null);

        willDoNothing().given(memberService).validateExists(any());

        // when
        mockMvc.perform(get("/carts")
                        .header("Authorization", encodedAuth))

                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/carts로 DELETE 요청을 보낼 수 있다")
    void test_delete_request() throws Exception {
        // given
        String content = objectMapper.writeValueAsString(new CartItemIdDto(1));

        willDoNothing().given(cartService).removeCartItem(any());
        willDoNothing().given(memberService).validateExists(any());

        // when
        mockMvc.perform(delete("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .header("Authorization", encodedAuth))

                // then
                .andExpect(status().isOk());
    }
}
