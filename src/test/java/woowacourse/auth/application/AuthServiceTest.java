package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.LogInRequest;
import woowacourse.auth.dto.LogInResponse;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("로그인에 성공한다.")
    void signIn() {
        customerService.addCustomer(new SignUpRequest("레넌", "rennon@woowa.com", "123456q!"));
        LogInResponse logInResponse = authService.signIn(new LogInRequest("rennon@woowa.com", "123456q!"));

        assertThat(logInResponse.getUsername()).isEqualTo("레넌");
        assertThat(logInResponse.getToken()).isNotNull();
    }

    @Test
    @DisplayName("로그인에 실패한다. - 유저가 존재하지 않는 경우")
    void signInFailIfNotExistUser() {
        assertThatThrownBy(() -> authService.signIn(new LogInRequest("rennon@woowa.com", "123456q!")))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessageContaining("존재하지 않는 유저입니다.");
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호가 맞지 않는 경우")
    void signInFailIfWrongPassword() {
        customerService.addCustomer(new SignUpRequest("레넌", "rennon@woowa.com", "123456q!"));

        assertThatThrownBy(() -> authService.signIn(new LogInRequest("rennon@woowa.com", "123578")))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("비밀번호가 틀렸습니다.");
    }
}
