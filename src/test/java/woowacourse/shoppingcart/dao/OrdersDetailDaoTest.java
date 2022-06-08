package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.OrderDetail;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("customerid", customerId);
        namedParameterJdbcTemplate.update("INSERT INTO orders (customer_id) VALUES (:customerid)", parameterSource);

        MapSqlParameterSource parameterSource2 = new MapSqlParameterSource();
        ordersId = namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", parameterSource2, Long.class);

        MapSqlParameterSource parameterSource3 = new MapSqlParameterSource("name", "name");
        parameterSource3.addValue("price", 1000);
        parameterSource3.addValue("imageurl", "imageurl");
        namedParameterJdbcTemplate.update("INSERT INTO product (name, price, image_url) VALUES (:name, :price, :imageurl)", parameterSource3);
        productId = namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", parameterSource2, Long.class);
    }

    @DisplayName("OrderDatail을 추가하는 기능")
    @Test
    void addOrdersDetail() {
        //given
        int quantity = 5;

        //when
        Long orderDetailId = ordersDetailDao
                .addOrdersDetail(ordersId, productId, quantity);

        //then
        assertThat(orderDetailId).isEqualTo(1L);
    }

    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
    @Test
    void findOrdersDetailsByOrderId() {
        //given
        final int insertCount = 3;
        for (int i = 0; i < insertCount; i++) {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource("orderid", ordersId);
            parameterSource.addValue("productid", productId);
            parameterSource.addValue("quantity", 3);
            namedParameterJdbcTemplate.
                    update("INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (:orderid, :productid, :quantity)",
                            parameterSource);
        }

        //when
        final List<OrderDetail> ordersDetailsByOrderId = ordersDetailDao
                .findOrdersDetailsByOrderId(ordersId);

        //then
        assertThat(ordersDetailsByOrderId).hasSize(insertCount);
    }
}
