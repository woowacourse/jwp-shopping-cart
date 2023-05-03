package cart.controller;

import cart.dto.ProductRequest;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @DisplayName("POST /product")
    @Test
    void createProduct() throws Exception {
        final ProductRequest productRequest = new ProductRequest("이오", 1000, null);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }

    @DisplayName("PUT /product/{id}")
    @Test
    void updateProduct() throws Exception {
        doNothing().when(productService).update(anyLong(), anyString(), anyInt(), anyString());
        final ProductRequest productRequest = new ProductRequest("애쉬", 2000, "image");
        final String request = objectMapper.writeValueAsString(productRequest);
        final int id = 1;

        mockMvc.perform(put("/product/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }

    @DisplayName("DELETE /product/{id}")
    @Test
    void deleteProduct() throws Exception {
        doNothing().when(productService).delete(anyLong());
        final int id = 1;

        mockMvc.perform(delete("/product/" + id))
                .andExpect(status().isOk());
    }
}
