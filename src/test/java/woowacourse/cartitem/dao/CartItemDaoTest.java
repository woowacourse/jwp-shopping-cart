package woowacourse.cartitem.dao;

import static org.assertj.core.api.Assertions.assertThat;

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

import woowacourse.cartitem.domain.CartItem;
import woowacourse.cartitem.domain.Quantity;
import woowacourse.customer.dao.CustomerDao;
import woowacourse.customer.domain.Customer;
import woowacourse.product.dao.ProductDao;
import woowacourse.product.domain.Price;
import woowacourse.product.domain.Product;
import woowacourse.product.domain.Stock;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {

    private final ProductDao productDao;
    private final CustomerDao customerDao;
    private final CartItemDao cartItemDao;
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    private Long customerId;
    private Long productId1;
    private Long productId2;

    public CartItemDaoTest(final DataSource dataSource, final JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        customerDao = new CustomerDao(jdbcTemplate);
        productDao = new ProductDao(dataSource);
        cartItemDao = new CartItemDao(dataSource);
    }

    @BeforeEach
    void setUp() {
        customerId = customerDao.save(Customer.of("jjanggu", "password1234", "01000001111", "test")).getId();
        productId1 = productDao.save(new Product("banana", new Price(1_000), new Stock(10), "woowa1.com"));
        productId2 = productDao.save(new Product("apple", new Price(2_000), new Stock(20), "woowa2.com"));
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        // given
        final CartItem cartItem = new CartItem(customerId, productId1, new Quantity(5));

        // when
        final Long cartId = cartItemDao.addCartItem(cartItem);

        // then
        assertThat(cartId).isNotNull();
    }
    //
    // @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
    // @Test
    // void findProductIdsByCustomerId() {
    //
    //     // given
    //     final Long customerId = 1L;
    //
    //     // when
    //     final List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(customerId);
    //
    //     // then
    //     assertThat(productsIds).containsExactly(1L, 2L);
    // }
    //
    // @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    // @Test
    // void findIdsByCustomerId() {
    //
    //     // given
    //     final Long customerId = 1L;
    //
    //     // when
    //     final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);
    //
    //     // then
    //     assertThat(cartIds).containsExactly(1L, 2L);
    // }
    //
    // @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    // @Test
    // void deleteCartItem() {
    //
    //     // given
    //     final Long cartId = 1L;
    //
    //     // when
    //     cartItemDao.deleteCartItem(cartId);
    //
    //     // then
    //     final Long customerId = 1L;
    //     final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);
    //
    //     assertThat(productIds).containsExactly(2L);
    // }
}
