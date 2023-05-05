package cart.controller;

import io.restassured.RestAssured;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("classpath:test.sql")
class CartResourceControllerTest {

    private static final String USERNAME = "user1@email.com";
    private static final String PASSWORD = "password1";
    @LocalServerPort
    private int port;


    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void 상품_조회() {
        RestAssured.given()
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .get("/cart/items")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 상품_추가() throws JSONException {
        final int productId = 1;

        RestAssured.given()
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .post("/cart/items/" + productId)
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 상품_삭제() {
        final int productId = 1;

        RestAssured.given()
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .delete("/cart/items/" + productId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
