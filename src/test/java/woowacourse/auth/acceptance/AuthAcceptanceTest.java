package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 로그인_성공() {
        // given
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new SignUpRequest("ellie", "Ellie1234!"))
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        // when
        String accessToken = RestAssured
                .given().log().all()
                .body(new LoginRequest("ellie", "Ellie1234!"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all().extract().as(LoginResponse.class).getAccessToken();

        // then
        CustomerResponse response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(CustomerResponse.class);

        assertThat(response.getUserName()).isEqualTo("ellie");
    }

    @Test
    void 비밀번호가_다른_경우_로그인_실패() {
        // given
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new SignUpRequest("ellie", "Ellie1234!"))
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        // when
        ExtractableResponse<Response> tokenResponse = RestAssured
                .given().log().all()
                .body(new LoginRequest("ellie", "Ellie1234@"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all().extract();

        // then
        assertThat(tokenResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 토큰이_유효하지_않은_경우_로그인_실패() {
        // given
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new SignUpRequest("ellie", "Ellie1234!"))
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        // when
        ExtractableResponse<Response> invalidTokenResponse = RestAssured
                .given().log().all()
                .auth().oauth2("invalid_token")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .extract();

        // then
        assertThat(invalidTokenResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
