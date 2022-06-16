package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.request.LoginCustomer;
import woowacourse.shoppingcart.exception.InvalidCustomerLoginException;
import woowacourse.shoppingcart.exception.InvalidTokenException;
import woowacourse.shoppingcart.util.HashTool;

@SuppressWarnings("NonAsciiChracters")
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @DisplayName("createToken 메서드는 토큰을 생성한다.")
    @Nested
    class CreateTokenTest {

        @Test
        void 토큰을_정상적으로_생성() {
            TokenRequest tokenRequest = new TokenRequest("sunhpark42@gmail.com", "12345678aA!");

            TokenResponse token = authService.createToken(tokenRequest);

            assertThat(token.getName()).isEqualTo("sunhpark42");
        }

        @Test
        void 토큰을_생성할_때_아이디가_일치하지_않는_경우_예외발생() {
            TokenRequest tokenRequest = new TokenRequest("invalidId@gmail.com", "12345678aA!");

            assertThatThrownBy(() -> authService.createToken(tokenRequest))
                .isInstanceOf(InvalidCustomerLoginException.class);
        }

        @Test
        void 토큰을_생성할_때_비밀번호가_일치하지_않는_경우_예외발생() {
            TokenRequest tokenRequest = new TokenRequest("sunhpark42@gmail.com", "invalidPassword");

            assertThatThrownBy(() -> authService.createToken(tokenRequest))
                .isInstanceOf(InvalidCustomerLoginException.class);
        }
    }

    @DisplayName("findCustomerByToken 메서드는 토큰으로 로그인 유저 정보를 찾는다.")
    @Nested
    class FindCustomerByTokenTest {

        @Test
        void 토큰_정보가_올바른_경우_LoginCustomer를_반환() {
            TokenRequest tokenRequest = new TokenRequest("sunhpark42@gmail.com", "12345678aA!");
            TokenResponse token = authService.createToken(tokenRequest);

            LoginCustomer loginCustomer = authService.findCustomerByToken(token.getAccessToken());

            assertThat(loginCustomer)
                .extracting("loginId", "username")
                .containsExactly("sunhpark42@gmail.com", "sunhpark42");
        }

        @Test
        void 토큰_정보가_올바르지_않은_경우_예외발생() {
            assertThatThrownBy(() -> authService.findCustomerByToken("InvalidToken"))
                .isInstanceOf(InvalidTokenException.class);
        }
    }


    @DisplayName("checkPassword 메서드는 회원의 비밀번호가 맞는지 확인한다.")
    @Nested
    class CheckPasswordTest {

        @Test
        void 회원의_비밀번호가_일치_할_경우_성공() {
            Customer customer = new Customer("angie@gmail.com", "angel", "12345678aA!")
                    .ofHashPassword(HashTool::hashing);
            String validPassword = "12345678aA!";

            assertThatCode(() -> authService.checkPassword(customer, validPassword))
                    .doesNotThrowAnyException();
        }

        @Test
        void 회원의_비밀번호가_일치하지_않을_경우_예외발생() {
            Customer customer = new Customer("angie@gmail.com", "angel", "12345678aA!")
                    .ofHashPassword(HashTool::hashing);
            String invalidPassword = "devilAngie";

            assertThatThrownBy(() -> authService.checkPassword(customer, invalidPassword))
                    .isInstanceOf(IllegalArgumentException.class);

        }
    }
}
