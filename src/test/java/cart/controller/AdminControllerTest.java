package cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dto.CreateProductRequest;
import cart.dto.ProductDto;
import cart.dto.UpdateProductRequest;
import cart.service.ProductManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    void 어드민_페이지를_조회한다() throws Exception {
        List<ProductDto> products = new ArrayList<>();
        given(productManagementService.findAllProduct())
                .willReturn(products);

        mockMvc.perform(get("/admin"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", products))
                .andExpect(status().isOk());
    }

    @Test
    void 상품을_추가한다() throws Exception {
        final CreateProductRequest request = new CreateProductRequest("하디", "imageUrl", 100000);
        final String requestBody = objectMapper.writeValueAsString(request);
        doNothing()
                .when(productManagementService)
                .addProduct(any());

        mockMvc.perform(post("/admin/products")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void 상품을_업데이트한다() throws Exception {
        final Long id = 1L;
        final UpdateProductRequest request = new UpdateProductRequest("코코닥", "imageUrl", 15000);
        final String requestBody = objectMapper.writeValueAsString(request);
        doNothing()
                .when(productManagementService)
                .updateProduct(any(), any());

        mockMvc.perform(patch("/admin/products/{product_id}", id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void 상품을_삭제한다() throws Exception {
        final Long id = 1L;

        doNothing()
                .when(productManagementService)
                .deleteProduct(anyLong());

        mockMvc.perform(delete("/admin/products/{product_id}", id))
                .andExpect(status().isOk());
    }
}
