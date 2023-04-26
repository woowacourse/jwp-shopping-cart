package cart.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dto.ProductRequest;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
class ProductRequestControllerTest extends AbstractProductControllerTest {

    @Test
    void 상품_이름_길이_초과_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("a".repeat(256), "naver.com", 1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }

    @Test
    void 상품_이름_공백_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("", "naver.com", 1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }

    @Test
    void 이미지_url_길이_초과_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("누누", "a".repeat(2049), 1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }

    @Test
    void 이미지_url_공백_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("누누", "", 1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }

    @Test
    void 상품_가격_음수_예외_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("누누", "naver.com", -1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }
}
