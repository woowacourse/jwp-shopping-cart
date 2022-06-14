package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.exception.PasswordMisMatchException;
import woowacourse.shoppingcart.exception.UserNotFoundException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class AuthServiceTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    @DisplayName("토큰을 생성한다.")
    @Test
    void createToken() {
        LoginRequest loginRequest = new LoginRequest("dongho108", "password1234");
        TokenResponse token = authService.createToken(loginRequest);

        assertThat(jwtTokenProvider.validateToken(token.getAccessToken())).isTrue();
    }

    @DisplayName("username으로 Customer를 조회한다.")
    @Test
    void findCustomerByToken() {
        SignupRequest signupRequest = new SignupRequest("dongho108", "password1234", "01012341234", "인천시 서구");
        Customer savedCustomer = customerService.save(signupRequest);

        LoginRequest loginRequest = new LoginRequest("dongho108", "password1234");
        Customer customer = authService.findCustomerByUsername(loginRequest.getUsername());

        assertThat(customer).isEqualTo(savedCustomer);
    }

    @DisplayName("username이 일치하지 않으면 UserNotFoundException을 반환해야 한다.")
    @Test
    void validateEmptyUser() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        customerService.save(signupRequest);

        // when
        LoginRequest loginRequest = new LoginRequest("dongho109", "password1234");

        // then
        assertThatThrownBy(() -> authService.validateLogin(loginRequest))
            .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("password가 일치하지 않으면 PasswordMisMatchException을 반환해야 한다.")
    @Test
    void validateWrongPassword() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        customerService.save(signupRequest);

        // when
        LoginRequest loginRequest = new LoginRequest("dongho108", "password1234");

        // then
        assertThatThrownBy(() -> authService.validateLogin(loginRequest))
            .isInstanceOf(PasswordMisMatchException.class);
    }
}
