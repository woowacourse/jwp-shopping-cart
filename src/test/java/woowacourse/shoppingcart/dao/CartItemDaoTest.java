package woowacourse.shoppingcart.dao;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.Product;

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
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Long productId1;
    private Long productId2;

    private Customer customer;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        customerDao = new CustomerDao(namedParameterJdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        productId1 = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        productId2 = productDao.save(new Product("apple", 2_000, "woowa2.com"));

        customer = customerDao.save(new Customer("abc@abc.com","abc","010-0000-0000","seoul","Test1234!"));
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", customer.getId(), productId1, 10);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", customer.getId(), productId2, 10);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        // given
        final Long customerId = customer.getId();
        final Long productId = productId1;
        final int quantity = 10;
        // when
        final Long cartId = cartItemDao.addCartItem(customerId, productId, quantity);
        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @DisplayName("Customer Id를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByCustomerId() {
        // given
        final Long customerId = customer.getId();
        // when
        final List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(customerId);
        // then
        assertThat(productsIds).containsExactly(productId1, productId2);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByCustomerId() {
        // given
        final Long customerId = customer.getId();
        // when
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);
        // then
        assertThat(cartIds).containsExactly(1L, 2L);
    }

    @DisplayName("장바구니에 담긴 아이템의 수량을 수정한다.")
    @Test
    void updateCartItem() {
        // given
        final Long customerId = customer.getId();
        final Long productId = productId1;
        // when
        cartItemDao.modifyQuantity(customerId, productId, 15);
        // then
        final List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);
        CartItem updateCartItem = cartItems.stream().filter(cartItem -> Objects.equals(cartItem.getProductId(), productId))
                .findFirst().get();
        assertThat(updateCartItem.getQuantity()).isEqualTo(15);
    }

    @DisplayName("상품 ID와 고객 ID를 통해 장바구니 아이템을 삭제한다.")
    @Test
    void deleteCartItem() {
        // given
        final Long customerId = customer.getId();
        final Long productId = productId1;
        // when
        cartItemDao.deleteCartItem(1L, productId);

        // then
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(productId2);
    }
}
