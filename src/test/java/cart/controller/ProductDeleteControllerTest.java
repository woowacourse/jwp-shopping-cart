package cart.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest
class ProductDeleteControllerTest extends AbstractProductControllerTest {

    @Test
    void 상품_삭제_테스트() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }
}
