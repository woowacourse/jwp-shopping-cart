package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.SignupRequest;

@SpringBootTest
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

        assertAll(
            () -> assertThat(customer.getUsername()).isEqualTo(savedCustomer.getUsername()),
            () -> assertThat(customer.getPassword()).isEqualTo(savedCustomer.getPassword()),
            () -> assertThat(customer.getPhoneNumber()).isEqualTo(savedCustomer.getPhoneNumber()),
            () -> assertThat(customer.getAddress()).isEqualTo(savedCustomer.getAddress())
        );
    }
}