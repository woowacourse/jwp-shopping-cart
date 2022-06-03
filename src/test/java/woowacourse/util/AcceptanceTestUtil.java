package woowacourse.util;

import static org.assertj.core.api.Assertions.fail;
import static woowacourse.util.HttpRequestUtil.get;
import static woowacourse.util.HttpRequestUtil.post;
import static woowacourse.util.HttpRequestUtil.postWithAuthorization;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.request.LoginRequest;
import woowacourse.auth.dto.request.MemberCreateRequest;
import woowacourse.auth.dto.request.PasswordCheckRequest;
import woowacourse.auth.dto.response.EmailUniqueCheckResponse;
import woowacourse.auth.dto.response.LoginResponse;

public class AcceptanceTestUtil {

    public static ExtractableResponse<Response> 회원가입을_한다(String email, String password, String nickname) {
        이메일_중복체크를_한다(email);
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(email, password, nickname);
        return post("/api/members", memberCreateRequest);
    }

    public static void 이메일_중복체크를_한다(String email) {
        boolean unique = get("/api/members/check-email?email=" + email)
                .as(EmailUniqueCheckResponse.class)
                .isUnique();
        if (!unique) {
            fail("이미 가입된 회원입니다.");
        }
    }

    public static ExtractableResponse<Response> 로그인을_한다(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        return post("/api/login", loginRequest);
    }

    public static String 로그인을_하고_토큰을_받는다(String email, String password) {
        return 로그인을_한다(email, password)
                .as(LoginResponse.class)
                .getToken();
    }

    public static ExtractableResponse<Response> 비밀번호를_확인한다(String token, String password) {
        return postWithAuthorization("/api/members/password-check", token, new PasswordCheckRequest(password));
    }

    public static ExtractableResponse<Response> 로그인_없이_비밀번호를_확인한다() {
        return post("/api/members/password-check", new PasswordCheckRequest("1q2w3e4r!"));
    }

    public static ExtractableResponse<Response> preflight_요청을_한다(String url) {
        return RestAssured.given().log().all()
                .when()
                .options(url)
                .then().log().all()
                .extract();
    }
}
