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
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql"})
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @DisplayName("회원을 등록한다.")
    @Test
    void saveCustomer() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        CustomerResponse response = authService.save(customer);

        assertThat(response).extracting("email", "name", "phone", "address")
                .containsExactly("email", "name", "010-2222-3333", "address");
    }

    @DisplayName("중복되는 email이면 예외가 발생한다.")
    @Test
    void existEmailException() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        authService.save(customer);
        assertThatThrownBy(() -> authService.save(customer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복된 email 입니다.");
    }

    @DisplayName("email과 password를 이용하여 일치하는 customer의 id를 찾는다.")
    @Test
    void loginCustomer() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        authService.save(customer);

        TokenRequest tokenRequest = new TokenRequest("email", "Pw123456!");
        Long customerId = authService.loginCustomer(tokenRequest);

        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("email과 password를 이용하여 일치하는 customer가 없는 경우 예외를 발생시킨다.")
    @Test
    void notExistsCustomerException() {
        TokenRequest tokenRequest = new TokenRequest("email", "Pw123456!");
        assertThatThrownBy(() -> authService.loginCustomer(tokenRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessageContaining("Email 또는 Password가 일치하지 않습니다.");
    }

    @DisplayName("customer id를 이용하여 token을 발급한다.")
    @Test
    void createToken() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        authService.save(customer);

        TokenRequest tokenRequest = new TokenRequest("email", "Pw123456!");
        Long customerId = authService.loginCustomer(tokenRequest);

        TokenResponse accessToken = authService.createToken(customerId);

        assertThat(accessToken.getAccessToken()).isNotBlank();
    }

    @DisplayName("customer id을 이용하여 회원 정보를 조회한다.")
    @Test
    void findCustomer() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        CustomerResponse savedResponse = authService.save(customer);

        CustomerResponse customerResponse = authService.findById(1L);

        assertThat(customerResponse).extracting("email", "name", "phone", "address")
                .containsExactly(savedResponse.getEmail(), savedResponse.getName(),
                        savedResponse.getPhone(), savedResponse.getAddress());
    }

    //TODO : CustomerResponse equals 정의 or getter 사용할지
}
