package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/init.sql")
@ActiveProfiles("test")
public class AcceptanceTest {

    protected static final int INVALID_FORMAT_ERROR_CODE = 1000;
    protected static final int DUPLICATE_EMAIL_ERROR_CODE = 1001;
    protected static final int INVALID_LOGIN_ERROR_CODE = 1002;

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    protected ExtractableResponse<Response> 회원가입_요청(SignUpRequest signUpRequest) {
        return RestAssured.given().log().all()
                .body(signUpRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/users")
                .then().log().all().extract();
    }

    protected ExtractableResponse<Response> 로그인_요청(TokenRequest tokenRequest) {
        return RestAssured.given().log().all()
                .body(tokenRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract();
    }

    protected String 토큰_요청(TokenRequest tokenRequest) {
        return 로그인_요청(tokenRequest).jsonPath().getString("accessToken");
    }

    protected ExtractableResponse<Response> 회원정보_요청(String token) {
        return RestAssured.given().log().all()
                .when().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .get("/users/me")
                .then().log().all().extract();
    }

    protected ExtractableResponse<Response> 회원탈퇴_요청(String token) {
        return RestAssured.given().log().all()
                .when().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .delete("/users/me")
                .then().log().all().extract();
    }

    protected ExtractableResponse<Response> 회원정보_수정_요청(CustomerUpdateRequest customerUpdateRequest, String token) {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(customerUpdateRequest)
                .put("/users/me")
                .then().log().all().extract();
        return response;
    }
}
