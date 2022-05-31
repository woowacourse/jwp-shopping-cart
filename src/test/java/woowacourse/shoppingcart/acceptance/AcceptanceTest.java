package woowacourse.shoppingcart.acceptance;

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
import woowacourse.shoppingcart.dto.MemberRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AcceptanceTest {

    protected static final MemberRequest 파랑 = new MemberRequest("email@email.com", "파랑", "password123!");
    protected static final TokenRequest 파랑토큰 = new TokenRequest("email@email.com", "password123!");

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
                .when().post("/api/members?email=" + email)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> 회원가입(final MemberRequest memberRequest) {
        이메일_중복_체크(memberRequest.getEmail());
        return RestAssured
                .given().log().all()
                .body(memberRequest)
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
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all()
                .extract().as(TokenResponse.class).getAccessToken();
    }

    protected ExtractableResponse<Response> 회원정보_조회(final String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/api/members/auth/me")
                .then().log().all()
                .extract();
    }
}
