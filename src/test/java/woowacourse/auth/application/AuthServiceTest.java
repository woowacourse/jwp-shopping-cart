package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.dto.request.LoginServiceRequest;
import woowacourse.auth.application.dto.request.MemberCreateServiceRequest;
import woowacourse.auth.application.dto.request.MemberUpdateServiceRequest;
import woowacourse.auth.application.dto.response.LoginServiceResponse;
import woowacourse.auth.application.dto.response.MemberServiceResponse;
import woowacourse.auth.exception.AuthorizationException;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AuthServiceTest {

    private static final String EMAIL = "abc@woowahan.com";
    private static final String PASSWORD = "1q2w3e4r!";
    private static final String NICKNAME = "닉네임";
    private static final String NON_EXISTING_MEMBER_EMAIL = "abc@naver.com";
    private static final String NEW_PASSWORD = "1q2w3e4r@";
    private static final String NEW_NICKNAME = "바뀐닉네임";

    @Autowired
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService.save(new MemberCreateServiceRequest(EMAIL, PASSWORD, NICKNAME));
    }

    @DisplayName("이미 존재하는 이메일로 회원을 생성하려고 하면 예외를 반환한다.")
    @Test
    void saveMember_DuplicatedEmail() {
        assertThatThrownBy(() -> authService.save(new MemberCreateServiceRequest(EMAIL, PASSWORD, NICKNAME)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 이메일 주소입니다.");
    }

    @DisplayName("로그인에 성공하면 jwt 토큰과 닉네임을 반환한다.")
    @Test
    void login() {
        LoginServiceRequest loginRequest = new LoginServiceRequest(EMAIL, PASSWORD);

        LoginServiceResponse loginServiceResponse = authService.login(loginRequest);

        assertAll(
                () -> assertThat(loginServiceResponse.getToken()).isNotNull(),
                () -> assertThat(loginServiceResponse.getNickname()).isEqualTo(NICKNAME)
        );
    }

    @DisplayName("올바르지 않은 정보로 로그인하려고 하면 예외를 반환한다.")
    @ParameterizedTest
    @CsvSource({"abc@naver.com, 1q2w3e4r!", "abc@woowahan.com, asdas1123!", "abc@naver.com, asdas1123!"})
    void login_Invalid(String email, String password) {
        LoginServiceRequest loginRequest = new LoginServiceRequest(email, password);

        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일과 비밀번호를 확인해주세요.");
    }

    @DisplayName("토큰과 비밀번호를 받아서, 올바른지 반환한다.")
    @ParameterizedTest
    @CsvSource({"1q2w3e4r!, true", "asda1234!, false"})
    void checkPassword(String password, boolean expected) {
        boolean actual = authService.checkPassword(EMAIL, password);

        assertThat(actual).isEqualTo(expected);
    }


    @DisplayName("이메일과 수정할 회원 정보를 받아 회원 정보를 수정한다.")
    @Test
    void updateMember() {
        authService.updateMember(EMAIL, new MemberUpdateServiceRequest(NEW_NICKNAME));
        LoginServiceRequest loginRequest = new LoginServiceRequest(EMAIL, PASSWORD);

        String updatedNickname = authService.login(loginRequest)
                .getNickname();

        assertThat(updatedNickname).isEqualTo(NEW_NICKNAME);
    }

    @DisplayName("존재하지 않는 회원의 정보를 수정하려고 하면 예외를 반환한다.")
    @Test
    void updateMember_NotFoundMember() {
        assertThatThrownBy(
                () -> authService.updateMember(NON_EXISTING_MEMBER_EMAIL, new MemberUpdateServiceRequest(NEW_NICKNAME)))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("유효하지 않은 토큰입니다.");
    }

    @DisplayName("올바르지 않은 형식의 닉네임으로 변경하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_InvalidNicknameFormat() {
        String invalidNickname = "1234";

        assertThatThrownBy(() -> authService.updateMember(EMAIL, new MemberUpdateServiceRequest(invalidNickname)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임 형식이 올바르지 않습니다.");
    }

    @DisplayName("이메일과 비밀번호를 받아 비밀번호를 수정한다.")
    @Test
    void updatePassword() {
        authService.updatePassword(EMAIL, NEW_PASSWORD);

        LoginServiceRequest loginRequest = new LoginServiceRequest(EMAIL, NEW_PASSWORD);

        assertThatCode(() -> authService.login(loginRequest))
                .doesNotThrowAnyException();
    }

    @DisplayName("회원의 정보를 반환한다.")
    @Test
    void findMember() {
        MemberServiceResponse memberServiceResponse = authService.findMember(EMAIL);

        assertAll(
                () -> assertThat(memberServiceResponse.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(memberServiceResponse.getNickname()).isEqualTo(NICKNAME)
        );
    }

    @DisplayName("존재하지 않는 회원의 정보를 요청하면 예외를 반환한다.")
    @Test
    void findMember_NotFoundMember() {
        assertThatThrownBy(() -> authService.findMember(NON_EXISTING_MEMBER_EMAIL))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("유효하지 않은 토큰입니다.");
    }

    @DisplayName("존재하지 않는 회원의 비밀번호를 수정하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_NotFoundMember() {
        assertThatThrownBy(
                () -> authService.updatePassword(NON_EXISTING_MEMBER_EMAIL, NEW_PASSWORD))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("유효하지 않은 토큰입니다.");
    }

    @DisplayName("올바르지 않은 형식의 비밀번호로 변경하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_InvalidPasswordFormat() {
        String invalidPassword = "1234";

        assertThatThrownBy(() -> authService.updatePassword(EMAIL, invalidPassword))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호 형식이 올바르지 않습니다.");
    }

    @DisplayName("이메일이 일치하는 회원 정보를 삭제한다.")
    @Test
    void deleteMember() {
        authService.delete(EMAIL);
        boolean actual = authService.existsEmail(EMAIL);

        assertThat(actual).isFalse();
    }

    @DisplayName("존재하는 이메일인지 반환한다.")
    @ParameterizedTest
    @CsvSource({"abc@woowahan.com, true", "abc@naver.com, false"})
    void existsEmail(String email, boolean expected) {
        boolean actual = authService.existsEmail(email);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("올바르지 않은 이메일 형식으로 이메일이 존재하는지 확인하려하면 예외를 반환한다.")
    @Test
    void existsEmail_InvalidFormat() {
        String invalidEmail = "abc";
        assertThatThrownBy(() -> authService.existsEmail(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다.");
    }

    @DisplayName("존재하지 않는 회원을 삭제하려 하면 예외를 반환한다.")
    @Test
    void deleteMember_NotFoundMember() {
        assertThatThrownBy(() -> authService.delete(NON_EXISTING_MEMBER_EMAIL))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("유효하지 않은 토큰입니다.");
    }
}
