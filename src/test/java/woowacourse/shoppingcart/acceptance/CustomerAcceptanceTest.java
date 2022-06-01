package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Autowired
    JwtTokenProvider tokenProvider;

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        CustomerRequest request = new CustomerRequest(
                "username",
                "password12!@",
                "example@example.com",
                "some-address",
                "010-0000-0001"
        );
        ExtractableResponse<Response> extract = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(extract.header("Location")).startsWith("/api/customers"),
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        );
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        CustomerRequest customerRequest = new CustomerRequest(
                "username",
                "password12!@",
                "example@example.com",
                "some-address",
                "010-0000-0001"
        );
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerRequest)
                .when().post("/api/customers")
                .then().log().all();

        String token = "Bearer " + tokenProvider.createToken("username");
        ExtractableResponse<Response> extractedResponse = RestAssured.given().log().all()
                .header(new Header("Authorization", token))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all().extract();
        CustomerResponse customerResponse = extractedResponse.jsonPath().getObject(".", CustomerResponse.class);

        assertThat(customerResponse.getName()).isEqualTo("username");
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        CustomerRequest customerRequest = new CustomerRequest(
                "username",
                "password12!@",
                "example@example.com",
                "some-address",
                "010-0000-0001"
        );
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerRequest)
                .when().post("/api/customers")
                .then().log().all();

        String token = "Bearer " + tokenProvider.createToken("username");
        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("another-address", "010-9999-9998");
        ExtractableResponse<Response> updatedResponse = RestAssured.given().log().all()
                .header(new Header("Authorization", token))
                .body(updateCustomerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/api/customers/me")
                .then().log().all().extract();

        ExtractableResponse<Response> foundResponse = RestAssured.given().log().all()
                .header(new Header("Authorization", token))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all().extract();
        CustomerResponse customerResponse = foundResponse.jsonPath().getObject(".", CustomerResponse.class);

        assertAll(
                () -> assertThat(updatedResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(customerResponse.getAddress()).isEqualTo("another-address"),
                () -> assertThat(customerResponse.getPhoneNumber()).isEqualTo("010-9999-9998")
        );
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        CustomerRequest customerRequest = new CustomerRequest(
                "username",
                "password12!@",
                "example@example.com",
                "some-address",
                "010-0000-0001"
        );
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerRequest)
                .when().post("/api/customers")
                .then().log().all();

        String token = "Bearer " + tokenProvider.createToken("username");
        ExtractableResponse<Response> deletedResponse = RestAssured.given().log().all()
                .header(new Header("Authorization", token))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/me")
                .then().log().all().extract();

        ExtractableResponse<Response> foundResponse = RestAssured.given().log().all()
                .header(new Header("Authorization", token))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all().extract();

        assertThat(deletedResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(foundResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
