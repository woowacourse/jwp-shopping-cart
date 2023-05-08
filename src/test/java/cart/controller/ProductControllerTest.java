package cart.controller;

import cart.dto.ProductRequest;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("POST /product")
    void createProduct() throws Exception {
        final ProductRequest productRequest = new ProductRequest("이오", 1000, null);
        final String request = objectMapper.writeValueAsString(productRequest);
        given(productService.save("이오", 1000, null)).willReturn(1L);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/product/1"));

        verify(productService, times(1)).save(anyString(), anyInt(), any());
    }

    @Test
    @DisplayName("PUT /product/{id}")
    void updateProduct() throws Exception {
        final ProductRequest productRequest = new ProductRequest("애쉬", 2000, "image");
        final String request = objectMapper.writeValueAsString(productRequest);
        final int id = 1;
        given(productService.update(anyLong(), anyString(), anyInt(), anyString())).willReturn(1L);

        mockMvc.perform(put("/product/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/product/1"));

        verify(productService, times(1)).update(anyLong(), anyString(), anyInt(), any());
    }


    @Test
    @DisplayName("DELETE /product/{id}")
    void deleteProduct() throws Exception {
        doNothing().when(productService).delete(anyLong());
        final int id = 1;

        mockMvc.perform(delete("/product/" + id))
                .andExpect(status().isOk());

        verify(productService, times(1)).delete(anyLong());
    }
}
