package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrderDaoTest {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final OrderDao orderDao;

    public OrderDaoTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.orderDao = new OrderDao(namedParameterJdbcTemplate);
    }

    @DisplayName("Order를 추가하는 기능")
    @Test
    void addOrders() {
        //given
        final Long customerId = 1L;

        //when
        final Long orderId = orderDao.addOrders(customerId);

        //then
        assertThat(orderId).isNotNull();
    }

    @DisplayName("CustomerId 집합을 이용하여 OrderId 집합을 얻는 기능")
    @Test
    void findOrderIdsByCustomerId() {
        //given
        final Long customerId = 1L;
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);
        namedParameterJdbcTemplate.update("INSERT INTO orders (customer_id) VALUES (:customerId)",
                new MapSqlParameterSource(parameters));
        namedParameterJdbcTemplate.update("INSERT INTO orders (customer_id) VALUES (:customerId)",
                new MapSqlParameterSource(parameters));

        //when
        final List<Long> orderIdsByCustomerId = orderDao.findOrderIdsByCustomerId(customerId);

        //then
        assertThat(orderIdsByCustomerId).hasSize(2);
    }

    @Test
    @DisplayName("유저에게 해당하는 order id 가 없으면 예외를 반환한다.")
    void isValidOrderId() {
        //given
        final Long customerId = 1L;
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);
        namedParameterJdbcTemplate.update("INSERT INTO orders (customer_id) VALUES (:customerId)",
                new MapSqlParameterSource(parameters));
        //when

        //then
        assertThat(orderDao.isValidOrderId(customerId, 1L)).isTrue();
    }
}
