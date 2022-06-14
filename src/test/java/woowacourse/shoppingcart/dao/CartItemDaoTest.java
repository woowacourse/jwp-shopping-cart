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
import woowacourse.shoppingcart.domain.cart.Cart;

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
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 1L, 3);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 2L, 5);
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

    @DisplayName("장바구니에 상품이 존재하는지 확인한다.")
    @Test
    void existProduct_exist_true() {
        // given
        Long productId = 1L;
        Long customerId = 1L;

        // when
        boolean isExist = cartItemDao.existProduct(customerId, productId);

        // then
        assertThat(isExist).isTrue();
    }

    @DisplayName("장바구니의 상품에 대한 수량을 변경한다.")
    @Test
    void updateCartItem() {
        // given
        Long customerId = 1L;
        int quantity = 5;
        Long productId = 1L;

        // when
        cartItemDao.updateCartItem(customerId, quantity, productId);

        // then
        Cart cart = cartItemDao.findCartByProductCustomer(customerId, productId);
        assertThat(cart.getQuantity()).isEqualTo(quantity);
    }

    @DisplayName("cartId를 조회하여 Cart 객체들을 반환한다.")
    @Test
    void findCart() {
        // given
        Long customerId = 1L;

        // when
        List<Cart> carts = cartItemDao.getCartsByCustomerId(customerId);

        // then
        assertThat(carts.size()).isEqualTo(2);
    }
}
