package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.SignInRequest;
import woowacourse.shoppingcart.dto.SignInResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 로그인_성공() {
        var signInRequest = new SignInRequest("crew01@naver.com", "a123");

        var signInResponse = RestAssured.given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .extract().as(SignInResponse.class);

        assertAll(
                () -> assertThat(signInResponse.getUsername()).isEqualTo("puterism"),
                () -> assertThat(signInResponse.getEmail()).isEqualTo("crew01@naver.com"),
                () -> assertThat(signInResponse.getToken()).isNotNull()
        );
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void 회원가입시_이메일이_공백인_경우(String invalidEmail) {

        SignInRequest signInRequest = new SignInRequest(invalidEmail, "asd123");

        var extract = RestAssured.given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extract.body().jsonPath().getString("message")).isEqualTo(
                        "[ERROR] 올바른 이메일이 아닙니다.")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"@naver.com", "bcc0830naver.com", "bcc0830@", "bcc0830", "bcc0830#naver.com",
            "bcc0830@navercom"})
    void 로그인시_이메일_형식이_아닌_경우(String invalidEmail) {

        SignInRequest signInRequest = new SignInRequest(invalidEmail, "1234");

        var extract = RestAssured.given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
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

        SignInRequest signInRequest = new SignInRequest("bcc0830@naver.com", invalidPassword);

        var extract = RestAssured.given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extract.body().jsonPath().getString("message")).isEqualTo(
                        "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.")
        );
    }

    @Test
    void 존재하지_않는_이메일로_로그인_하는_경우() {
        SignInRequest signInRequest = new SignInRequest("crew10@naver.com", "a123");

        var extract = RestAssured.given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extract.body().jsonPath().getString("message")).isEqualTo(
                        "[ERROR] 존재하지 않는 이메일 입니다.")
        );
    }

    @Test
    void 틀린_비밀번호로_로그인_하는_경우() {
        SignInRequest signInRequest = new SignInRequest("crew01@naver.com", "a1234");

        var extract = RestAssured.given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extract.body().jsonPath().getString("message")).isEqualTo(
                        "[ERROR] 비밀번호가 일치하지 않습니다.")
        );
    }

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        // 회원이 등록되어 있고
        // id, password를 사용해 토큰을 발급받고

        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보가 조회된다
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        // 회원이 등록되어 있고

        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면

        // then
        // 토큰 발급 요청이 거부된다
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보 조회 요청이 거부된다
    }
}
