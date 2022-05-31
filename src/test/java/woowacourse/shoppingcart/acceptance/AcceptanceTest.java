package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS=0");
        jdbcTemplate.update("truncate from orders_detail");
        jdbcTemplate.update("truncate from cart_item");
        jdbcTemplate.update("truncate from orders");
        jdbcTemplate.update("truncate from product");
        jdbcTemplate.update("truncate from customer");
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS=1");
//        jdbcTemplate.update("delete from orders_detail");
//        jdbcTemplate.update("delete from cart_item");
//        jdbcTemplate.update("delete from product");
//        jdbcTemplate.update("delete from orders");
//        jdbcTemplate.update("delete from customer");
    }
}
