package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Collectors;
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
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayName("CartItemItemDao 클래스의")
class CartItemDaoTest {
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

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 1L, 1);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 2L, 1);
    }

    @Nested
    @DisplayName("add 메서드는")
    class add {

        @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
        @Test
        void success() {

            // given
            final Long customerId = 1L;
            final CartItem cartItem = new CartItem(new Product(1L, "banana", 1_000, "woowa1.com"));

            // when
            final Long cartId = cartItemDao.add(customerId, cartItem);

            // then
            assertThat(cartId).isEqualTo(3L);
        }
    }

    @DisplayName("findProductIdsByCustomerId 메서드는 커스터머 아이디를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByCustomerId() {

        // given
        final Long customerId = 1L;

        // when
        final List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(customerId);

        // then
        assertThat(productsIds).containsExactly(1L, 2L);
    }

    @Nested
    @DisplayName("findCartItemsByLoginId 메서드는")
    class findCartItemsByLoginId {

        @DisplayName("고객 아이디를 넣으면, 고객의 장바구니 목록을 가져온다.")
        @Test
        void success() {
            // given
            final Long customerId = 1L;

            // when
            final List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);

            // then
            List<Long> productsIds = cartItems.stream()
                    .map(CartItem::getProductId)
                    .collect(Collectors.toList());
            assertThat(productsIds).containsExactly(1L, 2L);
        }
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

    @Nested
    @DisplayName("update 메서드는")
    class update {

        @DisplayName("장바구니 아이템의 수량을 수정한다.")
        @Test
        void success() {
            // given
            Long cartItemId = 1L;
            int quantity = 10;
            CartItem cartItem = new CartItem(cartItemId, 1L, "banana", 1_000, "woowa1.com", quantity);

            // when & then
            cartItemDao.update(cartItem);

            // then
            CartItem updatedItem = cartItemDao.findCartItemsByCustomerId(1L).stream()
                    .filter(it -> it.getId().equals(cartItemId))
                    .findFirst()
                    .get();
            assertThat(updatedItem.getQuantity()).isEqualTo(quantity);
        }

        @DisplayName("장바구니 아이템이 존재하지 않은 경우, 예외를 던진다.")
        @Test
        void item_notExist() {
            // given
            CartItem cartItem = new CartItem(3L, 1L, "banana", 1_000, "woowa1.com", 10);

            // when & then
            assertThatThrownBy(() -> cartItemDao.update(cartItem))
                    .isInstanceOf(InvalidCartItemException.class);
        }
    }

    @Nested
    @DisplayName("deleteAll 메서드는")
    class deleteAll {

        @DisplayName("Customer Id에 해당하는 장바구니 아이템을 모두 삭제한다.")
        @Test
        void success() {
            // given
            final Long customerId = 1L;

            // when
            cartItemDao.deleteAllByCustomerId(customerId);

            // then
            assertThat(cartItemDao.findIdsByCustomerId(customerId))
                    .isEmpty();
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class delete {

        @DisplayName("장바구니의 아이템을 삭제한다.")
        @Test
        void success() {
            // given
            final Long cartId = 1L;

            // when
            cartItemDao.delete(cartId);

            // then
            final Long customerId = 1L;
            final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

            assertThat(productIds).containsExactly(2L);
        }

        @DisplayName("장바구니 아이템이 존재하지 않는 경우, 예외를 던진다.")
        @Test
        void item_notExist() {
            // given
            final Long id = 3L;

            // when & then
            assertThatThrownBy(() -> cartItemDao.delete(id))
                    .isInstanceOf(InvalidCartItemException.class);
        }
    }
}
