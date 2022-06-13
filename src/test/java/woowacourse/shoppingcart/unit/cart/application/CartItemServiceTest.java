package woowacourse.shoppingcart.unit.cart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import woowacourse.shoppingcart.cart.application.CartService;
import woowacourse.shoppingcart.cart.domain.CartItem;
import woowacourse.shoppingcart.cart.dto.QuantityChangingRequest;
import woowacourse.shoppingcart.cart.exception.badrequest.DuplicateCartItemException;
import woowacourse.shoppingcart.cart.exception.badrequest.NoExistCartItemException;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.exception.notfound.NotFoundProductException;
import woowacourse.shoppingcart.unit.ServiceMockTest;

class CartItemServiceTest extends ServiceMockTest {

    @InjectMocks
    private CartService cartService;

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

    @Test
    @DisplayName("장바구니의 상품의 수량을 변경한다.")
    void changeQuantity_existProduct_updatedCartReturned() {
        // given
        final Long productId = 1L;
        final int quantity = 7;

        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        final QuantityChangingRequest request = new QuantityChangingRequest(quantity);

        final String name = "치약";
        final int price = 1200;
        final String imageUrl = "fake.org";
        final Product product = new Product(productId, name, price, imageUrl);

        final CartItem existCartItem = new CartItem(1L, product, 8);
        given(cartItemDao.findByProductAndCustomerId(productId, customer.getId()))
                .willReturn(existCartItem);

        final CartItem expected = new CartItem(1L, product, quantity);

        // when
        final CartItem actual = cartService.changeQuantity(customer, productId, request);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("장바구니에 존재하지 않는 상품의 수량을 변경하면 예외를 던진다.")
    void changeQuantity_notExistProduct_exceptionThrown() {
        // given
        final Long productId = 1L;
        final int quantity = 7;

        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        final QuantityChangingRequest request = new QuantityChangingRequest(quantity);

        given(cartItemDao.findByProductAndCustomerId(productId, customer.getId()))
                .willThrow(NoExistCartItemException.class);

        // when, then
        assertThatThrownBy(() -> cartService.changeQuantity(customer, productId, request))
                .isInstanceOf(NoExistCartItemException.class);
    }

    @Test
    @DisplayName("Product 아이디에 해당하는 Cart를 삭제한다.")
    void deleteCart() {
        // given
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        final Long productId = 1L;
        final Product product = new Product(productId, "망고", 1990, "man.go");

        final CartItem cartItem = new CartItem(1L, product, 3);
        given(cartItemDao.findByProductAndCustomerId(productId, customer.getId()))
                .willReturn(cartItem);

        // when, then
        assertThatCode(() -> cartService.deleteCartBy(customer, productId))
                .doesNotThrowAnyException();
    }
}
