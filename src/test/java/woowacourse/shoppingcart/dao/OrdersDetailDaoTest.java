package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.utils.Fixture.customer_id없음;
import static woowacourse.utils.Fixture.치킨_id없음;

import java.util.List;
import javax.sql.DataSource;
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

//    @DisplayName("OrderDatail을 추가하는 기능")
//    @Test
//    void addOrdersDetail() {
//        //given
//        int quantity = 5;
//
//        //when
//        Long orderDetailId = ordersDetailDao
//                .addOrdersDetail(ordersId, productId, quantity);
//
//        //then
//        assertThat(orderDetailId).isEqualTo(1L);
//    }
//
//    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
//    @Test
//    void findOrdersDetailsByOrderId() {
//        //given
//        final int insertCount = 3;
//        for (int i = 0; i < insertCount; i++) {
//            jdbcTemplate
//                    .update("INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (?, ?, ?)",
//                            ordersId, productId, 3);
//        }
//
//        //when
//        final List<OrderDetail> ordersDetailsByOrderId = ordersDetailDao
//                .findOrdersDetailsByOrderId(ordersId);
//
//        //then
//        assertThat(ordersDetailsByOrderId).hasSize(insertCount);
//    }
}
