package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import woowacourse.setup.DatabaseTest;
import woowacourse.shoppingcart.entity.OrderEntity;

@SuppressWarnings("NonAsciiCharacters")
class OrderDaoTest extends DatabaseTest {

    private final OrderDao orderDao;

    public OrderDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        orderDao = new OrderDao(jdbcTemplate);
    }

    @Test
    void save_메서드는_데이터를_저장하고_생성된_id를_포함한_엔티티를_반환한다() {
        long 고객_ID = 100L;
        OrderEntity 고객의_주문 = new OrderEntity(null, 고객_ID);

        OrderEntity actual  = orderDao.save(고객의_주문);
        OrderEntity expected  = new OrderEntity(1L, 고객_ID);

        assertThat(actual).isEqualTo(expected);
    }
}
