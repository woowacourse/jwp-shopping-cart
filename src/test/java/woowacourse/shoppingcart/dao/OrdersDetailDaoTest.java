package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.tuple;
import static woowacourse.utils.Fixture.customer_id없음;
import static woowacourse.utils.Fixture.치킨_id없음;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.product.Product;

@JdbcTest
class OrdersDetailDaoTest {

    @Autowired
    private DataSource dataSource;
    private OrderDao orderDao;
    private OrdersDetailDao ordersDetailDao;
    private CustomerDao customerDao;
    private ProductDao productDao;

    @BeforeEach
    public void setUp() {
        ordersDetailDao = new OrdersDetailDao(dataSource);
        orderDao = new OrderDao(dataSource);
        customerDao = new CustomerDao(dataSource);
        productDao = new ProductDao(dataSource);
    }

    @DisplayName("OrderDetail를 추가하는 기능")
    @Test
    void addOrders() {
        // given
        Customer save = customerDao.save(customer_id없음);
        Long orderId = orderDao.addOrders(save.getId());
        Product save1 = productDao.save(치킨_id없음);

        //when
        ordersDetailDao.addOrdersDetail(new OrderDetail(orderId, save1.getId(), 1000));

        //then
        assertThat(orderId).isNotNull();
    }

    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
    @Test
    void findOrdersDetailsByOrderId() {
        //given
        Customer save = customerDao.save(customer_id없음);
        Long orderId = orderDao.addOrders(save.getId());
        Product save1 = productDao.save(치킨_id없음);
        ordersDetailDao.addOrdersDetail(new OrderDetail(orderId, save1.getId(), 1000));

        //when
        List<OrderDetail> orderDetails = ordersDetailDao
                .findOrdersDetailsByOrderId(orderId);

        //then
        assertThat(orderDetails).hasSize(1)
                .extracting("ordersId", "productId", "quantity")
                .containsExactly(tuple(orderId, save1.getId(), 1000));
    }
}
