package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;
import woowacourse.shoppingcart.dto.customer.PasswordRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class AcceptanceTest {

    protected static final CustomerRequest 파리채 = new CustomerRequest("newemail@email.com", "파리채", "password123!");
    protected static final TokenRequest 파리채토큰 = new TokenRequest("newemail@email.com", "password123!");
    protected static final PasswordRequest 파리채비번 = new PasswordRequest("password123!");

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    private ExtractableResponse<Response> 이메일_중복_체크(final String email) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/members/email-check?email=" + email)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> 회원가입(final CustomerRequest customerRequest) {
        이메일_중복_체크(customerRequest.getEmail());
        return RestAssured
                .given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/members")
                .then().log().all()
                .extract();
    }

    protected String 로그인_후_토큰발급(final TokenRequest tokenRequest) {
        return RestAssured
                .given().log().all()
                .body(tokenRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all()
                .extract().as(TokenResponse.class).getToken();
    }

    private ExtractableResponse<Response> 비밀번호_확인(final String accessToken, final PasswordRequest passwordRequest) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(passwordRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/members/password-check")
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> 회원정보_조회(final String accessToken) {
        비밀번호_확인(accessToken, 파리채비번);
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/api/members/me")
                .then().log().all()
                .extract();
    }
}
