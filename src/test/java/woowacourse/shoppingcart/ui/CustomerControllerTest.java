package woowacourse.shoppingcart.ui;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "/sql/ClearTableCustomer.sql")
class CustomerControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("중복된 username으로 회원가입을 하면 실패한다.")
    void addCustomerDuplicateUsername() {
        SignUpRequest signUpRequest = new SignUpRequest("alien", "alien@woowa.com", "12345678");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest(name = "{displayName} : 길이 -> {arguments}")
    @ValueSource(ints = {1, 32})
    @DisplayName("username을 공백 제외 1 ~ 32자로 회원가입을 한다.")
    void addCustomerValidLength(int length) {
        SignUpRequest signUpRequest = new SignUpRequest("a".repeat(length), "alien@woowa.com", "12345678");

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @ParameterizedTest(name = "{displayName} : 길이 -> {arguments}")
    @ValueSource(ints = {0, 33})
    @DisplayName("username을 공백 또는 33자 이상 길이로 회원가입을 하면 실패한다.")
    void addCustomerInvalidLength(int length) {
        SignUpRequest signUpRequest = new SignUpRequest("a".repeat(length), "alien@woowa.com", "12345678");

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("잘못된 형식의 이메일로 회원가입할 경우 실패한다.")
    void addCustomerInvalidEmail() {
        SignUpRequest signUpRequest = new SignUpRequest("alien", "alien", "12345678");

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest(name = "{displayName} : 길이 -> {arguments}")
    @ValueSource(ints = {6, 255})
    @DisplayName("비밀번호를 공백 제외 6 ~ 255자로 회원가입을 한다.")
    void addCustomerValidPasswordLength(int length) {
        SignUpRequest signUpRequest = new SignUpRequest("alien", "alien@woowa.com", "1".repeat(length));

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @ParameterizedTest(name = "{displayName} : 길이 -> {arguments}")
    @ValueSource(ints = {5, 256})
    @DisplayName("비밀번호를 공백 제외 6 ~ 255자가 아닌 길이로 회원가입을 하면 실패한다.")
    void addCustomerInvalidPasswordLength(int length) {
        SignUpRequest signUpRequest = new SignUpRequest("alien", "alien@woowa.com", "1".repeat(length));

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("토큰이 없으면 내 정보를 조회할 수 없다")
    @Test
    void getMeNoTokenThrowException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "12345678");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "12345678");
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

    @DisplayName("토큰이 없으면 내 정보를 수정할 수 없다")
    @Test
    void updateMeNoTokenThrowException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "12345678");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "12345678");
        String token = RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        //when
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("12345678", "56781234");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(updatePasswordRequest)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/users/me")
                .then().log().all().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("토큰이 없으면 내 정보를 탈퇴할 수 없다")
    @Test
    void deleteMeThrowException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "12345678");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "12345678");
        RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().as(SignInResponse.class).getToken();

        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("12345678");
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(deleteCustomerRequest)
                .when().delete("/users/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
