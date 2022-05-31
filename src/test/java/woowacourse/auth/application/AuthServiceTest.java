package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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
    void loginDismatchPassword_throwException() {
        // given
        customerDao.save(customer);

        // when & then
        assertThatThrownBy(() -> authService.login(new TokenRequest("username", "wrongpassword")))
                .isInstanceOf(InvalidAuthException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("로그인 성공")
    void login() {
        // given
        customerDao.save(customer);

        // when
        TokenResponse tokenResponse = authService.login(new TokenRequest("username", "password123"));

        // then
        assertThat(tokenResponse).isNotNull();
    }
}
