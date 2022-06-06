package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static woowacourse.utils.Fixture.email;
import static woowacourse.utils.Fixture.nickname;
import static woowacourse.utils.Fixture.password;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.auth.dto.token.TokenResponse;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.auth.exception.InvalidCustomerException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private CustomerService customerService;
    @Mock
    private JwtTokenProvider tokenProvider;
    @InjectMocks
    private AuthService authService;

    @DisplayName("알맞은 정보로 로그인을 한다.")
    @Test
    void login() {
        // given
        given(customerService.findByEmail(email))
                .willReturn(new Customer(1L, email, nickname, password));
        given(tokenProvider.createToken(email))
                .willReturn("access-token");

        // when
        TokenResponse response = authService.login(new TokenRequest(email, password));

        // then
        assertAll(
                () -> assertThat(response.getNickname()).isEqualTo(nickname),
                () -> assertThat(response.getAccessToken()).isEqualTo("access-token")
        );
    }

    @DisplayName("틀린 이메일 정보로 로그인을 하면 예외 발생")
    @Test
    void loginFailByEmail() {
        // given
        given(customerService.findByEmail(email))
                .willThrow(InvalidCustomerException.class);

        // when
        assertThatThrownBy(() -> authService.login(new TokenRequest(email, password)))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("비밀번호가 틀리면 예외가 발생한다.")
    @Test
    void loginFailByPassword() {
        // given
        given(customerService.findByEmail(email))
                .willReturn(new Customer(1L, email, nickname, password));

        // when
        assertThatThrownBy(() -> authService.login(new TokenRequest(email, "a1234!!!")))
                .isInstanceOf(InvalidAuthException.class);
    }
}
