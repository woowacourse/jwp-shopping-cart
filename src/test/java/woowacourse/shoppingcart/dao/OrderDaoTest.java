package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import woowacourse.shoppingcart.domain.customer.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrderDaoTest {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final OrderDao orderDao;
    private final CustomerDao customerDao;

    private long customerId;

    public OrderDaoTest(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.customerDao = new CustomerDao(this.namedParameterJdbcTemplate);
        this.orderDao = new OrderDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        Customer customer = customerDao.save(new Customer("abc@abc.com", "abc", "010-0000-0000", "seoul", "Test1234!"));
        customerId = customer.getId();
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
        jdbcTemplate.update("INSERT INTO ORDERS (customer_id) VALUES (?)", customerId);
        jdbcTemplate.update("INSERT INTO ORDERS (customer_id) VALUES (?)", customerId);

        //when
        final List<Long> orderIdsByCustomerId = orderDao.findOrderIdsByCustomerId(customerId);

        //then
        assertThat(orderIdsByCustomerId).hasSize(2);
    }

}
