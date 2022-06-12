package woowacourse.member.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.acceptance.AcceptanceTest;
import woowacourse.acceptance.RestAssuredConvenienceMethod;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginResponse;
import woowacourse.member.ui.dto.SignUpRequest;
import woowacourse.member.ui.dto.UpdateNameRequest;
import woowacourse.member.ui.dto.UpdatePasswordRequest;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입에 성공한 경우 201 Created가 반환된다.")
    @Test
    void signUpMember() {
        SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", "우테코", "Woowacourse1!");

        RestAssuredConvenienceMethod.postRequest(request, "/api/members")
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("회원가입에 실패한 경우 400 Bad Request를 반환한다.")
    @Test
    void signUpMemberFailed() {
        SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", "우테코", "");

        RestAssuredConvenienceMethod.postRequest(request, "/api/members")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("이메일이 중복되는 경우 400 Bad Request를 반환한다.")
    @Test
    void checkDuplicateEmailWithDuplicateEmail() {
        String email = "pobi@wooteco.com";
        SignUpRequest signUpRequest = new SignUpRequest(email, "포비", "Wooteco1!");
        RestAssuredConvenienceMethod.postRequest(signUpRequest, "/api/members");

        RestAssuredConvenienceMethod.getRequest("/api/members/duplicate-email?email=" + email)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("이메일이 중복되지 않는 경우 200 OK를 반환한다.")
    @Test
    void checkDuplicateEmailWithNotDuplicateEmail() {
        String email = "pobi@wooteco.com";

        RestAssuredConvenienceMethod.getRequest("/api/members/duplicate-email?email=" + email)
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("이름을 정상적으로 수정하는 경우 200 ok를 반환한다.")
    @Test
    void updateName() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        RestAssuredConvenienceMethod.postRequest(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco1!");
        String accessToken = RestAssuredConvenienceMethod.postRequest(loginRequest, "/api/auth")
                .extract().as(LoginResponse.class).getAccessToken();

        UpdateNameRequest updateNameRequest = new UpdateNameRequest("자바지기");
        RestAssuredConvenienceMethod.putRequestWithToken(accessToken, updateNameRequest, "/api/members/me/name")
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("이전 이름과 동일한 이름으로 수정하는 경우 400 Bad Request를 반환한다.")
    @Test
    void updateNameWithSameName() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        RestAssuredConvenienceMethod.postRequest(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco1!");
        String accessToken = RestAssuredConvenienceMethod.postRequest(loginRequest, "/api/auth")
                .extract().as(LoginResponse.class).getAccessToken();

        UpdateNameRequest updateNameRequest = new UpdateNameRequest("포비");
        RestAssuredConvenienceMethod.putRequestWithToken(accessToken, updateNameRequest, "/api/members/me/name")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("비밀번호를 정상적으로 수정하는 경우 200 ok를 반환한다.")
    @Test
    void updatePassword() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        RestAssuredConvenienceMethod.postRequest(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco1!");
        String accessToken = RestAssuredConvenienceMethod.postRequest(loginRequest, "/api/auth")
                .extract().as(LoginResponse.class).getAccessToken();

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("Wooteco1!", "NewPassword1!");
        RestAssuredConvenienceMethod.putRequestWithToken(accessToken, updatePasswordRequest, "/api/members/me/password")
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("이전 비밀번호와 동일한 비밀번호로 수정하는 경우 400 Bad Request를 반환한다.")
    @Test
    void updatePasswordWithSamePassword() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        RestAssuredConvenienceMethod.postRequest(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco1!");
        String accessToken = RestAssuredConvenienceMethod.postRequest(loginRequest, "/api/auth")
                .extract().as(LoginResponse.class).getAccessToken();

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("Wooteco1!", "Wooteco1!");
        RestAssuredConvenienceMethod.putRequestWithToken(accessToken, updatePasswordRequest, "/api/members/me/password")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("회원탈퇴가 성공하면 204 No Content가 반환된다.")
    @Test
    void deleteMember() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        RestAssuredConvenienceMethod.postRequest(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco1!");
        String accessToken = RestAssuredConvenienceMethod.postRequest(loginRequest, "/api/auth")
                .extract().as(LoginResponse.class).getAccessToken();

        RestAssuredConvenienceMethod.deleteRequestWithToken(accessToken, "/api/members/me")
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    public static void 회원가입_요청(String email, String name, String password) {
        SignUpRequest signUpRequest = new SignUpRequest(email, name, password);
        RestAssuredConvenienceMethod.postRequest(signUpRequest, "/api/members");
    }

    public static String 로그인_요청(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        return RestAssuredConvenienceMethod.postRequest(loginRequest, "/api/auth")
                .extract().as(LoginResponse.class).getAccessToken();
    }
}
