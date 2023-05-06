package cart.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.helper.ControllerTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CartController.class)
class CartControllerTest extends ControllerTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("장바구니 페이지를 조회한다")
    @Test
    void getProducts() throws Exception {
        mockMvc.perform(get("/cart")
                .contentType(MediaType.TEXT_HTML))
            .andExpect(status().isOk());
    }
}
