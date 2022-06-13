package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.ProductFixture.PRODUCT_APPLE;
import static woowacourse.fixture.ProductFixture.PRODUCT_BANANA;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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
        productDao.save(PRODUCT_BANANA);
        productDao.save(PRODUCT_APPLE);

        jdbcTemplate.update("INSERT INTO cart_item(quantity, customer_id, product_id) VALUES(?, ?, ?)", 5, 1L, 1L);
        jdbcTemplate.update("INSERT INTO cart_item(quantity, customer_id, product_id) VALUES(?, ?, ?)", 10, 1L, 2L);
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

    @DisplayName("카트에 아이템을 담으면, 초기 수량은 1개이다.")
    @Test
    void addCartItemWithQuantityOnlyOne() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;

        // when
        final Long cartId = cartItemDao.addCartItem(customerId, productId);
        final List<Cart> carts = cartItemDao.findAllJoinProductByCustomerId(customerId);
        final Cart cart = carts.stream()
                .filter(each -> Objects.equals(each.getId(), cartId))
                .collect(Collectors.toList())
                .get(0);

        // then
        assertThat(cart.getQuantity()).isEqualTo(1);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니와 상품을 가져온다")
    @Test
    void findAllJoinProductByCustomerId() {
        // given
        final Long customerId = 1L;

        // when
        final List<Cart> carts = cartItemDao.findAllJoinProductByCustomerId(customerId);

        // then
        final List<Integer> quantities = carts.stream()
                .map(Cart::getQuantity)
                .collect(Collectors.toList());

        final List<String> productNames = carts.stream()
                .map(Cart::getProduct)
                .map(Product::getName)
                .collect(Collectors.toList());
        assertAll(
                () -> assertThat(quantities).containsExactly(5, 10),
                () -> assertThat(productNames).containsExactly("banana", "apple")
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

    @DisplayName("Customer Id를 넣으면, 해당 장바구니의 수량이 변경된다.")
    @Test
    void updateCartItemQuantity() {
        // given
        final Long customerId = 1L;
        final Long cartItemId = 1L;

        // when
        cartItemDao.updateQuantity(1, cartItemId);
        final List<Cart> carts = cartItemDao.findAllJoinProductByCustomerId(customerId);
        final Cart cart = carts.stream()
                .filter(each -> Objects.equals(each.getId(), cartItemId))
                .collect(Collectors.toList())
                .get(0);

        // then
        assertThat(cart.getQuantity()).isEqualTo(1);
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
