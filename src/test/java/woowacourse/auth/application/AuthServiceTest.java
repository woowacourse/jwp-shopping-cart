package woowacourse.auth.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.member.exception.MemberNotFoundException;
import woowacourse.member.exception.WrongPasswordException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class AuthServiceTest {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceTest(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @DisplayName("토큰을 생성한다.")
    @Test
    void createToken() {
        LoginResponse token = authService.createToken(123L);
        boolean result = jwtTokenProvider.validateToken(token.getAccessToken());
        assertThat(result).isTrue();
    }

    @DisplayName("올바른 데이터로 회원 인증에 성공한다.")
    @Test
    void authenticateMember() {
        assertDoesNotThrow(
                () -> authService.authenticate(new LoginRequest("ari@wooteco.com", "Wooteco1!"))
        );
    }

    @DisplayName("존재하지 않는 이메일로 회원 인증을 하는 경우 예외가 발생한다.")
    @Test
    void authenticateMemberWithNotExistEmail() {
        assertThatThrownBy(
                () -> authService.authenticate(new LoginRequest("pobi@wooteco.com", "Wooteco!"))
        ).isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");
    }

    @DisplayName("잘못된 비밀번호로 회원 인증을 하는 경우 예외가 발생한다.")
    @Test
    void authenticateMemberWithWrongPassword() {
        assertThatThrownBy(
                () -> authService.authenticate(new LoginRequest("ari@wooteco.com", "Javajigi!!"))
        ).isInstanceOf(WrongPasswordException.class)
                .hasMessageContaining("잘못된 비밀번호입니다.");
    }
}
