package woowacourse.shoppingcart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.헌치;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.repository.dao.CustomerDao;
import woowacourse.shoppingcart.repository.dao.OrderDao;
import woowacourse.shoppingcart.repository.dao.OrdersDetailDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class OrderRepositoryTest {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate) {
        this.orderRepository = new OrderRepository(new OrderDao(jdbcTemplate), new OrdersDetailDao(jdbcTemplate));
        this.customerRepository = new CustomerRepository(new CustomerDao(jdbcTemplate));
    }

    @DisplayName("OrderDatail을 추가하는 기능")
    @Test
    void create() {
        //given
        int quantity = 5;
        Long 헌치아이디 = customerRepository.create(헌치);

        //when
        Long 주문아이디 = orderRepository.create(헌치아이디, 1L, quantity);

        //then
        assertThat(주문아이디).isEqualTo(1L);
    }
}
