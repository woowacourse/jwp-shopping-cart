package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.OrderedProduct;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.ThumbnailImage;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrderedProductDaoTest {

    private final JdbcTemplate jdbcTemplate;
    private final OrderedProductDao orderedProductDao;
    private long ordersId;
    private long productId;
    private long customerId;

    public OrderedProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderedProductDao = new OrderedProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        customerId = 1L;
        jdbcTemplate.update("INSERT INTO orders (customer_id) VALUES (?)", customerId);
        ordersId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);

        jdbcTemplate.update("INSERT INTO product (name, price, stock_quantity) VALUES (?, ?, ?)"
                , "name", 1000, 10);
        jdbcTemplate.update("INSERT INTO thumbnail_image (product_id, url, alt) VALUES (?, ?, ?)"
                , 1L, "url", "alt");
        productId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);
    }

    @DisplayName("OrderDatail을 추가하는 기능")
    @Test
    void addOrdersDetail() {
        //given
        ThumbnailImage image = new ThumbnailImage("url", "alt");
        Product product = new Product(1L, "name", 1000, 10, image);
        CartItem cartItem = new CartItem(1L, 10, product);

        //when
        Long orderDetailId = orderedProductDao.save(ordersId, cartItem);

        //then
        assertThat(orderDetailId).isEqualTo(1L);
    }

    @DisplayName("OrderId로 OrderDetails 조회하는 기능")
    @Test
    void findOrdersDetailsByOrderId() {
        //given
        final int insertCount = 3;
        for (int i = 0; i < insertCount; i++) {
            jdbcTemplate
                    .update("INSERT INTO ordered_product (orders_id, product_id, quantity) VALUES (?, ?, ?)",
                            ordersId, productId, 3);
        }

        //when
        final List<OrderedProduct> ordersDetailsByOrderId = orderedProductDao
                .getAllByOrderId(ordersId);

        //then
        assertThat(ordersDetailsByOrderId).hasSize(insertCount);
    }
}
