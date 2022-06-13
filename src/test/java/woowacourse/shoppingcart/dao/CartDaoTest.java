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
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:test_schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartDaoTest {
    private final CartDao cartDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    public CartDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        cartDao = new CartDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        productDao.save(new Product("banana", 1_000, "woowa1.com"));
        productDao.save(new Product("apple", 2_000, "woowa2.com"));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, 1)", 1L, 1L);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, 1)", 1L, 2L);
    }

    @DisplayName("구매자 id로 해당 구매자가 담은 장바구니 아이템 목록을 가져온다.")
    @Test
    void findCartsByCustomerId() {
        // when
        final List<CartItem> cartItems = cartDao.findCartsByCustomerId(1L);

        // then
        assertAll(
                () -> assertThat(cartItems.get(0).getProduct().getName()).isEqualTo("banana"),
                () -> assertThat(cartItems.get(1).getProduct().getName()).isEqualTo("apple")
        );
    }

    @DisplayName("구매자 id로 해당 구매자가 담은 장바구니 상품 id 목록을 가져온다.")
    @Test
    void findProductIdsByCustomerId() {
        // when
        final List<Long> productIds = cartDao.findProductIdsByCustomerId(1L);

        // then
        assertThat(productIds).containsAll(List.of(1L, 2L));
    }

    @DisplayName("구매자 id와 상품 id로 장바구니에 담긴 상품 정보를 가져온다.")
    @Test
    void findCart() {
        // when
        CartItem cartItem = cartDao.findCartItemByProductId(1L, 1L)
                .orElseThrow(InvalidCartItemException::new);

        // then
        assertAll(
                () -> assertThat(cartItem.getProduct().getName()).isEqualTo("banana"),
                () -> assertThat(cartItem.getQuantity()).isEqualTo(1)
        );
    }

    @DisplayName("장바구니에 상품을 성공적으로 담는다.")
    @Test
    void addCartItem() {
        // given
        final Long productId = productDao.save(new Product("kiwi", 3_000, "woowakiwi.com"));

        // when
        final Long actual = cartDao.addCartItem(1L, productId);

        // then
        assertThat(actual).isEqualTo(productId);
    }

    @DisplayName("장바구니 상품의 수량을 변경한다.")
    @Test
    void updateCartItemQuantity() {
        cartDao.updateCartItemQuantity(3, 2L, 1L);

        final List<CartItem> cartItems = cartDao.findCartsByCustomerId(1L);

        assertThat(cartItems.get(1).getQuantity()).isEqualTo(3);
    }

    @DisplayName("장바구니 아이템을 성공적으로 삭제한다.")
    @Test
    void deleteCartItem() {
        // given
        final Long productId = productDao.save(new Product("kiwi", 3_000, "woowakiwi.com"));
        cartDao.addCartItem(1L, productId);

        // when
        cartDao.deleteCartItem(productId, 1L);
        final List<Long> productIds = cartDao.findProductIdsByCustomerId(1L);

        // then
        assertThat(productIds).doesNotContain(productId);
    }

    @DisplayName("장바구니를 성공적으로 비운다.")
    @Test
    void deleteCart() {
        // when
        cartDao.deleteCartByCustomerId(1L);
        final List<CartItem> actual = cartDao.findCartsByCustomerId(1L);

        // then
        assertThat(actual.size()).isEqualTo(0);
    }
}
