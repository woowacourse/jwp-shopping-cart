package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.ThumbnailImage;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemItemDaoTest {
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    public CartItemItemDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        ThumbnailImage wooWaOneImg = new ThumbnailImage("woowa1.com", "woowa1");
        ThumbnailImage wooWaTwoImg = new ThumbnailImage("woowa2.com", "woowa2");

        productDao.save(new Product("banana", 1_000, 10, wooWaOneImg));
        productDao.save(new Product("apple", 2_000, 10, wooWaTwoImg));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, quantity,  product_id) VALUES(?, ?, ?)", 1L, 10, 1L);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, quantity,  product_id) VALUES(?, ?, ?)", 1L, 10, 2L);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {

        // given
        final Long customerId = 1L;
        final int quantity = 10;
        final Long productId = 1L;

        // when
        final Long cartId = cartItemDao.save(customerId, quantity, productId);

        // then
        assertThat(cartId).isEqualTo(3L);
    }
}
