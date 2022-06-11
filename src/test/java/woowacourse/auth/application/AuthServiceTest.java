package woowacourse.auth.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.FakePasswordEncoder;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.*;
import woowacourse.shoppingcart.exception.LoginFailException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

class AuthServiceTest {

    private final AuthService authService;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    AuthServiceTest() {
        openMocks(this);
        this.authService = new AuthService(customerDao, jwtTokenProvider, new FakePasswordEncoder());
    }

    @Test
    @DisplayName("로그인 정보를 확인하고 토큰을 발행한다.")
    void createToken() {
        // given
        final Customer customer = new Customer(1L,
                new Account("leo0842"),
                new Nickname("eden"),
                new EncodedPassword("Password123!"),
                new Address("address"),
                new PhoneNumber("01023456789"));


        given(customerDao.findByAccount("leo0842")).willReturn(Optional.of(customer));
        given(jwtTokenProvider.createToken(String.valueOf(1L))).willReturn("token");

        // when
        final TokenResponse tokenResponse = authService.createToken(new TokenRequest("leo0842", "Password123!"));

        // then
        assertThat(tokenResponse.getAccessToken()).isEqualTo("token");
    }

    @Test
    @DisplayName("일치하는 아이디가 없으면 로그인 실패 예외를 반환한다.")
    void throwNotExistAccount() {
        // given
        given(customerDao.findByAccount("leo0842")).willReturn(Optional.empty());

        // when

        // then
        assertThatThrownBy(() -> authService.createToken(new TokenRequest("leo0842", "Password123!")))
                .isInstanceOf(LoginFailException.class)
                .hasMessage("ID 또는 비밀번호가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 로그인 실패 예외를 반환한다.")
    void throwPasswordNotMatch() {
        // given
        final Customer customer = new Customer(1L,
                new Account("leo0842"),
                new Nickname("eden"),
                new EncodedPassword("Password123!"),
                new Address("address"),
                new PhoneNumber("01023456789"));

        given(customerDao.findByAccount("leo0842")).willReturn(Optional.of(customer));

        // when

        // then
        assertThatThrownBy(() -> authService.createToken(new TokenRequest("leo0842", "wrong password")))
                .isInstanceOf(LoginFailException.class)
                .hasMessage("ID 또는 비밀번호가 올바르지 않습니다.");
    }
}
