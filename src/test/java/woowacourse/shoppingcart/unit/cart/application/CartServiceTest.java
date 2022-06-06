package woowacourse.shoppingcart.unit.cart.application;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import woowacourse.shoppingcart.cart.application.CartService;
import woowacourse.shoppingcart.cart.dao.CartItemDao;
import woowacourse.shoppingcart.customer.dao.CustomerDao;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.exception.badrequest.DuplicateCartItemException;
import woowacourse.shoppingcart.exception.notfound.NotFoundProductException;
import woowacourse.shoppingcart.product.dao.ProductDao;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.unit.ServiceMockTest;

class CartServiceTest extends ServiceMockTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartItemDao cartItemDao;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private ProductDao productDao;

    @Test
    @DisplayName("존재하지 않는 상품을 장바구니에 추가하면 예외를 던진다.")
    void addCart_notExistProduct_ExceptionThrown() {
        // given
        final Long productId = 1L;
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);

        given(productService.findProductById(productId))
                .willThrow(NotFoundProductException.class);

        // when, then
        assertThatThrownBy(() -> cartService.addCart(productId, customer))
                .isInstanceOf(NotFoundProductException.class);
    }

    @Test
    @DisplayName("이미 장바구니에 담겨있는 상품을 추가하면 예외를 던진다.")
    void addCart_alreadyAddedItem_ExceptionThrown() {
        // given
        final Long productId = 1L;
        final Product product = new Product(productId, "치약", 1200, "fake.org");
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);

        given(productService.findProductById(productId))
                .willReturn(product);
        given(cartItemDao.existProduct(customer.getId(), productId))
                .willReturn(true);

        // when, then
        assertThatThrownBy(() -> cartService.addCart(productId, customer))
                .isInstanceOf(DuplicateCartItemException.class);
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void addCart_newItem_voidReturned() {
        // given
        final Long productId = 1L;
        final Product product = new Product(productId, "치약", 1200, "fake.org");
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);

        given(productService.findProductById(productId))
                .willReturn(product);
        given(cartItemDao.existProduct(customer.getId(), productId))
                .willReturn(false);

        // when, then
        assertThatCode(() -> cartService.addCart(productId, customer))
                .doesNotThrowAnyException();
    }
}
