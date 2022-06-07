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
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartDaoTest {

    private final CartItemDao cartItemDao;

    public CartDaoTest(JdbcTemplate jdbcTemplate) {
        cartItemDao = new CartItemDao(jdbcTemplate);
    }

    @Test
    void 사용자의_ID로_장바구니를_조회할_경우_객체를_리턴하는지_확인() {
        // given
        Long customerId = 1L;

        // when
        final List<CartItem> actual = cartItemDao.findCartItemByUserId(customerId);

       List<CartItem> expected = List.of(new CartItem(1L, 1L, 1L, 1, true),
               new CartItem(2L, 1L, 3L, 2, false),
               new CartItem(3L, 1L, 5L, 1, true));

       assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 특정_사용자의_특정_제품이_장바구니에_있는지_확인() {
        Long customerId = 1L;
        Long productId = 1L;

        assertThat(cartItemDao.isCartContains(customerId, productId)).isTrue();
    }

    @Test
    void 특정_사용자의_특정_제품의_수량을_바꾸는_경우() {
        Long customerId = 1L;
        Long productId = 1L;

        cartItemDao.increaseQuantity(customerId, productId, 2);

        CartItem actual = cartItemDao.findCartItemByIds(customerId, productId);

        CartItem expected = new CartItem(1L, 1L, 1L, 3, true);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 특정_사용자의_특정_제품을_추가하는_경우() {
        Long customerId = 1L;
        Long productId = 4L;

        cartItemDao.saveItemInCart(customerId, productId, 2);

        CartItem actual = cartItemDao.findCartItemByIds(customerId, productId);

        CartItem expected = new CartItem(4L, 1L, 4L, 2, true);

        assertThat(actual).isEqualTo(expected);
    }
}
