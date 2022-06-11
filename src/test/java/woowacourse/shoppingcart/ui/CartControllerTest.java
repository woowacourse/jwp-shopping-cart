package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.exception.AuthorizationException;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.request.CartItemRequest;

@WebMvcTest(CartController.class)
class CartControllerTest {

    private static final String TOKEN = "access_token";
    private static final Long MEMBER_ID = 1L;

    @MockBean
    private CartService cartService;
    @MockBean
    private HandlerInterceptor handlerInterceptor;
    @MockBean
    private HandlerMethodArgumentResolver handlerMethodArgumentResolver;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("장바구니에 담긴 상품 목록을 모두 조회한다. - 200 OK")
    @Test
    void showAll_Ok() throws Exception {
        given(cartService.findAll(MEMBER_ID))
                .willReturn(List.of());
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.supportsParameter(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(MEMBER_ID);

        mockMvc.perform(get("/api/carts")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());
        verify(cartService, times(1))
                .findAll(MEMBER_ID);
    }

    @DisplayName("인증 없이 장바구니를 조회할 수 없다. - 401 Unauthorized")
    @Test
    void showAllWithoutAuth_Unauthorized() throws Exception {
        given(cartService.findAll(MEMBER_ID))
                .willReturn(List.of());
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willThrow(new AuthorizationException("로그인이 필요합니다."));

        mockMvc.perform(get("/api/carts"))
                .andExpect(status().isUnauthorized());
        verify(cartService, times(0))
                .findAll(MEMBER_ID);
    }

    @DisplayName("장바구니에 상품을 담는다. - 201 Created")
    @Test
    void addCartItem_Ok() throws Exception {
        given(cartService.addCartItem(eq(MEMBER_ID), any()))
                .willReturn(1L);
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.supportsParameter(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(MEMBER_ID);

        CartItemRequest requestBody = new CartItemRequest(1L, 1);

        mockMvc.perform(post("/api/carts/products")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated());
        verify(cartService, times(1))
                .addCartItem(eq(MEMBER_ID), any());
    }

    @DisplayName("장바구니에 담긴 상품의 수량을 변경하고 변경된 장바구니를 조회한다. - 200 Ok")
    @Test
    void updateCartItemQuantityAndShowCart_Ok() throws Exception {
        willDoNothing().given(cartService)
                .updateCartItemQuantity(eq(MEMBER_ID), any());
        given(cartService.findAll(MEMBER_ID))
                .willReturn(List.of());
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.supportsParameter(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(MEMBER_ID);

        CartItemRequest requestBody = new CartItemRequest(1L, 10);

        mockMvc.perform(patch("/api/carts/products")
                        .header("Authorization", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
        verify(cartService, times(1))
                .updateCartItemQuantity(eq(MEMBER_ID), any());
        verify(cartService, times(1))
                .findAll(MEMBER_ID);
    }

    @DisplayName("상품을 장바구니에서 제거하고 변경된 장바구니를 조회한다. - 200 Ok")
    @Test
    void deleteCartItemAndShowCart_Ok() throws Exception {
        willDoNothing().given(cartService)
                .deleteCartItem(eq(MEMBER_ID), any());
        given(cartService.findAll(MEMBER_ID))
                .willReturn(List.of());
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.supportsParameter(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(MEMBER_ID);

        mockMvc.perform(delete("/api/carts/products?productId=1")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());
        verify(cartService, times(1))
                .deleteCartItem(eq(MEMBER_ID), any());
        verify(cartService, times(1))
                .findAll(MEMBER_ID);
    }
}
