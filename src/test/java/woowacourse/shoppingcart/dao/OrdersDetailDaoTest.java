package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;

import javax.sql.DataSource;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrdersDetailDaoTest {

    private final JdbcTemplate jdbcTemplate;
    private final OrdersDetailDao ordersDetailDao;
    private final ProductDao productDao;
    private final OrderDao orderDao;

    public OrdersDetailDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.ordersDetailDao = new OrdersDetailDao(jdbcTemplate);
        this.productDao = new ProductDao(dataSource);
        this.orderDao = new OrderDao(jdbcTemplate);
    }

    @DisplayName("OrderDatail을 추가하는 기능")
    @Test
    void addOrdersDetail() {
        //given
        Product product = productDao.save(new Product("치킨", 1000, "test"));
        Long orderId = orderDao.addOrders(1L);

        //when
        Long orderDetailId = ordersDetailDao.addOrdersDetail(orderId, product.getId(), 5);

        //then
        assertThat(orderDetailId).isEqualTo(1L);
    }

    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
    @Test
    void findOrdersDetailsByOrderId() {
        //given
        Product product = productDao.save(new Product("치킨", 1000, "test"));
        Long orderId = orderDao.addOrders(1L);

        Product product2 = productDao.save(new Product("피자", 1000, "test"));

        //when
        ordersDetailDao.addOrdersDetail(orderId, product.getId(), 5);
        ordersDetailDao.addOrdersDetail(orderId, product2.getId(), 7);

        //then
        assertThat(ordersDetailDao.findOrdersDetailsByOrderId(orderId).size()).isEqualTo(2);

    }
}