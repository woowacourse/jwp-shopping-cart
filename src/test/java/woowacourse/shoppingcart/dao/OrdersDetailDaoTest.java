package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

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
}
