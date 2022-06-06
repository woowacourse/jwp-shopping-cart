package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartItemDao cartItemDao;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartService cartService;

    @DisplayName("사용자의 장바구니에 상품을 추가한다.")
    @Test
    void addCart_success_void() {
        // given
        String email = "kun@email.com";
        Long productId = 1L;
        Customer customer = new Customer(1L, "kun", email, "qwerasdf123");
        Product product = new Product(productId, "product1", 1000, "imageUrl1");
        given(productService.findProductById(productId))
                .willReturn(product);
        given(cartItemDao.addCartItem(customer.getId(), productId))
                .willReturn(anyLong());

        // when, then
        assertThatCode(() -> cartService.addCart(product.getId(), customer))
                .doesNotThrowAnyException();
    }
}
