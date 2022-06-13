package woowacourse.shoppingcart.unit.cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.cart.domain.CartItem;
import woowacourse.shoppingcart.cart.exception.badrequest.NoExistCartItemException;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.unit.DaoTest;

class CartItemDaoTest extends DaoTest {

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;

        // when
        final Long cartId = cartItemDao.addCartItem(customerId, productId);

        // then
        assertThat(cartId).isEqualTo(1L);
    }

    @Test
    @DisplayName("Customer의 ID에 해당하는 모든 Cart 목록을 조회힌다.")
    void findAllByCustomerId() {
        // given
        final Long customerId = 2L;
        final List<CartItem> expected = new ArrayList<>();

        cartItemDao.addCartItem(customerId, 1L);
        final CartItem cartItem1 = cartItemDao.findByProductAndCustomerId(1L, customerId);
        final CartItem updatedCartItem1 = cartItem1.changeQuantity(4);
        cartItemDao.updateQuantity(updatedCartItem1);
        expected.add(updatedCartItem1);

        cartItemDao.addCartItem(customerId, 5L);
        final CartItem cartItem2 = cartItemDao.findByProductAndCustomerId(5L, customerId);
        final CartItem updatedCartItem2 = cartItem2.changeQuantity(6);
        cartItemDao.updateQuantity(updatedCartItem2);
        expected.add(updatedCartItem2);

        cartItemDao.addCartItem(customerId, 3L);
        final CartItem cartItem3 = cartItemDao.findByProductAndCustomerId(3L, customerId);
        final CartItem updatedCartItem3 = cartItem3.changeQuantity(2);
        cartItemDao.updateQuantity(updatedCartItem3);
        expected.add(updatedCartItem3);

        // when
        final List<CartItem> actual = cartItemDao.findAllByCustomerId(customerId);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void deleteCartItem() {
        // given
        final Long customerId = 1L;
        final Long cartId = cartItemDao.addCartItem(customerId, 1L);
        cartItemDao.addCartItem(customerId, 2L);

        // when
        cartItemDao.deleteCartItem(cartId);

        // then
        final List<Long> productIds = cartItemDao.findAllByCustomerId(customerId)
                .stream()
                .map(CartItem::getProduct)
                .map(Product::getId)
                .collect(Collectors.toList());

        assertThat(productIds).containsExactly(2L);
    }

    @Test
    @DisplayName("카드에 이미 상품이 담겨있으면, true를 반환한다.")
    void existProduct_alreadyExistProduct_trueReturned() {
        // given
        final Long customerId = 1L;
        final Long productId = 2L;
        cartItemDao.addCartItem(customerId, productId);

        // when
        final boolean actual = cartItemDao.existProduct(customerId, productId);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("카드에 상품이 담겨있지 않으면, false를 반환한다.")
    void existProduct_notExistProduct_falseReturned() {
        // given
        final Long customerId = 1L;
        final Long productId = 2L;
        cartItemDao.addCartItem(customerId, productId);

        // when
        final boolean actual = cartItemDao.existProduct(customerId, 3L);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("Customer Id와 Product Id가 일치하는 Cart를 조회한다.")
    void findByProductAndCustomerId() {
        // given
        final Long customerId = 4L;
        final Long productId = 3L;
        final Long cartId = cartItemDao.addCartItem(customerId, productId);

        final Product product = productDao.findProductById(productId);
        final CartItem expected = new CartItem(cartId, product, 1);

        // when
        final CartItem actual = cartItemDao.findByProductAndCustomerId(productId, customerId);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Customer Id와 Product Id가 일치하는 Cart가 존재하지 않으면 에외를 던진다.")
    void findByProductAndCustomerId_notMatch_ExceptionThrown() {
        // given
        final Long productId = 999L;
        final Long customerId = 777L;

        // when, then
        assertThatThrownBy(() -> cartItemDao.findByProductAndCustomerId(productId, customerId))
                .isInstanceOf(NoExistCartItemException.class);
    }

    @Test
    @DisplayName("Cart의 수량을 변경한다.")
    void updateQuantity() {
        // given
        final Long customerId = 9L;
        final Long productId = 5L;
        final Long cartId = cartItemDao.addCartItem(customerId, productId);

        final int quantity = 77;
        final Product product = productDao.findProductById(productId);
        final CartItem cartItem = new CartItem(cartId, product, quantity);

        // when
        cartItemDao.updateQuantity(cartItem);

        final CartItem actual = cartItemDao.findByProductAndCustomerId(productId, customerId);

        // then
        assertThat(actual.getQuantity()).isEqualTo(quantity);
    }
}
