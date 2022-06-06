package woowacourse.shoppingcart.unit.cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import woowacourse.shoppingcart.cart.dao.CartItemDao;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.exception.notfound.NotFoundCartException;
import woowacourse.shoppingcart.product.dao.ProductDao;
import woowacourse.shoppingcart.product.domain.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CartItemDaoTest {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    private Long bananaId;
    private Long appleId;

    public CartItemDaoTest(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        bananaId = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        appleId = productDao.save(new Product("apple", 2_000, "woowa2.com"));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)", 1L, 1L);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)", 1L, 2L);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;

        // when
        final Long cartId = cartItemDao.addCartItem(customerId, productId);

        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByCustomerId() {
        // given
        final Long customerId = 1L;

        // when
        final List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(customerId);

        // then
        assertThat(productsIds).containsExactly(1L, 2L);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByCustomerId() {
        // given
        final Long customerId = 1L;

        // when
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        // then
        assertThat(cartIds).containsExactly(1L, 2L);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void deleteCartItem() {
        // given
        final Long cartId = 1L;

        // when
        cartItemDao.deleteCartItem(cartId);

        // then
        final Long customerId = 1L;
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(2L);
    }

    @Test
    @DisplayName("카드에 이미 상품이 담겨있으면, true를 반환한다.")
    void existProduct_alreadyExistProduct_trueReturned() {
        // given
        Long customerId = 1L;
        cartItemDao.addCartItem(customerId, bananaId);

        // when
        final boolean actual = cartItemDao.existProduct(customerId, bananaId);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("카드에 상품이 담겨있지 않으면, false를 반환한다.")
    void existProduct_notExistProduct_falseReturned() {
        // given
        Long customerId = 1L;
        cartItemDao.addCartItem(customerId, bananaId);

        // when
        final boolean actual = cartItemDao.existProduct(customerId, bananaId);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("Customer Id와 Product Id가 일치하는 Cart를 조회한다.")
    void findByProductAndCustomerId() {
        // given
        Long customerId = 4L;
        Long productId = 6L;
        final Long cartId = cartItemDao.addCartItem(customerId, productId);

        final Product product = productDao.findProductById(productId);
        final Cart expected = new Cart(cartId, productId, product.getName(), product.getPrice(), product.getImageUrl(),
                1);

        // when
        final Cart actual = cartItemDao.findByProductAndCustomerId(productId, customerId);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Customer Id와 Product Id가 일치하는 Cart가 존재하지 않으면 에외를 던진다.")
    void findByProductAndCustomerId_notMatch_ExceptionThrown() {
        // given
        Long productId = 999L;
        Long customerId = 777L;

        // when, then
        assertThatThrownBy(() -> cartItemDao.findByProductAndCustomerId(productId, customerId))
                .isInstanceOf(NotFoundCartException.class);
    }

    @Test
    @DisplayName("Cart의 수량을 변경한다.")
    void updateQuantity() {
        // given
        Long customerId = 9L;
        Long productId = 5L;
        final Long cartId = cartItemDao.addCartItem(customerId, productId);

        final int quantity = 77;
        final Product product = productDao.findProductById(productId);
        final Cart cart = new Cart(cartId, productId, product.getName(), product.getPrice(), product.getImageUrl(),
                quantity);

        // when
        cartItemDao.updateQuantity(cart);

        final Cart actual = cartItemDao.findByProductAndCustomerId(productId, customerId);

        // then
        assertThat(actual.getQuantity()).isEqualTo(quantity);
    }
}
