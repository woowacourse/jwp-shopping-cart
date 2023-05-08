package cart.controller.view;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.AbstractProductControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class CartViewControllerTest extends AbstractProductControllerTest {

    @Test
    void 장바구니_조회_테스트() throws Exception {
        mockMvc.perform(get("/cart"))

                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}
