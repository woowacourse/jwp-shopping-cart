package cart.domain.user.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.exception.VerifyUserException;
import cart.user.domain.CartUser;
import cart.user.domain.CartUserRepository;
import cart.user.domain.UserEmail;
import cart.user.service.BasicAuthorizationCartUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class BasicAuthorizationCartUserServiceTest {

    @Autowired
    private BasicAuthorizationCartUserService basicAuthorizationCartUserService;

    @Autowired
    private CartUserRepository cartUserRepository;

    @DisplayName("사용자의 비밀번호가 일치하는 지 검증한다.")
    @Test
    void verifyCartUser() {
        final String givenEmail = "a@a.com";
        final String password = "password";
        final String wrongPassword = "password12312312";
        final CartUser cartUser = new CartUser(UserEmail.from(givenEmail), password);
        cartUserRepository.save(cartUser);

        assertThatThrownBy(() ->
                basicAuthorizationCartUserService.verifyCartUser(givenEmail, wrongPassword)
        ).isInstanceOf(VerifyUserException.class);
    }
}
