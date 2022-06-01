package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("회원가입을 할 수 있다.")
    void addCustomer() {
        SignUpRequest signUpRequest = new SignUpRequest("alien", "alien@woowa.com", "1234");

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "1234");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "1234");
        String token = RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        //when
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/users/me")
                .then().log().all().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("토큰이 없으면 내 정보를 조회할 수 없다")
    @Test
    void getMeNoTokenThrowException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "1234");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "1234");
        String token = RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        //when
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/users/me")
                .then().log().all().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "1234");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "1234");
        String token = RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        //when
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("1234", "5678");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(updatePasswordRequest)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/users/me")
                .then().log().all().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("토큰이 없으면 내 정보를 수정할 수 없다")
    @Test
    void updateMeNoTokenThrowException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "1234");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "1234");
        String token = RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        //when
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("1234", "5678");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(updatePasswordRequest)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/users/me")
                .then().log().all().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "1234");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "1234");
        String token = RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("1234");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(deleteCustomerRequest)
                .when().delete("/users/me")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("토큰이 없으면 내 정보를 탈퇴할 수 없다")
    @Test
    void deleteMeThrowException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "1234");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "1234");
        RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("1234");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(deleteCustomerRequest)
                .when().delete("/users/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
