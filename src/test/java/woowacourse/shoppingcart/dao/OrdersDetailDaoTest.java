package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.product.ProductStock;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrdersDetailDaoTest {

    private final JdbcTemplate jdbcTemplate;
    private final OrdersDetailDao ordersDetailDao;
    private long ordersId;
    private long productId;
    private long customerId;

    public OrdersDetailDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.ordersDetailDao = new OrdersDetailDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        customerId = 1L;
        jdbcTemplate.update("INSERT INTO orders (customer_id) VALUES (?)", customerId);
        ordersId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);

        jdbcTemplate.update(
            "INSERT INTO product (name, price, stock_quantity, thumbnail_url, thumbnail_alt) VALUES (?, ?, ?, ?, ?)"
            , "name", 1000, 10, "imageUrl", "imageAlt");
        productId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);
    }

    @DisplayName("주문할 상품과 수량 저장")
    @Test
    void addAndFind() {
        // given
        ProductStock productStock = jdbcTemplate.queryForObject("SELECT * FROM product WHERE id = ?",
            ProductDao.PRODUCT_STOCK_ROW_MAPPER, productId);
        OrderDetail orderDetail = new OrderDetail(productStock.getProduct(),
            new Quantity(productStock.getStockQuantity()));

        // when
        ordersDetailDao.add(ordersId, orderDetail);

        // then
        assertThat(ordersDetailDao.findOrderDetailsByOrderId(ordersId).size()).isEqualTo(1);
    }
}
