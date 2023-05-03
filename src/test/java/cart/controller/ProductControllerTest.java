package cart.controller;

import cart.config.WebConfig;
import cart.dao.MemberDao;
import cart.dto.ProductRequest;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(WebConfig.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private MemberDao memberDao;

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
    void updateProduct() throws Exception {
        final ProductRequest productRequest = new ProductRequest("치킨", "test", 20000);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(put("/products/1")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());
    }
}
