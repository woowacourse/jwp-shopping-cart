package woowacourse.auth.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.LoginResponse;
import woowacourse.auth.support.JwtTokenProvider;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class AuthServiceTest {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceTest(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @DisplayName("토큰을 생성한다.")
    @Test
    void createToken() {
        LoginResponse token = authService.createToken(123L);
        boolean result = jwtTokenProvider.validateToken(token.getAccessToken());
        assertThat(result).isTrue();
    }
}
