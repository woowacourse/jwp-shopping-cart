package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.Fixtures.CUSTOMER_REQUEST_1;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.AcceptanceTest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        // 회원이 등록되어 있고
        // id, password를 사용해 토큰을 발급받고
        String customerUrl = createCustomer().header("Location");
        TokenResponse tokenResponse = getTokenResponse(CUSTOMER_REQUEST_1.getEmail(), CUSTOMER_REQUEST_1.getPassword());

        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .when().get(customerUrl)
                .then().log().all().extract();

        // then
        // 내 정보가 조회된다
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.body().jsonPath().getObject(".", CustomerResponse.class))
                        .extracting("email", "profileImageUrl", "name", "gender", "birthday", "contact", "terms")
                        .containsExactly(CUSTOMER_REQUEST_1.getEmail(), CUSTOMER_REQUEST_1.getProfileImageUrl(),
                                CUSTOMER_REQUEST_1.getName(), CUSTOMER_REQUEST_1.getGender(),
                                CUSTOMER_REQUEST_1.getBirthday(), CUSTOMER_REQUEST_1.getContact(),
                                CUSTOMER_REQUEST_1.isTerms())
        );
    }

    @DisplayName("로그인 성공 시 토큰을 발급한다.")
    @Test
    void login() {
        //given
        createCustomer();

        // when
        TokenResponse tokenResponse = getTokenResponse(CUSTOMER_REQUEST_1.getEmail(), CUSTOMER_REQUEST_1.getPassword());

        //then
        assertAll(
                () -> assertThat(tokenResponse.getAccessToken()).isNotBlank(),
                () -> assertThat(tokenResponse.getCustomerId()).isPositive()
        );
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void login_failed() {
        // given
        // 회원이 등록되어 있고
        createCustomer();

        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면
        ExtractableResponse<Response> response = getSignInResponse(CUSTOMER_REQUEST_1.getEmail(), "1234!@abc");

        // then
        // 토큰 발급 요청이 거부된다
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("로그인이 실패하였습니다.")
        );
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void invalidToken() {
        // given
        // 회원이 등록되어 있고
        ExtractableResponse<Response> customerResponse = createCustomer();
        String customerUri = customerResponse.header("location");

        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "invalidToken")
                .when().get(customerUri)
                .then().log().all()
                .extract();

        // then
        // 내 정보 조회 요청이 거부된다
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("Bearer Auth 만료된 토큰")
    @Test
    void expiredToken() {
        // given
        // 회원이 등록되어 있고
        ExtractableResponse<Response> customerResponse = createCustomer();
        String customerUri = customerResponse.header("Location");

        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization",
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjU0MDExOTk1LCJleHAiOjE2NTQwMTE5OTV9.L5pnN2Dorp20abb75HFXbYTLxhfFqP4pSfUFu5Rqyzs")
                .when().get(customerUri)
                .then().log().all()
                .extract();

        // then
        // 내 정보 조회 요청이 거부된다
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("로그아웃 성공")
    @Test
    void logOut() {
        //given
        ExtractableResponse<Response> customerResponse = createCustomer();
        String customerUri = customerResponse.header("Location");

        // when
        TokenResponse tokenResponse = getTokenResponse(CUSTOMER_REQUEST_1.getEmail(), CUSTOMER_REQUEST_1.getPassword());

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .when().post(customerUri + "/authentication/sign-out")
                .then().log().all()
                .extract();
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("로그아웃 실패 - 만료된 토큰")
    @Test
    void logOutFailedByExpiredToken() {
        //given
        ExtractableResponse<Response> customerResponse = createCustomer();
        String customerUri = customerResponse.header("Location");

        // when
        TokenResponse tokenResponse = getTokenResponse(CUSTOMER_REQUEST_1.getEmail(), CUSTOMER_REQUEST_1.getPassword());

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization",
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjU0MDExOTk1LCJleHAiOjE2NTQwMTE5OTV9.L5pnN2Dorp20abb75HFXbYTLxhfFqP4pSfUFu5Rqyzs")
                .when().post(customerUri + "/authentication/sign-out")
                .then().log().all()
                .extract();
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("로그아웃 실패 - 유효하지 않은 토큰")
    @Test
    void logOutFailedByInvalidToken() {
        //given
        ExtractableResponse<Response> customerResponse = createCustomer();
        String customerUri = customerResponse.header("Location");

        // when
        TokenResponse tokenResponse = getTokenResponse(CUSTOMER_REQUEST_1.getEmail(), CUSTOMER_REQUEST_1.getPassword());

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "invalidToken")
                .when().post(customerUri + "/authentication/sign-out")
                .then().log().all()
                .extract();
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }



    private ExtractableResponse<Response> createCustomer() {
        Map<String, Object> params = new HashMap<>();
        params.put("email", CUSTOMER_REQUEST_1.getEmail());
        params.put("password", CUSTOMER_REQUEST_1.getPassword());
        params.put("profileImageUrl", CUSTOMER_REQUEST_1.getProfileImageUrl());
        params.put("name", CUSTOMER_REQUEST_1.getName());
        params.put("gender", CUSTOMER_REQUEST_1.getGender());
        params.put("birthday", CUSTOMER_REQUEST_1.getBirthday());
        params.put("contact", CUSTOMER_REQUEST_1.getContact());
        params.put("fullAddress", Map.of("address", CUSTOMER_REQUEST_1.getFullAddress().getAddress(), "detailAddress",
                CUSTOMER_REQUEST_1.getFullAddress().getDetailAddress(), "zoneCode",
                CUSTOMER_REQUEST_1.getFullAddress().getZoneCode()));
        params.put("terms", true);

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

        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/customer/authentication/sign-in")
                .then().log().all()
                .extract();
    }
}
