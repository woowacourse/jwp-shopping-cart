package cart.web.controller.admin;

import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import cart.domain.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminViewController.class)
class AdminViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() throws Exception {
        // given
        final List<Product> products = List.of(
                new Product(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN),
                new Product(2L, "초밥", "chobobUrl", 30000, ProductCategory.JAPANESE),
                new Product(3L, "스테이크", "steakUrl", 40000, ProductCategory.WESTERN)
        );
        when(productService.getProducts()).thenReturn(products);

        // when, then
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

}