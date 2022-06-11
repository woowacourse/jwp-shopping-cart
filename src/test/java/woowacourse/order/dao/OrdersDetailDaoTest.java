package woowacourse.order.dao;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.customer.dao.CustomerDao;
import woowacourse.customer.domain.Customer;
import woowacourse.product.dao.ProductDao;
import woowacourse.product.domain.Price;
import woowacourse.product.domain.Product;
import woowacourse.product.domain.Stock;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrdersDetailDaoTest {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final ProductDao productDao;
    private final CustomerDao customerDao;
    private long orderId;
    private long productId;
    private long customerId;

    public OrdersDetailDaoTest(final DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
        this.ordersDetailDao = new OrdersDetailDao(dataSource);
        this.orderDao = new OrderDao(dataSource);
        this.customerDao = new CustomerDao(dataSource);
    }

    @BeforeEach
    void setUp() {
        final Customer customer = Customer.of("username", "password1", "01011112222", "서울시");
        customerId = customerDao.save(customer).getId();
        orderId = orderDao.save(customerId);
        productId = productDao.save(new Product("name", new Price(10000), new Stock(10), "test.jpg"));
    }

    @DisplayName("OrderDatail을 추가하는 기능")
    @Test
    void addOrdersDetail() {
        //given
        final int quantity = 5;

        //when
        final Long orderDetailId = ordersDetailDao.save(orderId, productId, quantity);

        //then
        assertThat(orderDetailId).isEqualTo(1L);
    }
}
