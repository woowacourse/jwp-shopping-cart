package woowacourse.auth.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.application.AuthService;
import woowacourse.shoppingcart.dto.SignInRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void 로그인_성공() {
        var signInRequest = new SignInRequest("crew01@naver.com", "a12345");

        var signInResponse = authService.signIn(signInRequest);

        assertAll(
                () -> assertThat(signInResponse.getUsername()).isEqualTo("puterism"),
                () -> assertThat(signInResponse.getEmail()).isEqualTo("crew01@naver.com"),
                () -> assertThat(signInResponse.getToken()).isNotNull()
        );
    }

    @Test
    void 비밀번호가_일치하지_않는_경우() {
        var signInRequest = new SignInRequest("crew01@naver.com", "a123456");

        assertThatThrownBy(() -> authService.signIn(signInRequest)).isInstanceOf(InvalidCustomerException.class);
    }
}
