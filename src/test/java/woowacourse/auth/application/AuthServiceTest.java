package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static woowacourse.fixture.PasswordFixture.encryptedBasicPassword;
import static woowacourse.fixture.PasswordFixture.plainBasicPassword;
import static woowacourse.fixture.PasswordFixture.plainReversePassword;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.auth.dto.LoginCustomer;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.LoginFailureException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.PasswordEncryptor;
import woowacourse.shoppingcart.domain.customer.UserName;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private PasswordEncryptor passwordEncryptor;

    @DisplayName("유저 정보로 로그인 하면 토큰이 발급된다.")
    @Test
    void login() {
        // given
        String userName = "giron";
        Customer customer = new Customer(1L, new UserName(userName), encryptedBasicPassword);
        given(customerDao.findByUserName(userName))
                .willReturn(Optional.of(customer));
        given(passwordEncryptor.matches(plainBasicPassword, encryptedBasicPassword.getValue()))
                .willReturn(true);
        given(jwtTokenProvider.createToken("1"))
                .willReturn("accessToken");

        // when
        TokenRequest request = new TokenRequest(userName, plainBasicPassword);
        final TokenResponse response = authService.login(request);

        // then
        assertAll(
                () -> assertThat(response.getAccessToken()).isEqualTo("accessToken"),
                () -> verify(customerDao).findByUserName(userName),
                () -> verify(passwordEncryptor).matches(plainBasicPassword, encryptedBasicPassword.getValue()),
                () -> verify(jwtTokenProvider).createToken("1")
        );
    }

    @DisplayName("잘못된 유저이름으로 로그인 하면 예외가 발생한다.")
    @Test
    void loginWithWrongUserName() {
        // given
        String userName = "giron";
        given(customerDao.findByUserName(userName))
                .willThrow(new LoginFailureException());

        // when
        TokenRequest request = new TokenRequest(userName, plainReversePassword);

        // then
        assertAll(
                () -> assertThatThrownBy(() -> authService.login(request))
                        .isExactlyInstanceOf(LoginFailureException.class)
                        .hasMessageContaining("일치하는 회원이 없거나 비밀번호가 일치하지 않습니다."),
                () -> verify(customerDao).findByUserName(userName)
        );
    }

    @DisplayName("잘못된 비밀번호로 로그인 하면 예외가 발생한다.")
    @Test
    void loginWithWrongPassword() {
        // given
        String userName = "giron";
        Customer customer = new Customer(1L, new UserName(userName), encryptedBasicPassword);
        given(customerDao.findByUserName(userName))
                .willReturn(Optional.of(customer));
        given(passwordEncryptor.matches(plainReversePassword, encryptedBasicPassword.getValue()))
                .willReturn(false);

        // when
        TokenRequest request = new TokenRequest(userName, plainReversePassword);

        // then
        assertAll(
                () -> assertThatThrownBy(() -> authService.login(request))
                        .isExactlyInstanceOf(LoginFailureException.class)
                        .hasMessageContaining("일치하는 회원이 없거나 비밀번호가 일치하지 않습니다."),
                () -> verify(customerDao).findByUserName(userName),
                () -> verify(passwordEncryptor).matches(plainReversePassword, encryptedBasicPassword.getValue())
        );
    }

    @DisplayName("JWT 토큰을 받아서 해당 유저를 반환한다.")
    @Test
    void getAuthenticatedCustomer() {
        // given
        String userName = "giron";
        String token = "accessToken";
        Customer customer = new Customer(1L, new UserName(userName), encryptedBasicPassword);
        given(jwtTokenProvider.getPayload(token))
                .willReturn("1");
        given(customerDao.findById(1L))
                .willReturn(Optional.of(customer));

        // when
        final LoginCustomer loginCustomer = authService.getAuthenticatedCustomer(token);

        // then
        assertAll(
                () -> assertThat(loginCustomer.getId()).isEqualTo(customer.getId()),
                () -> verify(jwtTokenProvider).getPayload(token),
                () -> verify(customerDao).findById(1L)
        );
    }
}
