package cart.web.controller.product;

import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import cart.web.controller.admin.AdminViewController;
import cart.web.controller.auth.LoginCheckInterceptor;
import cart.web.controller.auth.LoginUserArgumentResolver;
import cart.web.controller.config.WebConfig;
import cart.web.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {ProductViewController.class, AdminViewController.class},
        excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {
                WebConfig.class, LoginCheckInterceptor.class, LoginUserArgumentResolver.class
        }
))
class ProductViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @DisplayName("상세 페이지를 조회한다")
    @Test
    void getProduct() throws Exception {
        // given
        final Product product = new Product(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN);
        when(productService.getById(any())).thenReturn(product);

        //when
        mockMvc.perform(get("/products/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }
}
