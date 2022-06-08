package woowacourse.shoppingcart.dao;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)",
                1L, 1L, 1);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)",
                1L, 2L, 1);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;
        Product product = productDao.findProductById(productId);

        // when
        final Long cartId = cartItemDao.addCartItem(new CartItem(product, 1), customerId);

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

    @DisplayName("상품 id와 회원 id로 장바구니에 담은 수량을 찾는다.")
    @Test
    void findQuantity() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;

        // when
        int quantity = cartItemDao.findQuantity(productId, customerId);

        // then
        assertThat(quantity).isEqualTo(1);
    }

    @DisplayName("상품 id와 회원 id로 장바구니에 담은 수량을 수정한다.")
    @Test
    void updateQuantity() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;
        Product product = productDao.findProductById(productId);

        // when
        cartItemDao.updateQuantity(new CartItem(product, 2), customerId);
        int quantity = cartItemDao.findQuantity(productId, customerId);

        // then
        assertThat(quantity).isEqualTo(2);
    }

    @DisplayName("상품 id와 회원 id로 장바구니의 상품을 삭제한다.")
    @Test
    void deleteCartItem() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;

        // when
        cartItemDao.deleteCartItem(productId, customerId);

        // then
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(2L);
    }

    @DisplayName("회원 id로 장바구니를 비운다.")
    @Test
    void deleteCartItemByCustomer() {
        // given
        final Long customerId = 1L;

        // when
        cartItemDao.deleteByCustomer(customerId);

        // then
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).hasSize(0);
    }
}
