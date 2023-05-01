package cart.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@Sql({"/schema.sql", "/data.sql", "/cart_data.sql"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    private static final String EMAIL = "songsy405@naver.com";
    private static final String PASSWORD = "abcd";

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/cart로 get 요청을 보내면 상태코드 200(OK)을 응답한다")
    void getProducts() {
        RestAssured
            .given().contentType(ContentType.JSON)
            .auth().preemptive().basic(EMAIL, PASSWORD)
            .when().get("/cart/products")
            .then().statusCode(200);
    }

    @Test
    @DisplayName("/cart로 post 요청을 보내면 상태코드 200(OK)을 응답한다")
    void addCart() {
        RestAssured
            .given().contentType(ContentType.JSON)
            .auth().preemptive().basic(EMAIL, PASSWORD)
            .when().post("/cart/3")
            .then().statusCode(200);
    }

    @Test
    @DisplayName("/cart로 잘못된 형식의 post 요청을 보내면 상태코드 400(BadRequest)을 응답한다")
    void addCart_fail() {
        RestAssured
            .given().contentType(ContentType.JSON)
            .auth().preemptive().basic(EMAIL, PASSWORD)
            .when().post("/cart/2")
            .then().statusCode(400).body(Matchers.containsString("장바구니에 해당 제품이 이미 존재합니다."));
    }

    @Test
    @DisplayName("/cart/{product_id}로 delete 요청을 보내면 상태코드 200(OK)을 응답한다")
    void deleteProduct() {
        RestAssured
            .given().contentType(ContentType.JSON)
            .auth().preemptive().basic(EMAIL, PASSWORD)
            .when().delete("/cart/1")
            .then().statusCode(200);
    }

    @Test
    @DisplayName("/cart/{product_id}로 존재하지 않는 상품에 대해 delete 요청을 보내면 상태코드 400(BadRequest)을 응답한다")
    void deleteProduct_fail() {
        RestAssured
            .given().contentType(ContentType.JSON)
            .auth().preemptive().basic(EMAIL, PASSWORD)
            .when().delete("/cart/-1")
            .then().statusCode(400).body(Matchers.containsString("존재하지 않는 상품 정보입니다."));
    }
}