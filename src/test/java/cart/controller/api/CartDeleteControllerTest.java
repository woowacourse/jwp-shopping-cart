package cart.controller.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.AbstractProductControllerTest;
import org.junit.jupiter.api.Test;

class CartDeleteControllerTest extends AbstractProductControllerTest {

    @Test
    void 장바구니_삭제_테스트() throws Exception {
        mockMvc.perform(delete("/carts/1"))
                
                .andExpect(status().isNoContent());
    }
}
