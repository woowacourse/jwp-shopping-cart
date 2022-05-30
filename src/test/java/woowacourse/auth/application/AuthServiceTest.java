package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.AuthorizedMember;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginResponse;
import woowacourse.auth.dto.MemberCreateRequest;
import woowacourse.auth.dto.NicknameUpdateRequest;
import woowacourse.auth.dto.PasswordCheckRequest;
import woowacourse.auth.dto.PasswordUpdateRequest;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @DisplayName("회원 객체를 생성하고 DB에 저장한다.")
    @Test
    void saveMember() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");

        authService.save(memberCreateRequest);
    }

    @DisplayName("이미 존재하는 이메일로 회원을 생성하려고 하면 예외를 반환한다.")
    @Test
    void saveMember_DuplicatedEmail() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");

        authService.save(memberCreateRequest);

        assertThatThrownBy(() -> authService.save(memberCreateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 이메일 주소입니다.");
    }

    @DisplayName("로그인에 성공하면 jwt 토큰과 닉네임을 반환한다.")
    @Test
    void login() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        authService.save(memberCreateRequest);
        LoginRequest loginRequest = new LoginRequest("abc@woowahan.com", "1q2w3e4r!");

        LoginResponse loginResponse = authService.login(loginRequest);

        assertThat(loginResponse.getToken()).isNotNull();
        assertThat(loginResponse.getNickname()).isEqualTo("닉네임");
    }

    @DisplayName("올바르지 않은 정보로 로그인하려고 하면 예외를 반환한다.")
    @ParameterizedTest
    @CsvSource({"abc@naver.com, 1q2w3e4r!", "abc@woowahan.com, asdas1123!", "abc@naver.com, asdas1123!"})
    void login_Invalid(String email, String password) {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        authService.save(memberCreateRequest);
        LoginRequest loginRequest = new LoginRequest(email, password);

        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일과 비밀번호를 확인해주세요.");
    }

    @DisplayName("토큰과 비밀번호를 받아서, 올바른지 반환한다.")
    @ParameterizedTest
    @CsvSource({"1q2w3e4r!, true", "asda1234!, false"})
    void checkPassword(String password, boolean expected) {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        authService.save(memberCreateRequest);
        PasswordCheckRequest passwordCheckRequest = new PasswordCheckRequest(password);

        boolean actual = authService.checkPassword("abc@woowahan.com", passwordCheckRequest)
                .isSuccess();

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("토큰을 받아, 이메일과 닉네임을 반환한다.")
    @Test
    void findAuthorizedMemberByToken() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        authService.save(memberCreateRequest);
        LoginRequest loginRequest = new LoginRequest("abc@woowahan.com", "1q2w3e4r!");
        String token = authService.login(loginRequest)
                .getToken();

        AuthorizedMember authorizedMember = authService.findAuthorizedMemberByToken(token);

        assertThat(authorizedMember.getEmail()).isEqualTo("abc@woowahan.com");
        assertThat(authorizedMember.getNickname()).isEqualTo("닉네임");
    }

    @DisplayName("유효하지 않은 토큰으로 인증하려고 하면 예외를 반환한다.")
    @Test
    void findAuthorizedMemberByToken_InvalidToken() {
        assertThatThrownBy(() -> authService.findAuthorizedMemberByToken("abc"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 토큰입니다.");
    }

    @DisplayName("이메일과 닉네임을 받아 닉네임을 수정한다.")
    @Test
    void updateNickname() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        authService.save(memberCreateRequest);

        authService.updateNickname("abc@woowahan.com", new NicknameUpdateRequest("바뀐닉네임"));
        LoginRequest loginRequest = new LoginRequest("abc@woowahan.com", "1q2w3e4r!");
        String nickname = authService.login(loginRequest)
                .getNickname();

        assertThat(nickname).isEqualTo("바뀐닉네임");
    }

    @DisplayName("존재하지 않는 회원의 닉네임을 수정하려고 하면 예외를 반환한다.")
    @Test
    void updateNickName_NotFoundMember() {
        assertThatThrownBy(() -> authService.updateNickname("abc@woowahan.com", new NicknameUpdateRequest("바뀐닉네임")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("올바르지 않은 형식의 닉네임으로 변경하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_InvalidNicknameFormat() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        authService.save(memberCreateRequest);

        assertThatThrownBy(() -> authService.updateNickname("abc@woowahan.com", new NicknameUpdateRequest("1234")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임 형식이 올바르지 않습니다.");
    }

    @DisplayName("이메일과 비밀번호를 받아 비밀번호를 수정한다.")
    @Test
    void updatePassword() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        authService.save(memberCreateRequest);

        authService.updatePassword("abc@woowahan.com", new PasswordUpdateRequest("1q2w3e4r@"));

        LoginRequest loginRequest = new LoginRequest("abc@woowahan.com", "1q2w3e4r@");

        assertThatCode(() -> authService.login(loginRequest))
                .doesNotThrowAnyException();
    }

    @DisplayName("존재하지 않는 회원의 비밀번호를 수정하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_NotFoundMember() {
        assertThatThrownBy(() -> authService.updatePassword("abc@woowahan.com", new PasswordUpdateRequest("1q2w3e4r@")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("올바르지 않은 형식의 비밀번호로 변경하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_InvalidPasswordFormat() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        authService.save(memberCreateRequest);

        assertThatThrownBy(() -> authService.updatePassword("abc@woowahan.com", new PasswordUpdateRequest("1234")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호 형식이 올바르지 않습니다.");
    }

    @DisplayName("이메일이 일치하는 회원 정보를 삭제한다.")
    @Test
    void deleteMember() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        authService.save(memberCreateRequest);

        authService.delete("abc@woowahan.com");
        boolean actual = authService.existsEmail("abc@woowahan.com");

        assertThat(actual).isFalse();
    }

    @DisplayName("존재하는 이메일인지 반환한다.")
    @ParameterizedTest
    @CsvSource({"abc@woowahan.com, true", "abc@naver.com, false"})
    void existsEmail(String email, boolean expected) {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        authService.save(memberCreateRequest);

        boolean actual = authService.existsEmail(email);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("올바르지 않은 이메일 형식으로 이메일이 존재하는지 확인하려하면 예외를 반환한다.")
    @Test
    void existsEmail_InvalidFormat() {
        String invalid = "abc";
        assertThatThrownBy(() -> authService.existsEmail(invalid))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다.");
    }

    @DisplayName("존재하지 않는 회원을 삭제하려 하면 예외를 반환한다.")
    @Test
    void deleteMember_NotFoundMember() {
        assertThatThrownBy(() -> authService.delete("abc@woowahan.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }
}
