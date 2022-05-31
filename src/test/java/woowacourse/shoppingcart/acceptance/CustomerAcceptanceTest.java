package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerSaveRequest;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    private static final String USERNAME = "test";
    private static final String EMAIL = "test@email.com";
    private static final String PASSWORD = "1234567890";
    private static final String ADDRESS = "서울 강남구 테헤란로 411, 성담빌딩 13층 (선릉 캠퍼스)";
    private static final String PHONE_NUMBER = "010-0000-0000";

    @DisplayName("회원가입을 한다.")
    @Test
    void addCustomer() {
        CustomerSaveRequest request = new CustomerSaveRequest(USERNAME, EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER);
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        assertAll(() -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.header("Location")).contains("customers");
        });
    }

    @DisplayName("내 정보를 조회한다.")
    @Test
    void getMe() {
        CustomerSaveRequest request = new CustomerSaveRequest(USERNAME, EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        String accessToken = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(USERNAME, PASSWORD))
                .when().post("/api/auth/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();

        CustomerResponse customerResponse = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CustomerResponse.class);

        assertAll(() -> {
            assertThat(customerResponse.getId()).isNotNull();
            assertThat(customerResponse.getUsername()).isEqualTo(USERNAME);
            assertThat(customerResponse.getEmail()).isEqualTo(EMAIL);
            assertThat(customerResponse.getAddress()).isEqualTo(ADDRESS);
            assertThat(customerResponse.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        });
    }

    @DisplayName("유효하지 않는 토큰으로 내 정보 조회 시 401 상태코드를 반환한다.")
    @Test
    void getMe_error_invalidToken() {
        String accessToken = "aaaaaaa.bbbbbbb.ccccccc";

        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }

    @DisplayName("내 정보를 수정한다.")
    @Test
    void updateMe() {
        CustomerSaveRequest request = new CustomerSaveRequest(USERNAME, EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        String accessToken = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(USERNAME, PASSWORD))
                .when().post("/api/auth/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();

        String address = "선릉역";
        String phoneNumber = "010-1111-1111";
        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CustomerUpdateRequest(address, phoneNumber))
                .when().put("/api/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();

        CustomerResponse customerResponse = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CustomerResponse.class);

        assertAll(() -> {
            assertThat(customerResponse.getId()).isNotNull();
            assertThat(customerResponse.getUsername()).isEqualTo(USERNAME);
            assertThat(customerResponse.getEmail()).isEqualTo(EMAIL);
            assertThat(customerResponse.getAddress()).isEqualTo(address);
            assertThat(customerResponse.getPhoneNumber()).isEqualTo(phoneNumber);
        });
    }

    @DisplayName("회원을 탈퇴한다.")
    @Test
    void deleteMe() {
        CustomerSaveRequest request = new CustomerSaveRequest(USERNAME, EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        String accessToken = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(USERNAME, PASSWORD))
                .when().post("/api/auth/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();

        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();

        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();
    }
}
