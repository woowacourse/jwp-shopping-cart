package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
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
class OrdersDetailDaoTest {

    private final OrdersDetailDao ordersDetailDao;

    public OrdersDetailDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.ordersDetailDao = new OrdersDetailDao(jdbcTemplate, dataSource);
    }

    @DisplayName("주문 번호로 상품 번호를 조회한다.")
    @Test
    void findProductIdByOrderId() {
        Long productId = ordersDetailDao.findProductIdByOrderId(1L);

        Assertions.assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("주문 번호로 주문 수량을 조회한다.")
    @Test
    void findQuantityByOrderId() {
        Integer quantity = ordersDetailDao.findQuantityByOrderId(1L);

        Assertions.assertThat(quantity).isEqualTo(3);
    }
}
