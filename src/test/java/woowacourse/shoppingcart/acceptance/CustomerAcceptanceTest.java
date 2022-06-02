package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.Fixtures.BIRTHDAY_FORMATTED_VALUE_1;
import static woowacourse.Fixtures.CONTACT_VALUE_1;
import static woowacourse.Fixtures.CUSTOMER_INVALID_REQUEST_1;
import static woowacourse.Fixtures.CUSTOMER_REQUEST_1;
import static woowacourse.Fixtures.CUSTOMER_REQUEST_2;
import static woowacourse.Fixtures.EMAIL_VALUE_1;
import static woowacourse.Fixtures.GENDER_MALE;
import static woowacourse.Fixtures.NAME_VALUE_1;
import static woowacourse.Fixtures.PASSWORD_VALUE_1;
import static woowacourse.Fixtures.PROFILE_IMAGE_URL_VALUE_1;
import static woowacourse.Fixtures.TERMS_1;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.AcceptanceTest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.EmailDuplicationResponse;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {
    @Test
    @DisplayName("유저 회원가입 성공")
    void joinSuccess() {
        // given
        ExtractableResponse<Response> response = createCustomer(CUSTOMER_REQUEST_1);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("location")).isNotNull()
        );
    }

    @Test
    @DisplayName("유저 회원가입 실패")
    void joinFailed() {
        // given
        ExtractableResponse<Response> response = createCustomer(CUSTOMER_INVALID_REQUEST_1);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isNotNull()
        );
    }

    @Test
    @DisplayName("이메일 중복 검사")
    void validateEmailDuplication() {
        // given
        createCustomer(CUSTOMER_REQUEST_1);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/validation?email=" + EMAIL_VALUE_1)
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(
                        response.jsonPath().getObject(".", EmailDuplicationResponse.class).getIsDuplicated()).isTrue()
        );
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        // given
        ExtractableResponse<Response> customer = createCustomer(CUSTOMER_REQUEST_1);
        String customerUri = customer.header("Location");
        TokenResponse tokenResponse = getTokenResponse(EMAIL_VALUE_1, PASSWORD_VALUE_1);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .when().get(customerUri)
                .then().log().all().extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.body().jsonPath().getObject(".", CustomerResponse.class))
                        .extracting("email", "profileImageUrl", "name", "gender", "birthday", "contact", "terms")
                        .containsExactly(EMAIL_VALUE_1, PROFILE_IMAGE_URL_VALUE_1, NAME_VALUE_1, GENDER_MALE,
                                BIRTHDAY_FORMATTED_VALUE_1, CONTACT_VALUE_1, TERMS_1)
        );

    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        // given
        ExtractableResponse<Response> customer = createCustomer(CUSTOMER_REQUEST_1);
        String customerUri = customer.header("Location");
        TokenResponse tokenResponse = getTokenResponse(EMAIL_VALUE_1, PASSWORD_VALUE_1);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(CUSTOMER_REQUEST_2)
                .when().put(customerUri)
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        // given
        ExtractableResponse<Response> customer = createCustomer(CUSTOMER_REQUEST_1);
        String customerUri = customer.header("Location");
        TokenResponse tokenResponse = getTokenResponse(EMAIL_VALUE_1, PASSWORD_VALUE_1);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .when().delete(customerUri)
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private ExtractableResponse<Response> createCustomer(CustomerRequest customerRequest) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", customerRequest.getEmail());
        params.put("password", customerRequest.getPassword());
        params.put("profileImageUrl", customerRequest.getProfileImageUrl());
        params.put("name", customerRequest.getName());
        params.put("gender", customerRequest.getGender());
        params.put("birthday", customerRequest.getBirthday());
        params.put("contact", customerRequest.getContact());
        params.put("fullAddress", Map.of("address", customerRequest.getFullAddress().getAddress(), "detailAddress",
                customerRequest.getFullAddress().getDetailAddress(), "zoneCode",
                customerRequest.getFullAddress().getZoneCode()));
        params.put("terms", true);

        // when
        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/customers")
                .then().log().all()
                .extract();
    }

    private TokenResponse getTokenResponse(String email, String password) {
        ExtractableResponse<Response> response = getSignInResponse(email, password);
        return response.jsonPath().getObject(".", TokenResponse.class);
    }

    private ExtractableResponse<Response> getSignInResponse(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/customer/authentication/sign-in")
                .then().log().all()
                .extract();
        return response;
    }
}
