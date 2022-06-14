package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.ExceptionResponse;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {
    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("email", "Pw123456!", "name", "010-1234-5678", "address");

        RestAssured.given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/customers")
                .then().log().all()
                .extract();

        String accessToken = RestAssured.given().log().all()
                .body(new TokenRequest("email", "Pw123456!"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/auth/login")
                .then().log().all()
                .extract().as(TokenResponse.class).getAccessToken();
        // when
        CustomerResponse customerResponse = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CustomerResponse.class);

        // then
        assertThat(customerResponse).extracting("email", "name", "phone", "address")
                .containsExactly("email", "name", "010-1234-5678", "address");
    }

    @DisplayName("Bearer Auth 회원가입을 하지 않아, 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // when
        ExceptionResponse response = RestAssured.given().log().all()
                .body(new TokenRequest("email", "Pw123456!"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/auth/login")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ExceptionResponse.class);
        // then
        assertThat(response.getMessage()).isEqualTo("Email 또는 Password가 일치하지 않습니다.");
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰일 경우, 예외를 발생시킨다.")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        String accessToken = "aaaaaaaaa";
        ExceptionResponse response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract().as(ExceptionResponse.class);
        //then
        assertThat(response.getMessage()).isEqualTo("유효하지 않거나 만료된 토큰입니다.");
    }

    @DisplayName("Bearer Auth 토큰이 없는 경우 예외를 발생시킨다.")
    @Test
    void myInfoWithEmptyBearerAuth() {
        // when
        ExceptionResponse response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract().as(ExceptionResponse.class);
        // then
        assertThat(response.getMessage()).isEqualTo("유효하지 않거나 만료된 토큰입니다.");
    }

    @DisplayName("Bearer Auth 토큰이 없는 경우")
    @Test
    void myInfoWithEmptyBearerAuthWhenTokenEmpty() {
        // when
        String accessToken = "";
        ExceptionResponse response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customers")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract().as(ExceptionResponse.class);
        // then
        assertThat(response.getMessage()).isEqualTo("토큰 정보가 없습니다.");

    }
}
