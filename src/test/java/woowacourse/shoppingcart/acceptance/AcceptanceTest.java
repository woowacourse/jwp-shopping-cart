package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.ShoppingCartFixture.LOGIN_URI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import woowacourse.auth.ui.dto.TokenRequest;
import woowacourse.auth.ui.dto.TokenResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
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

    protected ExtractableResponse<Response> get(String uri, String token) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
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

    protected ExtractableResponse<Response> delete(String uri, Object object, String token) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(toJson(object))
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

    protected void 정상응답_OK(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    protected void 비어있음(final ExtractableResponse<Response> response) {
        assertThat(response.as(List.class)).isEmpty();
    }

    protected void 비어있지않음(final ExtractableResponse<Response> response) {
        assertThat(response.as(List.class)).isNotEmpty();
    }

    protected void 삭제성공_NO_CONTENT(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
