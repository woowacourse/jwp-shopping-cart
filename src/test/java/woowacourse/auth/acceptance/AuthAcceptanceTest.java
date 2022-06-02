package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.Fixtures.ADDRESS_VALUE_1;
import static woowacourse.Fixtures.BIRTHDAY_FORMATTED_VALUE_1;
import static woowacourse.Fixtures.CONTACT_VALUE_1;
import static woowacourse.Fixtures.CUSTOMER_REQUEST_1;
import static woowacourse.Fixtures.DETAIL_ADDRESS_VALUE_1;
import static woowacourse.Fixtures.EMAIL_VALUE_1;
import static woowacourse.Fixtures.GENDER_MALE;
import static woowacourse.Fixtures.NAME_VALUE_1;
import static woowacourse.Fixtures.PASSWORD_VALUE_1;
import static woowacourse.Fixtures.PROFILE_IMAGE_URL_VALUE_1;
import static woowacourse.Fixtures.TERMS_1;
import static woowacourse.Fixtures.ZONE_CODE_VALUE_1;

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
                        .containsExactly(EMAIL_VALUE_1, PROFILE_IMAGE_URL_VALUE_1, NAME_VALUE_1, GENDER_MALE,
                                BIRTHDAY_FORMATTED_VALUE_1, CONTACT_VALUE_1, TERMS_1)
        );
    }

    @DisplayName("로그인 성공 시 토큰을 발급한다.")
    @Test
    void login() {
        //given
        createCustomer();

        // when
        TokenResponse tokenResponse = getTokenResponse(EMAIL_VALUE_1, PASSWORD_VALUE_1);

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
        createCustomer();
        String invalidPassword = "1234!@abc";

        // when
        ExtractableResponse<Response> response = getSignInResponse(EMAIL_VALUE_1, invalidPassword);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("로그인이 실패하였습니다.")
        );
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @ValueSource(strings = {"", "abcd"})
    @ParameterizedTest
    void invalidToken(String token) {
        // given
        // 회원이 등록되어 있고
        ExtractableResponse<Response> customerResponse = createCustomer();
        String customerUri = customerResponse.header("location");

        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", token)
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
    void signOut_successWithValidToken() {
        // given
        String customerId = createCustomer().header("Location").split("/")[3];
        TokenResponse tokenResponse = getTokenResponse(EMAIL_VALUE_1, PASSWORD_VALUE_1);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .when().post("/api/customers/" + customerId + "/authentication/sign-out")
                .then().log().all().extract();
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("유효하지 않은 토큰으로 인한 로그아웃 실패")
    @Test
    void signOut_failWithMalformedToken() {
        // given
        String customerId = createCustomer().header("Location").split("/")[3];
        String malformedToken = "abcd";

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "Bearer " + malformedToken)
                .when().post("/api/customers/" + customerId + "/authentication/sign-out")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("토큰에 명시된 ID와 URI에 명시된 ID가 다를때 로그아웃 실패")
    @Test
    void signOut_IfIdIsDifferentBetweenTokenAndPathVariable() {
        // given
        String customerId = createCustomer().header("Location").split("/")[3];
        TokenResponse tokenResponse = getTokenResponse(EMAIL_VALUE_1, PASSWORD_VALUE_1);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .when().post("/api/customers/" + (customerId + 1) + "/authentication/sign-out")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    private ExtractableResponse<Response> createCustomer() {
        Map<String, Object> params = new HashMap<>();
        params.put("email", EMAIL_VALUE_1);
        params.put("password", PASSWORD_VALUE_1);
        params.put("profileImageUrl", PROFILE_IMAGE_URL_VALUE_1);
        params.put("name", NAME_VALUE_1);
        params.put("gender", GENDER_MALE);
        params.put("birthday", BIRTHDAY_FORMATTED_VALUE_1);
        params.put("contact", CONTACT_VALUE_1);
        params.put("fullAddress",
                Map.of("address", ADDRESS_VALUE_1, "detailAddress", DETAIL_ADDRESS_VALUE_1, "zoneCode",
                        ZONE_CODE_VALUE_1));
        params.put("terms", TERMS_1);

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
