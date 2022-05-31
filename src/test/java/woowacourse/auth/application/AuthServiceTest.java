package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
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
import woowacourse.shoppingcart.dto.LoginCustomer;

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

            assertThat(token.getUsername()).isEqualTo("sunhpark42");
        }

        @Test
        void 토큰을_생성할_때_아이디가_일치하지_않는_경우_예외발생() {
            TokenRequest tokenRequest = new TokenRequest("invalidId@gmail.com", "12345678aA!");

            assertThatThrownBy(() -> authService.createToken(tokenRequest))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 토큰을_생성할_때_비밀번호가_일치하지_않는_경우_예외발생() {
            TokenRequest tokenRequest = new TokenRequest("sunhpark42@gmail.com", "invalidPassword");

            assertThatThrownBy(() -> authService.createToken(tokenRequest))
                .isInstanceOf(IllegalArgumentException.class);
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
                .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
