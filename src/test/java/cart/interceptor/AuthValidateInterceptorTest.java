package cart.interceptor;

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
class AuthValidateInterceptorTest {

    private static final String URI = "/cart/products";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 인증_정보가_존재하지_않으면_예외_발생() {
        RestAssured.given()
                .header(new Header("Authorization", ""))
                .when()
                .get("/cart/products")
                .then()
                .statusCode(401);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 인증_정보가_공백이거나_빈_값이면_예외_발생(final String value) {
        RestAssured.given()
                .header(new Header("Authorization", value))
                .get("/cart/products")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"BASIK", "BASI C", "bearer", "basi c"})
    void 인증_타입이_BASIC이_아닌_값이면_예외_발생(final String authType) {
        RestAssured.given()
                .header(new Header("Authorization", authType + "dXNlckFAd29vd2FoYW4uY29tOnBhc3N3b3JkQQ=="))
                .when()
                .get("/cart/products")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 인증_타입이_BASIC_이고_저장된_멤버가_값으로_들어오면_정상_실행() {
        RestAssured.given()
                .auth().preemptive().basic("userA@woowahan.com", "passwordA")
                .when()
                .get(URI)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 존재하지_않는_멤버가_인증_값으로_들어오면_서버_에러_발생() {
        RestAssured.given()
                .auth().preemptive().basic("noUserName", "noUserPassword")
                .when()
                .get("/cart/products")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
