package woowacourse.shoppingcart.integration.cart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.cart.domain.CartItem;
import woowacourse.shoppingcart.cart.dto.QuantityChangingRequest;
import woowacourse.shoppingcart.cart.exception.badrequest.DuplicateCartItemException;
import woowacourse.shoppingcart.cart.exception.badrequest.NoExistCartItemException;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.integration.IntegrationTest;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.exception.notfound.NotFoundProductException;

class CartItemServiceIntegrationTest extends IntegrationTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        final Customer customer = new Customer("rick", "rick@gmail.com", "1q2w3e4r");
        final Long customerId = customerDao.save(customer);
        this.customer = new Customer(customerId, customer.getNickname(), customer.getEmail(), customer.getPassword());
    }

    @Test
    @DisplayName("Customer의 장바구니에 들어있는 모든 Cart를 조회한다.")
    void findCartsBy() {
        // given
        final List<Long> expectedProductIds = List.of(3L, 1L, 4L, 2L);
        for (final Long productId : expectedProductIds) {
            cartItemDao.addCartItem(customer.getId(), productId);
        }

        // when
        final List<CartItem> actual = cartService.findCartsBy(customer);

        final List<Long> actualProductIds = actual.stream()
                .map(CartItem::getProduct)
                .map(Product::getId)
                .collect(Collectors.toList());

        // then
        assertThat(actualProductIds).isEqualTo(expectedProductIds);
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addCart_newItem_voidReturned() {
        // given
        final Long productId = 1L;

        // when
        final Long actual = cartService.addCart(productId, customer);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 상품을 장바구니에 추가하면 예외를 던진다.")
    void addCart_notExistProduct_exceptionThrown() {
        // given
        final Long productId = 999L;

        // when, then
        assertThatThrownBy(() -> cartService.addCart(productId, customer))
                .isInstanceOf(NotFoundProductException.class);
    }

    @Test
    @DisplayName("이미 장바구니에 담겨있는 상품을 추가하면 예외를 던진다.")
    void addCart_alreadyAddedItem_exceptionThrown() {
        // given
        final Long productId = 1L;

        cartItemDao.addCartItem(customer.getId(), productId);

        // when, then
        assertThatThrownBy(() -> cartService.addCart(productId, customer))
                .isInstanceOf(DuplicateCartItemException.class);
    }

    @Test
    @DisplayName("상품의 수량을 변경한다.")
    void changeQuantity() {
        // given
        final Long productId = 1L;
        cartItemDao.addCartItem(customer.getId(), productId);

        final int quantity = 4;
        final QuantityChangingRequest request = new QuantityChangingRequest(quantity);

        // when
        final CartItem actual = cartService.changeQuantity(customer, productId, request);

        // then
        assertThat(actual.getQuantity()).isEqualTo(quantity);
    }

    @Test
    @DisplayName("장바구니에 존재하지 않는 상품의 수량을 변경하면 예외를 던진다.")
    void changeQuantity_notExistItem_exceptionThrown() {
        // given
        final Long productId = 1L;
        final QuantityChangingRequest request = new QuantityChangingRequest(4);

        // when, then
        assertThatThrownBy(() -> cartService.changeQuantity(customer, productId, request))
                .isInstanceOf(NoExistCartItemException.class);
    }

    @Test
    @DisplayName("Product 아이디에 해당하는 Cart를 삭제한다.")
    void deleteCart() {
        // given
        final Long productId = 4L;

        cartItemDao.addCartItem(customer.getId(), 3L);
        cartItemDao.addCartItem(customer.getId(), productId);
        cartItemDao.addCartItem(customer.getId(), 2L);

        // when
        cartService.deleteCartBy(customer, productId);

        final List<Long> actualProductIds = cartService.findCartsBy(customer)
                .stream()
                .map(CartItem::getProduct)
                .map(Product::getId)
                .collect(Collectors.toList());

        // then
        assertThat(actualProductIds).containsExactly(3L, 2L);
    }

    @Test
    @DisplayName("장바구니에 존재하지 않는 상품을 삭제하면 예외를 던진다.")
    void deleteCart_notExistProductInCart_exceptionThrown() {
        // when, then
        assertThatThrownBy(() -> cartService.deleteCartBy(customer, 1L))
                .isInstanceOf(NoExistCartItemException.class);
    }
}
