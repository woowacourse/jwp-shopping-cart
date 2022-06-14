package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.bodyexception.InvalidLoginException;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private static MockedStatic<BCrypt> bcrypt;
    @InjectMocks
    private AuthService authService;
    @Mock
    private CustomerService customerService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        bcrypt = mockStatic(BCrypt.class);
    }

    @AfterEach
    void close() {
        bcrypt.close();
    }

    @DisplayName("로그인하려는 이메일이 존재하지 않으면 예외를 던진다.")
    @Test
    void login_notExistEmail_exceptionThrown() {
        // given
        TokenRequest request = new TokenRequest("email@email.com", "1q2w3e4r");

        given(customerService.getByEmail(request.getEmail()))
                .willThrow(IllegalArgumentException.class);

        // when, then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidLoginException.class);
    }

    @DisplayName("로그인하려는 비밀번호가 일치하지 않으면 예외를 던진다.")
    @Test
    void login_differentSamePassword_exceptionThrown() {
        // given
        String email = "email@email.com";
        TokenRequest request = new TokenRequest(email, "1q2w3e4r");

        Customer customer = new Customer("knu", email, "q1w2e3r4");
        given(customerService.getByEmail(request.getEmail()))
                .willReturn(customer);

        // when, then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidLoginException.class);
    }

    @Test
    @DisplayName("로그인 성공할 경우에 토큰을 발급한다.")
    void login_success_tokenReturned() {
        // given
        String email = "kun@email.com";
        String password = "qwerasdf123";
        TokenRequest request = new TokenRequest(email, password);

        Customer customer = new Customer(1L, "kun", email, password);
        given(customerService.getByEmail(email))
                .willReturn(customer);

        given(BCrypt.checkpw(any(String.class), any(String.class)))
                .willReturn(true);

        String expected = "asdfqwerzxcv123456";
        given(jwtTokenProvider.createToken(email))
                .willReturn(expected);

        // when
        String accessToken = authService.login(request);

        // then
        assertThat(accessToken).isEqualTo(expected);
    }
}
