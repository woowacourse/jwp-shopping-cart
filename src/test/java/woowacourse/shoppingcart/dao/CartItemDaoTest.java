package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;
import woowacourse.shoppingcart.dto.request.ProductRequest;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiChracters")
@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql", "classpath:test.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CartItemDaoTest {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        cartItemDao = new CartItemDao(dataSource);
        productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        productDao.save(new ProductRequest("banana", 1_000, "woowa1.com"));
        productDao.save(new ProductRequest("apple", 2_000, "woowa2.com"));

        jdbcTemplate.update(
            "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 1L, 5);
        jdbcTemplate.update(
            "INSERT INTO cart_item(customer_id, product_id,quantity) VALUES(?, ?, ?)", 1L, 2L, 6);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트를 반환한다.")
    @Test
    void addCartItem() {
        // given
        final Long customerId = 1L;
        final Product product = new Product(4L, "banana", 1_000, "woowa1.com");

        Cart cart = cartItemDao.addCartItem(customerId, product);

        assertThat(cart)
            .extracting("id", "productId", "name", "price", "imageUrl", "quantity")
            .containsExactly(5L, 4L, "banana", 1_000, "woowa1.com", 1);
    }

    @Test
    void findIdsByCustomerId_메서드는_고객id로_장바구니_id들을_반환한다() {
        final Long customerId = 1L;

        List<Long> productIds = cartItemDao.findIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(3L, 4L);
    }

    @Test
    void findProductIdById_메서드는_장바구니id로_상품_id를_반환한다() {
        final Long cartId = 1L;

        Long productId = cartItemDao.findProductIdById(cartId);

        assertThat(productId).isEqualTo(1L);
    }

    @Test
    void deleteCartItem() {

        // given
        final Long cartId = 1L;

        // when
        cartItemDao.deleteCartItem(cartId);

        // then
        final Long customerId = 25L;
        final List<Long> productIds = cartItemDao.findIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(2L);
    }

    @Test
    void findByCartId() {
        final Long cartId = 1L;

        Cart cart = cartItemDao.findByCartId(cartId);

        System.out.println(cart);
    }

    @Test
    void update() {
        final Long cartId = 1L;

        cartItemDao.updateCartItemQuantity(cartId, 5);

        assertThat(cartItemDao.findQuantityById(cartId)).isEqualTo(5);
    }
}
