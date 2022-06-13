package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrderDaoTest {

    private final JdbcTemplate jdbcTemplate;
    private final OrderDao orderDao;

    public OrderDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderDao = new OrderDao(jdbcTemplate);
    }

    @DisplayName("Order를 추가하는 기능")
    @Test
    void addOrders() {
        final Long customerId = 1L;

        final Long orderId = orderDao.save(customerId);

        assertThat(orderId).isNotNull();
    }

    @DisplayName("CustomerId 집합을 이용하여 OrderId 집합을 얻는 기능")
    @Test
    void findOrderIdsByCustomerId() {
        final Long customerId = 1L;
        jdbcTemplate.update("INSERT INTO ORDERS (customer_id) VALUES (?)", customerId);
        jdbcTemplate.update("INSERT INTO ORDERS (customer_id) VALUES (?)", customerId);

        final List<Long> orderIdsByCustomerId = orderDao.findOrderIdsByCustomerId(customerId);

        assertThat(orderIdsByCustomerId).hasSize(2);
    }

}
