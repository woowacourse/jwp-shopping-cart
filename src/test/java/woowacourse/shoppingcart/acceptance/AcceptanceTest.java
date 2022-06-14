package woowacourse.shoppingcart.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.SignupRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AcceptanceTest {
    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    public ExtractableResponse<Response> 회원가입_되어있음(SignupRequest signupRequest) {
        return RestAssured.given().log().all()
            .body(signupRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/customers/signup")
            .then().log().all()
            .extract();
    }

    public String 로그인하고_토큰받아옴(LoginRequest loginRequest) {
        return RestAssured
            .given().log().all()
            .body(loginRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/customers/login")
            .then().log().all().extract().as(TokenResponse.class).getAccessToken();
    }
}
