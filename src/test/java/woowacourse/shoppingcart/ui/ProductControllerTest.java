package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.request.ProductRequest;
import woowacourse.shoppingcart.dto.response.ProductResponse;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    private ProductService productService;
    @MockBean
    private HandlerInterceptor handlerInterceptor;
    @MockBean
    private HandlerMethodArgumentResolver handlerMethodArgumentResolver;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("조회할 페이지와 페이지 당 상품의 숫자로 상품을 조회한다. - 200 Ok")
    @Test
    void showSelectedPage_Ok() throws Exception {
        List<ProductResponse> responses = List.of(
                new ProductResponse(1L, "아이스크림", 1_000, 1, "www.test1.com"),
                new ProductResponse(2L, "초콜렛", 1_000, 2, "www.test2.com"),
                new ProductResponse(3L, "사탕", 500, 3, "www.test3.com")
        );

        given(productService.findProducts(anyInt(), anyInt()))
                .willReturn(responses);
        given(productService.countAll())
                .willReturn(responses.size());

        mockMvc.perform(get("/api/products?page=1&limit=10"))
                .andExpect(status().isOk());
        verify(productService, times(1))
                .findProducts(1, 10);
        verify(productService, times(1))
                .countAll();
    }

    @DisplayName("상품을 등록한다. - 200 Ok")
    @Test
    void add_Ok() throws Exception {
        ProductRequest productRequest = new ProductRequest("아이스크림", 1_000, 1, "www.test.com");

        given(productService.save(any()))
                .willReturn(1L);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated());
        verify(productService, times(1))
                .save(any());
    }
}
