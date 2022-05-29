package woowacourse.shoppingcart.acceptance;

import static woowacourse.ShoppingCartFixture.LOGIN_URI;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AcceptanceTest {
    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    protected ExtractableResponse<Response> post(String uri, Object object) {
        return RestAssured.given().log().all()
                .body(object)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> get(String uri, String token) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> put(String uri, Object object, String token) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(object)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> delete(String uri, Object object, String token) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(object)
                .when()
                .delete(uri)
                .then().log().all()
                .extract();
    }

    protected String getToken(TokenRequest tokenRequest) {
        return post(LOGIN_URI, tokenRequest)
                .as(TokenResponse.class)
                .getAccessToken();
    }
}
