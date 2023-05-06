package cart.controller.api;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CartControllerTest {
    private static final String ENCODED_TOKEN = "Z2F2aUB3b293YWhhbi5jb206MTIzNA==";

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        jdbcTemplate.execute("INSERT INTO MEMBER(email, password) VALUES ('gavi@woowahan.com', '1234')");
    }

    @Test
    void 장바구니에_상품을_담을_수_있다() {
        given()
                .log().all().contentType(ContentType.JSON)
                .header("Authorization", "Basic " + ENCODED_TOKEN)
                .when()
                .post("/carts/" + 1)
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 장바구니의_상품을_삭제할_수_있다() {
        given()
                .log().all().contentType(ContentType.JSON)
                .header("Authorization", "Basic " + ENCODED_TOKEN)
                .when()
                .post("/carts/" + 2)
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value());
        given()
                .log().all().contentType(ContentType.JSON)
                .header("Authorization", "Basic " + ENCODED_TOKEN)
                .when()
                .delete("/carts/" + 2)
                .then()
                .log().all()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Test
    void 장바구니_목록을_가져올_수_있다() {
        given()
                .log().all().contentType(ContentType.JSON)
                .header("Authorization", "Basic " + ENCODED_TOKEN)
                .when()
                .get("/carts")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
