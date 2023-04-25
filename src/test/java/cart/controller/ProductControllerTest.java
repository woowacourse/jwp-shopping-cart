package cart.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
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
    void 상품_조회_테스트() throws Exception {
        final List<ProductResponse> productResponses = List.of(
                new ProductResponse(1, "누누", "naver.com", 1),
                new ProductResponse(2, "오도", "naver.com", 1)
        );
        final String result = objectMapper.writeValueAsString(productResponses);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    void 상품_업데이트_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("누누", "naver.com", 1);
        final String request = objectMapper.writeValueAsString(productRequest);
        final ProductResponse productResponse = new ProductResponse(1L, "누누", "naver.com", 1);
        final String result = objectMapper.writeValueAsString(productResponse);
        final int id = 1;
        mockMvc.perform(put("/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    void 상품_생성_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("오도", "naver.com", 1);
        final String request = objectMapper.writeValueAsString(productRequest);
        final ProductResponse productResponse = new ProductResponse(1, "오도", "naver.com", 1);
        final String result = objectMapper.writeValueAsString(productResponse);
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(content().json(result));
    }
}
