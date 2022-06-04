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
import woowacourse.auth.domain.EncryptedPassword;
import woowacourse.auth.domain.Password;
import woowacourse.auth.domain.Token;
import woowacourse.auth.domain.User;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.exception.AuthenticationException;
import woowacourse.util.DatabaseFixture;

@SuppressWarnings("NonAsciiCharacters")
@Sql("classpath:schema.sql")
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class AuthServiceTest {

    private static final String 유효한_아이디 = "username";
    private static final String 비밀번호 = "password1@";
    private static final EncryptedPassword 암호화된_비밀번호 = new Password(비밀번호).toEncrypted();
    private static final User 유효한_사용자 = new User(유효한_아이디, 암호화된_비밀번호);

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
            databaseFixture.save(유효한_사용자);
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
            databaseFixture.save(유효한_사용자);
            String 형식은_유효하지만_틀린_비밀번호 = "wrongpassword1!";
            TokenRequest tokenRequest = new TokenRequest(유효한_아이디, 형식은_유효하지만_틀린_비밀번호);

            assertThatThrownBy(() -> authService.createToken(tokenRequest))
                    .isInstanceOf(AuthenticationException.class);
        }
    }

    @DisplayName("findUserByToken 메서드는 토큰에 담긴 정보에 해당되는 사용자를 찾아내 반환")
    @Nested
    class FindUserByTokenTest {

        @Test
        void 토큰에_해당되는_사용자_정보_반환() {
            databaseFixture.save(유효한_사용자);
            String accessToken = authService.createToken(new TokenRequest(유효한_아이디, 비밀번호)).getAccessToken();

            User actual = authService.findUserByToken(accessToken);

            assertThat(actual).isEqualTo(유효한_사용자);
        }

        @Test
        void 존재하지_않는_사용자에_대한_정보로_만들어진_토큰인_경우_예외발생() {
            Token token = tokenService.generateToken("존재하지_않는_사용자_정보");
            String accessToken = token.getValue();

            assertThatThrownBy(() -> authService.findUserByToken(accessToken))
                    .isInstanceOf(AuthenticationException.class);
        }
    }
}
