package woowacourse.auth.acceptance;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        CustomerRequest customer = new CustomerRequest(
                "email", "Pw123456!", "name", "010-1234-5678", "address");
        RestAssured.given().log().all()
                .body(customer)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/customers")
                .then().log().all();

        String accessToken = RestAssured.given().log().all()
                .body(new TokenRequest("email", "Pw123456!"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/customers/login")
                .then().log().all()
                .extract().as(TokenResponse.class).getAccessToken();

        // when
        ValidatableResponse response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customers")
                .then().log().all();

        // then
        response.statusCode(HttpStatus.OK.value());
        response.body(
                "email", equalTo("email"),
                "name", equalTo("name"),
                "phone", equalTo("010-1234-5678"),
                "address", equalTo("address"));
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // when
        ValidatableResponse response = RestAssured.given().log().all()
                .body(new TokenRequest("email", "Pw123456!"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/customers/login")
                .then().log().all();

        // then
        response.statusCode(HttpStatus.BAD_REQUEST.value());
        response.body(containsString("Email 또는 Password가 일치하지 않습니다."));
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        String accessToken = "invalidToken";
        ValidatableResponse response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customers")
                .then().log().all();

        // then
        response.statusCode(HttpStatus.UNAUTHORIZED.value());
        response.body(containsString("유효하지 않거나 만료된 토큰입니다."));
    }

    @DisplayName("Bearer Auth 토큰 정보가 존재하지 않는 경우")
    @Test
    void myInfoWithNoBearerAuth() {
        // when
        ValidatableResponse response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customers")
                .then().log().all();

        // then
        response.statusCode(HttpStatus.UNAUTHORIZED.value());
        response.body(containsString("토큰 정보가 존재하지 않습니다."));
    }
}
