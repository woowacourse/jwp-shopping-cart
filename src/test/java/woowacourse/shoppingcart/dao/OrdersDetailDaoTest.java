package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.헌치;

import java.util.HashMap;
import java.util.List;
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
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.repository.dao.CustomerDao;
import woowacourse.shoppingcart.repository.dao.OrderDao;
import woowacourse.shoppingcart.repository.dao.OrdersDetailDao;
import woowacourse.shoppingcart.repository.dao.ProductDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrdersDetailDaoTest {
    private final OrdersDetailDao ordersDetailDao;
    private final OrderDao orderDao;
    private final CustomerDao customerDao;

    public OrdersDetailDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        this.orderDao = new OrderDao(jdbcTemplate);
        this.ordersDetailDao = new OrdersDetailDao(jdbcTemplate);
        this.customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("OrderDatail을 추가하는 기능")
    @Test
    void addOrdersDetail() {
        //given
        Long 헌치아이디 = customerDao.create(헌치);
        Long 주문아이디 = orderDao.create(헌치아이디);
        int quantity = 5;

        //when
        Long orderDetailId = ordersDetailDao.create(주문아이디, 1L, quantity);

        //then
        assertThat(orderDetailId).isEqualTo(1L);
    }
}
