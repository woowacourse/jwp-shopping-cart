package cart.domain.user.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.domain.user.CartUser;
import cart.domain.user.CartUserRepository;
import cart.domain.user.UserEmail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class UserAuthorizationVerifierTest {

    @Autowired
    private UserAuthorizationVerifier userAuthorizationVerifier;

    @Autowired
    private CartUserRepository cartUserRepository;

    @DisplayName("사용자의 비밀번호가 일치하는 지 검증한다.")
    @Test
    void verifyCartUser() {
        String givenEmail = "a@a.com";
        String password = "password";
        String wrongPassword = "password12312312";
        CartUser cartUser = new CartUser(UserEmail.from(givenEmail), password);
        cartUserRepository.save(cartUser);

        assertThatThrownBy(() ->
                userAuthorizationVerifier.verifyCartUser(givenEmail, wrongPassword)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
