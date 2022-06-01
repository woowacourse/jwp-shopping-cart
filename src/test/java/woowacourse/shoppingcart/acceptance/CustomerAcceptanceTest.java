package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.jsonwebtoken.Jwts;
import io.restassured.RestAssured;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.SignInRequest;
import woowacourse.shoppingcart.dto.SignInResponse;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.SignUpResponse;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {
    @Test
    void 회원가입() {

        SignUpRequest signUpRequest = new SignUpRequest("alpha", "bcc0830@naver.com", "asd123");

        var extract = RestAssured.given().log().all()
                .body(signUpRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/users")
                .then().log().all()
                .extract().as(SignUpResponse.class);

        assertAll(
                () -> assertThat(extract.getUsername()).isEqualTo("alpha"),
                () -> assertThat(extract.getEmail()).isEqualTo("bcc0830@naver.com")
        );
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void 회원가입시_이름이_공백인_경우(String invalidName) {

        SignUpRequest signUpRequest = new SignUpRequest(invalidName, "bcc0830@naver.com", "asd123");

        var extract = RestAssured.given().log().all()
                .body(signUpRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/users")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extract.body().jsonPath().getString("message")).isEqualTo(
                        "[ERROR] 이름은 공백 또는 빈 값일 수 없습니다.")
        );
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void 회원가입시_이메일이_공백인_경우(String invalidEmail) {

        SignUpRequest signUpRequest = new SignUpRequest("alpha", invalidEmail, "asd123");

        var extract = RestAssured.given().log().all()
                .body(signUpRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/users")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extract.body().jsonPath().getString("message")).isEqualTo(
                        "[ERROR] 올바른 이메일이 아닙니다.")
        );
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void 회원가입시_비밀번호가_공백인_경우(String invalidPassword) {

        SignUpRequest signUpRequest = new SignUpRequest("alpha", "bcc0830@naver.com", invalidPassword);

        var extract = RestAssured.given().log().all()
                .body(signUpRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/users")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extract.body().jsonPath().getString("message")).isEqualTo(
                        "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.")
        );
    }

    @Test
    void 이미_존재하는_이메일인_경우() {
        SignUpRequest signUpRequest = new SignUpRequest("alpha", "crew01@naver.com", "a123");

        var extract = RestAssured.given().log().all()
                .body(signUpRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/users")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extract.body().jsonPath().getString("message")).isEqualTo(
                        "[ERROR] 이미 존재하는 이메일입니다.")
        );
    }

    @Test
    void 이미_존재하는_사용자이름인_경우() {
        SignUpRequest signUpRequest = new SignUpRequest("puterism", "crew10@naver.com", "a123");

        var extract = RestAssured.given().log().all()
                .body(signUpRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/users")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extract.body().jsonPath().getString("message")).isEqualTo(
                        "[ERROR] 이미 존재하는 사용자 이름입니다.")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"@naver.com", "bcc0830naver.com", "bcc0830@", "bcc0830", "bcc0830#naver.com",
            "bcc0830@navercom"})
    void 회원가입시_이메일_형식이_아닌_경우(String invalidEmail) {

        SignUpRequest signUpRequest = new SignUpRequest("alpha", invalidEmail, "1234");

        var extract = RestAssured.given().log().all()
                .body(signUpRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/users")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extract.body().jsonPath().getString("message")).isEqualTo(
                        "[ERROR] 올바른 이메일이 아닙니다.")
        );
    }

    @Test
    void 유효한_토큰으로_정보를_조회하는_경우() {
        String email = "crew01@naver.com";
        String password = "a123";

        String accessToken = RestAssured
                .given().log().all()
                .body(new SignInRequest(email, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        CustomerResponse customerResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/users/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(CustomerResponse.class);

        assertAll(
                () -> assertThat(customerResponse.getUsername()).isEqualTo("puterism"),
                () -> assertThat(customerResponse.getEmail()).isEqualTo("crew01@naver.com")
        );
    }

    @Test
    void 유효기간이_지난_토큰으로_정보를_조회하는_경우() {
        Date now = new Date(0L);
        Date validity = new Date(1L);

        var invalidToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(invalidToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/users/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value()).extract();

        assertAll(
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo("[ERROR] 만료된 토큰입니다."),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value())
        );
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void 비밀번호_변경_시_기존_비밀번호가_공백인_경우(String invalidPassword) {
        String email = "crew01@naver.com";
        String password = "a123";

        String accessToken = RestAssured
                .given().log().all()
                .body(new SignInRequest(email, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(invalidPassword, "a1234");

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(changePasswordRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .patch("/users/me")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value()).extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo(
                        "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.")
        );
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void 비밀번호_변경_시_새로운_비밀번호가_공백인_경우(String invalidPassword) {
        String email = "crew01@naver.com";
        String password = "a123";

        String accessToken = RestAssured
                .given().log().all()
                .body(new SignInRequest(email, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("a123", invalidPassword);

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(changePasswordRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .patch("/users/me")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value()).extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo(
                        "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.")
        );
    }

    @Test
    void 유효기간이_지난_토큰으로_비밀번호를_변경하는_경우() {
        Date now = new Date(0L);
        Date validity = new Date(1L);

        var invalidToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(invalidToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/users/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value()).extract();

        assertAll(
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo("[ERROR] 만료된 토큰입니다."),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value())
        );
    }

    @Test
    void 비밀번호_수정_시_기존_비밀번호가_다른_경우() {
        String email = "crew01@naver.com";
        String password = "a123";

        String accessToken = RestAssured
                .given().log().all()
                .body(new SignInRequest(email, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("a1234", "a");

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(changePasswordRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .patch("/users/me")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value()).extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo(
                        "[ERROR] 비밀번호가 일치하지 않습니다.")
        );
    }

    @Test
    void 비밀번호_수정() {
        String email = "crew01@naver.com";
        String password = "a123";

        String accessToken = RestAssured
                .given().log().all()
                .body(new SignInRequest(email, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("a123", "a1234");

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(changePasswordRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .patch("/users/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();


        var response = RestAssured
                .given().log().all()
                .body(new SignInRequest(email, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo(
                        "[ERROR] 비밀번호가 일치하지 않습니다.")
        );
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
    }
}
