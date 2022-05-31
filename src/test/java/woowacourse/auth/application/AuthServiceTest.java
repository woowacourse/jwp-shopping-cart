package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.auth.dto.TokenRequest;
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

    @Test
    @DisplayName("이메일과 비밀번호를 입력 받아 토큰을 발급받는다.")
    void login() {
        // given
        final String token = "dsfsdfds";
        final TokenRequest tokenRequest = new TokenRequest("clay@gmail.com", "12345678");
        Mockito.when(customerDao.findByEmail(tokenRequest.getEmail()))
                .thenReturn(Optional.of(new Customer(1L, "클레이", "clay@gmail.com", "12345678")));
        Mockito.when(jwtTokenProvider.createToken(Long.toString(1L)))
                .thenReturn(token);

        // when
        final String actual = authService.login(tokenRequest.toServiceDto());

        // then
        assertThat(actual).isEqualTo(token);
    }

}
