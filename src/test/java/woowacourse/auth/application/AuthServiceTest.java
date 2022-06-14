package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.NoCustomerTokenException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.UnEncodedPassword;

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
        TokenResponse tokenResponse = authService.createToken(request);

        // when
        String email = jwtTokenProvider.getPayload(tokenResponse.getAccessToken());

        // then
        assertThat(email).isEqualTo("puterism@naver.com");
    }

    @DisplayName("토큰을 이용해서 Customer를 복원한다.")
    @Test
    void findCustomerByToken() {
        // given
        TokenRequest request = new TokenRequest("puterism@naver.com", "12349053145");
        TokenResponse tokenResponse = authService.createToken(request);

        // when
        Customer customer = authService.findCustomerByToken(tokenResponse.getAccessToken());

        Customer expected = new Customer("puterism@naver.com", "puterism",
                passwordEncoder.encode(new UnEncodedPassword(request.getPassword())));
        // then

        assertThat(customer).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @DisplayName("해당 토큰으로 회원이 존재하는지 검사한다.")
    @Test
    void validateExistCustomerByToken() {
        // given
        TokenRequest request = new TokenRequest("puterism@naver.com", "12349053145");
        TokenResponse tokenResponse = authService.createToken(request);

        // when then
        assertThatNoException().isThrownBy(
                () -> authService.validateExistCustomerByToken(tokenResponse.getAccessToken()));
    }

    @DisplayName("해당 토큰으로 회원이 존재하지 않으면 예외를 발생시킨다.")
    @Test
    void validateNoCustomerToken_exception() {
        // given
        String tokenNotExistCustomer = jwtTokenProvider.createToken("test@naver.com");

        // when then
        assertThatThrownBy(() -> authService.validateExistCustomerByToken(tokenNotExistCustomer))
                .isInstanceOf(NoCustomerTokenException.class);
    }
}
