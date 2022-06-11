package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.ImageUrl;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.ProductName;
import woowacourse.shoppingcart.entity.OrderDetailEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrderDetailDaoTest {

    private final OrderDetailDao orderDetailDao;
    private final OrderDao orderDao;
    private final ProductDao productDao;

    private long ordersId;
    private long productId;

    public OrderDetailDaoTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.orderDao = new OrderDao(namedParameterJdbcTemplate);
        this.orderDetailDao = new OrderDetailDao(namedParameterJdbcTemplate);
        this.productDao = new ProductDao(namedParameterJdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        long customerId = 1L;
        ordersId = orderDao.addOrders(customerId);

        productId = productDao.save(new Product(new ProductName("name"), 1000, new ImageUrl("imageUrl"))).getId();
    }

    @DisplayName("OrderDatail을 추가하는 기능")
    @Test
    void addOrdersDetail() {
        //given
        int quantity = 5;

        //when
        Long orderDetailId = orderDetailDao.save(ordersId, productId, quantity);

        //then
        assertThat(orderDetailId).isEqualTo(1L);
    }

    @DisplayName("여러 개의 상품을 주문 테이블에 batch insert 한다.")
    @Test
    void addAllOrdersDetails() {
        long otherProductId = productDao.save(new Product(new ProductName("name"), 1000, new ImageUrl("imageUrl"))).getId();

        final List<OrderDetailEntity> orderDetails = List.of(new OrderDetailEntity(ordersId, productId, 2), new OrderDetailEntity(ordersId, otherProductId, 3));
        final int affectedRows = orderDetailDao.saveAll(orderDetails);

        assertThat(affectedRows).isEqualTo(2);
    }

    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
    @Test
    void findOrdersDetailsByOrderId() {
        //given
        final int insertCount = 3;
        for (int i = 0; i < insertCount; i++) {
            orderDetailDao.save(ordersId, productId, 3);
        }

        //when
        final List<OrderDetail> ordersDetailsByOrderId = orderDetailDao.findOrderDetailsByOrderId(ordersId);

        //then
        assertThat(ordersDetailsByOrderId).hasSize(insertCount);
    }
}
