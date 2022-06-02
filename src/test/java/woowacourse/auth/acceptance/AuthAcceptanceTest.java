package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.util.HttpRequestUtil.deleteWithAuthorization;
import static woowacourse.util.HttpRequestUtil.get;
import static woowacourse.util.HttpRequestUtil.getWithAuthorization;
import static woowacourse.util.HttpRequestUtil.patchWithAuthorization;
import static woowacourse.util.HttpRequestUtil.post;
import static woowacourse.util.HttpRequestUtil.postWithAuthorization;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.request.LoginRequest;
import woowacourse.auth.dto.request.MemberCreateRequest;
import woowacourse.auth.dto.request.MemberUpdateRequest;
import woowacourse.auth.dto.request.PasswordCheckRequest;
import woowacourse.auth.dto.request.PasswordUpdateRequest;
import woowacourse.auth.dto.response.CheckResponse;
import woowacourse.auth.dto.response.ErrorResponse;
import woowacourse.auth.dto.response.LoginResponse;
import woowacourse.auth.dto.response.MemberResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("인증 관련 기능")
class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("이메일, 비밀번호, 닉네임으로 회원 가입에 성공하면 201를 응답한다.")
    @Test
    void signUp_Created() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(
                "abc@woowahan.com",
                "1q2w3e4r!",
                "닉네임"
        );

        ExtractableResponse<Response> response = post("/api/members", memberCreateRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("규칙에 맞지 않는 정보로 회원 가입을 시도하면 400을 응답한다.")
    @ParameterizedTest
    @CsvSource({"abc,1q2w3e4r!,닉네임", "abc@woowahan.com,1q2w3e4r,닉네임", "abc@woowahan.com,1q2w3e4r!,잘못된닉네임"})
    void signUp_BadRequest(String email, String password, String nickname) {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(email, password, nickname);

        ExtractableResponse<Response> response = post("/api/members", memberCreateRequest);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(message).contains("형식이 올바르지 않습니다.");
    }

    @DisplayName("이미 회원으로 등록된 이메일인지와 200을 응답한다.")
    @ParameterizedTest
    @CsvSource({"abc@woowahan.com, false", "abc@naver.com, true"})
    void checkDuplicatedEmail_OK(String email, boolean expected) {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");

        ExtractableResponse<Response> response = get("/api/members?email=" + email);
        boolean success = response.as(CheckResponse.class)
                .isSuccess();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(success).isEqualTo(expected);
    }

    @DisplayName("잘못된 이메일 형식으로 중복 체크를 하려하면 400을 응답한다.")
    @Test
    void checkDuplicatedEmail_BadRequest() {
        String invalid = "abc";

        ExtractableResponse<Response> response = get("/api/members?email=" + invalid);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(message).isEqualTo("이메일 형식이 올바르지 않습니다.");
    }

    @DisplayName("올바른 이메일과 비밀번호로 로그인 요청을 하면 토큰과 닉네임을 반환하고 200을 응답한다.")
    @Test
    void login_Ok() {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        LoginRequest loginRequest = new LoginRequest("abc@woowahan.com", "1q2w3e4r!");

        ExtractableResponse<Response> response = post("/api/login", loginRequest);
        LoginResponse loginResponse = response.as(LoginResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(loginResponse.getToken()).isNotNull();
        assertThat(loginResponse.getNickname()).isEqualTo("닉네임");
    }

    @DisplayName("올바르지 않은 이메일과 비밀번호로 로그인 요청을 하면 400을 응답한다.")
    @ParameterizedTest
    @CsvSource({"abc@naver.com, 1q2w3e4r!", "abc@woowahan.com, 1q2w3e4r@"})
    void login_BadRequest(String email, String password) {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        LoginRequest loginRequest = new LoginRequest(email, password);

        ExtractableResponse<Response> response = post("/api/login", loginRequest);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(message).isEqualTo("이메일과 비밀번호를 확인해주세요.");
    }

    @DisplayName("토큰에 해당하는 사용자의 비밀번호가 일치하는지를 반환하고 200을 응답한다.")
    @ParameterizedTest
    @CsvSource({"1q2w3e4r!, true", "1q2w3e4r@, false"})
    void confirmPassword_Ok(String password, boolean expected) {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        String token = 로그인을_한다("abc@woowahan.com", "1q2w3e4r!")
                .as(LoginResponse.class)
                .getToken();

        ExtractableResponse<Response> response = postWithAuthorization(
                "/api/members/password-check",
                token,
                new PasswordCheckRequest(password)
        );
        boolean success = response.as(CheckResponse.class)
                .isSuccess();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(success).isEqualTo(expected);
    }

    @DisplayName("로그인을 하지 않고(토큰이 없이) 인증이 필요한 URI에 접근하면 401을 응답한다.")
    @Test
    void requestWithUnauthorized_Unauthorized() {
        ExtractableResponse<Response> response = post(
                "/api/members/password-check",
                new PasswordCheckRequest("1q2w3e4r!")
        );
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(message).isEqualTo("로그인이 필요합니다.");
    }

    @DisplayName("유효하지 않은 토큰으로 인증이 필요한 URI에 접근하면 401을 응답한다.")
    @Test
    void requestWithInvalidToken_Unauthorized() {
        ExtractableResponse<Response> response = postWithAuthorization(
                "/api/members/password-check",
                "abc",
                new PasswordCheckRequest("1q2w3e4r!")
        );
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(message).isEqualTo("유효하지 않은 토큰입니다.");
    }

    @DisplayName("토큰에 해당하는 사용자의 회원 정보와 200을 응답한다.")
    @Test
    void showMember_Ok() {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        String token = 로그인을_한다("abc@woowahan.com", "1q2w3e4r!")
                .as(LoginResponse.class)
                .getToken();

        ExtractableResponse<Response> response = getWithAuthorization("/api/members/me", token);
        MemberResponse memberResponse = response.as(MemberResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(memberResponse.getEmail()).isEqualTo("abc@woowahan.com");
        assertThat(memberResponse.getNickname()).isEqualTo("닉네임");
    }

    @DisplayName("토큰에 해당하는 사용자의 회원 정보를 수정하고 성공하면 204를 응답한다.")
    @Test
    void updateMember_NoContent() {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        String token = 로그인을_한다("abc@woowahan.com", "1q2w3e4r!")
                .as(LoginResponse.class)
                .getToken();
        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest("바뀐닉네임");

        ExtractableResponse<Response> response =
                patchWithAuthorization("/api/members/me", token, memberUpdateRequest);
        MemberResponse memberResponse = getWithAuthorization("/api/members/me", token)
                .as(MemberResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(memberResponse.getEmail()).isEqualTo("abc@woowahan.com");
        assertThat(memberResponse.getNickname()).isEqualTo("바뀐닉네임");
    }

    @DisplayName("형식에 맞지 않는 회원 정보로 수정하려고 하면 400을 응답한다.")
    @Test
    void updateMember_BadRequest() {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        String token = 로그인을_한다("abc@woowahan.com", "1q2w3e4r!")
                .as(LoginResponse.class)
                .getToken();
        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest("잘못된닉네임");

        ExtractableResponse<Response> response =
                patchWithAuthorization("/api/members/me", token, memberUpdateRequest);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(message).isEqualTo("닉네임 형식이 올바르지 않습니다.");
    }

    @DisplayName("토큰에 해당하는 사용자의 비밀번호를 수정하고 성공하면 204를 응답한다.")
    @Test
    void updatePassword_NoContent() {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        String token = 로그인을_한다("abc@woowahan.com", "1q2w3e4r!")
                .as(LoginResponse.class)
                .getToken();
        PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest("1q2w3e4r@");

        ExtractableResponse<Response> response =
                patchWithAuthorization("/api/members/password", token, passwordUpdateRequest);
        LoginRequest updatedLoginRequest = new LoginRequest("abc@woowahan.com", "1q2w3e4r@");
        ExtractableResponse<Response> updatedLoginResponse = post("/api/login", updatedLoginRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(updatedLoginResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("형식에 맞지 않는 비밀번호로 수정하려고 하면 400을 응답한다.")
    @Test
    void updatePassword_BadRequest() {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        String token = 로그인을_한다("abc@woowahan.com", "1q2w3e4r!")
                .as(LoginResponse.class)
                .getToken();
        PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest("1q2w3e4r");

        ExtractableResponse<Response> response =
                patchWithAuthorization("/api/members/password", token, passwordUpdateRequest);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(message).isEqualTo("비밀번호 형식이 올바르지 않습니다.");
    }

    @DisplayName("토큰에 해당하는 회원을 삭제하고 성공하면 204를 응답한다.")
    @Test
    void deleteMember_NoContent() {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        String token = 로그인을_한다("abc@woowahan.com", "1q2w3e4r!")
                .as(LoginResponse.class)
                .getToken();

        ExtractableResponse<Response> response = deleteWithAuthorization("/api/members/me", token);
        ExtractableResponse<Response> loginResponse = 로그인을_한다("abc@woowahan.com", "1q2w3e4r!");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("이미 삭제된 회원의 토큰으로 접근하려고 하면 401을 응답한다.")
    @Test
    void requestWithDeletedMemberToken_Unauthorized() {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        String token = 로그인을_한다("abc@woowahan.com", "1q2w3e4r!")
                .as(LoginResponse.class)
                .getToken();
        deleteWithAuthorization("/api/members/me", token);

        ExtractableResponse<Response> response = deleteWithAuthorization("/api/members/me", token);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(message).isEqualTo("유효하지 않은 토큰입니다.");
    }

    @DisplayName("preflight 요청에 대해서는 인증 정보를 확인하지 않는다.")
    @Test
    void preflight() {
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .options("/api/members/me")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 회원가입을_한다(String email, String password, String nickname) {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(email, password, nickname);
        post("/api/members", memberCreateRequest);
    }

    private ExtractableResponse<Response> 로그인을_한다(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        return post("/api/login", loginRequest);
    }
}
