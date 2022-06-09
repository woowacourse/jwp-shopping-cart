package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.SignUpResponse;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("username, email, password로 회원가입을 하면 회원가입을 성공한다.")
    void addCustomer() {
        // given(username, email, password로)
        String username = "alien";
        String email = "alien@woowa.com";
        SignUpRequest signUpRequest = new SignUpRequest(username, email, "12345678");

        // when(회원가입을 하면)
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .extract();

        // then(회원가입을 성공한다)
        SignUpResponse signUpResponse = response.body().jsonPath().getObject(".", SignUpResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(signUpResponse.getUsername()).isEqualTo(username),
                () -> assertThat(signUpResponse.getEmail()).isEqualTo(email)
        );
    }

    @DisplayName("회원가입하고 로그인을 하고 내 정보를 조회하면 내 정보를 조회한다.")
    @Test
    void getMe() {
        //given(회원가입하고 로그인을 하고)
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

        //when(발급 받은 토큰을 사용하여 내 정보를 조회하면)
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/users/me")
                .then().log().all()
                .extract();

        // then(내 정보를 조회한다.)
        CustomerResponse customerResponse = response.body().jsonPath().getObject(".", CustomerResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(customerResponse.getUsername()).isEqualTo(username),
                () -> assertThat(customerResponse.getEmail()).isEqualTo(email)
        );
    }

    @DisplayName("회원가입하고 로그인을 하고 새로운 비밀번호로 비밀번호를 수정하면 비밀번호를 수정한다.")
    @Test
    void updateMe() {
        //given(회원가입하고 로그인을 하고)
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

        //when(발급 받은 토큰을 사용하여 새로운 비밀번호로 비밀번호를 수정하면)
        String newPassword = "56781234";
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(password, newPassword);
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(updatePasswordRequest)
                .header(AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/users/me")
                .then().log().all()
                .extract();

        // then(비밀번호를 수정한다.)
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        //given(회원가입하고 로그인을 하고)
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

        //when(발급 받은 토큰을 사용하여 회원탈퇴를 하면)
        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest(password);
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(AUTHORIZATION, "Bearer " + token)
                .body(deleteCustomerRequest)
                .when().delete("/users/me")
                .then().log().all()
                .extract();

        //then(회원을 탈퇴한다.)
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
