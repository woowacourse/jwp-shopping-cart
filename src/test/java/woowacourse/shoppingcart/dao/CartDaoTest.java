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
}
