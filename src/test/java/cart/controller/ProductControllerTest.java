package cart.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.dto.RequestFixture;
import cart.dto.ResponseFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 상품_생성_테스트() throws Exception {
        final ProductRequest productRequest = RequestFixture.NUNU_REQUEST;
        final String request = objectMapper.writeValueAsString(productRequest);
        final ProductResponse productResponse = ResponseFixture.NUNU_RESPONSE;
        final String result = objectMapper.writeValueAsString(productResponse);
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(content().json(result));
    }

    @Test
    void 상품_삭제_테스트() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }
}
