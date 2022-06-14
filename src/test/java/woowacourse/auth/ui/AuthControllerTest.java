package woowacourse.auth.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.utils.Fixture.email;
import static woowacourse.utils.Fixture.nickname;
import static woowacourse.utils.Fixture.signupRequest;
import static woowacourse.utils.Fixture.tokenRequest;
import static woowacourse.utils.RestAssuredUtils.login;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.utils.AcceptanceTest;

public class AuthControllerTest extends AcceptanceTest {

    @Autowired
    private CustomerService customerService;

    @DisplayName("로그인이 성공한다.")
    @Test
    void login_success() {
        // given
        customerService.signUp(signupRequest);

        // when
        ExtractableResponse<Response> response = login("/auth/login", tokenRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("nickname")).isEqualTo(nickname),
                () -> assertThat(response.jsonPath().getString("accessToken")).isNotNull()
        );
    }

    @DisplayName("로그인에 실패한다.")
    @Test
    void loginFail() {
        // given
        customerService.signUp(signupRequest);

        // when
        ExtractableResponse<Response> response = login("/auth/login", new TokenRequest(email, "a12232134!"));

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
