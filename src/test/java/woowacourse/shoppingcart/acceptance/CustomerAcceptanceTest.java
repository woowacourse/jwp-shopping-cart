package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static woowacourse.auth.support.AuthorizationExtractor.AUTHORIZATION;
import static woowacourse.auth.support.AuthorizationExtractor.BEARER_TYPE;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.request.ModifiedCustomerRequest;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.response.DuplicateEmailResponse;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        ExtractableResponse<Response> response = 회원_가입(
                회원_정보("example@example.com", "example123!", "http://gravatar.com/avatar/1?d=identicon",
                        "희봉", "male", "1998-08-07", "12345678910", "address", "detailAddress", "12345", true
                ));
        assertEquals(response.response().statusCode(), HttpStatus.CREATED.value());
    }


    @DisplayName("회원가입시 이메일 중복 여부를 검사한다.")
    @Test
    void isDuplicatedEmail() {
        회원_가입(회원_정보("example@example.com", "example123!", "http://gravatar.com/avatar/1?d=identicon",
                "희봉", "male", "1998-08-07", "12345678910",
                "address", "detailAddress", "12345", true));
        ExtractableResponse<Response> response = RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/validation?email=example@example.com")
                .then()
                .log().all()
                .extract();

        boolean isDuplicated = response.body()
                .jsonPath()
                .getObject("", DuplicateEmailResponse.class)
                .getIsDuplicated();
        assertThat(isDuplicated).isTrue();
    }

    @DisplayName("내 정보를 조회하려할 때")
    @Nested
    class findMyProfileTest {

        @DisplayName("본인이 맞으면 내 정보 조회")
        @Test
        void succeedTofindMyProfile() {
            회원_가입(회원_정보("example@example.com", "example123!", "http://gravatar.com/avatar/1?d=identicon",
                    "희봉", "male", "1998-08-07", "12345678910",
                    "address", "detailAddress", "12345", true));

            TokenResponse signInResponse =
                    로그인_후_토큰_발급(로그인_정보("example@example.com", "example123!"));

            ExtractableResponse<Response> response = 회원_조회(signInResponse.getAccessToken(),
                    signInResponse.getUserId());
            final CustomerResponse customerResponse = response.body()
                    .jsonPath()
                    .getObject("", CustomerResponse.class);

            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(customerResponse.getEmail()).isEqualTo("example@example.com")
            );
        }

        @DisplayName("본인이 아니면 에러를 던진다.")
        @Test
        void failToFindMyProfile() {
            ExtractableResponse<Response> response = 회원_조회(
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
                    1L);
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        회원_가입(회원_정보("example@example.com", "example123!", "http://gravatar.com/avatar/1?d=identicon",
                "희봉", "male", "1998-08-07", "12345678910",
                "address", "detailAddress", "12345", true));

        TokenResponse signInResponse =
                로그인_후_토큰_발급(로그인_정보("example@example.com", "example123!"));

        final ModifiedCustomerRequest request = 회원_수정_정보("example@example.com", "example123!",
                "http://gravatar.com/avatar/1?d=identicon",
                "수달", "male", "1998-08-07", "12345678910",
                "address", "detailAddress", "12345", true);

        ExtractableResponse<Response> response = RestAssured.given()
                .log().all()
                .header("authorization", "Bearer " + signInResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put("/api/customers/" + signInResponse.getUserId())
                .then()
                .log().all()
                .extract();

        ExtractableResponse<Response> updatedCustomerInformation = 회원_조회(signInResponse.getAccessToken(),
                signInResponse.getUserId());
        final CustomerResponse customerResponse = updatedCustomerInformation.body()
                .jsonPath()
                .getObject("", CustomerResponse.class);

        assertThat(customerResponse.getName()).isEqualTo(request.getName());
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        회원_가입(회원_정보("example@example.com", "example123!", "http://gravatar.com/avatar/1?d=identicon",
                "희봉", "male", "1998-08-07", "12345678910",
                "address", "detailAddress", "12345", true));

        TokenResponse signInResponse =
                로그인_후_토큰_발급(로그인_정보("example@example.com", "example123!"));
        회원_탈퇴(signInResponse.getAccessToken(), signInResponse.getUserId());
        ExtractableResponse<Response> response = 회원_조회(signInResponse.getAccessToken(), signInResponse.getUserId());
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private ExtractableResponse<Response> 회원_탈퇴(final String accessToken, final Long customerId) {
        return RestAssured.given()
                .log().all()
                .header(AUTHORIZATION, BEARER_TYPE + accessToken)
                .when()
                .delete("/api/customers/" + customerId)
                .then()
                .log().all()
                .extract();
    }


    private SignUpRequest 회원_정보(String email, String password, String profileImageUrl, String name, String gender,
                                String birthday, String contact, String address, String detailAddress,
                                String zoneCode,
                                boolean terms) {
        return new SignUpRequest(email, password, profileImageUrl, name, gender, birthday, contact, address,
                detailAddress, zoneCode, terms);
    }

    private ModifiedCustomerRequest 회원_수정_정보(String email, String password, String profileImageUrl, String name,
                                             String gender,
                                             String birthday, String contact, String address, String detailAddress,
                                             String zoneCode,
                                             boolean terms) {
        return new ModifiedCustomerRequest(email, password, profileImageUrl, name, gender, birthday, contact, address,
                detailAddress, zoneCode, terms);
    }


    private ExtractableResponse<Response> 회원_가입(SignUpRequest request) {
        return RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/customers")
                .then()
                .log().all()
                .extract();
    }

    private ExtractableResponse<Response> 회원_조회(String accessToken, Long customerId) {
        return RestAssured.given()
                .log().all()
                .header(AUTHORIZATION, BEARER_TYPE + accessToken)
                .when()
                .get("/api/customers/" + customerId)
                .then()
                .log().all()
                .extract();
    }

    private TokenRequest 로그인_정보(final String email, final String password) {
        return new TokenRequest(email, password);
    }

    private TokenResponse 로그인_후_토큰_발급(TokenRequest request) {
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/customer/authentication/sign-in")
                .then().log().all()
                .extract();
        return response.body()
                .jsonPath()
                .getObject("", TokenResponse.class);
    }
}
