package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

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

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {

    private final CartItemDao cartDao;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.cartDao = new CartItemDao(dataSource, jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        var addProductRequest = new AddCartItemRequest(1L, 1, true);
        cartDao.addCartItem(1L, addProductRequest);
    }

    @Test
    void 장바구니_상품_추가_() {
        var cart = cartDao.findByCustomerId(1L);
        assertThat(cart.get(0))
                .isEqualTo(new CartItem(1L, 1L, 1L, 1, true));
    }

    @Test
    void 장바구니_선택_상품_제거() {
        cartDao.deleteCartItem(1L);

        var cart = cartDao.findByCustomerId(1L);
        assertThat(cart.size()).isEqualTo(0);
    }
}
