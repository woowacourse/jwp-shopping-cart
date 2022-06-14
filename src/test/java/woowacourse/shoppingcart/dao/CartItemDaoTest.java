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
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 1L, 5);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 2L, 10);
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

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Cart들을 가져온다.")
    @Test
    void findIdsByCustomerId() {

        // given
        final Long customerId = 1L;

        // when
        List<Cart> carts = cartItemDao.findAllByCustomerId(customerId);

        // then
        assertAll(
                () -> assertThat(carts.get(0).getId()).isEqualTo(1L),
                () -> assertThat(carts.get(1).getId()).isEqualTo(2L)
        );
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

    @DisplayName("quantity에 따라 수량이 업데이트 된다.")
    @Test
    void updateQuantity() {
        // given
        final Long customerId = 1L;
        final Long cartId = 1L;

        // when
        cartItemDao.updateQuantity(cartId, 30);
        List<Cart> carts = cartItemDao.findAllByCustomerId(customerId);

        Cart findCart = carts.stream()
                .filter(cart -> cart.isSameId(cartId))
                .findFirst()
                .get();

        assertThat(findCart.getQuantity().getValue()).isEqualTo(30);
    }

    @DisplayName("Cart에 productId를 기준으로 cartId와 quantity를 찾는다.")
    @Test
    void findIdAndQuantityByProductId() {
        final Long productId = 1L;
        final Long customerId = 1L;
        Cart cart = cartItemDao.findIdAndQuantityByProductId(productId, customerId).get();
        assertAll(
                () -> assertThat(cart.getQuantity().getValue()).isEqualTo(5),
                () -> assertThat(cart.getId()).isEqualTo(1L)
        );
    }
}
