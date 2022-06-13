package woowacourse.auth.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {
    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CustomerRequest("testx", "1aB45678!"))
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        // when
        String accessToken = RestAssured
                .given().log().all()
                .body(new TokenRequest("testx", "1aB45678!"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all().extract().as(TokenResponse.class).getAccessToken();

        // then
        CustomerResponse customer = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(CustomerResponse.class);

        assertThat(customer.getUserName()).isEqualTo("testx");
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CustomerRequest("testx", "1aB45678!"))
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        // when
        ExtractableResponse<Response> tokenResponse = RestAssured
                .given().log().all()
                .body(new TokenRequest("testx", "1aB4567C!"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all().extract();

        // then
        assertThat(tokenResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // given
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CustomerRequest("testx", "1aB45678!"))
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
