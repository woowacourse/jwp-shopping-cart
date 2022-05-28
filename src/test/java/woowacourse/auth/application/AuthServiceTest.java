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
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginResponse;
import woowacourse.auth.dto.MemberRequest;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @DisplayName("회원 객체를 생성하고 DB에 저장한다.")
    @Test
    void saveMember() {
        MemberRequest memberRequest = new MemberRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");

        authService.save(memberRequest);
    }

    @DisplayName("이미 존재하는 이메일로 회원을 생성하려고 하면 예외를 반환한다.")
    @Test
    void saveMember_DuplicatedEmail() {
        MemberRequest memberRequest = new MemberRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");

        authService.save(memberRequest);

        assertThatThrownBy(() -> authService.save(memberRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 이메일 주소입니다.");
    }

    @DisplayName("로그인에 성공하면 jwt 토큰과 닉네임을 반환한다.")
    @Test
    void login() {
        MemberRequest memberRequest = new MemberRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        authService.save(memberRequest);
        LoginRequest loginRequest = new LoginRequest("abc@woowahan.com", "1q2w3e4r!");

        LoginResponse loginResponse = authService.login(loginRequest);

        assertThat(loginResponse.getToken()).isNotNull();
        assertThat(loginResponse.getNickname()).isEqualTo("닉네임");
    }

    @DisplayName("올바르지 않은 정보로 로그인하려고 하면 예외를 반환한다.")
    @ParameterizedTest
    @CsvSource({"abc@naver.com, 1q2w3e4r!", "abc@woowahan.com, asdas1123!", "abc@naver.com, asdas1123!"})
    void login_Invalid(String email, String password) {
        MemberRequest memberRequest = new MemberRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        authService.save(memberRequest);
        LoginRequest loginRequest = new LoginRequest(email, password);

        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일과 비밀번호를 확인해주세요.");
    }
}
