package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.fixture.ProductFixture.PRODUCT_APPLE;
import static woowacourse.fixture.ProductFixture.PRODUCT_BANANA;

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
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Quantity;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrdersDetailDaoTest {

    private final JdbcTemplate jdbcTemplate;
    private final OrdersDetailDao ordersDetailDao;
    private long ordersId1;
    private long ordersId2;
    private long productId;

    public OrdersDetailDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.ordersDetailDao = new OrdersDetailDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        final long customerId = 1L;
        jdbcTemplate.update("INSERT INTO orders (customer_id) VALUES (?)", customerId);
        ordersId1 = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);
        jdbcTemplate.update("INSERT INTO orders (customer_id) VALUES (?)", customerId);
        ordersId2 = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);

        jdbcTemplate.update("INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)"
                , "banana", 1_000, "woowa1.com");
        jdbcTemplate.update("INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)"
                , "apple", 2_000, "woowa2.com");
        productId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);
    }

    @DisplayName("OrderDatail 여러개를 한번에 추가하는 기능")
    @Test
    void batchAddOrdersDetail() {
        //given
        List<Cart> carts = List.of(
                new Cart(1L, new Quantity(5), PRODUCT_BANANA),
                new Cart(2L, new Quantity(10), PRODUCT_APPLE)
        );
        //when
        ordersDetailDao.batchAddOrdersDetail(ordersId1, carts);
        final List<OrderDetail> ordersDetailsByOrderId = ordersDetailDao
                .findOrdersDetailsJoinProductByOrderId(ordersId1);

        //then
        assertThat(ordersDetailsByOrderId).hasSize(2);
    }

    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
    @Test
    void findOrdersDetailsByOrderId() {
        //given
        final int insertCount = 3;
        for (int i = 0; i < insertCount; i++) {
            jdbcTemplate
                    .update("INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (?, ?, ?)",
                            ordersId1, productId, 3);
        }

        //when
        final List<OrderDetail> ordersDetailsByOrderId = ordersDetailDao
                .findOrdersDetailsJoinProductByOrderId(ordersId1);

        //then
        assertThat(ordersDetailsByOrderId).hasSize(insertCount);
    }

    @DisplayName("여러개의 OrderId로 해당 하는 모든 OrderDetails를 조회하는 기능")
    @Test
    void findAllJoinProductByOrderIds() {
        //given
        final int firstInsertCount = 3;
        for (int i = 0; i < firstInsertCount; i++) {
            jdbcTemplate
                    .update("INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (?, ?, ?)",
                            ordersId1, productId, 3);
        }

        final int secondInsertCount = 2;
        for (int i = 0; i < secondInsertCount; i++) {
            jdbcTemplate
                    .update("INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (?, ?, ?)",
                            ordersId2, productId, 3);
        }

        //when
        final List<OrderDetail> ordersDetailsByOrderId =
                ordersDetailDao.findAllJoinProductByOrderIds(List.of(ordersId1, ordersId2));

        //then
        assertThat(ordersDetailsByOrderId).hasSize(firstInsertCount + secondInsertCount);
    }
}
