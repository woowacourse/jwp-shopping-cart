package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
import woowacourse.shoppingcart.exception.IllegalProductException;
import woowacourse.shoppingcart.exception.NotFoundProductException;

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
        Customer customer = new Customer(1L, "kun", "kun@email.com", "qwerasdf123");

        Long productId = 1L;
        Product product = new Product(productId, "product1", 1000, "imageUrl1");

        given(productService.findProductById(productId))
                .willReturn(product);
        given(cartItemDao.addCartItem(customer.getId(), productId))
                .willReturn(anyLong());

        // when, then
        assertThatCode(() -> cartService.addCart(product.getId(), customer))
                .doesNotThrowAnyException();
    }

    @DisplayName("존재하지 않는 물품을 장바구니에 추가하면 예외가 발생한다.")
    @Test
    void addCart_notExistProduct_exceptionThrown() {
        // given
        Customer customer = new Customer(1L, "kun", "kun@email.com", "qwerasdf123");

        Long notExistProductId = 21L;

        given(productService.findProductById(notExistProductId))
                .willThrow(new NotFoundProductException());

        // when, then
        assertThatThrownBy(() -> cartService.addCart(notExistProductId, customer))
                .isInstanceOf(IllegalProductException.class);
    }
}
