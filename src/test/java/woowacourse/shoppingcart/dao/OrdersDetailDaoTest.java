package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Page;
import woowacourse.shoppingcart.domain.Product;
import java.util.List;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrdersDetailDaoTest {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final ProductDao productDao;
    private long ordersId;
    private long productId;
    private long customerId;

    public OrdersDetailDaoTest(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.orderDao = new OrderDao(namedParameterJdbcTemplate);
        this.ordersDetailDao = new OrdersDetailDao(namedParameterJdbcTemplate);
        this.productDao = new ProductDao(namedParameterJdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        customerId = 1L;
        ordersId = orderDao.addOrders(customerId);
        productDao.save(new Product("name", 1000, "imageUrl"));
        final List<Product> products = productDao.findProducts(Page.of(1, 10));
        productId = products.get(products.size() - 1).getId();
    }

    @DisplayName("OrderDatail을 추가하는 기능")
    @Test
    void addOrdersDetail() {
        //given
        int quantity = 5;

        //when
        Long orderDetailId = ordersDetailDao
                .addOrdersDetail(ordersId, productId, quantity);

        //then
        assertThat(orderDetailId).isEqualTo(1L);
    }

    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
    @Test
    void findOrdersDetailsByOrderId() {
        //given
        final int insertCount = 3;
        for (int i = 0; i < insertCount; i++) {
            ordersDetailDao.addOrdersDetail(ordersId, productId, 3);
        }

        //when
        final List<OrderDetail> ordersDetailsByOrderId = ordersDetailDao
                .findAllByOrderId(ordersId);

        //then
        assertThat(ordersDetailsByOrderId).hasSize(insertCount);
    }
}
