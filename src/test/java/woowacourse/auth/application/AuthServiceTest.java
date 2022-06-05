package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.request.LoginRequest;
import woowacourse.auth.dto.request.MemberCreateRequest;
import woowacourse.auth.dto.request.PasswordCheckRequest;
import woowacourse.auth.dto.response.LoginResponse;

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

    @DisplayName("로그인에 성공하면 토큰과 닉네임을 반환한다.")
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

    @DisplayName("이메일과 비밀번호를 받아서, 올바른지 반환한다.")
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
}
