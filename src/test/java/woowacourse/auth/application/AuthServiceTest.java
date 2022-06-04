package woowacourse.auth.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import woowacourse.auth.application.exception.InvalidTokenException;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static woowacourse.fixture.PasswordFixture.encryptedBasicPassword;
import static woowacourse.fixture.PasswordFixture.rawBasicPassword;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("유저 정보로 로그인 하면 토큰이 발급 된다.")
    @Test
    void login() {
        // given
        String userName = "giron";
        Customer customer = new Customer(1L, userName, encryptedBasicPassword);
        given(customerDao.findByUserName(userName))
                .willReturn(Optional.of(customer));
        given(jwtTokenProvider.createToken("1"))
                .willReturn("accessToken");
        given(passwordEncoder.matches(rawBasicPassword, encryptedBasicPassword))
                .willReturn(true);

        // when
        TokenRequest request = new TokenRequest(userName, rawBasicPassword);
        final TokenResponse response = authService.login(request);

        // then
        assertAll(
                () -> assertThat(response.getAccessToken()).isEqualTo("accessToken"),
                () -> verify(customerDao).findByUserName(userName),
                () -> verify(jwtTokenProvider).createToken("1"),
                () -> verify(passwordEncoder).matches(rawBasicPassword, encryptedBasicPassword)
        );
    }

    @DisplayName("JWT 토큰을 받아서 토큰 인증하고 해당 유저를 반환 한다.")
    @Test
    void getAuthenticatedCustomer() {
        // given
        String userName = "giron";
        String token = "accessToken";
        Customer customer = new Customer(1L, userName, encryptedBasicPassword);
        given(jwtTokenProvider.validateToken(token))
                .willReturn(true);
        given(jwtTokenProvider.getPayload(token))
                .willReturn("1");
        given(customerDao.findById(1L))
                .willReturn(Optional.of(customer));

        // when
        final Customer authenticatedCustomer = authService.getAuthenticatedCustomer(token);

        // then
        assertAll(
                () -> assertThat(authenticatedCustomer).isEqualTo(customer),
                () -> verify(jwtTokenProvider).validateToken(token),
                () -> verify(jwtTokenProvider).getPayload(token),
                () -> verify(customerDao).findById(1L)
        );
    }

    @DisplayName("JWT 토큰을 받아서 토큰 인증에 실패한다.")
    @Test
    void getAuthenticatedCustomerFailure() {
        // given
        String userName = "giron";
        String token = "accessToken";
        Customer customer = new Customer(1L, userName, encryptedBasicPassword);
        given(jwtTokenProvider.validateToken(token))
                .willReturn(false);

        // then
        assertAll(
                () -> assertThatThrownBy(() -> authService.getAuthenticatedCustomer(token))
                        .isExactlyInstanceOf(InvalidTokenException.class)
                        .hasMessageContaining("유효하지 않은 토큰입니다."),
                () -> verify(jwtTokenProvider).validateToken(token)
        );
    }
}
