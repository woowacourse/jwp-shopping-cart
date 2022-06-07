package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.entity.OrdersDetailEntity;
import woowacourse.shoppingcart.dao.entity.ProductEntity;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrdersDetailDaoTest {

    private final OrdersDetailDao ordersDetailDao;
    private final OrdersDao ordersDao;
    private final ProductDao productDao;
    private long ordersId;
    private long productId;

    public OrdersDetailDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        this.ordersDetailDao = new OrdersDetailDao(jdbcTemplate);
        this.ordersDao = new OrdersDao(jdbcTemplate);
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        productId = productDao.save(new ProductEntity("name", 1_000, "imageUrl"));
        ordersId = ordersDao.save(1L);
    }

    @DisplayName("OrderDatail을 추가하는 기능")
    @Test
    void addOrdersDetail() {
        //given
        int quantity = 5;

        //when
        Long orderDetailId = ordersDetailDao.save(new OrdersDetailEntity(null, ordersId, productId, quantity));

        //then
        assertThat(orderDetailId).isEqualTo(1L);
    }

    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
    @Test
    void findOrdersDetailsByOrderId() {
        //given
        final int insertCount = 3;
        for (int i = 0; i < insertCount; i++) {
            ordersDetailDao.save(new OrdersDetailEntity(null, ordersId, productId, 3));
        }

        //when
        final List<OrdersDetailEntity> ordersDetailsByOrderId = ordersDetailDao.findByOrderId(ordersId);

        //then
        assertThat(ordersDetailsByOrderId).hasSize(insertCount);
    }
}
