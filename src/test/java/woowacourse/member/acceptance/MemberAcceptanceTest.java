package woowacourse.member.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.member.acceptance.MemberAcceptanceTestFixture.DATA_EMPTY_EXCEPTION_MESSAGE;
import static woowacourse.member.acceptance.MemberAcceptanceTestFixture.EMAIL_DUPLICATION_CHECK_URI;
import static woowacourse.member.acceptance.MemberAcceptanceTestFixture.LOGIN_URI;
import static woowacourse.member.acceptance.MemberAcceptanceTestFixture.MEMBERS_URI;
import static woowacourse.member.acceptance.MemberAcceptanceTestFixture.MEMBER_CREATE_REQUEST;
import static woowacourse.member.acceptance.MemberAcceptanceTestFixture.PASSWORD_CHECK_URI;
import static woowacourse.member.acceptance.MemberAcceptanceTestFixture.PASSWORD_UPDATE_URI;
import static woowacourse.member.acceptance.MemberAcceptanceTestFixture.SIGN_UP_URI;
import static woowacourse.member.acceptance.MemberAcceptanceTestFixture.VALID_LOGIN_REQUEST;
import static woowacourse.member.acceptance.MemberAcceptanceTestFixture.VALID_NICKNAME_UPDATE_REQUEST;
import static woowacourse.member.acceptance.MemberAcceptanceTestFixture.VALID_PASSWORD_CHECK_REQUEST;
import static woowacourse.member.acceptance.MemberAcceptanceTestFixture.VALID_PASSWORD_UPDATE_REQUEST;
import static woowacourse.util.HttpRequestUtil.deleteWithAuthorization;
import static woowacourse.util.HttpRequestUtil.get;
import static woowacourse.util.HttpRequestUtil.getWithAuthorization;
import static woowacourse.util.HttpRequestUtil.patchWithAuthorization;
import static woowacourse.util.HttpRequestUtil.post;
import static woowacourse.util.HttpRequestUtil.postWithAuthorization;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import woowacourse.member.dto.request.LoginRequest;
import woowacourse.member.dto.request.MemberCreateRequest;
import woowacourse.member.dto.request.MemberUpdateRequest;
import woowacourse.member.dto.request.PasswordRequest;
import woowacourse.member.dto.response.UniqueEmailCheckResponse;
import woowacourse.member.dto.response.ErrorResponse;
import woowacourse.member.dto.response.LoginResponse;
import woowacourse.member.dto.response.MemberResponse;
import woowacourse.member.dto.response.PasswordCheckResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("인증 관련 기능")
class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("이메일, 비밀번호, 닉네임으로 회원 가입에 성공하면 201를 응답한다.")
    @Test
    void signUp_Created() {
        ExtractableResponse<Response> response = post(SIGN_UP_URI, MEMBER_CREATE_REQUEST);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("규칙에 맞지 않는 정보로 회원 가입을 시도하면 400을 응답한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidMemberCreateRequestAndExpectedMessage")
    void signUp_BadRequest(String email, String password, String nickname, String expectedMessage) {
        MemberCreateRequest invalidMemberCreateRequest = new MemberCreateRequest(email, password, nickname);

        ExtractableResponse<Response> response = post(SIGN_UP_URI, invalidMemberCreateRequest);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(message).contains(expectedMessage)
        );
    }

    private static Stream<Arguments> provideInvalidMemberCreateRequestAndExpectedMessage() {
        return Stream.of(
                Arguments.of("abc", "1q2w3e4r!", "닉네임", "형식이 올바르지 않습니다."),
                Arguments.of("abc@woowahan.com", "1q2w3e4r", "닉네임", "형식이 올바르지 않습니다."),
                Arguments.of("abc@woowahan.com", "1q2w3e4r!", "잘못된닉네임", "형식이 올바르지 않습니다."),
                Arguments.of(null, "1q2w3e4r!", "닉네임", DATA_EMPTY_EXCEPTION_MESSAGE),
                Arguments.of("", "1q2w3e4r!", "닉네임", DATA_EMPTY_EXCEPTION_MESSAGE),
                Arguments.of("abc@woowahan.com", null, "닉네임", DATA_EMPTY_EXCEPTION_MESSAGE),
                Arguments.of("abc@woowahan.com", "", "닉네임", DATA_EMPTY_EXCEPTION_MESSAGE),
                Arguments.of("abc@woowahan.com", "1q2w3e4r!", null, DATA_EMPTY_EXCEPTION_MESSAGE),
                Arguments.of("abc@woowahan.com", "1q2w3e4r!", "", DATA_EMPTY_EXCEPTION_MESSAGE)
        );
    }

    @DisplayName("이미 회원으로 등록된 이메일인지와 200을 응답한다.")
    @ParameterizedTest
    @CsvSource({"abc@woowahan.com, false", "abc@naver.com, true"})
    void checkDuplicatedEmail_OK(String email, boolean expected) {
        post(SIGN_UP_URI, MEMBER_CREATE_REQUEST);

        ExtractableResponse<Response> response = get(EMAIL_DUPLICATION_CHECK_URI + email);
        boolean success = response.as(UniqueEmailCheckResponse.class)
                .isUnique();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(success).isEqualTo(expected)
        );
    }

    @DisplayName("잘못된 이메일 형식으로 중복 체크를 하려하면 400을 응답한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidEmailAndExpectedMessage")
    void checkDuplicatedEmail_BadRequest(String invalidEmailCheckRequestUri, String expectedMessage) {

        ExtractableResponse<Response> response = get(invalidEmailCheckRequestUri);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(message).isEqualTo(expectedMessage)
        );
    }

    private static Stream<Arguments> provideInvalidEmailAndExpectedMessage() {
        return Stream.of(
                Arguments.of("/api/members/email-check?email=", DATA_EMPTY_EXCEPTION_MESSAGE),
                Arguments.of("/api/members/email-check?email= ", DATA_EMPTY_EXCEPTION_MESSAGE),
                Arguments.of("/api/members/email-check?email=abc", "이메일 형식이 올바르지 않습니다.")
        );
    }

    @DisplayName("올바른 이메일과 비밀번호로 로그인 요청을 하면 토큰과 닉네임을 반환하고 200을 응답한다.")
    @Test
    void login_Ok() {
        post(SIGN_UP_URI, MEMBER_CREATE_REQUEST);

        ExtractableResponse<Response> response = post(LOGIN_URI, VALID_LOGIN_REQUEST);
        LoginResponse loginResponse = response.as(LoginResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(loginResponse.getToken()).isNotNull(),
                () -> assertThat(loginResponse.getNickname()).isEqualTo(MEMBER_CREATE_REQUEST.getNickname())
        );
    }

    @DisplayName("올바르지 않은 이메일과 비밀번호로 로그인 요청을 하면 400을 응답한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidEmailAndPasswordAndExpectedMessage")
    void login_BadRequest(String email, String password, String expectedMessage) {
        post(SIGN_UP_URI, MEMBER_CREATE_REQUEST);
        LoginRequest invalidLoginRequest = new LoginRequest(email, password);

        ExtractableResponse<Response> response = post(LOGIN_URI, invalidLoginRequest);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(message).isEqualTo(expectedMessage)
        );
    }

    private static Stream<Arguments> provideInvalidEmailAndPasswordAndExpectedMessage() {
        return Stream.of(
                Arguments.of(null, "1q2w3e4r!", DATA_EMPTY_EXCEPTION_MESSAGE),
                Arguments.of("", "1q2w3e4r!", DATA_EMPTY_EXCEPTION_MESSAGE),
                Arguments.of("abc@naver.com", null, DATA_EMPTY_EXCEPTION_MESSAGE),
                Arguments.of("abc@naver.com", "", DATA_EMPTY_EXCEPTION_MESSAGE),
                Arguments.of("abc@naver.com", "1q2w3e4r!", "이메일과 비밀번호를 확인해주세요."),
                Arguments.of("abc@woowahan.com", "1q2w3e4r@", "이메일과 비밀번호를 확인해주세요.")
        );
    }

    @DisplayName("토큰에 해당하는 사용자의 비밀번호가 일치하는지를 반환하고 200을 응답한다.")
    @ParameterizedTest
    @CsvSource({"1q2w3e4r!, true", "1q2w3e4r@, false"})
    void confirmPassword_Ok(String password, boolean expected) {
        post(SIGN_UP_URI, MEMBER_CREATE_REQUEST);
        String token = post(LOGIN_URI, VALID_LOGIN_REQUEST).as(LoginResponse.class)
                .getToken();

        ExtractableResponse<Response> response =
                postWithAuthorization(PASSWORD_CHECK_URI, token, new PasswordRequest(password));
        boolean success = response.as(PasswordCheckResponse.class)
                .isSuccess();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(success).isEqualTo(expected)
        );
    }

    @DisplayName("로그인을 하지 않고(토큰이 없는 경우) 인증이 필요한 URI에 접근하면 401을 응답한다.")
    @Test
    void requestWithUnauthorized_Unauthorized() {
        ExtractableResponse<Response> response =
                post(PASSWORD_CHECK_URI, VALID_PASSWORD_CHECK_REQUEST);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(message).isEqualTo("로그인이 필요합니다.")
        );
    }

    @DisplayName("유효하지 않은 토큰으로 인증이 필요한 URI에 접근하면 401을 응답한다.")
    @Test
    void requestWithInvalidToken_Unauthorized() {
        String invalidToken = "abc";
        ExtractableResponse<Response> response =
                postWithAuthorization(PASSWORD_CHECK_URI, invalidToken, VALID_PASSWORD_CHECK_REQUEST);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(message).isEqualTo("유효하지 않은 토큰입니다.")
        );
    }

    @DisplayName("토큰에 해당하는 사용자의 회원 정보와 200을 응답한다.")
    @Test
    void showMember_Ok() {
        post(SIGN_UP_URI, MEMBER_CREATE_REQUEST);
        String token = post(LOGIN_URI, VALID_LOGIN_REQUEST).as(LoginResponse.class)
                .getToken();

        ExtractableResponse<Response> response = getWithAuthorization(MEMBERS_URI, token);
        MemberResponse memberResponse = response.as(MemberResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(memberResponse.getEmail()).isEqualTo(MEMBER_CREATE_REQUEST.getEmail()),
                () -> assertThat(memberResponse.getNickname()).isEqualTo(MEMBER_CREATE_REQUEST.getNickname())
        );
    }

    @DisplayName("토큰에 해당하는 사용자의 회원 정보를 수정하고 성공하면 204를 응답한다.")
    @Test
    void updateMember_NoContent() {
        post(SIGN_UP_URI, MEMBER_CREATE_REQUEST);
        String token = post(LOGIN_URI, VALID_LOGIN_REQUEST).as(LoginResponse.class)
                .getToken();

        ExtractableResponse<Response> response =
                patchWithAuthorization(MEMBERS_URI, token, VALID_NICKNAME_UPDATE_REQUEST);
        MemberResponse memberResponse = getWithAuthorization(MEMBERS_URI, token)
                .as(MemberResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(memberResponse.getEmail()).isEqualTo(MEMBER_CREATE_REQUEST.getEmail()),
                () -> assertThat(memberResponse.getNickname()).isEqualTo(VALID_NICKNAME_UPDATE_REQUEST.getNickname())
        );
    }

    @DisplayName("형식에 맞지 않는 회원 정보로 수정하려고 하면 400을 응답한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidNicknameAndExpectedMessage")
    void updateMember_BadRequest(String invalidNickname, String expectedMessage) {
        post(SIGN_UP_URI, MEMBER_CREATE_REQUEST);
        String token = post(LOGIN_URI, VALID_LOGIN_REQUEST).as(LoginResponse.class)
                .getToken();

        ExtractableResponse<Response> response =
                patchWithAuthorization(MEMBERS_URI, token, new MemberUpdateRequest(invalidNickname));
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(message).isEqualTo(expectedMessage)
        );
    }

    private static Stream<Arguments> provideInvalidNicknameAndExpectedMessage() {
        return Stream.of(
                Arguments.of("잘못된닉네임", "닉네임 형식이 올바르지 않습니다."),
                Arguments.of(null, DATA_EMPTY_EXCEPTION_MESSAGE),
                Arguments.of("", DATA_EMPTY_EXCEPTION_MESSAGE)
        );
    }

    @DisplayName("토큰에 해당하는 사용자의 비밀번호를 수정하고 성공하면 204를 응답한다.")
    @Test
    void updatePassword_NoContent() {
        post(SIGN_UP_URI, MEMBER_CREATE_REQUEST);
        String token = post(LOGIN_URI, VALID_LOGIN_REQUEST).as(LoginResponse.class)
                .getToken();

        ExtractableResponse<Response> response =
                patchWithAuthorization(PASSWORD_UPDATE_URI, token, VALID_PASSWORD_UPDATE_REQUEST);
        LoginRequest updatedLoginRequest =
                new LoginRequest(MEMBER_CREATE_REQUEST.getEmail(), VALID_PASSWORD_UPDATE_REQUEST.getPassword());
        ExtractableResponse<Response> updatedLoginResponse = post(LOGIN_URI, updatedLoginRequest);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(updatedLoginResponse.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @DisplayName("형식에 맞지 않는 비밀번호로 수정하려고 하면 400을 응답한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidPasswordAndExpectedMessage")
    void updatePassword_BadRequest(String invalidPassword, String expectedMessage) {
        post(SIGN_UP_URI, MEMBER_CREATE_REQUEST);
        String token = post(LOGIN_URI, VALID_LOGIN_REQUEST).as(LoginResponse.class)
                .getToken();

        ExtractableResponse<Response> response =
                patchWithAuthorization(PASSWORD_UPDATE_URI, token, new PasswordRequest(invalidPassword));
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(message).isEqualTo(expectedMessage)
        );
    }

    private static Stream<Arguments> provideInvalidPasswordAndExpectedMessage() {
        return Stream.of(
                Arguments.of("1q2w3e4r", "비밀번호 형식이 올바르지 않습니다."),
                Arguments.of(null, DATA_EMPTY_EXCEPTION_MESSAGE),
                Arguments.of("", DATA_EMPTY_EXCEPTION_MESSAGE)
        );
    }

    @DisplayName("토큰에 해당하는 회원을 삭제하고 성공하면 204를 응답한다.")
    @Test
    void deleteMember_NoContent() {
        post(SIGN_UP_URI, MEMBER_CREATE_REQUEST);
        String token = post(LOGIN_URI, VALID_LOGIN_REQUEST).as(LoginResponse.class)
                .getToken();

        ExtractableResponse<Response> response = deleteWithAuthorization(MEMBERS_URI, token);
        ExtractableResponse<Response> loginResponse = post(LOGIN_URI, VALID_LOGIN_REQUEST);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        );
    }

    @DisplayName("이미 삭제된 회원의 토큰으로 접근하려고 하면 401을 응답한다.")
    @Test
    void requestWithDeletedMemberToken_Unauthorized() {
        post(SIGN_UP_URI, MEMBER_CREATE_REQUEST);
        String token = post(LOGIN_URI, VALID_LOGIN_REQUEST).as(LoginResponse.class)
                .getToken();
        deleteWithAuthorization(MEMBERS_URI, token);

        ExtractableResponse<Response> response = deleteWithAuthorization(MEMBERS_URI, token);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(message).isEqualTo("유효하지 않은 토큰입니다.")
        );
    }
}
