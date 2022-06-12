package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.support.test.ExtendedJdbcTest;

@ExtendedJdbcTest
class OrderDetailDaoTest {

    private final JdbcTemplate jdbcTemplate;
    private final OrderDetailDao orderDetailDao;
    private long orderId;
    private long productId;
    private long customerId;

    public OrderDetailDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderDetailDao = new OrderDetailDao(new ProductDao(jdbcTemplate), jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        customerId = 1L;
        jdbcTemplate.update("INSERT INTO orders (customer_id) VALUES (?)", customerId);
        orderId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);

        jdbcTemplate.update("INSERT INTO product (name, price, image_url, is_deleted) VALUES (?, ?, ?, 0)"
            , "name", 1000, "http://imageUrl.com");
        productId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);
    }

    @DisplayName("OrderDatail을 추가하는 기능")
    @Test
    void addOrdersDetail() {
        //given
        final OrderDetail orderDetail = new OrderDetail(5,
            new Product(1L, "productname", 1000, "http://example.com", "some-description"));

        //when
        Long orderDetailId = orderDetailDao.addOrderDetail(orderId, orderDetail);

        //then
        assertThat(orderDetailId).isEqualTo(1L);
    }

    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
    @Test
    void findOrdersDetailsByOrderId() {
        //given
        final int insertCount = 3;
        for (int i = 0; i < insertCount; i++) {
            jdbcTemplate
                .update("INSERT INTO order_detail (order_id, product_id, quantity) VALUES (?, ?, ?)",
                    orderId, productId, 3);
        }

        //when
        final List<OrderDetail> orderDetails = orderDetailDao.findOrderDetailsByOrderId(orderId);

        //then
        assertThat(orderDetails).hasSize(insertCount);
    }
}
