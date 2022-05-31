package woowacourse.auth.ui;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerSaveRequest;

@SpringBootTest
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("이메일과 비밀번호를 받아 로그인한다.")
    void login() {
        // given
        final String email = "klay@gmail.com";
        final String password = "12345678";
        final TokenRequest tokenRequest = new TokenRequest(email, password);
        customerService.save(new CustomerSaveRequest("klay", email, password));

        // when
        final TokenResponse tokenResponse = authController.login(tokenRequest);
        final boolean actual = jwtTokenProvider.validateToken(tokenResponse.getAccessToken());

        // then
        assertThat(actual).isTrue();
    }
}
