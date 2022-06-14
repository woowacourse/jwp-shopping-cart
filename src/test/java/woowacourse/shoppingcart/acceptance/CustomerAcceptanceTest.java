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
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginTokenResponse;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        ExtractableResponse<Response> response = 회원가입_요청(
            new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));

        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
            () -> assertThat(response.header("Location")).startsWith("/api/customers/")
        );
    }

    @DisplayName("내 정보 조회")
    @Test
    void findCustomer() {
        ExtractableResponse<Response> createResponse = 회원가입_요청(
            new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));
        long savedId = ID_추출(createResponse);

        String token = 로그인_요청_및_토큰발급(new LoginRequest("roma@naver.com", "12345678"));
        ExtractableResponse<Response> response = 회원조회_요청(token, savedId);

        CustomerResponse customerResponse = response.as(CustomerResponse.class);
        CustomerResponse expected = new CustomerResponse(savedId, "roma@naver.com", "roma");

        assertAll(
            () -> assertThat(customerResponse).usingRecursiveComparison()
                .isEqualTo(expected)
        );
    }

    @DisplayName("내 정보 수정")
    @Test
    void update() {
        long savedId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));

        String token = 로그인_요청_및_토큰발급(new LoginRequest("roma@naver.com", "12345678"));
        ExtractableResponse<Response> response = 회원정보수정_요청(token, savedId, new CustomerUpdateRequest("sojukang"));

        CustomerResponse customerResponse = response.as(CustomerResponse.class);
        CustomerResponse expected = new CustomerResponse(savedId, "roma@naver.com", "sojukang");

        assertAll(
            () -> assertThat(customerResponse).usingRecursiveComparison()
                .isEqualTo(expected)
        );
    }

    @DisplayName("회원탈퇴")
    @Test
    void delete() {
        String token = 로그인_요청_및_토큰발급(new LoginRequest("puterism@naver.com", "12349053145"));
        ExtractableResponse<Response> response = 회원탈퇴_요청(token, 1L, "12349053145");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private String 로그인_요청_및_토큰발급(LoginRequest request) {
        ExtractableResponse<Response> loginResponse = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/auth/login")
            .then().log().all()
            .extract();

        LoginTokenResponse loginTokenResponse = loginResponse.body().as(LoginTokenResponse.class);
        return loginTokenResponse.getAccessToken();
    }

    private ExtractableResponse<Response> 회원가입_요청(CustomerCreateRequest request) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/customers")
            .then().log().all()
            .extract();
    }

    private long 회원가입_요청_및_ID_추출(CustomerCreateRequest request) {
        return ID_추출(회원가입_요청(request));
    }

    private ExtractableResponse<Response> 회원조회_요청(String token, Long id) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .when().get("/api/customers/" + id)
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 회원정보수정_요청(String token, long id, CustomerUpdateRequest request) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().put("/api/customers/" + id)
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 회원탈퇴_요청(String token, long id, String password) {
        CustomerDeleteRequest request = new CustomerDeleteRequest(password);
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/customers/{id}", id)
            .then().log().all()
            .extract();
    }

    private long ID_추출(ExtractableResponse<Response> response) {
        String[] locations = response.header("Location").split("/");
        return Long.parseLong(locations[locations.length - 1]);
    }
}
