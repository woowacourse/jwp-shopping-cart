package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.InvalidLoginException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.customer.application.CustomerService;
import woowacourse.customer.dto.CustomerResponse;
import woowacourse.customer.dto.SignupRequest;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql"})
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
        SignupRequest signupRequest = new SignupRequest("dongho108", "password1234", "01012341234", "인천시 서구");
        customerService.save(signupRequest);

        LoginRequest loginRequest = new LoginRequest("dongho108", "password1234");
        TokenResponse token = authService.createToken(loginRequest);

        assertThat(jwtTokenProvider.validateToken(token.getAccessToken())).isTrue();
    }

    @DisplayName("username으로 Customer를 조회한다.")
    @Test
    void findCustomerByToken() {
        SignupRequest signupRequest = new SignupRequest("dongho108", "password1234", "01012341234", "인천시 서구");
        customerService.save(signupRequest);

        LoginRequest loginRequest = new LoginRequest("dongho108", "password1234");
        CustomerResponse customer = customerService.findCustomerByUsername(loginRequest.getUsername());

        assertAll(
            () -> assertThat(customer.getUsername()).isEqualTo(signupRequest.getUsername()),
            () -> assertThat(customer.getPhoneNumber()).isEqualTo(signupRequest.getPhoneNumber()),
            () -> assertThat(customer.getAddress()).isEqualTo(signupRequest.getAddress())
        );
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
        assertThatThrownBy(() -> authService.createToken(loginRequest))
            .isInstanceOf(InvalidLoginException.class);
    }

    @DisplayName("password가 일치하지 않으면 PasswordMisMatchException을 반환해야 한다.")
    @Test
    void validateWrongPassword() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        customerService.save(signupRequest);

        // when
        LoginRequest loginRequest = new LoginRequest("dongho108", "password");

        // then
        assertThatThrownBy(() -> authService.createToken(loginRequest))
            .isInstanceOf(InvalidLoginException.class);
    }
}
