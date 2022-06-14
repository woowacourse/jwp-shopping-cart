package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
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


    @DisplayName("email과 password를 이용하여 일치하는 customer의 id를 찾는다.")
    @Test
    void loginCustomer() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        customerService.save(customer);

        TokenRequest tokenRequest = new TokenRequest("email", "Pw123456!");
        TokenResponse tokenResponse = authService.loginCustomer(tokenRequest);

        assertThat(tokenResponse.getAccessToken()).isNotBlank();
    }

    @DisplayName("email과 password를 이용하여 일치하는 customer가 없는 경우 예외를 발생시킨다.")
    @Test
    void notExistsCustomerException() {
        TokenRequest tokenRequest = new TokenRequest("email", "Pw123456!");
        assertThatThrownBy(() -> authService.loginCustomer(tokenRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessageContaining("Email 또는 Password가 일치하지 않습니다.");
    }

}
