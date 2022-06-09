package woowacourse.shoppingcart.dao;

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

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final CustomerDao customerDao;
    private final JdbcTemplate jdbcTemplate;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        customerDao = new CustomerDao(jdbcTemplate);
    }


    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        // given
        Long customerId = customerDao.save(new Customer("awesomeo@gmail.com", "awesome", "Password123!"));
        Long productId = productDao.save(new Product("banana", 1_000, "woowa1.com"));

        // when
        final Long cartId = cartItemDao.addCartItem(customerId, productId);

        // then
        assertThat(cartId).isNotNull();
    }

    @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머가 장바구니에 담은 상품의 목록을 가져온다.")
    @Test
    void getCartItemsByCustomerId() {
        // given
        Long customerId = customerDao.save(new Customer("awesomeo@gmail.com", "awesome", "Password123!"));

        Long productId1 = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        Long productId2 = productDao.save(new Product("apple", 2_000, "woowa2.com"));

        Long cartItemId1 = cartItemDao.addCartItem(customerId, productId1);
        Long cartItemId2 = cartItemDao.addCartItem(customerId, productId2);

        // when
        List<CartItem> carts = cartItemDao.findCartItemsByCustomerId(customerId);

        // then
        assertThat(carts).usingRecursiveComparison().isEqualTo(
                List.of(new CartItem(cartItemId1, productId1, "banana", 1_000, 1, "woowa1.com"),
                        new CartItem(cartItemId2, productId2, "apple", 2_000, 1, "woowa2.com"))
        );
    }

    @DisplayName("커스터머 아이디와 프로덕트 아이디로 장바구니 아이템의 수량을 증가시킨다.")
    @Test
    void addCartItemQuantity() {
        // given
        Long customerId = customerDao.save(new Customer("awesomeo@gmail.com", "awesome", "Password123!"));
        Long productId = productDao.save(new Product("banana", 1_000, "woowa1.com"));

        Long cartItemId = cartItemDao.addCartItem(customerId, productId);

        // when
        int quantity = 2;
        cartItemDao.updateQuantity(customerId, productId, quantity);

        // then
        List<CartItem> carts = cartItemDao.findCartItemsByCustomerId(customerId);
        assertThat(carts).usingRecursiveComparison().isEqualTo(
                List.of(new CartItem(cartItemId, productId, "banana", 1_000, quantity, "woowa1.com"))
        );
    }

    @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByCustomerId() {
        // given
        Long customerId = customerDao.save(new Customer("awesomeo@gmail.com", "awesome", "Password123!"));
        Long productId1 = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        Long productId2 = productDao.save(new Product("apple", 2_000, "woowa2.com"));
        cartItemDao.addCartItem(customerId, productId1);
        cartItemDao.addCartItem(customerId, productId2);

        // when
        final List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(customerId);

        // then
        assertThat(productsIds).containsExactly(productId1, productId2);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByCustomerId() {
        // given
        Long customerId = customerDao.save(new Customer("awesomeo@gmail.com", "awesome", "Password123!"));
        Long productId = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        Long cartItemId = cartItemDao.addCartItem(customerId, productId);

        // when
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        // then
        assertThat(cartIds).containsExactly(cartItemId);
    }

    @DisplayName("커스터머 아이디와 프로덕트 아이디로 장바구니의 아이템을 삭제한다.")
    @Test
    void deleteCartItem() {
        Long customerId = customerDao.save(new Customer("awesomeo@gmail.com", "awesome", "Password123!"));
        Long productId = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        cartItemDao.addCartItem(customerId, productId);

        cartItemDao.deleteCartItem(customerId, productId);

        List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);
        assertThat(cartItems.size()).isEqualTo(0);
    }

    @DisplayName("이미 장바구니에 추가된 아이템이라면 참을 반환한다.")
    @Test
    void existsByCustomerIdAndProductIdReturnTrue() {
        Long customerId = customerDao.save(new Customer("awesomeo@gmail.com", "awesome", "Password123!"));
        Long productId = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        cartItemDao.addCartItem(customerId, productId);

        boolean actual = cartItemDao.existsByCustomerIdAndProductId(customerId, productId);

        assertThat(actual).isTrue();
    }

    @DisplayName("장바구니에 추가되지 않은 아이템이라면 거짓을 반환한다.")
    @Test
    void existsByCustomerIdAndProductIdReturnFalse() {
        Long customerId = customerDao.save(new Customer("awesomeo@gmail.com", "awesome", "Password123!"));
        Long productId = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        cartItemDao.addCartItem(customerId, productId);
        cartItemDao.deleteCartItem(customerId, productId);

        boolean actual = cartItemDao.existsByCustomerIdAndProductId(customerId, productId);

        assertThat(actual).isFalse();
    }
}
