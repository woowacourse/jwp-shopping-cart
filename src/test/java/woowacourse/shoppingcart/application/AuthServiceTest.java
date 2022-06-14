package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.shoppingcart.fixture.CustomerFixture.connieDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.BadRequestException;
import woowacourse.auth.exception.NotFoundException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.application.dto.SignInDto;

@SpringBootTest
@DisplayName("AuthService 는")
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JwtTokenProvider provider;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS=0");
        jdbcTemplate.update("truncate table orders_detail");
        jdbcTemplate.update("truncate table cart_item");
        jdbcTemplate.update("truncate table orders");
        jdbcTemplate.update("truncate table product");
        jdbcTemplate.update("truncate table customer");
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS=1");
    }

    @DisplayName("로그인을 할 때")
    @Nested
    class SignInTest {

        @Test
        @DisplayName("로그인이 되면 토큰이 정상적으로 발급된다.")
        void createAccessToken() {
            Long 코니_id = 코니_회원_가입();
            final String email = "her0807@naver.com";
            final TokenResponse response = authService.signIn(new SignInDto(email, "password1!"));
            String payload = provider.getPayload(response.getAccessToken());
            assertThat(payload).isEqualTo(코니_id.toString());
            assertThat(response.getCustomerId()).isNotNull();
        }

        @Test
        @DisplayName("회원 가입을 하지 않은 유저면 에러가 발생한다.")
        void noSignUpCustomer() {
            final String email = "her0807@naver.com";
            assertThatThrownBy(() -> authService.signIn(new SignInDto(email, "password1!"))).isInstanceOf(
                    NotFoundException.class).hasMessage("가입하지 않은 유저입니다.");
        }

        @Test
        @DisplayName("비밀번호가 일치하지 않으면, 예외가 발생한다..")
        void noSignInCausePassword() {
            코니_회원_가입();
            final String email = "her0807@naver.com";
            assertThatThrownBy(() -> authService.signIn(new SignInDto(email, "password23!"))).isInstanceOf(
                    BadRequestException.class).hasMessage("올바르지 않은 비밀번호입니다.");
        }

        @Test
        @DisplayName("이메일이 일치하지 않으면, 예외가 발생한다.")
        void noSignInCauseEmail() {
            코니_회원_가입();
            final String email = "sudal@naver.com";
            assertThatThrownBy(() -> authService.signIn(new SignInDto(email, "password23!"))).isInstanceOf(
                    NotFoundException.class).hasMessage("가입하지 않은 유저입니다.");
        }
    }

    private Long 코니_회원_가입() {
        CustomerDto newCustomer = connieDto;
        final Long customerId = customerService.createCustomer(newCustomer);
        return customerId;
    }
}
