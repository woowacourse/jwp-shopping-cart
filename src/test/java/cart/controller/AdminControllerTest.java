package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductManagementService productManagementService;

    @Test
    void 상품을_추가한다() throws Exception {
        final ProductDto productDto = new ProductDto("하디", "imageUrl", 100000);
        final String requestBody = objectMapper.writeValueAsString(productDto);
        given(productManagementService.addProduct(any()))
                .willReturn(anyLong());

        mockMvc.perform(post("/admin/products")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void 상품을_업데이트한다() throws Exception {
        final Long id = 1L;
        final ProductDto updatedProductDto = new ProductDto("코코닥", "imageUrl", 15000);
        final String requestBody = objectMapper.writeValueAsString(updatedProductDto);
        doNothing()
                .when(productManagementService)
                .updateProduct(any());

        mockMvc.perform(put("/admin/products/{product_id}", id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    void 상품을_삭제한다() throws Exception {
        final Long id = 1L;

        doNothing()
                .when(productManagementService)
                .deleteProduct(anyLong());

        mockMvc.perform(delete("/admin/products/{product_id}", id))
                .andExpect(status().isNoContent());
    }
}
