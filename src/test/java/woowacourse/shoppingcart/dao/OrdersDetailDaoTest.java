package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.OrderDetail;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrdersDetailDaoTest {

    private final JdbcTemplate jdbcTemplate;
    private final OrdersDetailDao ordersDetailDao;
    private long ordersId;
    private long productId1;
    private long productId2;
    private long customerId;

    public OrdersDetailDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.ordersDetailDao = new OrdersDetailDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        customerId = 1L;
        jdbcTemplate.update("INSERT INTO orders (customer_id) VALUES (?)", customerId);
        ordersId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);

        jdbcTemplate.update("INSERT INTO product (name, price, stock, image_url) VALUES (?, ?, ?, ?)"
                , "name", 1000, 10, "imageUrl");
        productId1 = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);
        jdbcTemplate.update("INSERT INTO product (name, price, stock, image_url) VALUES (?, ?, ?, ?)"
                , "name", 1000, 10, "imageUrl");
        productId2 = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);
    }

    @DisplayName("OrderDatail을 추가하는 기능")
    @Test
    void save() {
        OrderDetail orderDetail = new OrderDetail(5, ordersId, productId1, 1_000, "coffee", "coffee.png");

        Long orderDetailId = ordersDetailDao.save(ordersId, orderDetail);

        assertThat(orderDetailId).isEqualTo(1L);
    }

    @DisplayName("OrderDetail의 리스트를 저장")
    @Test
    void saveAll() {
        OrderDetail orderDetail1 = new OrderDetail(5, ordersId, productId1, 1_000, "coffee", "coffee.png");
        OrderDetail orderDetail2 = new OrderDetail(5, ordersId, productId2, 1_000, "tea", "tea.png");
        ordersDetailDao.saveAll(ordersId, List.of(orderDetail1, orderDetail2));

        assertThat(ordersDetailDao.findAllByCustomerId(customerId).size()).isEqualTo(2);
    }

    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
    @Test
    void findOrderDetailsByOrderId() {
        final int insertCount = 3;
        for (int i = 0; i < insertCount; i++) {
            jdbcTemplate
                    .update("INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (?, ?, ?)",
                            ordersId, productId1, 3);
        }

        final List<OrderDetail> ordersDetailsByOrderId = ordersDetailDao
                .findOrderDetailsByOrderIdAndCustomerId(ordersId, customerId);

        assertThat(ordersDetailsByOrderId).hasSize(insertCount);
    }

}
