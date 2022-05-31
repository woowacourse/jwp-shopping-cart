package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerResponse;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(customerDao, jwtTokenProvider);
    }

    @DisplayName("이메일과 비밀번호를 입력 받아 토큰을 발급 받는다.")
    @Test
    void login() {

        // given
        String email = "beomWhale@naver.com";
        String password = "Password1234!";
        Customer customer = new Customer(email, "beomWhale", password);
        customerDao.save(customer);

        // when && then
        String token = authService.login(new TokenRequest(email, password));
        assertThat(token).isNotEmpty();
    }

    @DisplayName("이메일을 입력 받아 정보를 조회한다.")
    @Test
    void findCustomerByEmail() {
        // given
        String email = "beomWhale@naver.com";
        String password = "Password1234!";
        String nickname = "beomWhale";
        Customer customer = new Customer(email, nickname, password);
        Long saveId = customerDao.save(customer);

        // when
        CustomerResponse expected = new CustomerResponse(saveId, email, nickname);
        CustomerResponse customerResponse = authService.findCustomerByEmail(email);

        // then
        assertThat(customerResponse).usingRecursiveComparison().isEqualTo(expected);
    }
}
