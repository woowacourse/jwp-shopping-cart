package cart.controller;

import cart.dao.ProductDao;
import cart.request.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    ProductDao productDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 상품_목록_조회() {
        RestAssured.when()
                .get("/")
                .then()
                .contentType(MediaType.TEXT_HTML_VALUE)
                .statusCode(200);
    }

    @Test
    void 어드민_페이지_조회() {
        RestAssured.when()
                .get("/admin")
                .then()
                .contentType(MediaType.TEXT_HTML_VALUE)
                .statusCode(200);
    }

    @Test
    void 상품_추가() {
        RestAssured.given()
                .body(new ProductRequest("족발", 5000, "족발 이미지"))
                .contentType(ContentType.JSON)
                .when()
                .post("/admin")
                .then()
                .contentType(MediaType.TEXT_HTML_VALUE)
                .statusCode(200);
    }

    @Test
    void 상품_수정() {
        final long id = 1L;

        RestAssured.given()
                .body(new ProductRequest("족발", 5000, "족발 이미지"))
                .contentType(ContentType.JSON)
                .when()
                .post("/admin");

        RestAssured.given()
                .body(new ProductRequest("피자", 3000, "피자 이미지"))
                .contentType(ContentType.JSON)
                .when()
                .put("/admin/" + id)
                .then()
                .statusCode(200);
    }
}
