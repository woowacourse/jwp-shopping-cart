package woowacourse.member.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.http.HttpStatus;
import woowacourse.acceptance.AcceptanceTest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.member.dto.request.*;

import static woowacourse.acceptance.RestAssuredConvenienceMethod.*;

@DisplayName("회원 관련 기능")
public class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입 - 성공한 경우 201 Created가 반환된다.")
    @Test
    void signUpMember() {
        SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", "우테코", "Woowacourse1!");

        postRequestWithoutToken(request, "/api/members").statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("회원가입 - 실패한 경우 400 Bad Request를 반환한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void signUpMemberWithNullName(String name) {
        SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", name, "Woowacourse1!");

        postRequestWithoutToken(request, "/api/members").statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("회원탈퇴 - 성공한 경우 204 No Content가 반환된다.")
    @Test
    void deleteMember() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        postRequestWithoutToken(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco1!");
        String accessToken = postRequestWithoutToken(loginRequest, "/api/auth").extract().as(TokenResponse.class).getAccessToken();

        DeleteMemberRequest deleteRequest = new DeleteMemberRequest("Wooteco1!");
        deleteRequestWithToken(accessToken, deleteRequest, "/api/members/me").statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원탈퇴 - 토큰 없이 접근한 경우 401 Unauthorized가 반환된다.")
    @Test
    void deleteMemberWithoutToken() {
        deleteRequestWithoutToken("/api/members/me").statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("이메일중복확인 -  중복되는 경우 400 Bad Request를 반환한다.")
    @Test
    void checkDuplicateEmailWithDuplicateEmail() {
        getRequestWithoutToken("/api/members/duplicate-email?email=ari@wooteco.com").statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("이메일중복확인 - 중복되지 않는 경우 200 OK를 반환한다.")
    @Test
    void checkDuplicateEmailWithNotDuplicateEmail() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        postRequestWithoutToken(signUpRequest, "/api/members");

        getRequestWithoutToken("/api/members/duplicate-email?email=tony@wooteco.com").statusCode(HttpStatus.OK.value());
    }

    @DisplayName("이름수정 -  정상적으로 수정하는 경우 200 ok를 반환한다.")
    @Test
    void updateName() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        postRequestWithoutToken(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco1!");
        String accessToken = postRequestWithoutToken(loginRequest, "/api/auth").extract().as(TokenResponse.class).getAccessToken();

        UpdateNameRequest updateNameRequest = new UpdateNameRequest("자바지기");
        putRequestWithToken(accessToken, updateNameRequest, "/api/members/me/name").statusCode(HttpStatus.OK.value());
    }

    @DisplayName("이름수정 - 토큰 없이 접근한 경우 401 Unauthorized가 반환된다.")
    @Test
    void updateNameWithoutToken() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        postRequestWithoutToken(signUpRequest, "/api/members");

        UpdateNameRequest updateNameRequest = new UpdateNameRequest("포비");
        putRequestWithoutToken(updateNameRequest, "/api/members/me/name").statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("이름수정 - 이전 이름과 동일한 경우 400 Bad Request를 반환한다.")
    @Test
    void updateNameWithSameName() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        postRequestWithoutToken(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco1!");
        String accessToken = postRequestWithoutToken(loginRequest, "/api/auth").extract().as(TokenResponse.class).getAccessToken();

        UpdateNameRequest updateNameRequest = new UpdateNameRequest("포비");
        putRequestWithToken(accessToken, updateNameRequest, "/api/members/me/name").statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("비밀번호수정 -  정상적으로 수정하는 경우 200 ok를 반환한다.")
    @Test
    void updatePassword() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        postRequestWithoutToken(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco1!");
        String accessToken = postRequestWithoutToken(loginRequest, "/api/auth").extract().as(TokenResponse.class).getAccessToken();

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("Wooteco1!", "NewPassword1!");
        putRequestWithToken(accessToken, updatePasswordRequest, "/api/members/me/password").statusCode(HttpStatus.OK.value());
    }

    @DisplayName("비밀번호수정 - 토큰 없이 접근한 경우 401 Unauthorized가 반환된다.")
    @Test
    void updatePasswordWithoutToken() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        postRequestWithoutToken(signUpRequest, "/api/members");

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("Wooteco1!", "NewPassword1!");
        putRequestWithoutToken(updatePasswordRequest, "/api/members/me/password").statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("비밀번호수정 -이전 비밀번호와 동일한 경우 400 Bad Request를 반환한다.")
    @Test
    void updatePasswordWithSamePassword() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        postRequestWithoutToken(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco1!");
        String accessToken = postRequestWithoutToken(loginRequest, "/api/auth").extract().as(TokenResponse.class).getAccessToken();

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("Wooteco1!", "Wooteco1!");
        putRequestWithToken(accessToken, updatePasswordRequest, "/api/members/me/password").statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
