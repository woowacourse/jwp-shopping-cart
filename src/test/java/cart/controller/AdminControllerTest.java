package cart.controller;

import cart.controller.dto.ProductDto;
import cart.persistence.entity.ProductCategory;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @DisplayName("어드민 상품 리스트 페이지를 조회한다")
    @Test
    void getProducts() throws Exception {
        // given
        final List<ProductDto> productDtos = List.of(
                new ProductDto(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN),
                new ProductDto(2L, "초밥", "chobobUrl", 30000, ProductCategory.JAPANESE),
                new ProductDto(3L, "스테이크", "steakUrl", 40000, ProductCategory.WESTERN)
        );
        when(productService.getProducts()).thenReturn(productDtos);

        // when, then
        mockMvc.perform(get("/admin")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @DisplayName("상품 정보를 추가한다")
    @Test
    void addProduct() throws Exception {
        // given
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN);
        when(productService.save(any())).thenReturn(1L);

        // when, then
        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/1"));
    }

    @DisplayName("상품 정보를 추가 시 잘못된 정보 형식으로 들어오면 예외가 발생한다")
    @Test
    void addProduct_fail() throws Exception {
        // given
        final ProductDto productDto = new ProductDto(1L, "", "", null, null);

        // when, then
        mockMvc.perform(post("/admin")
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

    @DisplayName("상품 정보를 수정한다")
    @Test
    void updateProduct() throws Exception {
        // given
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN);
        doNothing().when(productService).update(any(), any());

        // when, then
        mockMvc.perform(put("/admin/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(status().isNoContent());
    }

    @DisplayName("상품 수정 시 잘못된 정보 형식으로 들어오면 예외가 발생한다")
    @Test
    void updateProduct_fail() throws Exception {
        // given
        final ProductDto productDto = new ProductDto(1L, "", "", null, null);

        // when, then
        mockMvc.perform(put("/admin/{id}", 1L)
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

    @DisplayName("상품 정보를 삭제한다")
    @Test
    void deleteProduct() throws Exception {
        // given
        doNothing().when(productService).delete(any());

        // when, then
        mockMvc.perform(delete("/admin/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isNoContent());
    }
}
