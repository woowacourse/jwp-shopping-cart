package cart.controller;

import cart.request.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("classpath:schema.sql")
class ProductResourceControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 상품_추가() {
        RestAssured.given()
                .body(new ProductRequest("족발", 5000, "https://image.com"))
                .contentType(ContentType.JSON)
                .when()
                .post("/admin/products")
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
                .post("/admin/products");

        RestAssured.given()
                .body(new ProductRequest("피자", 3000, "https://image.com"))
                .contentType(ContentType.JSON)
                .when()
                .put("/admin/products/" + id)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 상품_삭제() {
        final long id = 1L;

        RestAssured.given()
                .body(new ProductRequest("족발", 5000, "https://image.com"))
                .contentType(ContentType.JSON)
                .when()
                .post("/admin/products");

        RestAssured.given()
                .when()
                .delete("/admin/products/" + id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullSource
    void 상품의_이름에_빈값_널_공백이_들어가면_예외_발생(final String name) {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new ProductRequest(name, 1234, "https://image.url");
                }
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 100_000_001})
    void 범위가_벗어난_가격을_입력하면_예외_발생(final int price) {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new ProductRequest("name", price, "https://image.url");
                }
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"http", "", " ", "asdfasdf", "smtp", "ssh"})
    @NullSource
    void 유효하지_않은_URL을_입력하면_예외_발생(final String imageUrl) {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new ProductRequest("name", 1234, imageUrl);
                }
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {2072, 2073, 2345})
    void 길이_제한을_벗어난_URL을_입력하면_예외_발생(final int repeatCount) {
        final String url = "https://" + "a".repeat(repeatCount) + ".com";

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new ProductRequest("name", 1234, url);
                }
        );
    }
}
