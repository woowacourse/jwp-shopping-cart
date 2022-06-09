package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.exception.AuthorizationException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;

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
        customerService.addCustomer(new SignUpRequest("레넌", "rennon@woowa.com", "123456"));

        // when
        SignInResponse signInResponse = authService.signIn(new SignInRequest("rennon@woowa.com", "123456"));

        // then
        assertAll(
                () -> assertThat(signInResponse.getUsername()).isEqualTo("레넌"),
                () -> assertThat(signInResponse.getToken()).isNotNull()
        );
    }

    @Test
    @DisplayName("회원이 존재하지 않으면 로그인에 실패한다.")
    void signInFailThrowNoCustomerException() {
        assertThatThrownBy(() -> authService.signIn(new SignInRequest("rennon@woowa.com", "123456")))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @Test
    @DisplayName("비빌번호가 일치하지 않으면 로그인에 실패한다.")
    void signInFailThrowNotMatchPasswordException() {
        // given
        customerService.addCustomer(new SignUpRequest("레넌", "rennon@woowa.com", "123456"));

        // when & then
        assertThatThrownBy(() -> authService.signIn(new SignInRequest("rennon@woowa.com", "1234567")))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("비밀번호가 틀렸습니다.");
    }

    @Test
    @DisplayName("존재하는 회원이면 예외가 발생하지 않는다.")
    void validateExistUser() {
        // given
        customerService.addCustomer(new SignUpRequest("rennon", "rennon@woowa.com", "123456"));
        customerService.addCustomer(new SignUpRequest("greenlawn", "greenlawn@woowa.com", "123456"));

        // when & then
        assertThatCode(() -> authService.validateExistUser("rennon"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("존재하는 회원이 아니면 예외가 발생한다.")
    void validateExistUserThrowException() {
        // given
        customerService.addCustomer(new SignUpRequest("rennon", "rennon@woowa.com", "123456"));
        customerService.addCustomer(new SignUpRequest("greenlawn", "greenlawn@woowa.com", "123456"));

        // when & then
        assertThatThrownBy(() -> authService.validateExistUser("alien"))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("인증되지 않은 회원입니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("토큰이 null이거나 비어있으면 에러가 발생한다.")
    void validateToken(String token) {
        assertThatThrownBy(() -> authService.validateToken(token))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("인증되지 않은 회원입니다.");
    }

    @Test
    @DisplayName("토큰을 재발급 받는다.")
    void reIssueToken() {
        // given
        customerService.addCustomer(new SignUpRequest("rennon", "rennon@woowa.com", "123456"));

        // when
        SignInResponse signInResponse = authService.reIssueToken("rennon");

        // then
        assertThat(signInResponse.getToken()).isNotBlank();
    }
}
