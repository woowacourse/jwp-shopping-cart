package cart.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.domain.product.service.AdminService;
import cart.domain.product.service.dto.ProductDto;
import cart.web.controller.dto.request.ProductCreationRequest;
import cart.web.controller.dto.request.ProductModificationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminRestController.class)
class AdminRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @DisplayName("POST 요청시, 상품을 등록할 수 있다.")
    @Test
    void postProduct() throws Exception {
        String productName = "ProductA";
        int productPrice = 18_000;
        ProductCreationRequest request =
                new ProductCreationRequest(productName, productPrice, "FOOD", "image.com");
        when(adminService.save(any()))
                .thenReturn(1L);

        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(request)))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(productName))
                .andExpect(jsonPath("$.price").value(productPrice));
    }

    @DisplayName("Product id로 상품을 삭제 할 수 있다.")
    @Test
    void deleteProduct() throws Exception {
        doNothing().when(adminService).delete(anyLong());

        mockMvc.perform(delete("/admin/{deletedId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deletedId").value(1L));
    }

    @DisplayName("Product를 수정할 수 있다.")
    @Test
    void updateProduct() throws Exception {
        ProductModificationRequest request =
                new ProductModificationRequest(1L, "Chicken", 18_000, "FOOD", "image.com");
        ProductDto response =
                new ProductDto(1L, "Chicken", 18_000, "FOOD", "image.com");
        when(adminService.update(any())).thenReturn(response);

        mockMvc.perform(put("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chicken"));
    }
}
