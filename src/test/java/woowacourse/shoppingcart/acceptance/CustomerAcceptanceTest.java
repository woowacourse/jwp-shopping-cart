package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입이 정상적으로 된 경우 상태코드 200을 반환한다.")
    @Test
    void create_right_200() {
        // given
        CustomerCreationRequest request = new CustomerCreationRequest("kun@naver.com", "1q2w3e4r", "kun");

        // when
        ValidatableResponse response = postUser(request);

        // then
        response.statusCode(HttpStatus.OK.value());
    }

    @DisplayName("회원 정보 양식이 잘못 되었을 때, 상태코드 400을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "kun#naver.com:12345667a:쿤aa:이메일 양식이 잘못 되었습니다.",
            "kun@naver.com:1234:쿤aa:비밀번호 양식이 잘못 되었습니다.",
            "kun@naver.com:123456677aa:쿤:닉네임 양식이 잘못 되었습니다."}, delimiter = ':')
    void create_wrongForm_400(String email, String password, String nickname, String message) {
        //given
        CustomerCreationRequest request = new CustomerCreationRequest(email, password, nickname);

        //when
        ValidatableResponse response = postUser(request);

        // then
        response.statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errorCode", equalTo("1000"))
                .body("message", equalTo(message));
    }

    @DisplayName("이메일이 중복 되었을 때, 상태코드 400을 반환한다.")
    @Test
    void create_duplicateEmail_400() {
        //given
        CustomerCreationRequest request = new CustomerCreationRequest("kun@naver.com", "1q2w3e4r", "kun");

        postUser(request);

        //when
        ValidatableResponse response = postUser(request);

        // then
        response.statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errorCode", equalTo("1001"))
                .body("message", equalTo("이메일이 중복입니다."));
    }

    @DisplayName("유효한 토큰으로 로그인한 자신의 정보를 요청한다.")
    @Test
    void getMe_validToken_200() {
        // given
        String email = "kun@gmail.com";
        String nickname = "kun";
        String password = "1q2w3e4r";

        CustomerCreationRequest signUpRequest = new CustomerCreationRequest(email, password, nickname);
        postUser(signUpRequest);

        TokenRequest tokenRequest = new TokenRequest(email, password);
        String accessToken = postLogin(tokenRequest)
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();

        // when
        ValidatableResponse response = RestAssured
                .given().log().all()
                .header(AuthorizationExtractor.AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + accessToken)
                .when().get("/users/me")
                .then().log().all();

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("email", equalTo(email))
                .body("nickname", equalTo(nickname));
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
    }

    @DisplayName("유효한 토큰으로 회원 탈퇴에 성공한다.")
    @Test
    void deleteMe() {
        // given
        String email = "kun@email.com";
        String password = "qwerasdf123";
        CustomerCreationRequest createRequest = new CustomerCreationRequest(email, password, "kun");
        postUser(createRequest);

        TokenRequest tokenRequest = new TokenRequest(email, password);
        String accessToken = postLogin(tokenRequest)
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();

        // when
        ValidatableResponse response = RestAssured
                .given().log().all()
                .header(AuthorizationExtractor.AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + accessToken)
                .when().delete("/users")
                .then().log().all();

        ValidatableResponse loginResponse = postLogin(tokenRequest);

        // then
        response.statusCode(HttpStatus.OK.value());
        loginResponse.statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errorCode", equalTo("1002"))
                .body("message", equalTo("로그인에 실패했습니다."));
    }
}
