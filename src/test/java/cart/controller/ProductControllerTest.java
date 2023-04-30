package cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.dto.ProductRequest;
import cart.controller.dto.ProductResponse;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void addProduct() throws Exception {
        final ProductRequest productRequest = new ProductRequest("치킨", "test", 20000);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void addProductWithInvalidName() throws Exception {
        final ProductRequest productRequest = new ProductRequest("  ", "test", 20000);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("상품명은 공백일 수 없습니다."));
    }

    @Test
    void addProductWithInvalidPrice() throws Exception {
        final ProductRequest productRequest = new ProductRequest("치킨", "test", -20000);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("가격은 0보다 커야 합니다."));
    }

    @Test
    void findById() throws Exception {
        given(productService.findProductById(1L)).willReturn(
                new ProductResponse(1L, "피자", "test.url", 20000));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("피자"))
                .andExpect(jsonPath("$.price").value(20000))
                .andExpect(jsonPath("$.imageUrl").value("test.url"));
    }

    @Test
    void updateProduct() throws Exception {
        final ProductRequest productRequest = new ProductRequest("피자", "test.url", 20000);
        final String request = objectMapper.writeValueAsString(productRequest);
        given(productService.updateProduct(any(Long.class), any(ProductRequest.class))).willReturn(
                new ProductResponse(1L, "피자", "test.url", 20000));

        mockMvc.perform(put("/products/1")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("피자"))
                .andExpect(jsonPath("$.price").value(20000))
                .andExpect(jsonPath("$.imageUrl").value("test.url"));
    }

    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }
}
