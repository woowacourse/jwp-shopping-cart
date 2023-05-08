package cart.cart.controller;

import cart.cart.service.CartService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CartViewController.class)
public class CartViewControllerMockTest {

    private static final String EMAIL = "rg970604@naver.com";
    private static final String PASSWORD = "password";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    void 장바구니_페이지_조회() throws Exception {
        mockMvc.perform(get("/carts")
                        .accept(ContentType.HTML.toString())
                )
                .andExpect(status().isOk());
    }

    @Test
    void 개별_장바구니_조회() throws Exception {
        mockMvc
                .perform(get("/carts/all").with(SecurityMockMvcRequestPostProcessors.httpBasic(EMAIL, PASSWORD))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void 장바구니_상품_추가() throws Exception {
        mockMvc.perform(post("/carts?productId=1").with(SecurityMockMvcRequestPostProcessors.httpBasic(EMAIL, PASSWORD)))
                .andExpect(status().isCreated());
    }

    @Test
    void 장바구니_상품_삭제() throws Exception {
        mockMvc.perform(delete("/carts/1").with(SecurityMockMvcRequestPostProcessors.httpBasic(EMAIL, PASSWORD))
                        .accept(ContentType.HTML.toString()))
                .andExpect(status().isNoContent());
    }

}
