package cart.cart.controller;

import cart.cart.service.CartService;
import cart.global.infrastructure.AuthorizationExtractor;
import cart.member.dto.AuthInfo;
import cart.member.service.MemberService;
import cart.product.service.ProductService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class CartControllerMockTest {

    private static final String EMAIL = "rg970604@naver.com";
    private static final String PASSWORD = "password";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private ProductService productService;

    @MockBean
    private AuthorizationExtractor authorizationExtractor;

    @Test
    void 장바구니_페이지_조회() throws Exception {
        mockMvc.perform(get("/cart")
                        .accept(ContentType.HTML.toString())
                )
                .andExpect(status().isOk());
    }

    @Test
    void 개별_장바구니_조회() throws Exception {
        AuthInfo authInfo = new AuthInfo(EMAIL, PASSWORD);
        when(authorizationExtractor.extract(any())).thenReturn(authInfo);
        mockMvc
                .perform(get("/cart/all").with(SecurityMockMvcRequestPostProcessors.httpBasic(EMAIL, PASSWORD))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void 장바구니_상품_추가() throws Exception {
        AuthInfo authInfo = new AuthInfo(EMAIL, PASSWORD);
        when(authorizationExtractor.extract(any())).thenReturn(authInfo);

        mockMvc.perform(post("/cart?productId=1").with(SecurityMockMvcRequestPostProcessors.httpBasic(EMAIL, PASSWORD)))
                .andExpect(status().isCreated());
    }

    @Test
    void 장바구니_상품_삭제() throws Exception {
        AuthInfo authInfo = new AuthInfo(EMAIL, PASSWORD);
        when(authorizationExtractor.extract(any())).thenReturn(authInfo);

        mockMvc.perform(delete("/cart/1").with(SecurityMockMvcRequestPostProcessors.httpBasic(EMAIL, PASSWORD))
                        .accept(ContentType.HTML.toString()))
                .andExpect(status().isNoContent());
    }

}
