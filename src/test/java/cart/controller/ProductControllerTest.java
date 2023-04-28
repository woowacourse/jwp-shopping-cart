package cart.controller;

import cart.dto.ProductRequest;
import cart.fixture.ImageFixture;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cart.fixture.ProductFixture.CHICKEN_RESPONSE;
import static cart.fixture.ProductFixture.SNACK_RESPONSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        ProductRequest request = new ProductRequest(ImageFixture.url, "name", 1000);
        String jsonRequest = objectMapper.writeValueAsString(request);

        when(productService.findAll())
                .thenReturn(List.of(SNACK_RESPONSE));

        mockMvc.perform(post("/products")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void 수정_요청() throws Exception {
        ProductRequest request = new ProductRequest(ImageFixture.url, "name", 1000);
        String jsonRequest = objectMapper.writeValueAsString(request);

        when(productService.update(any(), any())).thenReturn(CHICKEN_RESPONSE);

        mockMvc.perform(put("/products/{id}", 1L)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 삭제_요청() throws Exception {
        mockMvc.perform(delete("/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 조회_요청() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
