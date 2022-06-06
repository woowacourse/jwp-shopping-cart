package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_1;
import static woowacourse.shoppingcart.fixture.OrderFixtures.ORDER_REQUEST_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_2;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.JdbcCustomerDao;
import woowacourse.shoppingcart.dao.JdbcOrderDao;
import woowacourse.shoppingcart.dao.JdbcOrdersDetailDao;
import woowacourse.shoppingcart.dao.JdbcProductDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.OrderRequest;

@JdbcTest
class OrderServiceTest {
    private final OrderService orderService;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    @Autowired
    public OrderServiceTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        final OrderDao orderDao = new JdbcOrderDao(jdbcTemplate);
        final OrdersDetailDao ordersDetailDao = new JdbcOrdersDetailDao(jdbcTemplate);

        this.customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
        this.productDao = new JdbcProductDao(jdbcTemplate);
        this.orderService = new OrderService(orderDao, productDao, ordersDetailDao);
    }

    @DisplayName("주문을 추가한다.")
    @Test
    public void addOrders() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long productId2 = productDao.save(PRODUCT_2);

        // when
        final Long orderId = orderService.addOrders(customerId,
                new OrderRequest(List.of(new CartRequest(productId1, 3), new CartRequest(productId2, 5))));

        // then
        assertThat(orderId).isPositive();
    }

    @DisplayName("없는 아이템을 추가 시 에외가 발생한다.")
    @Test
    public void addOrdersByNotExistProduct() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        productDao.save(PRODUCT_1);

        // when & then
        assertThatThrownBy(() -> orderService.addOrders(customerId, ORDER_REQUEST_1))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }


}