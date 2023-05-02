package cart.controller;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.Matchers.stringContainsInOrder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(@LocalServerPort final int port) {
        RestAssured.port = port;
    }

    @AfterEach
    void clear() {
        jdbcTemplate.execute("TRUNCATE TABLE product");
    }


    @DisplayName("GET /admin")
    @Test
    void getAdmin() {
        productDao.insert(new Product("이오", 1000, null));
        productDao.insert(new Product("애쉬", 2000, null));

        RestAssured.given().log().all()
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(stringContainsInOrder("이오", "1000", "애쉬", "2000"));
    }
}
