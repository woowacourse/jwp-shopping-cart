package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.EMAIL_VALUE_1;
import static woowacourse.Fixtures.PASSWORD_VALUE_1;
import static woowacourse.Fixtures.PRODUCT_DESCRIPTION_VALUE_1;
import static woowacourse.Fixtures.PRODUCT_NAME_VALUE_1;
import static woowacourse.Fixtures.PRODUCT_PRICE_VALUE_1;
import static woowacourse.Fixtures.PRODUCT_STOCK_VALUE_1;
import static woowacourse.Fixtures.PROFILE_IMAGE_URL_VALUE_1;
import static woowacourse.Fixtures.TERMS_1;

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
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.entity.CustomerEntity;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final CustomerDao customerDao;

    private Long customerId;
    private Long productId;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
    }

    @BeforeEach
    void setUp() {
        customerId = (long) customerDao.save(
                new CustomerEntity(EMAIL_VALUE_1, PASSWORD_VALUE_1, PROFILE_IMAGE_URL_VALUE_1, TERMS_1));

        productId = productDao.save(
                new Product(PRODUCT_NAME_VALUE_1, PRODUCT_DESCRIPTION_VALUE_1, PRODUCT_PRICE_VALUE_1,
                        PRODUCT_STOCK_VALUE_1, PROFILE_IMAGE_URL_VALUE_1));
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다.")
    @Test
    void addCartItem() {
        // given
        Product product = productDao.findProductById(productId);
        CartItem cartItem = new CartItem(customerId, product, 10);

        // when
        final Long cartId = cartItemDao.addCartItem(customerId, cartItem);

        // then
        assertThat(cartId).isNotNull();
    }

    @DisplayName("카트에 특정 상품이 존재하면 true를 반환한다.")
    @Test
    void isProductExisting_existing() {
        // given
        Product product = productDao.findProductById(productId);
        cartItemDao.addCartItem(customerId, new CartItem(customerId, product, 10));

        // when
        boolean expected = cartItemDao.isProductExisting(customerId, productId);

        // then
        assertThat(expected).isTrue();
    }

    @DisplayName("카트에 특정 상품이 존재하지 않으면 false를 반환한다.")
    @Test
    void isProductExisting_notExisting() {
        // when
        boolean expected = cartItemDao.isProductExisting(customerId, productId + 1);

        // then
        assertThat(expected).isFalse();
    }
}
