package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_INVALID_REQUEST_1;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_REQUEST_1;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_REQUEST_2;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.AcceptanceTest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.domain.customer.privacy.Birthday;
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

        //then
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

        //then
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

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/validation?email=" + CUSTOMER_REQUEST_1.getEmail())
                .then().log().all()
                .extract();

        //then
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
        TokenResponse tokenResponse = getTokenResponse(CUSTOMER_REQUEST_1.getEmail(), CUSTOMER_REQUEST_1.getPassword());

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
                        .containsExactly(CUSTOMER_REQUEST_1.getEmail(), CUSTOMER_REQUEST_1.getProfileImageUrl(),
                                CUSTOMER_REQUEST_1.getName(), CUSTOMER_REQUEST_1.getGender(),
                                Birthday.empty().getValue().toString(), CUSTOMER_REQUEST_1.getContact(),
                                CUSTOMER_REQUEST_1.isTerms())
        );

    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        // given
        ExtractableResponse<Response> customer = createCustomer(CUSTOMER_REQUEST_1);
        String customerUri = customer.header("Location");
        TokenResponse tokenResponse = getTokenResponse(CUSTOMER_REQUEST_1.getEmail(), CUSTOMER_REQUEST_1.getPassword());

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
        TokenResponse tokenResponse = getTokenResponse(CUSTOMER_REQUEST_1.getEmail(), CUSTOMER_REQUEST_1.getPassword());

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .when().delete(customerUri)
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static ExtractableResponse<Response> createCustomer(CustomerRequest customerRequest) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", customerRequest.getEmail());
        params.put("password", customerRequest.getPassword());
        params.put("profileImageUrl", customerRequest.getProfileImageUrl());
        params.put("name", customerRequest.getName());
        params.put("gender", customerRequest.getGender());
        params.put("birthday", customerRequest.getBirthday());
        params.put("contact", customerRequest.getContact());
        params.put("address", customerRequest.getAddress());
        params.put("detailAddress", customerRequest.getDetailAddress());
        params.put("zonecode", customerRequest.getZonecode());
        params.put("terms", true);

        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/customers")
                .then().log().all()
                .extract();
    }

    public static TokenResponse getTokenResponse(String email, String password) {
        ExtractableResponse<Response> response = getSignInResponse(email, password);
        return response.jsonPath().getObject(".", TokenResponse.class);
    }

    public static ExtractableResponse<Response> getSignInResponse(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/customer/authentication/sign-in")
                .then().log().all()
                .extract();
    }
}
