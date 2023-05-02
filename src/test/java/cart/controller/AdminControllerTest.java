package cart.controller;

import cart.dto.ProductRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("classpath:schema.sql")
public class AdminControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
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
                .post("/admin/product")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 상품_수정() {
        final long id = 1L;

        RestAssured.given()
                .body(new ProductRequest("족발", 5000, "https://image.com"))
                .contentType(ContentType.JSON)
                .when()
                .post("/admin/product");

        RestAssured.given()
                .body(new ProductRequest("피자", 3000, "https://image.com"))
                .contentType(ContentType.JSON)
                .when()
                .put("/admin/product/" + id)
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
                .post("/admin/product");

        RestAssured.given()
                .when()
                .delete("/admin/product/" + id)
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
                .post("/admin/product")
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
                .post("/admin/product")
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
                .post("/admin/product")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @ParameterizedTest
    @ValueSource(ints = {2072, 2073, 2345})
    void 길이_제한을_벗어난_URL을_입력하면_예외_발생(final int repeatCount) {
        final String url = "https://" + "a".repeat(repeatCount) + ".com";

        RestAssured.given()
                .body(new ProductRequest("상품 이름", 1000, url))
                .contentType(ContentType.JSON)
                .when()
                .post("/admin/product")
                .then()
                .assertThat()
                .statusCode(400);
    }
}
