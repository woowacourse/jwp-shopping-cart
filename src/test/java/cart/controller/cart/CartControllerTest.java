package cart.controller.cart;

import cart.config.auth.Base64AuthInterceptor;
import cart.service.CartService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import java.util.Collections;

import static cart.config.admin.Base64AdminAccessInterceptor.ADMIN_EMAIL;
import static cart.config.admin.Base64AdminAccessInterceptor.ADMIN_NAME;
import static cart.config.auth.Base64AuthInterceptor.BASIC;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(CartController.class)
public class CartControllerTest {

    public static final String ADMIN = ADMIN_EMAIL + ":" + ADMIN_NAME;
    public static final String ADMIN_CREDENTIALS = BASIC + " " + Base64Utils.encodeToString(ADMIN.getBytes());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    void 회원_장바구니를_조회한다() throws Exception {
        given(cartService.findAllCartProductByEmail(any()))
                .willReturn(Collections.emptyList());

        mockMvc.perform(get("/carts")
                        .header(Base64AuthInterceptor.AUTHORIZATION_HEADER, ADMIN_CREDENTIALS))
                .andExpect(status().isOk());
    }

    @Test
    void 회원_장바구니에_상품을_추가한다() throws Exception {
        final Long productId = 10L;
        final Long cartId = 2L;
        given(cartService.addProductInCart(anyLong(), anyString()))
                .willReturn(cartId);

        mockMvc.perform(post("/carts/{product_id}", productId)
                        .header(Base64AuthInterceptor.AUTHORIZATION_HEADER, ADMIN_CREDENTIALS))
                .andExpect(header().exists("location"))
                .andExpect(header().string("location", "/carts/" + cartId));
    }

    @Test
    void 회원_장바구니에_상품을_삭제한다() throws Exception {
        doNothing()
                .when(cartService).deleteProductInCart(anyLong(), anyString());

        mockMvc.perform(delete("/carts/{product_id}", 10L)
                        .header(Base64AuthInterceptor.AUTHORIZATION_HEADER, ADMIN_CREDENTIALS))
                .andExpect(status().isNoContent());
    }
}
