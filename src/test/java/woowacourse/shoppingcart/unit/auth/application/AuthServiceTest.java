package woowacourse.shoppingcart.unit.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.security.crypto.bcrypt.BCrypt;
import woowacourse.shoppingcart.auth.application.AuthService;
import woowacourse.shoppingcart.auth.dto.LoginRequest;
import woowacourse.shoppingcart.auth.exception.badrequest.InvalidLoginException;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.customer.exception.notfound.NotFoundCustomerException;
import woowacourse.shoppingcart.unit.ServiceMockTest;

class AuthServiceTest extends ServiceMockTest {

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("로그인하려는 이메일이 존재하지 않으면 예외를 던진다.")
    void login_notExistEmail_exceptionThrown() {
        // given
        final LoginRequest request = new LoginRequest("email@email.com", "1q2w3e4r");

        given(customerService.getByEmail(request.getEmail()))
                .willThrow(NotFoundCustomerException.class);

        // when, then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidLoginException.class);
    }

    @Test
    @DisplayName("로그인하려는 비밀번호가 일치하지 않으면 예외를 던진다.")
    void login_differentSamePassword_exceptionThrown() {
        // given
        final String email = "email@email.com";
        final LoginRequest request = new LoginRequest(email, "1q2w3e4r");

        final Customer customer = new Customer("knu", email, "q1w2e3r4");
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
        final String email = "kun@email.com";
        final String password = "qwerasdf123";
        final LoginRequest request = new LoginRequest(email, password);

        final Customer customer = new Customer(1L, "kun", email, HASH);
        given(customerService.getByEmail(email))
                .willReturn(customer);

        given(BCrypt.checkpw(any(String.class), any(String.class)))
                .willReturn(true);

        final String expected = "asdfqwerzxcv123456";
        given(jwtTokenProvider.createToken(email))
                .willReturn(expected);

        // when
        final String accessToken = authService.login(request);

        // then
        assertThat(accessToken).isEqualTo(expected);
    }
}
