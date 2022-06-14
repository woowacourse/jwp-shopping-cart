package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.ProductResponse;

@WebMvcTest(ProductController.class)
@Import(JwtTokenProvider.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @DisplayName("상품을 조회한다.")
    @Test
    void get() throws Exception {
        // given
        Long productId = 1L;

        // when
        when(productService.findById(any()))
                .thenReturn(new ProductResponse(1L, "apple", 1000, "http://mart/apple"));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("apple"))
                .andExpect(jsonPath("price").value(1000))
                .andExpect(jsonPath("imageUrl").value("http://mart/apple"));
    }

    @DisplayName("특정 페이지의 상품 목록을 조회한다.")
    @Test
    void getProductsOfPage() throws Exception {
        // given
        int page = 2;
        int limit = 3;

        // when
        when(productService.findProductsOfPage(page, limit))
                .thenReturn(List.of(
                        new ProductResponse(4L, "apple", 1000, "http://mart/apple"),
                        new ProductResponse(5L, "peach", 2000, "http://mart/peach"),
                        new ProductResponse(6L, "banana", 1500, "http://mart/bananan")
                ));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/products?page=2&limit=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"id\":4,\"name\":\"apple\",\"price\":1000,\"imageUrl\":\"http://mart/apple\"},"
                                + "{\"id\":5,\"name\":\"peach\",\"price\":2000,\"imageUrl\":\"http://mart/peach\"},"
                                + "{\"id\":6,\"name\":\"banana\",\"price\":1500,\"imageUrl\":\"http://mart/bananan\"}]"
                ));
    }
}
