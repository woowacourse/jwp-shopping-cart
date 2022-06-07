package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql"})
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private CustomerService customerService;

    @BeforeEach
    private void beforeEach() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        customerService.save(customer);
    }

    @DisplayName("email과 password를 이용하여 일치하는 customer의 id를 찾는다.")
    @Test
    void loginCustomer() {
        LoginRequest loginRequest = new LoginRequest("email", "Pw123456!");
        Long customerId = authService.loginCustomer(loginRequest);

        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("email과 password를 이용하여 일치하는 customer가 없는 경우 예외를 발생시킨다.")
    @Test
    void notExistsCustomerException() {
        LoginRequest loginRequest = new LoginRequest("email", "Pw123456~~");
        assertThatThrownBy(() -> authService.loginCustomer(loginRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessageContaining("Email 또는 Password가 일치하지 않습니다.");
    }

    @DisplayName("customer id를 이용하여 token을 발급한다.")
    @Test
    void createToken() {
        LoginRequest loginRequest = new LoginRequest("email", "Pw123456!");
        Long customerId = authService.loginCustomer(loginRequest);

        TokenResponse accessToken = authService.createToken(customerId);

        assertThat(accessToken.getAccessToken()).isNotBlank();
    }
}
