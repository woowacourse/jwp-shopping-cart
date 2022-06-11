package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
class AuthServiceTest {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceTest(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @DisplayName("토큰을 만들어 반환한다.")
    @Test
    void createToken() {
        final TokenRequest tokenRequest = new TokenRequest("email@email.com", "password123!");

        final String accessToken = authService.createToken(tokenRequest);

        assertThat(jwtTokenProvider.getSubject(accessToken)).isEqualTo("email@email.com");
    }

    @DisplayName("잘못된 이메일과 비밀번호를 통해 토큰 생성을 요청할 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"email@email.com, invalid123!", "notexistingemail@email.com, password123!",
            "notexistingemail@email.com, invalid123!"})
    void tokenCreationFail(final String email, final String password) {
        final TokenRequest tokenRequest = new TokenRequest(email, password);

        assertThatThrownBy(() -> authService.createToken(tokenRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("아이디나 비밀번호를 잘못 입력했습니다.");
    }
}
