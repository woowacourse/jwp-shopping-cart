package cart.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dto.ProductCreateRequest;
import cart.dto.ProductDto;
import cart.dto.ProductUpdateRequest;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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

    @Test
    @DisplayName("/products로 GET 요청을 보내면 HTTP 200 코드와 함께 상품이 조회되어야 한다.")
    void findAllProducts_success() throws Exception {
        // given
        List<ProductDto> productDtos = List.of(
                new ProductDto(1, "글렌피딕", 100_000, "image1"),
                new ProductDto(2, "글렌리벳", 200_000, "image2"),
                new ProductDto(3, "글렌모렌지", 300_000, "image3"),
                new ProductDto(4, "글렌드로낙", 400_000, "image4")
        );
        given(productService.findAllProducts())
                .willReturn(productDtos);

        // expect
        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("총 4개의 상품이 조회되었습니다."))
                .andExpect(jsonPath("$.result[0].id").value(1))
                .andExpect(jsonPath("$.result[0].name").value("글렌피딕"))
                .andExpect(jsonPath("$.result[0].price").value(100_000))
                .andExpect(jsonPath("$.result[0].imageUrl").value("image1"))

                .andExpect(jsonPath("$.result[1].id").value(2))
                .andExpect(jsonPath("$.result[1].name").value("글렌리벳"))
                .andExpect(jsonPath("$.result[1].price").value(200_000))
                .andExpect(jsonPath("$.result[1].imageUrl").value("image2"))

                .andExpect(jsonPath("$.result[2].id").value(3))
                .andExpect(jsonPath("$.result[2].name").value("글렌모렌지"))
                .andExpect(jsonPath("$.result[2].price").value(300_000))
                .andExpect(jsonPath("$.result[2].imageUrl").value("image3"))

                .andExpect(jsonPath("$.result[3].id").value(4))
                .andExpect(jsonPath("$.result[3].name").value("글렌드로낙"))
                .andExpect(jsonPath("$.result[3].price").value(400_000))
                .andExpect(jsonPath("$.result[3].imageUrl").value("image4"));
    }
}
