package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.SignUpRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입을 하고 로그인을 하면 토큰을 반환한다.")
    @Test
    void createToken() {
        // given(회원가입을 하고)
        String username = "rennon";
        String email = "rennon@woowa.com";
        String password = "12345678";
        SignUpRequest signUpRequest = new SignUpRequest(username, email, password);
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // when(등록된 email, password로 로그인을 하면)
        SignInRequest signInRequest = new SignInRequest(email, password);
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract();

        // then(토큰을 반환한다.)
        String token = response.as(SignInResponse.class).getToken();
        assertThat(token).isNotBlank();
    }

    @DisplayName("발급 받은 토큰을 사용하여 내 정보 조회를 요청하면 내 정보가 조회된다.")
    @Test
    void myInfoWithBearerAuth() {
        // given(회원가입하고 로그인을 하고)
        String username = "rennon";
        String email = "rennon@woowa.com";
        String password = "12345678";
        SignUpRequest signUpRequest = new SignUpRequest(username, email, password);
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        SignInRequest signInRequest = new SignInRequest(email, password);
        String token = RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        // when(발급 받은 토큰을 사용하여 내 정보 조회를 요청하면)
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/users/me")
                .then().log().all()
                .extract();

        // then(내 정보가 조회된다)
        CustomerResponse customerResponse = response.as(CustomerResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(customerResponse.getUsername()).isEqualTo(username),
                () -> assertThat(customerResponse.getEmail()).isEqualTo(email)
        );
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given(회원가입을 하고)
        String username = "rennon";
        String email = "rennon@woowa.com";
        String password = "12345678";
        SignUpRequest signUpRequest = new SignUpRequest(username, email, password);
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // when(잘못된 email, password를 사용해 토큰을 요청하면)
        String wrongEmail = "greenlawn@woowa.com";
        SignInRequest signInRequest = new SignInRequest(wrongEmail, password);
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract();

        // then(토큰 발급 요청이 거부된다)
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // given(회원가입하고 로그인을 하고)
        String username = "rennon";
        String email = "rennon@woowa.com";
        String password = "12345678";
        SignUpRequest signUpRequest = new SignUpRequest(username, email, password);
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        SignInRequest signInRequest = new SignInRequest(email, password);
        String token = RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        // when(유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면)
        String wrongToken = "dummy";
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(AUTHORIZATION, "Bearer " + wrongToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/users/me")
                .then().log().all()
                .extract();

        // then(내 정보 조회 요청이 거부된다)
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("토큰을 전송하면 새로운 토큰을 발급해준다.")
    @Test
    void createTokenRefresh() {
        // given(회원가입하고 로그인을 하고)
        String username = "rennon";
        String email = "rennon@woowa.com";
        String password = "12345678";
        SignUpRequest signUpRequest = new SignUpRequest(username, email, password);
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        SignInRequest signInRequest = new SignInRequest(email, password);
        String token = RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        // when(2초 뒤 토큰 재발급을 요청하면)
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String newToken = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/token/refresh")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        // then(토큰을 반환한다.)
        assertThat(token).isNotEqualTo(newToken);
    }
}
