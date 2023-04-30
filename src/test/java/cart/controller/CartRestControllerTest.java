package cart.controller;

import cart.common.annotation.MemberEmailArgumentResolver;
import cart.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartRestController.class)
class CartRestControllerTest {

    private String authorization;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private MemberEmailArgumentResolver memberEmailArgumentResolver;

    @BeforeEach
    void setUp() throws Exception {
        authorization = "Basic am91cm5leUBnbWFpbC5jb206cGFzc3dvcmQ=";
        when(memberEmailArgumentResolver.supportsParameter(any())).thenReturn(true);
        when(memberEmailArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .thenReturn("journey@gmail.com");
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addCart() throws Exception {
        // given
        when(cartService.addCart(any(), any())).thenReturn(1L);

        // when, then
        mockMvc.perform(post("/cart/{productId}", 1L)
                        .header("Authorization", authorization)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated(),
                        header().string("Location", "/cart/me"));
    }
}
