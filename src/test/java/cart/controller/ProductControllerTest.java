package cart.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dto.ProductRequest;
import cart.entity.Product;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("상품 정보를 등록한다")
    @Test
    void updateTest() throws Exception {
        ProductRequest request = new ProductRequest("박스터", "https://boxster.com", 10000);
        String requestString = objectMapper.writeValueAsString(request);

        Product product = new Product(1L, "박스터", "https://boxster.com", 10000);
        when(productService.createProduct(any())).thenReturn(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestString))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is("/products/1")))
                .andExpect(jsonPath("$.name", is("박스터")))
                .andExpect(jsonPath("$.imgUrl", is("https://boxster.com")))
                .andExpect(jsonPath("$.price", is(10000)));
    }


    @Test
    public void testUpdateProduct() throws Exception {
        Long productId = 1L;
        ProductRequest productRequest = new ProductRequest("박스터", "https://boxster.com", 10000);
        String requestString = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(put("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestString))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/products/{id}", productId))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProductBy(productId);
    }
}
