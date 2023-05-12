package cart.controller.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import cart.dto.request.CreateCartRequest;
import cart.dto.request.DeleteCartRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CartControllerTest {
    private static final String GAVI_ENCODED_TOKEN = "Z2F2aUB3b293YWhhbi5jb206MTIzNA==";

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 장바구니에_상품을_담을_수_있다() {
        given()
                .log().all().contentType(ContentType.JSON)
                .header("Authorization", "Basic " + GAVI_ENCODED_TOKEN)
                .body(new CreateCartRequest(1L))
                .when()
                .post("/carts")
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @ParameterizedTest
    @NullSource
    void 상품_정보_없이_카트에_담을_수_없다(final Long wrongId) {
        given()
                .log().all().contentType(ContentType.JSON)
                .header("Authorization", "Basic " + GAVI_ENCODED_TOKEN)
                .body(new CreateCartRequest(wrongId))
                .when()
                .post("/carts")
                .then()
                .log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(containsString("상품 id가 입력되지 않았습니다"));
    }

    @Test
    void 장바구니의_상품을_삭제할_수_있다() {
        given()
                .log().all().contentType(ContentType.JSON)
                .header("Authorization", "Basic " + GAVI_ENCODED_TOKEN)
                .body(new CreateCartRequest(1L))
                .when()
                .post("/carts")
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value());

        given()
                .log().all().contentType(ContentType.JSON)
                .header("Authorization", "Basic " + GAVI_ENCODED_TOKEN)
                .body(new DeleteCartRequest(1L))
                .when()
                .delete("/carts")
                .then()
                .log().all()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @ParameterizedTest
    @NullSource
    void 상품_정보_없이_장바구니_상품을_삭제할_수_없다(final Long wrongId) {
        given()
                .log().all().contentType(ContentType.JSON)
                .header("Authorization", "Basic " + GAVI_ENCODED_TOKEN)
                .body(new DeleteCartRequest(wrongId))
                .when()
                .delete("/carts")
                .then()
                .log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(containsString("상품 id가 입력되지 않았습니다"));
    }

    @Test
    void 장바구니_목록을_가져올_수_있다() {
        given()
                .log().all().contentType(ContentType.JSON)
                .header("Authorization", "Basic " + GAVI_ENCODED_TOKEN)
                .when()
                .get("/carts")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
