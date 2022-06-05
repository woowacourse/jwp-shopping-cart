package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_1;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.JdbcCartItemDao;
import woowacourse.shoppingcart.dao.JdbcCustomerDao;
import woowacourse.shoppingcart.dao.JdbcProductDao;
import woowacourse.shoppingcart.dao.ProductDao;

@JdbcTest
class CartServiceTest {
    private final CartService cartService;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    @Autowired
    CartServiceTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        productDao = new JdbcProductDao(jdbcTemplate);
        customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
        final CartItemDao cartItemDao = new JdbcCartItemDao(jdbcTemplate);
        this.cartService = new CartService(cartItemDao,customerDao,productDao);
    }

    @DisplayName("카트 아이템을 저장한다.")
    @Test
    public void addCart() {
        //given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId = productDao.save(PRODUCT_1);

        // when
        final Long id = cartService.addCart(customerId, productId, 3);

        // then
        assertThat(id).isPositive();
    }
}