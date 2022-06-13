package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.Fixtures.BIRTHDAY_FORMATTED_VALUE_1;
import static woowacourse.Fixtures.CONTACT_VALUE_1;
import static woowacourse.Fixtures.CUSTOMER_INVALID_REQUEST_1;
import static woowacourse.Fixtures.CUSTOMER_REQUEST_1;
import static woowacourse.Fixtures.CUSTOMER_REQUEST_2;
import static woowacourse.Fixtures.EMAIL_VALUE_1;
import static woowacourse.Fixtures.EXPIRED_TOKEN;
import static woowacourse.Fixtures.GENDER_MALE;
import static woowacourse.Fixtures.NAME_VALUE_1;
import static woowacourse.Fixtures.PASSWORD_VALUE_1;
import static woowacourse.Fixtures.PROFILE_IMAGE_URL_VALUE_1;
import static woowacourse.Fixtures.TERMS_1;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.AcceptanceTest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;

@DisplayName("회원 기능 인수 테스트")
@Sql("classpath:schema.sql")
public class CustomerAcceptanceTest extends AcceptanceTest {
    @Test
    @DisplayName("이메일 중복 검사")
    void validateEmailDuplication() {
        // given
        회원가입_요청(CUSTOMER_REQUEST_1);

        // when
        ExtractableResponse<Response> response = 이메일_중복_체크_요청(EMAIL_VALUE_1);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getBoolean("isDuplicated")).isTrue()
        );
    }

    private ExtractableResponse<Response> 회원가입_요청(CustomerRequest customerRequest) {
        return RestAssured.given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/customers")
                .then().log().all()
                .extract();
    }

    private int 회원가입_요청_후_사용자_ID_반환(CustomerRequest customerRequest) {
        ExtractableResponse<Response> response = 회원가입_요청(customerRequest);
        return Integer.parseInt(response.header("Location").split("/")[3]);
    }

    private ExtractableResponse<Response> 로그인_요청(String email, String password) {
        return RestAssured.given().log().all()
                .body(new TokenRequest(email, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/customer/authentication/sign-in")
                .then().log().all()
                .extract();
    }

    private TokenResponse 로그인_요청_후_토큰_DTO_반환(String email, String password) {
        ExtractableResponse<Response> response = 로그인_요청(email, password);
        return response.jsonPath().getObject(".", TokenResponse.class);
    }

    private ExtractableResponse<Response> 이메일_중복_체크_요청(String email) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/validation?email=" + email)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 사용자_정보_조회_요청(int customerId, String accessToken) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when().get("/api/customers/" + customerId)
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> 사용자_정보_수정_요청(int customerId, String accessToken,
                                                       CustomerRequest customerRequest) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerRequest)
                .when().put("/api/customers/" + customerId)
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> 회원탈퇴_요청(int customerId, String accessToken) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when().delete("/api/customers/" + customerId)
                .then().log().all().extract();
    }

    private void 만료된_토큰인지_확인(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    private void 유효하지_않은_토큰인지_확인(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("회원가입")
    @Nested
    class join extends AcceptanceTest {
        @Test
        @DisplayName("성공")
        void success() {
            // given
            ExtractableResponse<Response> response = 회원가입_요청(CUSTOMER_REQUEST_1);

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                    () -> assertThat(response.header("location")).isNotNull()
            );
        }

        @Test
        @DisplayName("실패 - 잘못된 요청")
        void failed() {
            // given
            ExtractableResponse<Response> response = 회원가입_요청(CUSTOMER_INVALID_REQUEST_1);

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                    () -> assertThat(response.jsonPath().getString("message")).isNotNull()
            );
        }
    }

    @DisplayName("내 정보 조회")
    @Nested
    class getMe extends AcceptanceTest {
        private int 생성된_사용자_ID;

        @Override
        @BeforeEach
        public void setUp() {
            super.setUp();
            생성된_사용자_ID = 회원가입_요청_후_사용자_ID_반환(CUSTOMER_REQUEST_1);
        }

        @DisplayName("성공")
        @Test
        void success() {
            // given
            TokenResponse tokenResponse = 로그인_요청_후_토큰_DTO_반환(EMAIL_VALUE_1, PASSWORD_VALUE_1);

            // when
            ExtractableResponse<Response> response = 사용자_정보_조회_요청(생성된_사용자_ID, tokenResponse.getAccessToken());
            // then

            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(response.body().jsonPath().getObject(".", CustomerResponse.class))
                            .extracting("email", "profileImageUrl", "name", "gender", "birthday", "contact", "terms")
                            .containsExactly(EMAIL_VALUE_1, PROFILE_IMAGE_URL_VALUE_1, NAME_VALUE_1, GENDER_MALE,
                                    BIRTHDAY_FORMATTED_VALUE_1, CONTACT_VALUE_1, TERMS_1)
            );
        }

        @DisplayName("실패 - 유효하지 않은 토큰")
        @ValueSource(strings = {"", "abcd"})
        @ParameterizedTest
        void failedWithInvalidToken(String 유효하지_않은_토큰) {
            // when
            ExtractableResponse<Response> response = 사용자_정보_조회_요청(생성된_사용자_ID, 유효하지_않은_토큰);

            // then
            유효하지_않은_토큰인지_확인(response);
        }

        @DisplayName("실패 - 만료된 토큰")
        @Test
        void failedWithExpiredToken() {
            // when
            ExtractableResponse<Response> response = 사용자_정보_조회_요청(생성된_사용자_ID, EXPIRED_TOKEN);

            // then
            만료된_토큰인지_확인(response);
        }
    }

    @DisplayName("내 정보 수정")
    @Nested
    class updateMe extends AcceptanceTest {
        private int 생성된_사용자_ID;

        @Override
        @BeforeEach
        public void setUp() {
            super.setUp();
            생성된_사용자_ID = 회원가입_요청_후_사용자_ID_반환(CUSTOMER_REQUEST_1);
        }

        @DisplayName("성공")
        @Test
        void success() {
            // given
            TokenResponse tokenResponse = 로그인_요청_후_토큰_DTO_반환(EMAIL_VALUE_1, PASSWORD_VALUE_1);

            // when
            ExtractableResponse<Response> response = 사용자_정보_수정_요청(생성된_사용자_ID, tokenResponse.getAccessToken(),
                    CUSTOMER_REQUEST_2);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        }

        @DisplayName("실패 - 유효하지 않은 토큰")
        @ValueSource(strings = {"", "abcd"})
        @ParameterizedTest
        void failedWithInvalidToken(String 유효하지_않은_토큰) {
            // when
            ExtractableResponse<Response> response = 사용자_정보_수정_요청(생성된_사용자_ID, 유효하지_않은_토큰, CUSTOMER_REQUEST_2);

            // then
            유효하지_않은_토큰인지_확인(response);
        }

        @DisplayName("실패 - 만료된 토큰")
        @Test
        void failedWithExpiredToken() {
            // when
            ExtractableResponse<Response> response = 사용자_정보_수정_요청(생성된_사용자_ID, EXPIRED_TOKEN, CUSTOMER_REQUEST_2);

            // then
            만료된_토큰인지_확인(response);
        }
    }

    @DisplayName("내 정보 제거")
    @Nested
    class deleteMe extends AcceptanceTest {
        private int 생성된_사용자_ID;

        @Override
        @BeforeEach
        public void setUp() {
            super.setUp();
            생성된_사용자_ID = 회원가입_요청_후_사용자_ID_반환(CUSTOMER_REQUEST_1);
        }

        @DisplayName("성공")
        @Test
        void success() {
            // given
            TokenResponse tokenResponse = 로그인_요청_후_토큰_DTO_반환(EMAIL_VALUE_1, PASSWORD_VALUE_1);

            // when
            ExtractableResponse<Response> response = 회원탈퇴_요청(생성된_사용자_ID, tokenResponse.getAccessToken());

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        }

        @DisplayName("실패 - 유효하지 않은 토큰")
        @ValueSource(strings = {"", "abcd"})
        @ParameterizedTest
        void failedWithInvalidToken(String 유효하지_않은_토큰) {
            // when

            ExtractableResponse<Response> response = 회원탈퇴_요청(생성된_사용자_ID, 유효하지_않은_토큰);

            // then
            유효하지_않은_토큰인지_확인(response);
        }

        @DisplayName("실패 - 만료된 토큰")
        @Test
        void failedWithExpiredToken() {
            // when
            ExtractableResponse<Response> response = 회원탈퇴_요청(생성된_사용자_ID, EXPIRED_TOKEN);

            // then
            만료된_토큰인지_확인(response);
        }
    }
}
