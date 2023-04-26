package cart.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dto.ProductCreateRequest;
import cart.dto.ProductDto;
import cart.dto.ProductUpdateRequest;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductApiController.class)
class ProductApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;


    @Test
    @DisplayName("/products로 POST 요청과 상품의 정보를 보내면 HTTP 201 코드와 함께 상품이 등록되어야 한다.")
    void createProduct_success() throws Exception {
        // given
        ProductCreateRequest request = new ProductCreateRequest("글렌피딕", 230_000, "url");
        given(productService.createProduct(anyString(), anyInt(), anyString()))
                .willReturn(new ProductDto(1, "글렌피딕", 230_000, "url"));

        // expect
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("201"))
                .andExpect(jsonPath("$.message").value("상품이 생성되었습니다."))
                .andExpect(jsonPath("$.result.id").value(1))
                .andExpect(jsonPath("$.result.name").value("글렌피딕"))
                .andExpect(jsonPath("$.result.price").value(230000))
                .andExpect(jsonPath("$.result.imageUrl").value("url"));
    }

    @Test
    @DisplayName("/products/{id}로 DELETE 요청을 보내면 HTTP 200 코드와 함께 상품이 삭제되어야 한다.")
    void deleteProduct_success() throws Exception {
        // given
        long productId = 1;
        willDoNothing().given(productService).deleteById(anyLong());

        // expect
        mockMvc.perform(delete("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("상품이 삭제되었습니다."));
    }

    @Test
    @DisplayName("/products/{id}로 PATCH 요청과 상품의 정보를 보내면 HTTP 200 코드와 함께 상품이 수정되어야 한다.")
    void updateProduct_success() throws Exception {
        // given
        long productId = 1;
        ProductUpdateRequest request = new ProductUpdateRequest("글렌피딕", 200000, "url");

        given(productService.updateProductById(anyLong(), anyString(), anyInt(), anyString()))
                .willReturn(new ProductDto(productId, "글렌피딕", 200000, "url"));

        // expect
        mockMvc.perform(patch("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("상품이 수정되었습니다."))
                .andExpect(jsonPath("$.result.id").value(1))
                .andExpect(jsonPath("$.result.name").value("글렌피딕"))
                .andExpect(jsonPath("$.result.price").value(200000))
                .andExpect(jsonPath("$.result.imageUrl").value("url"));
    }
}
