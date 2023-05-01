package cart.controller;

import cart.domain.product.ProductCategory;
import cart.domain.product.ProductService;
import cart.web.controller.product.ProductViewController;
import cart.web.controller.product.dto.ProductRequest;
import cart.web.controller.product.dto.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductViewController.class)
class ProductViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @DisplayName("사용자 - ")
    @Nested
    class Customer {
        @DisplayName("메인 페이지를 조회한다")
        @Test
        void index() throws Exception {
            // given
            final List<ProductResponse> productRequests = List.of(
                    new ProductResponse(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN),
                    new ProductResponse(2L, "초밥", "chobobUrl", 30000, ProductCategory.JAPANESE),
                    new ProductResponse(3L, "스테이크", "steakUrl", 40000, ProductCategory.WESTERN)
            );
            when(productService.getProducts()).thenReturn(productRequests);

            // when, then
            mockMvc.perform(get("/")
                            .contentType(MediaType.TEXT_HTML))
                    .andExpect(status().isOk());
        }

        @DisplayName("상세 페이지를 조회한다")
        @Test
        void getProduct() throws Exception {
            // given
            final ProductRequest productRequest = new ProductRequest("치킨", "chickenUrl", 20000, ProductCategory.KOREAN);
            when(productService.getById(any())).thenReturn(productRequest);

            //when
            mockMvc.perform(get("/products/{id}", 1L)
                            .contentType(MediaType.TEXT_HTML))
                    .andExpect(status().isOk());
        }
    }

    @DisplayName("관리자 - ")
    @Nested
    class Admin {
        @DisplayName("상품 목록을 조회한다")
        @Test
        void getProducts() throws Exception {
            // given
            final List<ProductResponse> productRequests = List.of(
                    new ProductResponse(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN),
                    new ProductResponse(2L, "초밥", "chobobUrl", 30000, ProductCategory.JAPANESE),
                    new ProductResponse(3L, "스테이크", "steakUrl", 40000, ProductCategory.WESTERN)
            );
            when(productService.getProducts()).thenReturn(productRequests);

            // when, then
            mockMvc.perform(get("/admin"))
                    .andExpect(status().isOk());
        }

    }
}