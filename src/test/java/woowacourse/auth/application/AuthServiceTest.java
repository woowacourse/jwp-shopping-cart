package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Sql("classpath:schema.sql")
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("로그인에 성공한다.")
    void signIn() {
        // given
        customerService.addCustomer(new SignUpRequest("레넌", "rennon@woowa.com", "1234"));

        // when
        SignInResponse signInResponse = authService.signIn(new SignInRequest("rennon@woowa.com", "1234"));

        // then
        assertAll(
                () -> assertThat(signInResponse.getUsername()).isEqualTo("레넌"),
                () -> assertThat(signInResponse.getToken()).isNotNull()
        );
    }

    @Test
    @DisplayName("회원이 존재하지 않으면 로그인에 실패한다.")
    void signInFailThrowNoCustomerException() {
        assertThatThrownBy(() -> authService.signIn(new SignInRequest("rennon@woowa.com", "1234")))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessageContaining("로그인 실패");
    }

    @Test
    @DisplayName("비빌번호가 일치하지 않으면 로그인에 실패한다.")
    void signInFailThrowNotMatchPasswordException() {
        // given
        customerService.addCustomer(new SignUpRequest("레넌", "rennon@woowa.com", "1234"));

        // when & then
        assertThatThrownBy(() -> authService.signIn(new SignInRequest("rennon@woowa.com", "1235")))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("로그인 실패");
    }
}
