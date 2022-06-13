package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.exception.NoSuchEmailException;
import woowacourse.auth.exception.PasswordNotMatchException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.EncodedPassword;
import woowacourse.shoppingcart.domain.PlainPassword;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private static final String RAW_PASSWORD = "12345678";
    private static final PlainPassword PLAIN_PASSWORD = new PlainPassword(RAW_PASSWORD);
    private static final EncodedPassword PASSWORD = new EncodedPassword(PLAIN_PASSWORD.encode());

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private CustomerDao customerDao;

    @Test
    @DisplayName("이메일과 비밀번호를 입력 받아 토큰을 발급받는다.")
    void certify() {
        // given
        final String token = "dsfsdfds";
        final TokenRequest tokenRequest = new TokenRequest("klay@gmail.com", RAW_PASSWORD);
        when(customerDao.findByEmail(new Email(tokenRequest.getEmail())))
                .thenReturn(Optional.of(new Customer(1L, "클레이", new Email("clay@gmail.com"), PASSWORD)));
        when(jwtTokenProvider.createToken(Long.toString(1L)))
                .thenReturn(token);

        // when
        final String actual = authService.certify(tokenRequest);

        // then
        assertThat(actual).isEqualTo(token);
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 사용자를 인증할 경우 예외가 발생한다.")
    void certify_invalidEmail_throwsException() {
        // given
        final TokenRequest tokenRequest = new TokenRequest("klay@gmail.com", RAW_PASSWORD);
        when(customerDao.findByEmail(new Email(tokenRequest.getEmail())))
                .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> authService.certify(tokenRequest))
                .isInstanceOf(NoSuchEmailException.class);
    }

    @Test
    @DisplayName("사용자 인증 시 비밀번호가 일치하지 않을 경우 예외가 발생한다.")
    void certify_passwordNotMatch_throwsException() {
        // given
        final TokenRequest tokenRequest = new TokenRequest("klay@gmail.com", "11111111");
        when(customerDao.findByEmail(new Email(tokenRequest.getEmail())))
                .thenReturn(Optional.of(new Customer(1L, "클레이", new Email("klay@gmail.com"), PASSWORD)));

        // when, then
        assertThatThrownBy(() -> authService.certify(tokenRequest))
                .isInstanceOf(PasswordNotMatchException.class);
    }
}
