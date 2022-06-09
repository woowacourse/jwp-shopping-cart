package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.PasswordFixture.plainBasicPassword;
import static woowacourse.fixture.PasswordFixture.plainReversePassword;
import static woowacourse.fixture.TokenFixture.BEARER;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.request.CustomerUserNameRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.response.DuplicateResponse;
import woowacourse.shoppingcart.dto.response.ErrorResponse;

@DisplayName("회원 관련 기능")
@SuppressWarnings("NonAsciiCharacters")
public class CustomerAcceptanceTest extends AcceptanceShoppingCartTest {

    @DisplayName("회원가입 성공하면 201 CREATED를 반환한다.")
    @Test
    void signUpCustomer() {
        // given
        CustomerRequest signUpRequest = new CustomerRequest("giron", plainBasicPassword);

        // when
        ExtractableResponse<Response> response = 회원가입_요청(signUpRequest);

        // then
        Long id = Long.parseLong(response.header("Location").split("/")[3]);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(id).isNotNull()
        );
    }

    @DisplayName("회원 가입할 때 패스워드가 잘못된 경우 400-BAD_REQUEST를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullSource
    void signUpWithShortPassword(String password) {
        // given
        CustomerRequest signUpRequest = new CustomerRequest("giron", password);

        // when
        ExtractableResponse<Response> response = 회원가입_요청(signUpRequest);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("비밀번호는 빈칸일 수 없습니다.")
        );
    }

    @DisplayName("회원 가입할 때 유저 이름이 잘못된 경우 400-BAD_REQUEST를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullSource
    void signUpWithWrongUserName(String userName) {
        // given
        CustomerRequest signUpRequest = new CustomerRequest(userName, plainBasicPassword);

        // when
        ExtractableResponse<Response> response = 회원가입_요청(signUpRequest);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("유저 이름은 빈칸일 수 없습니다.")
        );
    }

    @DisplayName("로그인 한 유저가 내 정보 조회하면 200 OK를 반환한다.")
    @Test
    void getMe() {
        // given
        CustomerRequest signUpRequest = new CustomerRequest("giron", plainBasicPassword);
        회원가입_요청(signUpRequest);

        TokenRequest tokenRequest = new TokenRequest("giron", plainBasicPassword);
        TokenResponse tokenResponse = 로그인_요청_토큰_생성됨(tokenRequest);

        // when
        ExtractableResponse<Response> extractableCustomerResponse = 내_정보_조회_요청(tokenResponse);
        CustomerResponse customerResponse = extractableCustomerResponse.as(CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(extractableCustomerResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(customerResponse.getUserName()).isEqualTo("giron")
        );
    }

    @DisplayName("로그인 한 유저가 내 정보 수정하면 200 OK를 반환한다.")
    @Test
    void updateMe() {
        // given
        CustomerRequest signUpRequest = new CustomerRequest("giron", plainBasicPassword);
        회원가입_요청(signUpRequest);

        TokenRequest tokenRequest = new TokenRequest("giron", plainBasicPassword);
        TokenResponse tokenResponse = 로그인_요청_토큰_생성됨(tokenRequest);

        // when
        CustomerRequest request = new CustomerRequest("giron", plainReversePassword);
        ExtractableResponse<Response> extractableUpdateResponse = 내_정보_수정_요청(tokenResponse, request);
        CustomerResponse response = extractableUpdateResponse.as(CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(extractableUpdateResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.getUserName()).isEqualTo("giron")
        );
    }

    @DisplayName("로그인 한 유저가 회원탈퇴를 하면 204 NO_CONTENT를 반환한다.")
    @Test
    void deleteMe() {
        // given
        CustomerRequest signUpRequest = new CustomerRequest("giron", plainBasicPassword);
        회원가입_요청(signUpRequest);

        TokenRequest tokenRequest = new TokenRequest("giron", plainBasicPassword);
        TokenResponse tokenResponse = 로그인_요청_토큰_생성됨(tokenRequest);

        // when
        ExtractableResponse<Response> extractableDeleteResponse = 회원탈퇴_요청(tokenResponse);
        ExtractableResponse<Response> extractableCustomerResponse = 내_정보_조회_요청(tokenResponse);

        // then
        assertAll(
                () -> assertThat(extractableDeleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(extractableCustomerResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value())
        );
    }

    @DisplayName("유효하지않은 토큰을 담아서 요청을 보내면 401-UNAUTHORIZED를 반환한다.")
    @Test
    void getMeWithInvalidToken() {
        // given
        TokenResponse tokenResponse = new TokenResponse("invalidToken");

        // when
        ExtractableResponse<Response> extractableCustomerResponse = 내_정보_조회_요청(tokenResponse);

        // then
        assertThat(extractableCustomerResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("중복된 이름이 있는지 확인하여 200-OK를 반환한다.")
    @Test
    void isDuplicatedUserName() {
        // given
        CustomerRequest signUpRequest = new CustomerRequest("giron", plainBasicPassword);
        회원가입_요청(signUpRequest);

        // when
        CustomerUserNameRequest request = new CustomerUserNameRequest("giron");
        ExtractableResponse<Response> extractableDuplicateResponse = 유저_중복_검사_요청(request);
        DuplicateResponse duplicateResponse = extractableDuplicateResponse.as(DuplicateResponse.class);

        // then
        assertAll(
                () -> assertThat(extractableDuplicateResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(duplicateResponse.getIsDuplicate()).isTrue()
        );
    }

    private ExtractableResponse<Response> 내_정보_조회_요청(final TokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, BEARER + tokenResponse.getAccessToken())
                .when().get("/api/customers/me")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 내_정보_수정_요청(
            final TokenResponse tokenResponse, final CustomerRequest request) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, BEARER + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/api/customers/me")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 회원탈퇴_요청(final TokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, BEARER + tokenResponse.getAccessToken())
                .when().delete("/api/customers/me")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 유저_중복_검사_요청(final CustomerUserNameRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/exists?userName=" + request.getUserName())
                .then().log().all()
                .extract();
    }
}
