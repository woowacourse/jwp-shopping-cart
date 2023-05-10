package cart.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/schema.sql", "/data.sql"})
class CartControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 장바구니에_담겨진_상품을_가져올_때_인증정보가_포함되지_않으면_401에러_발생(final String value) {
        RestAssured.given()
                .header(new Header("Authorization", value))
                .get("/cart/products")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 장바구니에_담겨진_상품을_가져올_때_인증정보가_포함되면_성공() {
        RestAssured.given()
                .auth().preemptive().basic("userA@woowahan.com", "passwordA")
                .when()
                .get("/cart/products")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 장바구니에_상품을_등록할_때_인증정보가_포함되지_않으면_401에러_발생(final String value) {
        RestAssured.given()
                .header(new Header("Authorization", value))
                .post("/cart/1")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 장바구니에_상품을_가져올_때_인증정보가_포함되면_성공() {
        RestAssured.given()
                .auth().preemptive().basic("userA@woowahan.com", "passwordA")
                .when()
                .get("/cart/products")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 장바구니에_상품을_삭제할_때_인증정보가_포함되지_않으면_401에러_발생(final String value) {
        RestAssured.given()
                .header(new Header("Authorization", value))
                .delete("/cart/1")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 장바구니에_상품을_삭제할_때_인증정보가_포함되면_성공() {
        RestAssured.given()
                .auth().preemptive().basic("userB@woowahan.com", "passwordB")
                .when()
                .delete("/cart/2")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
