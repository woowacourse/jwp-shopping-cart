package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.support.test.ExtendedJdbcTest;

@ExtendedJdbcTest
public class CartItemDaoTest {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        productDao = new ProductDao(jdbcTemplate);
        cartItemDao = new CartItemDao(productDao, jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        productDao.save(new Product("banana", 1_000, "http://woowa1.com"));
        productDao.save(new Product("apple", 2_000, "http://woowa2.com"));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 1L, 5L);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 2L, 10L);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {

        // given
        final Long customerId = 1L;
        final Long productId = 1L;
        final Integer quantity = 10;

        // when
        final Long cartId = cartItemDao.addCartItem(customerId, productId, quantity);

        // then
        assertThat(cartId).isEqualTo(1L);
    }

    @DisplayName("카트에 같은 아이템을 두 번 담으면, 수량이 합쳐진다.")
    @Test
    void addCartItemTwice() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;
        final Integer quantity = 10;

        // when
        final Long cartId = cartItemDao.addCartItem(customerId, productId, quantity);

        // then
        final List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);
        final CartItem cartItem = cartItems.stream()
            .filter(item -> item.getId().equals(cartId))
            .findAny()
            .orElseThrow();

        Assertions.assertAll(
            () -> assertThat(cartId).isEqualTo(1L),
            () -> assertThat(cartItem.getQuantity()).isEqualTo(15)
        );
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

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 아이템들을 가져온다.")
    @Test
    void findIdsByCustomerId() {

        // given
        final Long customerId = 1L;

        // when
        final List<CartItem> items = cartItemDao.findCartItemsByCustomerId(customerId);

        // then
        final List<Long> ids = items.stream().map(CartItem::getId).collect(Collectors.toList());
        assertThat(ids).containsExactly(1L, 2L);
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
}
