package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import woowacourse.setup.DatabaseTest;
import woowacourse.shoppingcart.entity.OrderDetailEntity;

@SuppressWarnings("NonAsciiCharacters")
class OrderDetailDaoTest extends DatabaseTest {

    private static final Long 주문_ID = 100L;
    private static final Long 호박_ID = 1L;
    private static final Long 고구마_ID = 2L;
    private static final Long 호박고구마_ID = 3L;

    private final OrderDetailDao orderDetailDao;

    public OrderDetailDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        orderDetailDao = new OrderDetailDao(jdbcTemplate);
    }

    @Test
    void findAll_메서드는_저장된_모든_데이터를_조회한다() {
        OrderDetailEntity 호박_3개 = new OrderDetailEntity(1L, 주문_ID, 호박_ID, 3);
        OrderDetailEntity 고구마_5개 = new OrderDetailEntity(2L, 주문_ID, 고구마_ID, 5);
        OrderDetailEntity 호박고구마_1개 = new OrderDetailEntity(3L, 주문_ID, 호박고구마_ID, 1);
        orderDetailDao.save(List.of(호박_3개, 고구마_5개, 호박고구마_1개));

        List<OrderDetailEntity> actual = orderDetailDao.findAll();
        List<OrderDetailEntity> expected = List.of(호박_3개, 고구마_5개, 호박고구마_1개);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("save 메서드는 리스트에 담긴 데이터를 일괄 저장")
    @Nested
    class SaveTest {

        @Test
        void 저장_성공() {
            OrderDetailEntity 호박_3개 = new OrderDetailEntity(null, 주문_ID, 호박_ID, 3);
            OrderDetailEntity 고구마_5개 = new OrderDetailEntity(null, 주문_ID, 고구마_ID, 5);
            OrderDetailEntity 호박고구마_1개 = new OrderDetailEntity(null, 주문_ID, 호박고구마_ID, 1);

            orderDetailDao.save(List.of(호박_3개, 고구마_5개, 호박고구마_1개));
            int savedData = orderDetailDao.findAll().size();

            assertThat(savedData).isEqualTo(3);
        }

        @Test
        void 같은_주문에_대해_동일한_상품을_중복으로_저장하려는_경우_예외() {
            OrderDetailEntity 호박_3개 = new OrderDetailEntity(null, 주문_ID, 호박_ID, 3);
            OrderDetailEntity 호박_5개 = new OrderDetailEntity(null, 주문_ID, 호박_ID, 5);

            assertThatThrownBy(() -> orderDetailDao.save(List.of(호박_3개, 호박_5개)))
                    .isInstanceOf(DataAccessException.class);
        }
    }
}
