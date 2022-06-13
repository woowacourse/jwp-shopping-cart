package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static woowacourse.auth.support.AuthorizationExtractor.AUTHORIZATION;
import static woowacourse.auth.support.AuthorizationExtractor.BEARER_TYPE;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.ModifiedCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        ExtractableResponse<Response> response = 회원_가입(
                회원_정보("example@example.com", "example123!", "http://gravatar.com/avatar/1?d=identicon",
                        "희봉", "male", "1998-08-07", "12345678910", "address", "detailAddress", "12345", true
                ));
        assertEquals(response.response().statusCode(), HttpStatus.CREATED.value());
    }

    @DisplayName("로그인을 시도할 때")
    @Nested
    class SignInTest {

        @DisplayName("이메일과 비밀번호가 유효할 경우, 로그인을 시킨다.")
        @Test
        void succeedSignIn() {
            회원_가입(회원_정보("example@example.com", "example123!", "http://gravatar.com/avatar/1?d=identicon",
                    "희봉", "male", "1998-08-07", "12345678910",
                    "address", "detailAddress", "12345", true));

            TokenResponse response =
                    로그인_후_토큰_발급(로그인_정보("example@example.com", "example123!"));

            assertAll(
                    () -> assertThat(response.getAccessToken()).isNotNull(),
                    () -> assertThat(response.getCustomerId()).isNotNull()
            );
        }

        @DisplayName("이메일과 비밀번호가 유효하지 않을 경우, 로그인에 실패한다..")
        @Test
        void falseSignIn() {
            회원_가입(회원_정보("example@example.com", "example123!", "http://gravatar.com/avatar/1?d=identicon",
                    "희봉", "male", "1998-08-07", "12345678910",
                    "address", "detailAddress", "12345", true));

            ExtractableResponse<Response> response = 로그인(로그인_정보("sudal@example.com", "example123!"));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }
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
                    signInResponse.getCustomerId());
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
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
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
                .put("/api/customers")
                .then()
                .log().all()
                .extract();

        ExtractableResponse<Response> updatedCustomerInformation = 회원_조회(signInResponse.getAccessToken(),
                signInResponse.getCustomerId());
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
        회원_탈퇴(signInResponse.getAccessToken(), signInResponse.getCustomerId());
        ExtractableResponse<Response> response = 회원_조회(signInResponse.getAccessToken(), signInResponse.getCustomerId());
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private ExtractableResponse<Response> 회원_탈퇴(final String accessToken, final Long customerId) {
        return RestAssured.given()
                .log().all()
                .header(AUTHORIZATION, BEARER_TYPE + accessToken)
                .when()
                .delete("/api/customers")
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

    private ExtractableResponse<Response> 회원_조회(String accessToken, Long customerId) {
        return RestAssured.given()
                .log().all()
                .header(AUTHORIZATION, BEARER_TYPE + accessToken)
                .when()
                .get("/api/customers")
                .then()
                .log().all()
                .extract();
    }

    private TokenRequest 로그인_정보(final String email, final String password) {
        return new TokenRequest(email, password);
    }

    private ExtractableResponse<Response> 로그인(TokenRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/customer/authentication/sign-in")
                .then().log().all()
                .extract();
    }
}
