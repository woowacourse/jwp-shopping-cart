package woowacourse.shoppingcart.acceptance;

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
import woowacourse.shoppingcart.dto.CustomerRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        // given
        CustomerRequest customer = new CustomerRequest(
                "email", "Pw123456!", "name", "010-1234-5678", "address");

        // when
        ValidatableResponse response = RestAssured.given().log().all()
                .body(customer)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/customers")
                .then().log().all();

        // then
        response.statusCode(HttpStatus.CREATED.value());
        response.body(
                "email", equalTo("email"),
                "name", equalTo("name"),
                "phone", equalTo("010-1234-5678"),
                "address", equalTo("address"));
    }

    @DisplayName("이메일 중복 여부 조회")
    @Test
    void emailDuplication() {
        // given
        CustomerRequest customer = new CustomerRequest(
                "email@naver.com", "Pw123456!", "name", "010-1234-5678", "address");

        RestAssured.given().log().all()
                .body(customer)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/customers")
                .then().log().all();

        // when
        ValidatableResponse response = RestAssured.given().log().all()
                .when()
                .get("/customers/email?email=email@naver.com")
                .then().log().all();

        // then
        response.body(containsString("true"));
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
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

        //when
        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(new CustomerRequest("email", "Pw123456!", "judy", "010-1111-2222", "address2"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/customers")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        //then
        ValidatableResponse response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customers")
                .then().log().all();

        response.statusCode(HttpStatus.OK.value());
        response.body(
                "email", equalTo("email"),
                "name", equalTo("judy"),
                "phone", equalTo("010-1111-2222"),
                "address", equalTo("address2"));
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
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

        //when
        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/customers")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        //then
        ValidatableResponse response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customers")
                .then().log().all();

        response.statusCode(HttpStatus.BAD_REQUEST.value());
        response.body(containsString("존재하지 않는 유저입니다."));
    }
}
