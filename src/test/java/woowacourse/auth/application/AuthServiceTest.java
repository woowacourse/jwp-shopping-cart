package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.domain.customer.Customer;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("로그인 정보를 이용해서 토큰을 생성한다.")
    @Test
    void createToken() {
        // given
        LoginRequest request = new LoginRequest("puterism@naver.com", "12349053145");
        String token = authService.createToken(request).getAccessToken();

        // when
        String email = jwtTokenProvider.getPayload(token);

        // then
        assertThat(email).isEqualTo("puterism@naver.com");
    }

    @DisplayName("토큰을 이용해서 Customer를 복원한다.")
    @Test
    void findCustomerByToken() {
        // given
        LoginRequest request = new LoginRequest("puterism@naver.com", "12349053145");
        String token = authService.createToken(request).getAccessToken();

        // when
        Customer customer = authService.findCustomerByToken(token);

        Customer expected = new Customer("puterism@naver.com", "puterism", "12349053145");
        // then

        assertThat(customer).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
    }
}
