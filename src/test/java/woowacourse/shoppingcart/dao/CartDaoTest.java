package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.CartItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:testSchema.sql", "classpath:testCartData.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartDaoTest {

    private final CartItemDao cartItemDao;

    public CartDaoTest(JdbcTemplate jdbcTemplate) {
        cartItemDao = new CartItemDao(jdbcTemplate);
    }

    @Test
    void 사용자의_ID로_장바구니를_조회할_경우_객체를_리턴하는지_확인() {
        Long customerId = 1L;
        List<CartItem> expected = List.of(new CartItem(1L, 1L, 1L, 1, true),
                new CartItem(2L, 1L, 3L, 2, false),
                new CartItem(3L, 1L, 5L, 1, true));

        final List<CartItem> actual = cartItemDao.findCartItemByUserId(customerId);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 특정_사용자의_특정_제품이_장바구니에_있는지_확인() {
        Long customerId = 1L;
        Long productId = 1L;

        assertThat(cartItemDao.isCartContains(customerId, productId)).isTrue();
    }

    @Test
    void 특정_사용자의_특정_제품의_수량을_증가시키는_경우() {
        Long customerId = 1L;
        Long productId = 1L;
        CartItem expected = new CartItem(1L, 1L, 1L, 3, true);

        cartItemDao.increaseQuantity(customerId, productId, 2);
        CartItem actual = cartItemDao.findCartItemByIds(customerId, productId);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 특정_사용자의_특정_제품을_추가하는_경우() {
        Long customerId = 1L;
        Long productId = 4L;
        CartItem expected = new CartItem(4L, 1L, 4L, 2, true);

        cartItemDao.saveItemInCart(customerId, productId, 2);
        CartItem actual = cartItemDao.findCartItemByIds(customerId, productId);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 특정_사용자의_특정_제품의_수량을_바꾸는_경우() {
        Long customerId = 1L;
        Long cartId = 1L;
        CartItem expected = new CartItem(1L, 1L, 1L, 2, true);

        cartItemDao.updateQuantityAndCheck(customerId, cartId, 2, true);
        CartItem actual = cartItemDao.findCartItemByIds(customerId, cartId);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 특정_사용자의_특정_제품의_체크상태를_바꾸는_경우() {
        Long customerId = 1L;
        Long cartId = 1L;
        CartItem expected = new CartItem(1L, 1L, 1L, 1, false);

        cartItemDao.updateQuantityAndCheck(customerId, cartId, 1, false);
        CartItem actual = cartItemDao.findCartItemByIds(customerId, cartId);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 장바구니에서_하나의_항목을_삭제하는_경우() {
        Long customerId = 1L;
        Long cartId = 1L;

        cartItemDao.deleteOneItem(customerId, cartId);
        List<CartItem> cartItems = cartItemDao.findCartItemByUserId(customerId);

        assertThat(cartItems.size()).isEqualTo(2);
    }

    @Test
    void 장바구니_자체를_삭제하는_경우() {
        Long customerId = 1L;

        cartItemDao.deleteCart(customerId);
        List<CartItem> cartItems = cartItemDao.findCartItemByUserId(customerId);

        assertThat(cartItems.size()).isEqualTo(0);
    }
}
