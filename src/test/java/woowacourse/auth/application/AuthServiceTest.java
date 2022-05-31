package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static woowacourse.fixture.PasswordFixture.basicPassword;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private CustomerDao customerDao;

    @DisplayName("유저 정보로 로그인 하면 토큰이 발급 된다.")
    @Test
    void login() {
        // given
        String userName = "기론";
        Customer customer = new Customer(1L, userName, basicPassword);
        given(customerDao.findByUserName(userName))
                .willReturn(Optional.of(customer));

        given(jwtTokenProvider.createToken("1"))
                .willReturn("accessToken");

        // when
        TokenRequest request = new TokenRequest(userName, basicPassword);
        final TokenResponse response = authService.login(request);

        // then
        assertAll(
                () -> assertThat(response.getAccessToken()).isEqualTo("accessToken"),
                () -> verify(customerDao).findByUserName(userName),
                () -> verify(jwtTokenProvider).createToken("1")
        );
    }
}
