package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        productDao.save(new Product("banana", 1_000, "woowa1.com"));
        productDao.save(new Product("apple", 2_000, "woowa2.com"));
        productDao.save(new Product("cheeze", 3_000, "woowa2.com"));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 1L, 1);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 2L, 1);
    }

    @DisplayName("카트의 아이템 개수를 추가하면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        // given
        final Long cartItemId = 2L;
        final Integer quantity = 2;

        // when
        final Long cartId = cartItemDao.addCartItemById(cartItemId, quantity);

        // then
        assertThat(cartId).isEqualTo(2L);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void createCartItem() {
        // given
        final Long customerId = 1L;
        final Long productId = 3L;
        final Integer quantity = 2;

        // when
        final Long cartId = cartItemDao.createCartItem(customerId, productId, quantity);

        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @DisplayName("같은 Customer Id와 같은 Product Id를 가진 CartItem을 가져온다.")
    @Test
    void findByIds() {
        // given
        final Long customerId = 1L;

        // when
        final List<CartItem> cartItem = cartItemDao.findByCustomerId(customerId);

        // then
        assertThat(cartItem.size()).isEqualTo(2);
    }

    @DisplayName("카트아이템이 있으면 true를 반환한다.")
    @Test
    void existCartItem_true() {
        //given
        final Long customerId = 1L;
        final Long productId = 1L;

        //when
        assertThat(cartItemDao.existCartItem(customerId, productId)).isTrue();
    }

    @DisplayName("카트아이템이 없으면 false를 반환한다.")
    @Test
    void existCartItem_false() {
        //given
        final Long customerId = 4L;
        final Long productId = 1L;

        //when
        assertThat(cartItemDao.existCartItem(customerId, productId)).isFalse();
    }
}
