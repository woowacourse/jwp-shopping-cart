package cart.presentation.controller;

import cart.business.service.ProductService;
import cart.business.domain.product.Product;
import cart.presentation.controller.ProductController;
import cart.presentation.dto.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName("/product 로 POST 요청을 보낼 수 있다")
    void test_create_request() throws Exception {
        // given
        willDoNothing().given(productService).create(any(Product.class));

        String content = objectMapper.writeValueAsString(
                new ProductDto(1, "teo", "https://", 1));

        // when
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/product 로 GET 요청을 보낼 수 있다")
    void test_read_request() throws Exception {
        // given
        given(productService.readAll()).willReturn(Collections.emptyList());

        // when
        mockMvc.perform(get("/product"))

                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/product 로 POST 요청을 보낼 수 있다")
    void test_update_request() throws Exception {
        // given
        willDoNothing().given(productService).update(any(Product.class));

        String content = objectMapper.writeValueAsString(
                new ProductDto(1, "teo", "https://", 1)
        );

        // when
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

                // then
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("/product 로 DELETE 요청을 보낼 수 있다")
    void test_delete_request() throws Exception {
        // given
        willDoNothing().given(productService).delete(any(Integer.class));

        String content = objectMapper.writeValueAsString(
                new ProductDto(1, "teo", "https://", 1)
        );

        // when
        mockMvc.perform(delete("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))

                // then
                .andExpect(status().isOk());
    }
}
