package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.utils.Fixture.email;
import static woowacourse.auth.utils.Fixture.nickname;
import static woowacourse.auth.utils.Fixture.password;
import static woowacourse.auth.utils.Fixture.signupRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.customer.SignupRequest;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.utils.AcceptanceTest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        signUp(signupRequest);

        // when
        ExtractableResponse<Response> loginResponse = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(email, password))
                .when().post("/auth/login")
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(loginResponse.jsonPath().getString("nickname")).isEqualTo(nickname),
                () -> assertThat(loginResponse.jsonPath().getString("accessToken")).isNotNull()
        );
    }

    @DisplayName("로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        signUp(new SignupRequest(email, password, nickname));

        // when
        ExtractableResponse<Response> loginResponse = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(email, "a1234!!!23"))
                .when().post("/auth/login")
                .then().log().all()
                .extract();

        // then
        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private void signUp(SignupRequest request) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/customers")
                .then().log().all()
                .extract();
    }
}
