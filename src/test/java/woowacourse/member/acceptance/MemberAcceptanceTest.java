package woowacourse.member.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.acceptance.AcceptanceTest;
import woowacourse.acceptance.RestAssuredConvenienceMethod;
import woowacourse.member.dto.request.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.member.dto.request.*;

@DisplayName("회원 관련 기능")
public class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입에 성공한 경우 201 Created가 반환된다.")
    @Test
    void signUpMember() {
        SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", "우테코", "Woowacourse1!");

        RestAssuredConvenienceMethod.postRequest(request, "/api/members")
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("회원가입에 성공한 경우 201 Created가 반환된다.")
    @Test
    void signUpMemberWithNullName() {
        SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", null, "Woowacourse1!");

        RestAssuredConvenienceMethod.postRequest(request, "/api/members")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("회원가입에 실패한 경우 400 Bad Request를 반환한다.")
    @Test
    void signUpMemberFailed() {
        SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", "우테코", "");

        RestAssuredConvenienceMethod.postRequest(request, "/api/members")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("회원탈퇴가 성공하면 204 No Content가 반환된다.")
    @Test
    void deleteMember() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        RestAssuredConvenienceMethod.postRequest(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco1!");
        String accessToken = RestAssuredConvenienceMethod.postRequest(loginRequest, "/api/auth")
                .extract().as(TokenResponse.class).getAccessToken();

        DeleteMemberRequest deleteRequest = new DeleteMemberRequest("Wooteco1!");
        RestAssuredConvenienceMethod.deleteRequestWithToken(accessToken, deleteRequest, "/api/members/me")
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("이메일이 중복되는 경우 400 Bad Request를 반환한다.")
    @Test
    void checkDuplicateEmailWithDuplicateEmail() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        RestAssuredConvenienceMethod.postRequest(signUpRequest, "/api/members");

        DuplicateEmailRequest duplicateEmailRequest = new DuplicateEmailRequest("pobi@wooteco.com");
        RestAssuredConvenienceMethod.postRequest(duplicateEmailRequest, "/api/members/duplicate-email")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("이메일이 중복되지 않는 경우 200 OK를 반환한다.")
    @Test
    void checkDuplicateEmailWithNotDuplicateEmail() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        RestAssuredConvenienceMethod.postRequest(signUpRequest, "/api/members");

        DuplicateEmailRequest duplicateEmailRequest = new DuplicateEmailRequest("woni@wooteco.com");
        RestAssuredConvenienceMethod.postRequest(duplicateEmailRequest, "/api/members/duplicate-email")
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("이름을 정상적으로 수정하는 경우 200 ok를 반환한다.")
    @Test
    void updateName() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        RestAssuredConvenienceMethod.postRequest(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco1!");
        String accessToken = RestAssuredConvenienceMethod.postRequest(loginRequest, "/api/auth")
                .extract().as(TokenResponse.class).getAccessToken();

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
                .extract().as(TokenResponse.class).getAccessToken();

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
                .extract().as(TokenResponse.class).getAccessToken();

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
                .extract().as(TokenResponse.class).getAccessToken();

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("Wooteco1!", "Wooteco1!");
        RestAssuredConvenienceMethod.putRequestWithToken(accessToken, updatePasswordRequest, "/api/members/me/password")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
