package woowacourse.fixture;

import static woowacourse.fixture.Fixture.get;
import static woowacourse.fixture.Fixture.post;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;

public class AuthFixture {

    public static ExtractableResponse<Response> 로그인_요청(TokenRequest request) {
        return post("/api/auth/login", request);
    }

    public static String 로그인_요청_및_토큰발급(TokenRequest request) {
        ExtractableResponse<Response> loginResponse = 로그인_요청(request);
        TokenResponse tokenResponse = loginResponse.body().as(TokenResponse.class);

        return tokenResponse.getAccessToken();
    }

    public static ExtractableResponse<Response> 회원조회_요청(String token, Long id) {

        return get("/api/customers/" + id, token);
    }
}
