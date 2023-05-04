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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/products로 post 요청을 보내면 상태코드 200(OK)을 응답한다")
    void createProduct() {
        final String requestName = "소주";
        final int requestPrice = 4_000;
        final String requestUrl = "none";
        final ProductRequest createRequest = new ProductRequest(requestName, requestPrice,
            requestUrl);

        RestAssured
            .given().contentType(ContentType.JSON).body(createRequest)
            .when().post("/products")
            .then().statusCode(201);
    }

    @Test
    @DisplayName("/products로 잘못된 형식의 post 요청을 보내면 상태코드 400(BadRequest)을 응답한다")
    void createProduct_fail() {
        final String requestName = " ";
        final int requestPrice = 4_000;
        final String requestUrl = "none";
        final ProductRequest createRequest = new ProductRequest(requestName, requestPrice,
            requestUrl);

        RestAssured
            .given().contentType(ContentType.JSON).body(createRequest)
            .when().post("/products")
            .then().statusCode(400).body(Matchers.containsString("유효하지 않은 값입니다."));
    }

    @Test
    @DisplayName("/products/{id}로 put 요청을 보내면 상태코드 200(OK)을 응답한다")
    void updateProduct() {
        final String requestName = "소주";
        final int requestPrice = 4_000;
        final String requestUrl = "none";
        final ProductRequest createRequest = new ProductRequest(requestName, requestPrice,
            requestUrl);

        RestAssured
            .given().contentType(ContentType.JSON).body(createRequest)
            .when().put("/products/1")
            .then().statusCode(200);
    }

    @Test
    @DisplayName("/products로 잘못된 형식의 put 요청을 보내면 상태코드 400(BadRequest)을 응답한다")
    void updateProduct_fail() {
        final String requestName = " ";
        final int requestPrice = 4_000;
        final String requestUrl = "none";
        final ProductRequest createRequest = new ProductRequest(requestName, requestPrice,
            requestUrl);

        RestAssured
            .given().contentType(ContentType.JSON).body(createRequest)
            .when().put("/products/1")
            .then().statusCode(400).body(Matchers.containsString("유효하지 않은 값입니다."));
    }

    @Test
    @DisplayName("/products로 존재하지 않는 상품에 대해 put 요청을 보내면 상태코드 400(BadRequest)을 응답한다")
    void updateProduct_fail2() {
        final String requestName = "맥주";
        final int requestPrice = 4_000;
        final String requestUrl = "none";
        final ProductRequest createRequest = new ProductRequest(requestName, requestPrice,
            requestUrl);

        RestAssured
            .given().contentType(ContentType.JSON).body(createRequest)
            .when().put("/products/-1")
            .then().statusCode(400).body(Matchers.containsString("존재하지 않는 id입니다."));
    }

    @Test
    @DisplayName("/products/{id}로 delete 요청을 보내면 상태코드 200(OK)을 응답한다")
    void deleteProduct() {
        RestAssured
            .given().contentType(ContentType.JSON)
            .when().delete("/products/1")
            .then().statusCode(200);
    }

    @Test
    @DisplayName("/products/{id}로 존재하지 않는 상품에 대해 delete 요청을 보내면 상태코드 400(BadRequest)을 응답한다")
    void deleteProduct_fail() {
        RestAssured
            .given().contentType(ContentType.JSON)
            .when().delete("/products/-1")
            .then().statusCode(400).body(Matchers.containsString("존재하지 않는 id입니다."));
    }
}