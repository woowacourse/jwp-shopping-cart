package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.util.HttpRequestUtil.get;
import static woowacourse.util.HttpRequestUtil.post;
import static woowacourse.util.HttpRequestUtil.postWithAuthorization;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.CheckResponse;
import woowacourse.auth.dto.ErrorResponse;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginResponse;
import woowacourse.auth.dto.MemberCreateRequest;
import woowacourse.auth.dto.PasswordCheckRequest;
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
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(
                "abc@woowahan.com",
                "1q2w3e4r!",
                "닉네임"
        );
        post("/api/members", memberCreateRequest);

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
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(
                "abc@woowahan.com",
                "1q2w3e4r!",
                "닉네임"
        );
        post("/api/members", memberCreateRequest);
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
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(
                "abc@woowahan.com",
                "1q2w3e4r!",
                "닉네임"
        );
        post("/api/members", memberCreateRequest);
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
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(
                "abc@woowahan.com",
                "1q2w3e4r!",
                "닉네임"
        );
        post("/api/members", memberCreateRequest);
        LoginRequest loginRequest = new LoginRequest("abc@woowahan.com", "1q2w3e4r!");

        LoginResponse loginResponse = post("/api/login", loginRequest).as(LoginResponse.class);

        ExtractableResponse<Response> response = postWithAuthorization(
                "/api/members/auth/password-check",
                loginResponse.getToken(),
                new PasswordCheckRequest(password)
        );

        boolean success = response.as(CheckResponse.class)
                .isSuccess();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(success).isEqualTo(expected);
    }

    @DisplayName("로그인을 하지 않고(토큰이 없는 경우) 인증이 필요한 URI에 접근하면 401을 응답한다.")
    @Test
    void requestWithUnauthorized() {
        ExtractableResponse<Response> response = post(
                "/api/members/auth/password-check",
                new PasswordCheckRequest("1q2w3e4r!")
        );
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(message).isEqualTo("로그인이 필요합니다.");
    }

    @DisplayName("유효하지 않은 토큰으로 인증이 필요한 URI에 접근하면 401을 응답한다.")
    @Test
    void requestWithInvalidToken() {
        ExtractableResponse<Response> response = postWithAuthorization(
                "/api/members/auth/password-check",
                "abc",
                new PasswordCheckRequest("1q2w3e4r!")
        );
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(message).isEqualTo("유효하지 않은 토큰입니다.");
    }
}
