package cart.web.controller.product;

import cart.domain.product.ProductCategory;
import cart.domain.product.ProductService;
import cart.web.controller.product.dto.ProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    private ProductRequest productRequest;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRequest = new ProductRequest("치킨", "chickenUrl", 20000, ProductCategory.KOREAN);
    }

    @DisplayName("상품을 정상적으로 추가한다")
    @Test
    void addProduct_success() throws Exception {
        // given
        when(productService.save(any())).thenReturn(1L);

        // when, then
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(status().isCreated());
    }

    @DisplayName("상품의 정보를 정상적으로 입력하지 않으면 예외가 발생한다")
    @Test
    void addProduct_fail() throws Exception {
        // given
        final ProductRequest productRequest = new ProductRequest("", "", null, null);

        // when, then
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.containsString("상품 이름은 비어있을 수 없습니다.")))
                .andExpect(jsonPath("$.message", Matchers.containsString( "상품 가격은 비어있을 수 없습니다.")))
                .andExpect(jsonPath("$.message", Matchers.containsString("상품 카테고리는 비어있을 수 없습니다.")));
    }


    @DisplayName("상품을 정상적으로 수정한다")
    @Test
    void updateProduct_success() throws Exception {
        // given
        doNothing().when(productService).update(any(), any());

        // when, then
        mockMvc.perform(put("/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(status().isNoContent());
    }

    @DisplayName("수정할 상품의 정보를 잘못 입력하면 예외가 발생한다")
    @Test
    void updateProduct_fail() throws Exception {
        // given
        final ProductRequest productRequest = new ProductRequest("", "", null, null);

        // when, then
        mockMvc.perform(put("/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.containsString("상품 이름은 비어있을 수 없습니다.")))
                .andExpect(jsonPath("$.message", Matchers.containsString( "상품 가격은 비어있을 수 없습니다.")))
                .andExpect(jsonPath("$.message", Matchers.containsString("상품 카테고리는 비어있을 수 없습니다.")));
    }

    @DisplayName("상품을 정상적으로 삭제한다")
    @Test
    void deleteProduct_success() throws Exception {
        // given
        doNothing().when(productService).delete(any());

        // when, then
        mockMvc.perform(delete("/products/{productId}", 1L))
                .andExpect(status().isNoContent());
    }
}
