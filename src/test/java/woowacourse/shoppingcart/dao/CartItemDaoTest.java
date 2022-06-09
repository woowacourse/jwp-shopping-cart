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
import woowacourse.shoppingcart.domain.customer.Customer;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:customer.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayName("CartItem DAO 테스트")
public class CartItemDaoTest {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    private Long customerId;
    private Long productId1;
    private Long productId2;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        cartItemDao = new CartItemDao(jdbcTemplate);
        customerDao = new CustomerDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        customerId = customerDao.save(Customer.from(1L, "test@woowacourse.com", "test", "1234asdf!"));
        productId1 = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        productId2 = productDao.save(new Product("apple", 2_000, "woowa2.com"));
    }

    @DisplayName("장바구니에 상품을 저장한다.")
    @Test
    void addCartItem() {
        // when
        Long cartId = cartItemDao.addCartItem(customerId, productId1, 1);

        // then
        CartItem cartItem = cartItemDao.findCartItemById(cartId).get();

        assertAll(
                () -> assertThat(cartItem.getId()).isEqualTo(cartId),
                () -> assertThat(cartItem.getCustomerId()).isEqualTo(customerId),
                () -> assertThat(cartItem.getProductId()).isEqualTo(productId1),
                () -> assertThat(cartItem.getQuantity()).isEqualTo(1)
        );
    }

    @DisplayName("장바구니 상품 아이디 목록을 조회한다.")
    @Test
    void findIdsByCustomerId() {
        // given
        Long cartId1 = cartItemDao.addCartItem(customerId, productId1, 1);
        Long cartId2 = cartItemDao.addCartItem(customerId, productId2, 1);

        // when
        List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        // then
        assertThat(cartIds).containsExactly(cartId1, cartId2);
    }

    @DisplayName("장바구니 상품을 조회한다.")
    @Test
    void findCartItemById() {
        // given
        Long cartId = cartItemDao.addCartItem(customerId, productId1, 1);

        // when
        Optional<CartItem> cartItem = cartItemDao.findCartItemById(cartId);

        // then
        assertThat(cartItem.isPresent()).isTrue();
    }

    @DisplayName("장바구니 상품의 개수를 수정한다.")
    @Test
    void updateQuantity() {
        // given
        Long cartId = cartItemDao.addCartItem(customerId, productId1, 1);

        // when
        cartItemDao.updateQuantity(cartId, customerId, 2);

        // then
        CartItem cartItem = cartItemDao.findCartItemById(cartId).get();

        assertAll(
                () -> assertThat(cartItem.getId()).isEqualTo(cartId),
                () -> assertThat(cartItem.getCustomerId()).isEqualTo(customerId),
                () -> assertThat(cartItem.getProductId()).isEqualTo(productId1),
                () -> assertThat(cartItem.getQuantity()).isEqualTo(2)
        );
    }

    @DisplayName("장바구니 상품을 삭제한다.")
    @Test
    void delete() {
        // given
        Long cartId = cartItemDao.addCartItem(customerId, productId1, 1);

        // when
        cartItemDao.delete(cartId);

        // then
        assertThat(cartItemDao.findCartItemById(cartId).isEmpty()).isTrue();
    }
}
