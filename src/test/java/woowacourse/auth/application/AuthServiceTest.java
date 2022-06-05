package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.auth.application.dto.LoginServiceRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private static final String RAW_PASSWORD = "12345678";
    private static final String EMAIL = "klay@gmail.com";

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private CustomerService customerService;

    @Test
    @DisplayName("이메일과 비밀번호를 입력 받아 토큰을 발급받는다.")
    void certify() {
        // given
        final String token = "dsfsdfds";
        final LoginServiceRequest loginServiceRequest = new LoginServiceRequest(EMAIL, RAW_PASSWORD);
        when(customerService.validateCustomer(EMAIL, RAW_PASSWORD))
                .thenReturn(1L);
        when(jwtTokenProvider.createToken(Long.toString(1L)))
                .thenReturn(token);

        // when
        final String actual = authService.certify(loginServiceRequest);

        // then
        assertThat(actual).isEqualTo(token);
    }

    @Test
    @DisplayName("토큰을 ID로 변환한다.")
    void parseToLong() {
        // given
        final Long expected = 1L;
        when(jwtTokenProvider.getPayload("mytoken"))
                .thenReturn(String.valueOf(expected));

        // when
        final Long id = authService.parseToId("mytoken");

        // then
        assertThat(id).isEqualTo(expected);
    }

    @Test
    @DisplayName("토큰의 유효성을 검사한다.")
    void isInvalidToken() {
        // given
        final String token = "validToken";
        when(jwtTokenProvider.validateToken(token))
                .thenReturn(true);

        // when
        final boolean actual = authService.isValidToken(token);

        // then
        assertThat(actual).isTrue();
    }
}
