package cart.controller;

import cart.dto.ProductRequest;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static cart.fixture.ProductFixture.CHICKEN_RESPONSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void 저장_요청() throws Exception {
        ProductRequest request = new ProductRequest("img", "name", 1000);
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/products")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void 올바르지_않은_상품명_저장_요청_시_예외_발생() throws Exception {
        String invalidProductName = "a".repeat(51);
        ProductRequest request = new ProductRequest("img", invalidProductName, 1000);
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/products")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 올바르지_않은_금액_저장_요청_시_예외_발생() throws Exception {
        ProductRequest request = new ProductRequest("img", "name", 1_000_000_001);
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/products")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 올바르지_않은_이미지_저장_요청_시_예외_발생() throws Exception {
        ProductRequest request = new ProductRequest(null, "name", 1_000_000_000);
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/products")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 수정_요청() throws Exception {
        ProductRequest request = new ProductRequest("img", "name", 1000);
        String jsonRequest = objectMapper.writeValueAsString(request);

        when(productService.update(any(), any())).thenReturn(CHICKEN_RESPONSE);

        mockMvc.perform(put("/products/{id}", 1L)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void 삭제_요청() throws Exception {
        mockMvc.perform(delete("/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void 조회_요청() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}
