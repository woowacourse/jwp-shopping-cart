package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.entity.CustomerEntity;
import woowacourse.shoppingcart.dao.entity.OrdersEntity;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = "classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings({"NonAsciiCharacters"})
class OrdersDaoTest {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrdersDao ordersDao;
    private final CustomerDao customerDao;

    public OrdersDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.ordersDao = new OrdersDao(jdbcTemplate);
        this.customerDao = new CustomerDao(jdbcTemplate);
    }

    @Test
    void 주문_추가() {
        //given
        final Long customerId = customerDao.save(new CustomerEntity(null, "yeonlog", "연로그", "123abc!A",
                "연로그네", "01011112222"));

        //when
        final Long orderId = ordersDao.save(customerId);

        //then
        assertThat(orderId).isNotNull();
    }

    @DisplayName("CustomerId 집합을 이용하여 OrderId 집합을 얻는 기능")
    @Test
    void findOrderIdsByCustomerId() {
        //given
        final Long customerId = customerDao.save(new CustomerEntity(null, "yeonlog", "연로그", "123abc!A",
                "연로그네", "01011112222"));
        SqlParameterSource source = new MapSqlParameterSource("id", customerId);
        jdbcTemplate.update("INSERT INTO ORDERS (customer_id) VALUES (:id)", source);
        jdbcTemplate.update("INSERT INTO ORDERS (customer_id) VALUES (:id)", source);

        //when
        final List<Long> orderIdsByCustomerId = ordersDao.findByCustomerId(customerId)
                .stream()
                .map(OrdersEntity::getId)
                .collect(Collectors.toUnmodifiableList());

        //then
        assertThat(orderIdsByCustomerId).hasSize(2);
    }
}
