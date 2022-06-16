package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.auth.dto.LoginRequest;
import woowacourse.shoppingcart.auth.dto.LoginResponse;
import woowacourse.shoppingcart.customer.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.customer.dto.CustomerUpdationRequest;
import woowacourse.shoppingcart.support.AuthorizationExtractor;

@DisplayName("회원 관련 기능")
class CustomerAcceptanceTest extends AcceptanceTest {

    private static final String REQUEST_URL = "/users/me";

    @DisplayName("회원가입이 정상적으로 된 경우 상태코드 204를 반환한다.")
    @Test
    void create_right_200() {
        // given
        final CustomerCreationRequest request = new CustomerCreationRequest("kun@naver.com", "1q2w3e4r", "kun");

        // when
        final ValidatableResponse response = postUser(request);

        // then
        response.statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원 정보 양식이 잘못 되었을 때, 상태코드 400을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "kun#naver.com:12345667a:쿤aa:이메일 양식이 잘못 되었습니다.",
            "kun@naver.com:1234:쿤aa:비밀번호 양식이 잘못 되었습니다.",
            "kun@naver.com:123456677aa:쿤:닉네임 양식이 잘못 되었습니다."}, delimiter = ':')
    void create_wrongForm_400(final String email, final String password, final String nickname, final String message) {
        //given
        final CustomerCreationRequest request = new CustomerCreationRequest(email, password, nickname);

        //when
        final ValidatableResponse response = postUser(request);

        // then
        response.statusCode(HttpStatus.BAD_REQUEST.value())
                .body(ERROR_CODE, equalTo("1000"))
                .body(MESSAGE, equalTo(message));
    }

    @DisplayName("이메일이 중복 되었을 때, 상태코드 400을 반환한다.")
    @Test
    void create_duplicateEmail_400() {
        //given
        final CustomerCreationRequest request = new CustomerCreationRequest("kun@naver.com", "1q2w3e4r", "kun");

        postUser(request);

        //when
        final ValidatableResponse response = postUser(request);

        // then
        response.statusCode(HttpStatus.BAD_REQUEST.value())
                .body(ERROR_CODE, equalTo("1001"))
                .body(MESSAGE, equalTo("이메일이 중복입니다."));
    }

    @DisplayName("유효한 토큰으로 로그인한 자신의 정보를 요청한다.")
    @Test
    void getMe_validToken_200() {
        // given
        final String email = "kun@gmail.com";
        final String nickname = "kun";
        final String password = "1q2w3e4r";

        final CustomerCreationRequest signUpRequest = new CustomerCreationRequest(email, password, nickname);
        postUser(signUpRequest);

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final String accessToken = postLogin(loginRequest)
                .extract()
                .as(LoginResponse.class)
                .getAccessToken();

        // when
        final ValidatableResponse response = getMe(accessToken);

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("email", equalTo(email))
                .body("nickname", equalTo(nickname));
    }

    @DisplayName("유효한 토큰으로 회원 정보 수정을 성공한다.")
    @Test
    void updateMe() {
        // given
        final String email = "kun@email.com";
        final String password = "qwerasdf123";
        final CustomerCreationRequest createRequest = new CustomerCreationRequest(email, password, "kun");
        postUser(createRequest);

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final String accessToken = postLogin(loginRequest)
                .extract()
                .as(LoginResponse.class)
                .getAccessToken();

        final String updatedNickname = "rick";
        final CustomerUpdationRequest updateRequest = new CustomerUpdationRequest(updatedNickname, "qwerasdf321");

        // when
        final ValidatableResponse response = RestAssured
                .given().log().all()
                .header(AuthorizationExtractor.AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateRequest)
                .when().put(REQUEST_URL)
                .then().log().all();

        final ValidatableResponse updatedResponse = getMe(accessToken);

        // then
        response.statusCode(HttpStatus.NO_CONTENT.value());

        updatedResponse.body("nickname", equalTo(updatedNickname));
    }

    @DisplayName("유효한 토큰으로 회원 탈퇴에 성공한다.")
    @Test
    void deleteMe() {
        // given
        final String email = "kun@email.com";
        final String password = "qwerasdf123";
        final CustomerCreationRequest createRequest = new CustomerCreationRequest(email, password, "kun");
        postUser(createRequest);

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final String accessToken = postLogin(loginRequest)
                .extract()
                .as(LoginResponse.class)
                .getAccessToken();

        // when
        final ValidatableResponse response = RestAssured
                .given().log().all()
                .header(AuthorizationExtractor.AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + accessToken)
                .when().delete(REQUEST_URL)
                .then().log().all();

        final ValidatableResponse loginResponse = postLogin(loginRequest);

        // then
        response.statusCode(HttpStatus.NO_CONTENT.value());
        loginResponse.statusCode(HttpStatus.BAD_REQUEST.value())
                .body(ERROR_CODE, equalTo("1002"))
                .body(MESSAGE, equalTo("로그인에 실패했습니다."));
    }
}
