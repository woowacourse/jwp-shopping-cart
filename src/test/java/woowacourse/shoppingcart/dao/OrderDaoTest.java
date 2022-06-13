package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.sql.DataSource;
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
@Sql(scripts = {"/initSchema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrderDaoTest {

    private final OrderDao orderDao;

    public OrderDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.orderDao = new OrderDao(jdbcTemplate, dataSource);
    }

    @DisplayName("Order를 추가한다.")
    @Test
    void addOrders() {
        final Long orderId = orderDao.save(1L);

        //then
        assertThat(orderId).isNotNull();
    }

    @DisplayName("Orders를 조회한다.")
    @Test
    void findOrderIdsByCustomerId() {
        //given
        Long customerId = 1L;
        orderDao.save(1L);
        orderDao.save(1L);

        //when
        List<Long> orderIdsByCustomerId = orderDao.findOrderIdsByCustomerId(customerId);

        //then
        assertThat(orderIdsByCustomerId).containsExactly(1L, 2L, 3L);
    }

}
