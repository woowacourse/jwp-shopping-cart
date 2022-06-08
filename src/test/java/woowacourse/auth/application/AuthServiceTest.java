package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.domain.token.Token;
import woowacourse.auth.domain.user.Customer;
import woowacourse.auth.domain.user.EncryptedPassword;
import woowacourse.auth.domain.user.Password;
import woowacourse.auth.domain.token.ValidToken;
import woowacourse.auth.dto.request.TokenRequest;
import woowacourse.auth.dto.response.TokenResponse;
import woowacourse.common.exception.AuthenticationException;
import woowacourse.util.DatabaseFixture;

@SuppressWarnings("NonAsciiCharacters")
@Sql("classpath:test_schema.sql")
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class AuthServiceTest {

    private static final String 유효한_아이디 = "username";
    private static final String 비밀번호 = "password1@";
    private static final EncryptedPassword 암호화된_비밀번호 = new Password(비밀번호).toEncrypted();
    private static final String 유효한_닉네임 = "닉네임";
    private static final int 유효한_나이 = 20;
    private final Customer 유효한_고객 = new Customer(1L, 유효한_아이디, 암호화된_비밀번호, 유효한_닉네임, 유효한_나이);

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private DatabaseFixture databaseFixture;

    @DisplayName("createToken 메서드는 로그인 정보에 해당되는 사용자에 대해 토큰을 생성하여 반환")
    @Nested
    class CreateTokenTest {

        @Test
        void 존재하는_사용자의_아이디와_비밀번호인_경우_토큰_반환() {
            databaseFixture.save(유효한_고객);
            TokenRequest tokenRequest = new TokenRequest(유효한_아이디, 비밀번호);

            TokenResponse tokenResponse = authService.createToken(tokenRequest);

            assertThat(tokenResponse.getAccessToken()).isNotBlank();
        }

        @Test
        void 존재하지_않는_사용자의_아이디인_경우_예외발생() {
            TokenRequest tokenRequest = new TokenRequest(유효한_아이디, 비밀번호);

            assertThatThrownBy(() -> authService.createToken(tokenRequest))
                    .isInstanceOf(AuthenticationException.class);
        }

        @Test
        void 존재하는_사용자의_아이디지만_비밀번호를_틀린_경우_예외발생() {
            databaseFixture.save(유효한_고객);
            String 형식은_유효하지만_틀린_비밀번호 = "wrongpassword1!";
            TokenRequest tokenRequest = new TokenRequest(유효한_아이디, 형식은_유효하지만_틀린_비밀번호);

            assertThatThrownBy(() -> authService.createToken(tokenRequest))
                    .isInstanceOf(AuthenticationException.class);
        }
    }

    @DisplayName("findCustomerByToken 메서드는 토큰에 담긴 정보에 해당되는 고객을 찾아내 반환")
    @Nested
    class FindCustomerByTokenTest {

        @Test
        void 토큰에_해당되는_고객_정보_반환() {
            databaseFixture.save(유효한_고객);
            TokenResponse generatedToken = authService.createToken(new TokenRequest(유효한_아이디, 비밀번호));
            Token validAccessToken = new ValidToken(generatedToken.getAccessToken());

            Customer actual = authService.findCustomerByToken(validAccessToken);

            assertThat(actual).isEqualTo(유효한_고객);
        }

        @Test
        void 존재하지_않는_고객에_대한_정보로_만들어진_토큰인_경우_예외발생() {
            Token accessToken = tokenService.generateToken("존재하지_않는_사용자_정보");

            assertThatThrownBy(() -> authService.findCustomerByToken(accessToken))
                    .isInstanceOf(AuthenticationException.class);
        }
    }
}
