package woowacourse.auth.acceptance;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("로그인 성공")
    @Test
    void login() {
        // given
        String email = "email@email.com";
        String password = "1q2w3e4r";

        CustomerCreationRequest signUpRequest = new CustomerCreationRequest(email, password, "kun");
        postUser(signUpRequest);

        TokenRequest request = new TokenRequest(email, password);

        // when
        ValidatableResponse response = postLogin(request);

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("accessToken", Matchers.notNullValue(String.class));
    }

    @DisplayName("로그인 양식이 잘못 되었을 때, 상태코드 400을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "kun#naver.com:12345667a:이메일 양식이 잘못 되었습니다.",
            "kun@naver.com:1234:비밀번호 양식이 잘못 되었습니다."}, delimiter = ':')
    void login_wrongForm_400(String email, String password, String message) {

        // when
        final TokenRequest request = new TokenRequest(email, password);
        ValidatableResponse response = postLogin(request);

        // then
        response.statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errorCode", equalTo("1000"))
                .body("message", equalTo(message));
    }

    @DisplayName("비밀번호가 일치하지 않을 경우, 상태코드 400을 반환한다.")
    @Test
    void login_wrongPassword_400() {
        // given
        String email = "kun@email.com";
        CustomerCreationRequest request = new CustomerCreationRequest(email, "123456qwer", "kun");
        postUser(request);

        // when
        TokenRequest tokenRequest = new TokenRequest(email, "qwer123456");
        ValidatableResponse response = postLogin(tokenRequest);

        // then
        response.statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errorCode", equalTo("1002"))
                .body("message", equalTo("로그인에 실패했습니다."));
    }

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        // 회원이 등록되어 있고
        // id, password를 사용해 토큰을 발급받고

        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보가 조회된다
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        // 회원이 등록되어 있고

        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면

        // then
        // 토큰 발급 요청이 거부된다
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보 조회 요청이 거부된다
    }
}
