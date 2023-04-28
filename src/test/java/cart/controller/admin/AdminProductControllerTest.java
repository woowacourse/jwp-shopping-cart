package cart.controller.admin;

import cart.controller.dto.ProductDto;
import cart.persistence.entity.ProductCategory;
import cart.service.ShoppingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminProductController.class)
class AdminProductControllerTest {

    private ProductDto productDto;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShoppingService shoppingService;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN);
    }


    @Test
    void addProduct() throws Exception {
        // given
//        doNothing().when(shoppingService).save(any());
        when(shoppingService.save(any())).thenReturn(1L);

        // when, then
        mockMvc.perform(post("/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void addProduct_fail() throws Exception {
        final ProductDto productDto = new ProductDto(1L, "", "", null, null);
        mockMvc.perform(post("/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage",
                        containsInAnyOrder(
                                "상품 이름의 길이는 1 ~ 25글자여야 합니다.",
                                "상품 가격은 비어있을 수 없습니다.",
                                "상품 카테고리는 비어있을 수 없습니다."
                        )
                ));
    }


    @Test
    void updateProduct() throws Exception {
        // given
        doNothing().when(shoppingService).update(any(), any());

        // when, then
        mockMvc.perform(put("/admin/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void updateProduct_fail() throws Exception {
        final ProductDto productDto = new ProductDto(1L, "", "", null, null);
        mockMvc.perform(put("/admin/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage",
                        containsInAnyOrder(
                                "상품 이름의 길이는 1 ~ 25글자여야 합니다.",
                                "상품 가격은 비어있을 수 없습니다.",
                                "상품 카테고리는 비어있을 수 없습니다."
                        )
                ));
    }

    @Test
    void deleteProduct() throws Exception {
        // given
        doNothing().when(shoppingService).delete(any());

        // when, then
        mockMvc.perform(delete("/admin/products/{productId}", 1L))
                .andExpect(status().isNoContent());
    }
}
