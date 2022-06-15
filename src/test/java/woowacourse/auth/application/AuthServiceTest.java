package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.exception.InvalidLoginFormException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.customer.CustomerSignUpRequest;
import woowacourse.shoppingcart.exception.customer.InvalidCustomerBadRequestException;

@SpringBootTest
@Transactional
public class AuthServiceTest {

    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "12345678a";
    private static final String NICKNAME = "tonic";

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest(EMAIL, PASSWORD, NICKNAME);
        customerService.register(customerSignUpRequest);
    }

    @DisplayName("존재하지 않는 로그인 정보일 경우에 예외를 발생")
    @Test
    void notFoundCustomerException() {
        String notFoundEmail = "notFoundEmail@email.com";
        assertThatThrownBy(() ->
                authService.createToken(new TokenRequest(notFoundEmail, PASSWORD))
        ).isInstanceOf(InvalidCustomerBadRequestException.class);
    }

    @DisplayName("존재하는 로그인 정보일 경우 토큰 반환")
    @Test
    void createValidCustomerToken() {
        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        String token = authService.createToken(tokenRequest);
        Assertions.assertAll(
                () -> assertThat(token).isNotBlank(),
                () -> assertThat(jwtTokenProvider.validateToken(token)).isTrue()
        );
    }

    @DisplayName("잘못된 비밀번호일 경우 예외 발생")
    @Test
    void invalidPasswordThrowException() {
        String invalidPassword = "12345678b";
        TokenRequest tokenRequest = new TokenRequest(EMAIL, invalidPassword);

        assertThatThrownBy(() -> authService.createToken(tokenRequest))
                .isInstanceOf(InvalidLoginFormException.class);
    }
}
