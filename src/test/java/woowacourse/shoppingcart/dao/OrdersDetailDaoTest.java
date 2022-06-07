package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_2;

import java.util.List;
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
import woowacourse.shoppingcart.entity.OrdersDetailEntity;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrdersDetailDaoTest {

    private final OrdersDetailDao ordersDetailDao;
    private final ProductDao productDao;
    private final CustomerDao customerDao;
    private final OrderDao orderDao;
    private long ordersId;
    private long productId1;
    private long productId2;

    public OrdersDetailDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.ordersDetailDao = new JdbcOrdersDetailDao(jdbcTemplate);
        this.productDao = new JdbcProductDao(jdbcTemplate);
        this.customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
        this.orderDao = new JdbcOrderDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        int customerId = customerDao.save(CUSTOMER_1);
        productId1 = productDao.save(PRODUCT_1);
        productId2 = productDao.save(PRODUCT_2);
        ordersId = orderDao.addOrders(customerId);
    }

    @DisplayName("OrderDatail을 추가하는 기능")
    @Test
    void addOrdersDetail() {
        //given
        int quantity = 5;

        //when
        Long orderDetailId = ordersDetailDao.addOrdersDetail(ordersId, productId1, quantity);

        //then
        assertThat(orderDetailId).isPositive();
    }

    @DisplayName("없는 성품을 추가하면 예외가 발생한다.")
    @Test
    void addOrdersDetailByInvalidProductId() {
        //given
        int quantity = 5;

        //when
        assertThatThrownBy(() -> ordersDetailDao.addOrdersDetail(ordersId, 9999999999L, quantity))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
    @Test
    void findOrdersDetailsByOrderId() {
        //given
        Long orderDetailId1 = ordersDetailDao.addOrdersDetail(ordersId, productId1, 3);
        Long orderDetailId2 = ordersDetailDao.addOrdersDetail(ordersId, productId2, 4);

        //when
        final List<OrdersDetailEntity> ordersDetailsByOrderId = ordersDetailDao.findOrdersDetailsByOrderId(ordersId);

        //then
        assertThat(ordersDetailsByOrderId)
                .extracting("id", "orders_id", "product_id", "quantity")
                .containsExactly(
                        tuple(orderDetailId1, ordersId, productId1, 3),
                        tuple(orderDetailId2, ordersId, productId2, 4)
                );
    }
}
