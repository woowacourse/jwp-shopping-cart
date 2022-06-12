package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.domain.CartItemNotFoundException;
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
        productDao.save(new Product("banana", 1_000, "http://woowa1.com", "banana-description"));
        productDao.save(new Product("apple", 2_000, "http://woowa2.com", "apple-description"));

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
        final List<CartItem> cartItems = cartItemDao.findCartByCustomerId(customerId).getItems();
        final CartItem cartItem = cartItems.stream()
            .filter(item -> item.getId().equals(cartId))
            .findAny()
            .orElseThrow();

        Assertions.assertAll(
            () -> assertThat(cartId).isEqualTo(1L),
            () -> assertThat(cartItem.getQuantity()).isEqualTo(15)
        );
    }

    @DisplayName("cart의 id로 product의 id를 찾는다.")
    @Test
    void findProductIdById() {
        // given
        final long id = 1L;

        // when
        final Long productId = cartItemDao.findProductIdById(id);

        // then
        assertThat(productId).isEqualTo(1L);
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
        final List<CartItem> items = cartItemDao.findCartByCustomerId(customerId).getItems();

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
        cartItemDao.deleteById(cartId);

        // then
        final Long customerId = 1L;
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(2L);
    }

    @DisplayName("잘못된 id의 장바구니 품목을 삭제하면 변경이 일어나지 않는다..")
    @Test
    void throwsExceptionWithInvalidCartItemIdOnDelete() {
        // given
        final Long cartId = 99L;
        // when
        final boolean isDeleted = cartItemDao.deleteById(cartId);
        // then
        assertThat(isDeleted).isFalse();
    }

    @DisplayName("수량을 업데이트한다.")
    @Test
    void updateQuantity() {
        // given
        final Long customerId = 1L;
        final Long cartId = 1L;
        final Quantity quantity = new Quantity(100);

        // when
        cartItemDao.updateQuantity(customerId, cartId, quantity);
        final List<CartItem> items = cartItemDao.findCartByCustomerId(customerId).getItems();
        final CartItem updatedCartItem = items.stream()
            .filter(item -> item.getId().equals(cartId))
            .findAny()
            .orElseThrow();
        // then
        assertThat(updatedCartItem.getQuantity()).isEqualTo(100);
    }
}
