package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_REQUEST_1;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.TOKEN_REQUEST_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.LoginFailedException;
import woowacourse.shoppingcart.application.CustomerService;

// TODO: SpringBootTest 대신 적용할 수 있는 방법 공부하기
@SpringBootTest
@Transactional
class AuthServiceTest {
    private final AuthService authService;
    private final CustomerService customerService;

    @Autowired
    public AuthServiceTest(AuthService authService, CustomerService customerService) {
        this.authService = authService;
        this.customerService = customerService;
    }

    @BeforeEach
    void setUp() {
        customerService.create(CUSTOMER_REQUEST_1);
    }

    @DisplayName("올바른 이메일과 패스워드를 전달하면, TokenResponse를 반환한다.")
    @Test
    void generateToken() {
        // when
        TokenResponse tokenResponse = authService.generateToken(TOKEN_REQUEST_1);

        // then
        assertAll(
                () -> assertThat(tokenResponse.getAccessToken()).isNotBlank(),
                () -> assertThat(tokenResponse.getUserId()).isPositive()
        );
    }

    @DisplayName("존재하지 않는 이메일을 전달시 예외가 발생한다.")
    @Test
    void generateToken_throwsExceptionIfEmailNotExists() {
        // given
        TokenRequest tokenRequest = new TokenRequest("notexists@gmail.com", "1234!@a2443");

        // when
        assertThatThrownBy(() -> authService.generateToken(tokenRequest))
                .isInstanceOf(LoginFailedException.class);
    }

    @DisplayName("이메일은 존재하지만 비밀번호가 일치하지 않은 경우 예외가 발생한다.")
    @Test
    void generateToken_throwsExceptionIfInvalidPassword() {
        // given
        TokenRequest tokenRequest = new TokenRequest("seongwoo0513@example.com", "1234!@a2443");

        // when
        assertThatThrownBy(() -> authService.generateToken(tokenRequest))
                .isInstanceOf(LoginFailedException.class);
    }
}
