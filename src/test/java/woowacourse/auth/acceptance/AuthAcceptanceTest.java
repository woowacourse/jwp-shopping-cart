package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.Fixtures.BIRTHDAY_FORMATTED_VALUE_1;
import static woowacourse.Fixtures.CONTACT_VALUE_1;
import static woowacourse.Fixtures.CUSTOMER_REQUEST_1;
import static woowacourse.Fixtures.EMAIL_VALUE_1;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.AcceptanceTest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {
    private int 생성된_사용자_ID;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        생성된_사용자_ID = 회원가입_요청_후_생성된_아이디_반환(CUSTOMER_REQUEST_1);
    }

    @DisplayName("로그인 후 사용자 정보 조회 요청")
    @Test
    void myInfoWithBearerAuth() {
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

    @DisplayName("로그인 성공 시 토큰을 발급한다.")
    @Test
    void login() {
        // when
        TokenResponse tokenResponse = 로그인_요청_후_토큰_DTO_반환(EMAIL_VALUE_1, PASSWORD_VALUE_1);

        // then
        assertAll(
                () -> assertThat(tokenResponse.getAccessToken()).isNotBlank(),
                () -> assertThat(tokenResponse.getCustomerId()).isPositive()
        );
    }

    @DisplayName("잘못된 비밀번호를 전달하면 토큰 생성에 실패한다.")
    @Test
    void login_failed() {
        // given
        String 잘못된_비밀번호 = "1234!@abc";

        // when
        ExtractableResponse<Response> response = 로그인_요청(EMAIL_VALUE_1, 잘못된_비밀번호);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("이메일 혹은 패스워드가 잘못되어 로그인에 실패하였습니다.")
        );
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @ValueSource(strings = {"", "abcd"})
    @ParameterizedTest
    void invalidToken(String token) {
        // when
        ExtractableResponse<Response> response = 사용자_정보_조회_요청(생성된_사용자_ID, token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("Bearer Auth 만료된 토큰")
    @Test
    void expiredToken() {
        // given
        String 만료된_토큰 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjU0MDExOTk1LCJleHAiOjE2NTQwMTE5OTV9.L5pnN2Dorp20abb75HFXbYTLxhfFqP4pSfUFu5Rqyzs";

        // when
        ExtractableResponse<Response> response = 사용자_정보_조회_요청(생성된_사용자_ID, 만료된_토큰);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("로그아웃 성공")
    @Test
    void signOut_successWithValidToken() {
        // given
        TokenResponse tokenResponse = 로그인_요청_후_토큰_DTO_반환(EMAIL_VALUE_1, PASSWORD_VALUE_1);

        // when
        ExtractableResponse<Response> response = 로그아웃_요청(생성된_사용자_ID, tokenResponse.getAccessToken());

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("유효하지 않은 토큰으로 인한 로그아웃 실패")
    @Test
    void signOut_failWithMalformedToken() {
        // given
        String 유효하지_않은_토큰 = "abcd";

        // when
        ExtractableResponse<Response> response = 로그아웃_요청(생성된_사용자_ID, 유효하지_않은_토큰);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("토큰에 명시된 ID와 URI에 명시된 ID가 다를때 로그아웃 실패")
    @Test
    void signOut_IfIdIsDifferentBetweenTokenAndPathVariable() {
        // given
        TokenResponse tokenResponse = 로그인_요청_후_토큰_DTO_반환(EMAIL_VALUE_1, PASSWORD_VALUE_1);

        // when
        ExtractableResponse<Response> response = 로그아웃_요청((생성된_사용자_ID + 1), tokenResponse.getAccessToken());

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    private int 회원가입_요청_후_생성된_아이디_반환(CustomerRequest customerRequest) {
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/customers")
                .then().log().all()
                .extract();

        return Integer.parseInt(response.header("Location").split("/")[3]);
    }

    private ExtractableResponse<Response> 로그인_요청(String email, String password) {
        return RestAssured.given().log().all()
                .body(new TokenRequest(email, password)).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/customer/authentication/sign-in")
                .then().log().all()
                .extract();
    }

    private TokenResponse 로그인_요청_후_토큰_DTO_반환(String email, String password) {
        return 로그인_요청(email, password).jsonPath().getObject(".", TokenResponse.class);
    }

    private ExtractableResponse<Response> 사용자_정보_조회_요청(int customerId, String accessToken) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when().get("/api/customers/" + customerId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 로그아웃_요청(int customerId, String accessToken) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when().post("/api/customers/" + customerId + "/authentication/sign-out")
                .then().log().all().extract();
    }
}
