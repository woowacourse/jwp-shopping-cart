package cart.cart.controller;

import cart.cart.service.CartService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CartViewController.class)
public class CartViewControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    void 장바구니_페이지_조회() throws Exception {
        mockMvc.perform(get("/cart")
                        .accept(ContentType.HTML.toString())
                )
                .andExpect(status().isOk());
    }

}
