package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.domain.Customer;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("로그인 정보를 이용해서 토큰을 생성한다.")
    @Test
    void createToken() {
        // given
        TokenRequest request = new TokenRequest("puterism@naver.com", "12349053145");
        String token = authService.createToken(request);

        // when
        String email = jwtTokenProvider.getPayload(token);

        // then
        assertThat(email).isEqualTo("puterism@naver.com");
    }

    @DisplayName("토큰을 이용해서 Customer를 복원한다.")
    @Test
    void findCustomerByToken() {
        // given
        TokenRequest request = new TokenRequest("puterism@naver.com", "12349053145");
        String token = authService.createToken(request);

        // when
        Customer customer = authService.findCustomerByToken(token);

        Customer expected = new Customer("puterism@naver.com", "puterism",
                passwordEncoder.encode(request.getPassword()));
        // then

        assertThat(customer).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}
