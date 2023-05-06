package cart.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.web.config.auth.BasicAuthorizedUserArgumentResolver;
import cart.web.controller.index.CartViewController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@MockBean(BasicAuthorizedUserArgumentResolver.class)
@WebMvcTest(CartViewController.class)
class CartViewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("/cart 경로 요청 시, cart.html을 반환한다.")
    @Test
    void loadCartPage() throws Exception {

        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andDo(print());
    }
}
