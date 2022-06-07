package woowacourse.shoppingcart.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.JdbcCustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.ProductExistingInCartResponse;
import woowacourse.shoppingcart.entity.CustomerEntity;

@JdbcTest
class CartServiceTest {
    private final CartService cartService;
    private final ProductDao productDao;
    private final CustomerDao customerDao;

    private Long customerId;
    private Long productId;

    @Autowired
    public CartServiceTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        productDao = new ProductDao(jdbcTemplate);
        customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);

        cartService = new CartService(new CartItemDao(jdbcTemplate), productDao);
    }

    @BeforeEach
    void setUp() {
        customerId = (long) customerDao.save(
                new CustomerEntity(EMAIL_VALUE_1, PASSWORD_VALUE_1, PROFILE_IMAGE_URL_VALUE_1, TERMS_1));

        productId = productDao.save(
                new Product(PRODUCT_NAME_VALUE_1, PRODUCT_DESCRIPTION_VALUE_1, PRODUCT_PRICE_VALUE_1,
                        PRODUCT_STOCK_VALUE_1, PROFILE_IMAGE_URL_VALUE_1));
    }

    @DisplayName("특정 유저가 특정 품목을 장바구니에 담았는지 확인")
    @Test
    void isProductExisting() {
        // given
        cartService.addCart(new CartItemRequest(productId, 10), customerId);

        // when
        ProductExistingInCartResponse actual = cartService.isProductExisting(customerId, productId);

        // then
        assertThat(actual.isExists()).isTrue();
    }
}
