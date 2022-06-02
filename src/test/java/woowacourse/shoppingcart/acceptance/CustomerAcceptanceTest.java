package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.acceptance.fixture.CustomerAcceptanceFixture;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;
import woowacourse.support.SimpleRestAssured;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Autowired
    JwtTokenProvider tokenProvider;

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        ExtractableResponse<Response> saveResponse = CustomerAcceptanceFixture.saveCustomer();

        assertAll(
            () -> assertThat(saveResponse.header("Location")).startsWith("/api/customers"),
            () -> assertThat(saveResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        );
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        CustomerAcceptanceFixture.saveCustomer();

        String token = "Bearer " + tokenProvider.createToken("username");

        CustomerResponse customerResponse = SimpleRestAssured.toObject(
            SimpleRestAssured.get("/api/customers/me", new Header("Authorization", token)),
            CustomerResponse.class
        );

        assertThat(customerResponse.getName()).isEqualTo("username");
    }

    @DisplayName("존재하지 않는 회원을 조회하면 예외를 발생한다.")
    @Test
    void notFoundException() {
        String token = "Bearer " + tokenProvider.createToken("invalidUser");

        ExtractableResponse<Response> foundResponse =
            SimpleRestAssured.get("/api/customers/me", new Header("Authorization", token)
            );

        assertThat(foundResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        CustomerAcceptanceFixture.saveCustomer();

        String token = "Bearer " + tokenProvider.createToken("username");
        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("another-address", "010-9999-9998");
        ExtractableResponse<Response> updatedResponse =
            SimpleRestAssured.put("/api/customers/me", new Header("Authorization", token), updateCustomerRequest);

        ExtractableResponse<Response> foundResponse =
            SimpleRestAssured.get("/api/customers/me", new Header("Authorization", token));
        CustomerResponse customerResponse = SimpleRestAssured.toObject(foundResponse, CustomerResponse.class);

        assertAll(
            () -> assertThat(updatedResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
            () -> assertThat(customerResponse.getAddress()).isEqualTo("another-address"),
            () -> assertThat(customerResponse.getPhoneNumber()).isEqualTo("010-9999-9998")
        );
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        CustomerAcceptanceFixture.saveCustomer();

        String token = "Bearer " + tokenProvider.createToken("username");
        ExtractableResponse<Response> deletedResponse =
            SimpleRestAssured.delete("/api/customers/me", new Header("Authorization", token));

        ExtractableResponse<Response> foundResponse =
            SimpleRestAssured.get("/api/customers/me", new Header("Authorization", token));

        assertThat(deletedResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(foundResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
