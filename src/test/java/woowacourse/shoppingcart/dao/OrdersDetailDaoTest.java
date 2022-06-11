package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.order.Order;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.dto.OrderRequest;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrdersDetailDaoTest {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final OrdersDetailDao ordersDetailDao;
    private long ordersId;
    private long productId;
    private long customerId;

    public OrdersDetailDaoTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.ordersDetailDao = new OrdersDetailDao(namedParameterJdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        customerId = 1L;
        insertOrders();
        ordersId = Objects.requireNonNull(
                namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", new HashMap<>(), Long.class));

        insertProduct("milk", 1_000, "milk.com");
        productId = namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", new HashMap<>(), Long.class);
    }

    private void insertOrders() {
        final Map<String, Object> orderParameters = new HashMap<>();
        orderParameters.put("customerId", customerId);
        namedParameterJdbcTemplate.update("INSERT INTO orders (customer_id) VALUES (:customerId)",
                new MapSqlParameterSource(orderParameters));
    }

    @DisplayName("OrderDetail 을 추가하는 기능")
    @Test
    void addOrdersDetail() {
        //given
        int quantity = 5;

        //when
        Long ordersDetailId = ordersDetailDao
                .addOrdersDetail(ordersId, productId, quantity);

        //then
        assertThat(ordersDetailId).isEqualTo(1L);
    }

    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
    @Test
    void findOrdersDetailsByOrderId() {
        //given
        insertProduct("banana", 1_500, "banana.com");
        long productId2 = namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", new HashMap<>(),
                Long.class);

        ordersDetailDao.addOrdersDetail(ordersId, productId, 3);
        ordersDetailDao.addOrdersDetail(ordersId, productId2, 5);
        //when

        final List<OrderDetail> ordersDetails = ordersDetailDao
                .findOrdersDetailsByOrderId(ordersId);

        //then
        assertThat(ordersDetails).hasSize(2);

    }

    @Test
    void addAllOrderDetails() {
        //given
        insertProduct("banana", 1_500, "banana.com");
        long productId2 = namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", new HashMap<>(),
                Long.class);

        //when

        Order bananaOrder = new Order(ordersId, productId, 3);
        Order appleOrder = new Order(ordersId, productId2, 5);
        int affectedQuery = ordersDetailDao.addAllOrdersDetails(List.of(bananaOrder, appleOrder));
        //then
        assertThat(affectedQuery).isEqualTo(2);
    }

    private void insertProduct(String name, int price, String imageUrl) {
        final Map<String, Object> productParameters = new HashMap<>();
        productParameters.put("name", name);
        productParameters.put("price", price);
        productParameters.put("imageUrl", imageUrl);
        namedParameterJdbcTemplate.update(
                "INSERT INTO product (name, price, image_url) VALUES (:name, :price, :imageUrl)",
                new MapSqlParameterSource(productParameters));
    }
}
