package cart.integration;


import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ShoppingCartIntegrationTest {

    private static final String CREDENTIAL = "hongSile@wooteco.com:hongSile";
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("장바구니 페이지 접근 테스트")
    void serveCartPage() {
        given()
                .when()
                .get("/cart")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Nested
    @DisplayName("장바구니에 담긴 물품을 반환하는 기능 테스트")
    class shoppingList {

        @Test
        @DisplayName("Authorization 헤더가 없는 경우 AuthorizationException 발생")
        void headerNotContainAuthorization() {
            given()
                    .when()
                    .get("/cart/products")
                    .then()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
        }

        @Test
        @DisplayName("Authorization 헤더가 있지만, Basic이 아닌 경우")
        void authTypeIsNotBasic() {
            given()
                    .header("Authorization", "Barer " + encodeBase64(CREDENTIAL))
                    .when()
                    .get("/cart/products")
                    .then()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
        }
    }

    private static String encodeBase64(final String credential) {
        final byte[] bytes = Base64.encodeBase64(credential.getBytes(StandardCharsets.UTF_8));
        return new String(bytes);
    }
}
