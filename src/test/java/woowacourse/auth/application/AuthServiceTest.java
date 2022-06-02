package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("등록된 회원으로 로그인하면 accessToken을 발급한다.")
    void login() {
        // given
        customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
        TokenRequest tokenRequest = new TokenRequest(페퍼_아이디, 페퍼_비밀번호);
        String expectToken = jwtTokenProvider.createToken(페퍼_아이디);

        // when
        TokenResponse tokenResponse = authService.login(tokenRequest);

        // then
        assertThat(tokenResponse.getAccessToken()).isEqualTo(expectToken);
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 예외처리")
    void login_failByPassword() {
        // given
        customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
        TokenRequest tokenRequest = new TokenRequest(페퍼_아이디, "Fake1234!");

        // when & then
        assertThatThrownBy(() -> authService.login(tokenRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("유효하지 않은 고객입니다");
    }

    @Test
    @DisplayName("등록되지 않은 회원으로 로그인하면 예외 처리")
    void login_failByNoCustomer() {
        // given
        TokenRequest tokenRequest = new TokenRequest(페퍼_아이디, 페퍼_비밀번호);

        // when & then
        assertThatThrownBy(() -> authService.login(tokenRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("유효하지 않은 고객입니다");
    }
}
