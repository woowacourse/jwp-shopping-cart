package woowacourse.util;

import static org.assertj.core.api.Assertions.fail;
import static woowacourse.util.HttpRequestUtil.deleteWithAuthorization;
import static woowacourse.util.HttpRequestUtil.get;
import static woowacourse.util.HttpRequestUtil.getWithAuthorization;
import static woowacourse.util.HttpRequestUtil.patchWithAuthorization;
import static woowacourse.util.HttpRequestUtil.post;
import static woowacourse.util.HttpRequestUtil.postWithAuthorization;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.request.LoginRequest;
import woowacourse.shoppingcart.dto.request.MemberCreateRequest;
import woowacourse.shoppingcart.dto.request.MemberUpdateRequest;
import woowacourse.auth.dto.request.PasswordCheckRequest;
import woowacourse.shoppingcart.dto.request.PasswordUpdateRequest;
import woowacourse.shoppingcart.dto.response.EmailUniqueCheckResponse;
import woowacourse.auth.dto.response.LoginResponse;

public class AcceptanceTestUtil {

    public static ExtractableResponse<Response> 회원가입을_한다(String email, String password, String nickname) {
        이메일_중복체크를_한다(email);
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(email, password, nickname);
        return post("/api/members", memberCreateRequest);
    }

    public static void 이메일_중복체크를_한다(String email) {
        boolean unique = get("/api/members/email-check?email=" + email)
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

    public static ExtractableResponse<Response> 회원_정보를_조회한다(String token) {
        return getWithAuthorization("/api/members/me", token);
    }

    public static ExtractableResponse<Response> 로그인_없이_회원_정보를_조회한다() {
        return get("/api/members/me");
    }

    public static ExtractableResponse<Response> 회원_정보를_수정한다(String token, MemberUpdateRequest requestBody) {
        return patchWithAuthorization("/api/members/me", token, requestBody);
    }

    public static ExtractableResponse<Response> 비밀번호를_수정한다(String token, PasswordUpdateRequest requestBody) {
        return patchWithAuthorization("/api/members/password", token, requestBody);
    }

    public static ExtractableResponse<Response> 회원을_삭제한다(String token) {
        return deleteWithAuthorization("/api/members/me", token);
    }
}
