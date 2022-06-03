package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.exception.LoginException;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dao.CustomerDao;

@SpringBootTest
@Sql("classpath:truncate.sql")
class AuthServiceTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("패스워드가 일치하지 않을 시 에러를 발생시킨다.")
    void loginFailByDifferentPassword() {
        //given
        String email = "test@gmail.com";
        String password = "password0!";
        String username = "name";

        customerService.register(email, password, username);

        //then
        assertThatThrownBy(() -> authService.login(email, "wrongPwd1!")).isInstanceOf(LoginException.class);
    }

    @Test
    @DisplayName("이메일이 일치하지 않을 시 에러를 발생시킨다.")
    void loginFailByDifferentEmail() {
        //given
        String email = "test@gmail.com";
        String password = "password0!";
        String username = "name";

        customerService.register(email, password, username);

        //then
        assertThatThrownBy(() -> authService.login("test1@gmail.com", password)).isInstanceOf(LoginException.class);
    }
}