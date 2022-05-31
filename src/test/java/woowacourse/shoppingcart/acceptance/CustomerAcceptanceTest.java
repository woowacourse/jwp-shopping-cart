package woowacourse.shoppingcart.acceptance;

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
