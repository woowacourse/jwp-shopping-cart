package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.domain.Product;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @DisplayName("상품 목록 조회 - OK, 상품 목록을 반환한다.")
    @Test
    void findProducts() throws Exception {

        final List<Product> expected = List.of(
                new Product(1L, "캠핑 의자", 10000,
                        "https://thawing-fortress-83192.herokuapp.com/static/images/camping-chair.jpg", 10),
                new Product(2L, "그릴", 10000,
                        "https://thawing-fortress-83192.herokuapp.com/static/images/grill.jpg", 10),
                new Product(3L, "아이스박스", 10000,
                        "https://thawing-fortress-83192.herokuapp.com/static/images/icebox.jpg", 10)
        );
        final int expectedCount = expected.size();

        when(productService.findByPage(any()))
                .thenReturn(expected);
        when(productService.findTotalCount())
                .thenReturn(expectedCount);

        mockMvc.perform(get("/api/products")
                        .param("page", "1")
                        .param("limit", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("x-total-count", String.valueOf(expectedCount)))

                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("캠핑 의자"))
                .andExpect(jsonPath("$[0].price").value(10000))
                .andExpect(jsonPath("$[0].imageUrl").value(
                        "https://thawing-fortress-83192.herokuapp.com/static/images/camping-chair.jpg"))
                .andExpect(jsonPath("$[0].stock").value(10))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("그릴"))
                .andExpect(jsonPath("$[1].price").value(10000))
                .andExpect(jsonPath("$[1].imageUrl").value(
                        "https://thawing-fortress-83192.herokuapp.com/static/images/grill.jpg"))
                .andExpect(jsonPath("$[1].stock").value(10));
    }

    @DisplayName("상품 목록 조회 실패 - Bad Request")
    @Test
    void findProductsFail() throws Exception {
        mockMvc.perform(get("/api/products")
                        .param("page", "0")
                        .param("limit", "0"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
