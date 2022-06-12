package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
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
public class CartItemDaoImplTest {
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    public CartItemDaoImplTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        cartItemDao = new CartItemDaoImpl(jdbcTemplate);
        productDao = new ProductDaoImpl(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        productDao.save(new Product("banana", 1_000, "woowa1.com"));
        productDao.save(new Product("apple", 2_000, "woowa2.com"));

        String sql = "INSERT INTO cart_item(customer_id, product_id, quantity, checked) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql, 1L, 1L, 1, true);
        jdbcTemplate.update(sql, 1L, 2L, 2, false);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {

        // given
        final Long customerId = 1L;
        final Long productId = 1L;
        final long quantity = 3;
        final boolean checked = true;

        // when
        final Long cartId = cartItemDao.addCartItem(customerId, productId, quantity, checked);

        // then
        Cart cart = cartItemDao.findById(cartId);
        assertAll(
                () -> assertThat(cart.getCustomerId()).isEqualTo(customerId),
                () -> assertThat(cart.getProductId()).isEqualTo(productId),
                () -> assertThat(cart.getQuantity()).isEqualTo(quantity),
                () -> assertThat(cart.isChecked()).isEqualTo(checked)
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
        final List<Cart> carts = cartItemDao.findAllByCustomerId(customerId);

        // then
        final List<Long> cartIds = carts.stream()
                .map(Cart::getId)
                .collect(Collectors.toList());
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

    @DisplayName("id를 넣으면 해당 장바구니의 정보를 가져온다")
    @Test
    void findById() {
        Product product = new Product("tomato", 5_000, "woowa5.com");
        Long productId = productDao.save(product);
        Long id = cartItemDao.addCartItem(1L, productId, 3, true);

        Cart cart = cartItemDao.findById(id);

        assertThat(cart.getId()).isEqualTo(id);
    }

    @DisplayName("id를 넣으면 해당 장바구니의 productId를 가져온다")
    @Test
    void findProductIdById() {
        Product product = new Product("tomato", 5_000, "woowa5.com");
        Long productId = productDao.save(product);
        Long id = cartItemDao.addCartItem(1L, productId, 3, true);

        assertThat(cartItemDao.findProductIdById(id)).isEqualTo(productId);
    }

    @DisplayName("customerId와 productId를 주면 넣으면 해당 장바구니의 정보를 가져온다")
    @Test
    void findByCustomerIdAndProductId() {
        Product product = new Product("tomato", 5_000, "woowa5.com");
        Long productId = productDao.save(product);
        Long id = cartItemDao.addCartItem(1L, productId, 3, true);

        Cart cart = cartItemDao.findByCustomerIdAndProductId(1L, productId);

        assertThat(cart.getProductId()).isEqualTo(productId);
    }

    @DisplayName("id, 수량, 체크여부를 넣으면 해당 장바구니 아이템의 수량과 체크여부를 수정한다.")
    @Test
    void update() {
        Product product = new Product("tomato", 5_000, "woowa5.com");
        Long productId = productDao.save(product);
        Long id = cartItemDao.addCartItem(1L, productId, 3, true);
        Cart cart = new Cart(id, 1L, productId, 3, true);
        cart.update(100, false);

        cartItemDao.update(id, cart);

        Cart changed = cartItemDao.findById(id);
        assertThat(changed.getProductId()).isEqualTo(productId);
    }

    @DisplayName("customerId를 넣으면 해당 장바구니 모두를 제거한다.")
    @Test
    void deleteAllByCustomerId() {
        Product product = new Product("tomato", 5_000, "woowa5.com");
        Long productId = productDao.save(product);
        cartItemDao.addCartItem(1L, productId, 3, true);

        cartItemDao.deleteAllByCustomerId(1L);

        List<Cart> carts = cartItemDao.findAllByCustomerId(1L);
        assertThat(carts).isEmpty();
    }

    @DisplayName("id와 수량을 넣으면 해당 장바구니 아이템의 수량에서 더한다")
    @Test
    void increaseQuantityById() {
        Product product = new Product("tomato", 5_000, "woowa5.com");
        Long productId = productDao.save(product);
        Long id = cartItemDao.addCartItem(1L, productId, 3, true);

        cartItemDao.increaseQuantityById(id, 5);

        Cart cart = cartItemDao.findById(id);
        assertThat(cart.getQuantity()).isEqualTo(8);
    }
}
