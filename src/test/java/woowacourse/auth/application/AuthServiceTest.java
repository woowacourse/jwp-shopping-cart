package woowacourse.auth.application;

import static Fixture.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Sql("/truncate.sql")
class AuthServiceTest {

    private final AuthService authService;
    private final CustomerService customerService;

    AuthServiceTest(AuthService authService, CustomerService customerService) {
        this.authService = authService;
        this.customerService = customerService;
    }

    @DisplayName("로그인 정보를 기반으로 토큰을 발급 받는다.")
    @Test
    void createToken() {
        customerService.save(YAHO_SAVE_REQUEST);
        TokenRequest tokenRequest = new TokenRequest(YAHO_USERNAME, YAHO_PASSWORD);

        TokenResponse tokenResponse = authService.createToken(tokenRequest);

        assertThat(tokenResponse.getAccessToken()).isNotEmpty();
    }

    @DisplayName("존재하지 않는 username 으로 토큰을 요청할 경우 예외를 던진다.")
    @Test
    void createToken_error_notExistUsername() {
        customerService.save(YAHO_SAVE_REQUEST);
        TokenRequest tokenRequest = new TokenRequest(MAT_USERNAME, YAHO_PASSWORD);

        assertThatThrownBy(() -> authService.createToken(tokenRequest))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("존재하지 않는 username 으로 토큰을 요청할 경우 예외를 던진다.")
    @Test
    void createToken_error_wrongPassword() {
        customerService.save(YAHO_SAVE_REQUEST);
        TokenRequest tokenRequest = new TokenRequest(YAHO_USERNAME, MAT_PASSWORD);

        assertThatThrownBy(() -> authService.createToken(tokenRequest))
                .isInstanceOf(InvalidCustomerException.class);
    }
}
