package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
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
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.product.Product;

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
        productDao.save(new Product("banana", 1_000, 100, "woowa1.com"));
        productDao.save(new Product("apple", 2_000, 100, "woowa2.com"));
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 1L, 1);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 2L, 1);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        final Long customerId = 1L;
        Product product = Product.builder()
                .id(1L)
                .productName("banana")
                .price(1_000)
                .stock(100)
                .build();
        final Long cartId = cartItemDao.save(customerId, new CartItem(product, 1));

        assertThat(cartId).isEqualTo(3L);
    }

    @DisplayName("id로 카트 아이템을 조회한다.")
    @Test
    void findById() {
        final Long customerId = 1L;
        Product product = Product.builder()
                .id(1L)
                .productName("banana")
                .price(1_000)
                .stock(100)
                .imageUrl("woowa1.com")
                .build();
        CartItem cartItem = new CartItem(product, 1);
        final Long cartItemId = cartItemDao.save(customerId, cartItem);

        CartItem result = cartItemDao.findById(cartItemId);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(cartItem);
    }

    @DisplayName("id 리스트에 해당하는 모든 카트 아이템 조회")
    @Test
    void findByIdsIn() {
        final Long customerId = 1L;
        Product product1 = new Product(1L, "banana", 1_000, 100, "woowa1.com");
        Product product2 = new Product(2L, "apple", 2_000, 100, "woowa2.com");

        CartItem cartItem1 = new CartItem(product1, 1);
        Long cartItemId1 = cartItemDao.save(customerId, cartItem1);
        CartItem cartItem2 = new CartItem(product2, 1);
        Long cartItemId2 = cartItemDao.save(customerId, cartItem2);

        List<CartItem> cartItems = cartItemDao.findByIdsIn(List.of(cartItemId1, cartItemId2));
        assertThat(cartItems.size()).isEqualTo(2);
    }

    @DisplayName("회원 id로 모든 카트 아이템을 가져온다.")
    @Test
    void findAllByCustomerId() {
        final Long customerId = 2L;
        Product product1 = new Product("coffee", 2_000, 10, "coffee.png");
        Product product2 = new Product("tea", 3_000, 10, "tea.png");

        Long productId1 = productDao.save(product1);
        Long productId2 = productDao.save(product2);

        CartItem cartItem1 = new CartItem(
                new Product(productId1, "coffee", 2_000, 10, "coffee.png"), 1);
        CartItem cartItem2 = new CartItem(
                new Product(productId2, "tea", 3_000, 10, "tea.png"), 1);

        cartItemDao.save(customerId, cartItem1);
        cartItemDao.save(customerId, cartItem2);

        List<CartItem> cartItems = cartItemDao.findAllByCustomerId(customerId);

        assertThat(cartItems).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(List.of(cartItem1, cartItem2));
    }

    @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머 카트 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByCustomerId() {
        final Long customerId = 1L;

        final List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productsIds).containsExactly(1L, 2L);
    }

    @DisplayName("Customer Id를 넣으면, 해당 카트 아이템 Id들을 가져온다.")
    @Test
    void findIdsByCustomerId() {
        final Long customerId = 1L;

        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        assertThat(cartIds).containsExactly(1L, 2L);
    }

    @DisplayName("수량을 변경한다.")
    @Test
    void updateQuantity() {
        final Long customerId = 1L;
        Product product = Product.builder()
                .id(1L)
                .productName("banana")
                .price(1_000)
                .stock(100)
                .build();

        Long cartItemId = cartItemDao.save(customerId, new CartItem(product, 1));
        CartItem cartItem = cartItemDao.findById(cartItemId);

        cartItem.changeQuantity(10);
        assertDoesNotThrow(() -> cartItemDao.updateQuantity(cartItem, customerId));
    }

    @DisplayName("해당 id의 카트 아이템을 삭제한다.")
    @Test
    void deleteCartItem() {
        final Long cartId = 1L;
        final Long customerId = 1L;

        cartItemDao.deleteById(cartId, customerId);

        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(2L);
    }

    @DisplayName("id 리스트에 해당하는 모든 카트 아이템 삭제")
    @Test
    void deleteByIdsIn() {
        final Long cartId1 = 1L;
        final Long cartId2 = 2L;
        final Long customerId = 1L;

        cartItemDao.deleteByIdsIn(new ArrayList<>(List.of(1L, 2L)), customerId);

        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).doesNotContain(cartId1, cartId2);
    }

    @DisplayName("해당 id의 customer_id가 주어진 customer_id와 일치하므로 true를 반환한다.")
    @Test
    void isCartItemExistByCustomer_true() {
        assertThat(cartItemDao.isCartItemExistByCustomer(1L, 1L)).isTrue();
    }

    @DisplayName("해당 id의 customer_id가 주어진 customer_id와 일치하지 않으므로 false를 반환한다.")
    @Test
    void isCartItemExistByCustomer_false() {
        assertThat(cartItemDao.isCartItemExistByCustomer(1L, 3L)).isFalse();
    }

}
