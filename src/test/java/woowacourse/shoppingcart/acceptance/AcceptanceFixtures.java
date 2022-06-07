package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.ExceptionRequest;
import woowacourse.shoppingcart.dto.PasswordChangeRequest;

public class AcceptanceFixtures {

    public static ExtractableResponse<Response> 회원가입(final CustomerRequest customerRequest) {
        return RestAssured
                .given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers/signUp")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인(final CustomerLoginRequest customerLoginRequest) {
        return RestAssured
                .given().log().all()
                .body(customerLoginRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers/login")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 나의_정보조회(final String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/auth/customers/profile")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원탈퇴(final String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/auth/customers/profile")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내_정보_수정(final CustomerUpdateRequest customerUpdateRequest,
                                                        final String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerUpdateRequest)
                .when().patch("/auth/customers/profile")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 비밀번호_변경(PasswordChangeRequest passwordChangeRequest, String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(passwordChangeRequest)
                .when().patch("/auth/customers/profile/password")
                .then().log().all()
                .extract();
    }

    public static void BAD_REQUEST(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    public static void OK(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void NO_CONTENT(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void UNAUTHORIZED(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    public static void NOT_FOUND(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    public static void 예외메세지_검증(final ExtractableResponse<Response> response, final String message) {
        assertThat(response.body().as(ExceptionRequest.class).getMessage()).isEqualTo(message);
    }
}
