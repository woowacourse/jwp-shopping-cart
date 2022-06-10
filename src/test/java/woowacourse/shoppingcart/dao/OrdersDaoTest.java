package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static woowacourse.fixture.Fixture.PRICE;
import static woowacourse.fixture.Fixture.PRODUCT_NAME;
import static woowacourse.fixture.Fixture.QUANTITY;
import static woowacourse.fixture.Fixture.TEST_EMAIL;
import static woowacourse.fixture.Fixture.TEST_PASSWORD;
import static woowacourse.fixture.Fixture.TEST_USERNAME;
import static woowacourse.fixture.Fixture.THUMBNAIL_URL;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.OrdersDetail;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrdersDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductDao productDao;
    private CustomerDao customerDao;
    private OrdersDao ordersDao;

    private Product product;
    private Long customerId;
    private List<OrdersDetail> ordersDetails;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
        customerDao = new CustomerDao(jdbcTemplate);
        ordersDao = new OrdersDao(jdbcTemplate, new OrdersDetailDao(jdbcTemplate, productDao));

        final Product tempProduct = Product.createWithoutId(PRODUCT_NAME, PRICE, THUMBNAIL_URL, QUANTITY);
        final Long productId = productDao.save(tempProduct);
        product = new Product(
                productId,
                tempProduct.getName(),
                tempProduct.getPrice(),
                tempProduct.getThumbnailUrl(),
                tempProduct.getQuantity()
        );
        customerId = customerDao.save(Customer.createWithoutId(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));
        ordersDetails = List.of(OrdersDetail.createWithoutId(product, 1));
    }

    @Test
    @DisplayName("사용자의 주문 정보를 저장한다.")
    void save() {
        // given
        final Orders orders = Orders.createWithoutId(customerId, ordersDetails);

        // when
        final Long savedId = ordersDao.save(orders);

        // then
        final Orders saved = ordersDao.findById(savedId).get();
        assertAll(
                () -> assertThat(saved.getCustomerId()).isEqualTo(orders.getCustomerId()),
                () -> assertThat(saved.getOrdersDetails().get(0).getProductId()).isEqualTo(product.getId())
        );
    }
}