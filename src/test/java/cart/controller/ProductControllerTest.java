package cart.controller;

import cart.request.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("classpath:schema.sql")
public class ProductControllerTest {

    @LocalServerPort
    private int port;

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
                .body(new ProductRequest("족발", 5000, "https://image.com"))
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
                .body(new ProductRequest("족발", 5000, "https://image.com"))
                .contentType(ContentType.JSON)
                .when()
                .post("/admin");

        RestAssured.given()
                .body(new ProductRequest("피자", 3000, "https://image.com"))
                .contentType(ContentType.JSON)
                .when()
                .put("/admin/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    void 상품_삭제() {
        final long id = 1L;

        RestAssured.given()
                .body(new ProductRequest("족발", 5000, "https://image.com"))
                .contentType(ContentType.JSON)
                .when()
                .post("/admin");

        RestAssured.given()
                .when()
                .delete("/admin/" + id)
                .then()
                .statusCode(200);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullSource
    void 상품의_이름에_빈값_널_공백이_들어가면_예외_발생(final String name) {
        RestAssured.given()
                .body(new ProductRequest(name, 1000, "https://image.com"))
                .contentType(ContentType.JSON)
                .when()
                .post("/admin")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 100_000_001})
    void 범위가_벗어난_가격을_입력하면_예외_발생(final int price) {
        RestAssured.given()
                .body(new ProductRequest("상품 이름", price, "https://image.com"))
                .contentType(ContentType.JSON)
                .when()
                .post("/admin")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @ParameterizedTest
    @ValueSource(strings = {"http", "", " ", "asdfasdf", "smtp", "ssh"})
    @NullSource
    void 범위가_벗어난_가격을_입력하면_예외_발생(final String imageUrl) {
        RestAssured.given()
                .body(new ProductRequest("상품 이름", 1000, imageUrl))
                .contentType(ContentType.JSON)
                .when()
                .post("/admin")
                .then()
                .assertThat()
                .statusCode(400);
    }
}
