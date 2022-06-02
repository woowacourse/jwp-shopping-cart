package woowacourse.auth.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.utils.Fixture.email;
import static woowacourse.auth.utils.Fixture.nickname;
import static woowacourse.auth.utils.Fixture.password;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.utils.AcceptanceTest;
import woowacourse.auth.application.CustomerService;
import woowacourse.auth.dto.customer.SignupRequest;
import woowacourse.auth.dto.token.TokenRequest;

public class AuthControllerTest extends AcceptanceTest {

    @Autowired
    private CustomerService customerService;

    @DisplayName("로그인이 성공한다.")
    @Test
    void login() {
        // given
        customerService.signUp(new SignupRequest(email, password, nickname));

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(email, password))
                .when().post("/auth/login")
                .then().log().all()
                .extract();

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
        customerService.signUp(new SignupRequest(email, password, nickname));

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(email, "a12232134!"))
                .when().post("/auth/login")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
