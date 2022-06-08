package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.fixture.Fixture.PRICE;
import static woowacourse.fixture.Fixture.PRODUCT_NAME;
import static woowacourse.fixture.Fixture.QUANTITY;
import static woowacourse.fixture.Fixture.TEST_EMAIL;
import static woowacourse.fixture.Fixture.TEST_PASSWORD;
import static woowacourse.fixture.Fixture.TEST_USERNAME;
import static woowacourse.fixture.Fixture.THUMBNAIL_URL;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {
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
    @DisplayName("장바구니에 물건을 담는다.")
    void addCartItem() {
        cartItemDao.addCartItem(customerId, productId, 1);

        final CartItem cartItem = cartItemDao.findCartItemByCustomerIdAndProductId(customerId, productId).get();
        assertThat(cartItem.getName()).isEqualTo(PRODUCT_NAME);
    }
}
