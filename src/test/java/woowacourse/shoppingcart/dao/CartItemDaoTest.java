package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.UpdateCartItemRequest;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/init.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {

    private final CartItemDao cartDao;

    public CartItemDaoTest(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.cartDao = new CartItemDao(dataSource, jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        final var addProductRequest = new AddCartItemRequest(1L, 1, true);
        cartDao.addCartItem(1L, addProductRequest);
    }

    @Test
    void 장바구니_상품_추가_() {
        final var cart = cartDao.findByCustomerId(1L);
        assertThat(cart.get(0))
                .isEqualTo(new CartItem(1L, 1L, 1, true));
    }

    @Test
    void 장바구니_선택_상품_제거() {
        cartDao.deleteCartItem(1L);

        final var cart = cartDao.findByCustomerId(1L);
        assertThat(cart.size()).isEqualTo(0);
    }

    @Test
    void 장바구니_전체_상품_제거() {
        cartDao.deleteAllByCustomerId(1L);

        final var cart = cartDao.findByCustomerId(1L);
        assertThat(cart.size()).isEqualTo(0);
    }

    @Test
    void 장바구니_상품_정보_수정() {
        final var updateCartItemRequest = new UpdateCartItemRequest(1L, 3, false);
        cartDao.update(1L, updateCartItemRequest);

        final var cartItem = cartDao.findByCustomerId(1L).get(0);

        assertAll(
                () -> assertThat(cartItem.getCartItemId()).isEqualTo(1L),
                () -> assertThat(cartItem.getQuantity()).isEqualTo(3),
                () -> assertThat(cartItem.getChecked()).isFalse()
        );
    }
}
