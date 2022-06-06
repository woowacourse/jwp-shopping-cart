package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.AuthenticationException;
import woowacourse.auth.exception.AuthorizationException;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.request.SignUpRequest;

@SpringBootTest
@Sql(scripts = {"classpath:cleanse_test_db.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private CustomerService customerService;

    private static Long 식별자;
    private static final String 유효한_아이디 = "유효한_아이디";
    private static final String 유효한_비밀번호 = "password1@";

    @BeforeEach
    void setUp() {
        SignUpRequest request = new SignUpRequest(유효한_아이디, 유효한_비밀번호, "닉네임", 15);
        식별자 = customerService.signUp(request);
    }

    @DisplayName("로그인 성공시 토큰이 담긴 response를 반환하고, 토큰에 담긴 id값은 식별자 값과 같다.")
    @Test
    void createTokenTest_success() {
        TokenRequest request = new TokenRequest(유효한_아이디, 유효한_비밀번호);
        TokenResponse response = authService.createToken(request);

        Long resultId = authService.findUserIdByToken(response.getAccessToken());

        assertThat(resultId).isEqualTo(식별자);
    }

    @DisplayName("로그인 실패시 예외가 발생한다.")
    @Test
    void createTokenTest_fail() {
        String 유효하지_않은_비밀번호 = "password!!2";
        TokenRequest request = new TokenRequest(유효한_아이디, 유효하지_않은_비밀번호);

        assertThatThrownBy(() -> authService.createToken(request))
                .isInstanceOf(AuthenticationException.class);
    }

    @DisplayName("잘못된 토큰 입력시 예외가 발생한다.")
    @Test
    void findUserIdByToken_fail() {
        String 잘못된_토큰 = "wrong_token.wrong.token";

        assertThatThrownBy(() -> authService.findUserIdByToken(잘못된_토큰))
                .isInstanceOf(AuthorizationException.class);
    }
}
