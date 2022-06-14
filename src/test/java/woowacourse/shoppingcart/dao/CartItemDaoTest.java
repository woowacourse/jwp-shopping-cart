package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
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
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CartItemDaoTest {

    private final CustomerDao customerDao;
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        customerDao = new CustomerDao(dataSource);
        cartItemDao = new CartItemDao(dataSource);
        productDao = new ProductDao(dataSource);
    }

    @BeforeEach
    void setUp() {
        customerDao.save(new Customer("email@email.com", "password123!A", "rookie"));
        productDao.save(new Product("banana", 1_000, "woowa1.com"));
        productDao.save(new Product("apple", 2_000, "woowa2.com"));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 1L, 1);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 2L, 1);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;

        // when
        final Long cartId = cartItemDao.saveCartItem(customerId, productId);

        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @Test
    @DisplayName("유저 id를 통해서 카트에 저장된 상품 목록들을 조회할 수 있다.")
    void findAllByCustomerId() {
        // given
        Long customerId = 1L;

        // when
        List<CartItem> cartsProduct = cartItemDao.findAllByCustomerId(customerId);

        // then
        assertThat(cartsProduct).hasSize(2);
    }

    @Test
    @DisplayName("회원 id와 상품 id를 통해서 상품의 수량을 변경할 수 있다.")
    void updateQuantity() {
        // given
        Long customerId = 1L;
        Long productId = 1L;

        // when
        cartItemDao.updateQuantity(customerId, productId, 2);

        // then
        List<CartItem> cartItems = cartItemDao.findAllByCustomerId(1L);
        assertThat(cartItems.get(0).getQuantity()).isEqualTo(2);
    }

    @DisplayName("회원 id와 상품 id를 통해서 장바구니의 상품을 삭제할 수 있다.")
    @Test
    void deleteCartItem() {
        // given
        Long customerId = 1L;
        Long productId = 1L;

        // when & then
        assertDoesNotThrow(() -> cartItemDao.deleteCartItem(customerId, productId));
    }

    @Test
    @DisplayName("추가할 상품이 동일 회원의 장바구니에 동일한 상품인지 확인할 수 있다.")
    void existCartItems() {
        // given
        Long customerId = 1L;
        Long productId = 1L;
        cartItemDao.saveCartItem(customerId, productId);

        // when
        boolean flag = cartItemDao.existCartItems(1L, 1L);

        // then
        assertThat(flag).isTrue();
    }
}
