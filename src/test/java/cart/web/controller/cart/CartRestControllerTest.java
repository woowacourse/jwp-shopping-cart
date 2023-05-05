package cart.web.controller.cart;

import cart.domain.cart.CartProduct;
import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import cart.web.controller.auth.LoginCheckInterceptor;
import cart.web.controller.auth.LoginUserArgumentResolver;
import cart.web.controller.config.WebConfig;
import cart.web.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CartRestController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        WebConfig.class, LoginCheckInterceptor.class, LoginUserArgumentResolver.class
                }
        )
)
class CartRestControllerTest {

    private static final String COOKIE_VALUE = "Basic : YUBhLmNvbTpwYXNzd29yZDE=";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @DisplayName("카트에 상품을 추가한다.")
    @Test
    void addProduct() throws Exception {
        // given
        when(cartService.add(any(), any())).thenReturn(1L);

        // when, then
        mockMvc.perform(post("/cart/1").header("Authorization", COOKIE_VALUE))
                .andExpect(status().isCreated());
    }

    @DisplayName("카트에서 상품을 제거한다.")
    @Test
    void deleteProduct() throws Exception {
        // given
        doNothing().when(cartService).delete(any(), any());

        // when, then
        mockMvc.perform(delete("/cart/1").header("Authorization", COOKIE_VALUE))
                .andExpect(status().isNoContent());
    }

    @DisplayName("카트의 모든 상품을 조회한다.")
    @Test
    void getProducts() throws Exception {
        // given
        final List<Product> products = List.of(
                new Product(1L, "치킨", "chickenUrl", 30000, ProductCategory.KOREAN),
                new Product(2L, "초밥", "chobobUrl", 20000, ProductCategory.JAPANESE)
        );
        final List<CartProduct> cartProducts = List.of(
                new CartProduct(1L, products.get(0)),
                new CartProduct(2L, products.get(1))
        );

        when(cartService.getCartProducts(any())).thenReturn(cartProducts);

        // when
        mockMvc.perform(get("/cart/all").header("Authorization", COOKIE_VALUE))
                .andExpect(status().isOk());
    }
}