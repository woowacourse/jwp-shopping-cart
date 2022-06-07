package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Customer.Customer;
import woowacourse.shoppingcart.domain.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.fixture.Fixture.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartItemDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartItemDao cartItemDao;
    private CustomerDao customerDao;
    private ProductDao productDao;

    private Long customerId;
    private Long productId;

    @BeforeEach
    void setUp() {
        cartItemDao = new CartItemDao(jdbcTemplate);
        customerDao = new CustomerDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        customerId = customerDao.save(Customer.createWithoutId(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));
        productId = productDao.save(new Product(PRODUCT_NAME, PRICE, THUMBNAIL_URL, QUANTITY));
    }

    @Test
    @DisplayName("주문 수량을 수정한다.")
    void updateCartItem() {

        int expected = 2;
        cartItemDao.addCartItem(customerId, productId, 1);

        cartItemDao.updateCartItem(customerId, productId, expected);

        final CartItem cartItem = cartItemDao.findCartItemByCustomerId(customerId, productId);
        assertThat(cartItem.getCount()).isEqualTo(expected);
    }
}
