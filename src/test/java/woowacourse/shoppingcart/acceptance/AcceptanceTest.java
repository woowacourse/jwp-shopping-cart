package woowacourse.shoppingcart.acceptance;

import static woowacourse.ShoppingCartFixture.CUSTOMER_URI;
import static woowacourse.ShoppingCartFixture.LOGIN_URI;
import static woowacourse.ShoppingCartFixture.잉_회원생성요청;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.request.CustomerRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AcceptanceTest {
    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected ExtractableResponse<Response> post(String uri, Object object) {
        return RestAssured.given().log().all()
                .body(toJson(object))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> postWithToken(String uri, Object object, String token) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(toJson(object))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> getWithToken(String uri, String token) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> get(String uri) {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> put(String uri, Object object, String token) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(toJson(object))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> deleteWithToken(String uri, Object object, String token) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(toJson(object))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> delete(String uri, Object object) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
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

    protected void signUp() {
        RestAssured.given().log().all()
                .body(toJson(잉_회원생성요청))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(CUSTOMER_URI)
                .then().log().all()
                .extract();
    }
}
