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
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.bodyexception.InvalidLoginException;

@SpringBootTest
@Transactional
public class AuthServiceIntegrationTest {

    private static final String PASSWORD = "qwerasdf123";

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerDao customerDao;

    private Customer customer;
    private Long id;


    @BeforeEach
    void setUp() {
        customer = new Customer("kun", "kun@email.com", PASSWORD);
        id = customerDao.save(customer);
    }

    @DisplayName("로그인하려는 이메일이 존재하지 않으면 예외를 던진다.")
    @Test
    void login_notExistEmail_exceptionThrown() {
        // given
        TokenRequest request = new TokenRequest("rick@email.com", PASSWORD);

        // when, then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidLoginException.class);
    }

    @DisplayName("로그인하려는 비밀번호가 일치하지 않으면 예외를 던진다.")
    @Test
    void login_differentSamePassword_exceptionThrown() {
        // given
        TokenRequest request = new TokenRequest(customer.getEmail(), "1q2w3e4r");

        // when, then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidLoginException.class);
    }

    @Test
    @DisplayName("로그인 성공할 경우에 토큰을 발급한다.")
    void login_success_tokenReturned() {
        // given
        TokenRequest request = new TokenRequest(customer.getEmail(), PASSWORD);

        // when
        String accessToken = authService.login(request);

        // then
        assertThat(accessToken).isNotBlank();
    }
}
