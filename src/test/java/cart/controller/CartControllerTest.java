package cart.controller;

import cart.domain.product.Product;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    private long key;

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        final var simpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
        final var parameterSource = new BeanPropertySqlParameterSource(
                Product.createWithoutId("product1", 100, "url.com"));
        final KeyHolder keyHolder = simpleInsert.executeAndReturnKeyHolder(parameterSource);

        key = keyHolder.getKey().longValue();
    }

    @DisplayName("GET /cart-products 요청시 존재하는 아이디 비밀번호일 시 OK 반환")
    @Test
    void authenticateOk() {
        given().log().all()
                .auth().preemptive().basic("user1@woowa.com", "123456")
                .when()
                .get("/cart-products")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK);
    }

    @DisplayName("GET /cart-products 요청시 존재하지 않는 아이디 비밀번호일 시 unauthorized 반환")
    @Test
    void authenticateBad() {
        given().log().all()
                .auth().preemptive().basic("user1@woowa.co", "123456")
                .when()
                .get("/cart-products")
                .then().log().all()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @DisplayName("POST /cart-products 요청 시 성공하면 status Created 반환")
    @Test
    void deleteCartProductTest() {
        given().log().all()
                .contentType(ContentType.JSON)
                .body(key)
                .auth().preemptive().basic("user1@woowa.com", "123456")
                .when()
                .post("/cart-products")
                .then().log().all()
                .statusCode(HttpStatus.SC_CREATED);
    }

}
