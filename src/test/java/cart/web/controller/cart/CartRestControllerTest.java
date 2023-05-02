package cart.web.controller.cart;

import cart.domain.cart.CartService;
import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartRestController.class)
class CartRestControllerTest {

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
        mockMvc.perform(post("/cart/1"))
                .andExpect(status().isCreated());
    }

    @DisplayName("카트에서 상품을 제거한다.")
    @Test
    void deleteProduct() throws Exception {
        // given
        doNothing().when(cartService).delete(any(), any());

        // when, then
        mockMvc.perform(delete("/cart/1"))
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
        when(cartService.getProducts(any())).thenReturn(products);

        // when
        mockMvc.perform(get("/cart/all"))
                .andExpect(status().isOk());
    }
}