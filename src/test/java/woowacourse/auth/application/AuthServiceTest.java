package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.PasswordRequest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private AuthService authService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .username("username")
                .password("ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f")
                .phoneNumber("01012345678")
                .address("성담빌딩")
                .build();
    }

    @Test
    @DisplayName("로그인 시 패스워드 다른 경우 예외 발생")
    void loginMismatchPassword_throwException() {
        customerDao.save(customer);

        assertThatThrownBy(() -> authService.login(new TokenRequest("username", "wrongpassword")))
                .isInstanceOf(InvalidAuthException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("로그인 성공")
    void login() {
        customerDao.save(customer);

        TokenResponse tokenResponse = authService.login(new TokenRequest("username", "password123"));

        assertThat(tokenResponse).isNotNull();
    }

    @Test
    @DisplayName("패스워드 확인 시 다른 경우 예외 발생")
    void mismatchPassword_throwException() {
        Long customerId = customerDao.save(customer);

        assertThatThrownBy(() -> authService.checkPassword(customerId, new PasswordRequest("wrongpassword")))
                .isInstanceOf(InvalidAuthException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("패스워드 확인 시 일치한다면 예외 발생하지 않음")
    void matchPassword_notThrowException() {
        Long customerId = customerDao.save(customer);

        assertDoesNotThrow(() -> authService.checkPassword(customerId, new PasswordRequest("password123")));
    }

}
