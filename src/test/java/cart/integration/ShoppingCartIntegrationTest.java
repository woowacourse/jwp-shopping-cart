package cart.integration;


import static cart.fixture.CartFixture.TEST_CART_RECORD;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import cart.service.dto.CartResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ShoppingCartIntegrationTest {

    private static final String CREDENTIAL = "hongSile@wooteco.com:hongSile";
    private static final String AUTHORIZATION = "Authorization";
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
                    .header(AUTHORIZATION, "Barer " + encodeBase64(CREDENTIAL))
                    .when()
                    .get("/cart/products")
                    .then()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
        }

        @Test
        @DisplayName("credential이 유효하지 않은 경우")
        void invalidCredential() {
            given()
                    .header(AUTHORIZATION, "Basic " + encodeBase64("backFox@a.com:back"))
                    .when()
                    .get("/cart/products")
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Sql("/shoppingCartData.sql")
        @Test
        @DisplayName("모든 인증정보가 유효한 경우")
        void success() throws JsonProcessingException {
            final ExtractableResponse<Response> response = given()
                    .header(AUTHORIZATION, "Basic " + encodeBase64(CREDENTIAL))
                    .when()
                    .get("/cart/products")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .extract();
            final List<CartResponse> cartResponses = mapCartResponses(response);

            assertThat(cartResponses)
                    .extracting(CartResponse::getId)
                    .containsExactly(TEST_CART_RECORD.getId());
        }
    }

    @Sql("/shoppingCartData.sql")
    @Test
    @DisplayName("장바구니에 상품을 추가하는 기능 테스트")
    void addProductTest() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, "Basic " + encodeBase64(CREDENTIAL))
                .body("{\"productId\": 1}")
                .when()
                .post("/cart/products")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Sql("/shoppingCartData.sql")
    @Test
    @DisplayName("장바구니에 상품을 삭제하는 기능 테스트")
    void removeProductTest() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, "Basic " + encodeBase64(CREDENTIAL))
                .when()
                .delete("/cart/products/1")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private static String encodeBase64(final String credential) {
        final byte[] bytes = Base64.encodeBase64(credential.getBytes(StandardCharsets.UTF_8));
        return new String(bytes);
    }

    private static List<CartResponse> mapCartResponses(final ExtractableResponse<Response> response)
            throws JsonProcessingException {
        final String jsonResponse = response.asString();
        final ObjectMapper objectMapper = new ObjectMapper();
        final CartResponse[] cartResponses = objectMapper.readValue(jsonResponse, CartResponse[].class);
        return Arrays.stream(cartResponses)
                .collect(Collectors.toUnmodifiableList());
    }
}
