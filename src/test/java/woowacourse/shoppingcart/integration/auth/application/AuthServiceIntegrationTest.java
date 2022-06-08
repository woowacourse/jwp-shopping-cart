package woowacourse.shoppingcart.integration.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.auth.dto.LoginRequest;
import woowacourse.shoppingcart.auth.exception.badrequest.InvalidLoginException;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.integration.IntegrationTest;

class AuthServiceIntegrationTest extends IntegrationTest {

    private static final String PASSWORD = "qwerasdf123";

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("kun", "kun@email.com", PASSWORD);
        customerDao.save(customer);
    }

    @Test
    @DisplayName("로그인하려는 이메일이 존재하지 않으면 예외를 던진다.")
    void login_notExistEmail_exceptionThrown() {
        // given
        final LoginRequest request = new LoginRequest("rick@email.com", PASSWORD);

        // when, then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidLoginException.class);
    }

    @Test
    @DisplayName("로그인하려는 비밀번호가 일치하지 않으면 예외를 던진다.")
    void login_differentSamePassword_exceptionThrown() {
        // given
        final LoginRequest request = new LoginRequest(customer.getEmail(), "1q2w3e4r");

        // when, then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidLoginException.class);
    }

    @Test
    @DisplayName("로그인 성공할 경우에 토큰을 발급한다.")
    void login_success_tokenReturned() {
        // given
        final LoginRequest request = new LoginRequest(customer.getEmail(), PASSWORD);

        // when
        final String accessToken = authService.login(request);

        // then
        assertThat(accessToken).isNotBlank();
    }
}
