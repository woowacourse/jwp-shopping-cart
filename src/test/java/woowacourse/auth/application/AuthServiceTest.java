package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.exception.InvalidLoginFormException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Transactional
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("존재하지 않는 로그인 정보일 경우에 예외를 발생")
    @Test
    void notFoundCustomerException() {
        assertThatThrownBy(() ->
                authService.createToken(new TokenRequest("email@email.com", "12345678a"))
        ).isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("존재하는 로그인 정보일 경우 토큰 반환")
    @Test
    void createValidCustomerToken() {
        SignUpRequest signUpRequest = new SignUpRequest("email@email.com", "12345678a", "tonic");
        customerService.registerCustomer(signUpRequest);
        TokenRequest tokenRequest = new TokenRequest("email@email.com", "12345678a");
        String token = authService.createToken(tokenRequest);
        Assertions.assertAll(
                () -> assertThat(token).isNotBlank(),
                () -> assertThat(jwtTokenProvider.validateToken(token)).isTrue(),
                () -> assertThat(jwtTokenProvider.getPayload(token)).isEqualTo("email@email.com")
        );
    }

    @DisplayName("잘못된 비밀번호일 경우 예외 발생")
    @Test
    void invalidPasswordThrowException() {
        SignUpRequest signUpRequest = new SignUpRequest("email@email.com", "12345678a", "tonic");
        customerService.registerCustomer(signUpRequest);
        TokenRequest tokenRequest = new TokenRequest("email@email.com", "12345678b");

        assertThatThrownBy(() -> authService.createToken(tokenRequest))
                .isInstanceOf(InvalidLoginFormException.class);
    }
}
