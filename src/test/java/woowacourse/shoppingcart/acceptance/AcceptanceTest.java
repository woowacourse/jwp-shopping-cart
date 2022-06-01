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
        jdbcTemplate.update("truncate table orders_detail");
        jdbcTemplate.update("truncate table cart_item");
        jdbcTemplate.update("truncate table orders");
        jdbcTemplate.update("truncate table product");
        jdbcTemplate.update("truncate table customer");
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS=1");
    }
}
