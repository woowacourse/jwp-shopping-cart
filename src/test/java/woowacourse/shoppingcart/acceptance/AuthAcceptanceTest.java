package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.auth.dto.LoginRequest;
import woowacourse.shoppingcart.customer.dto.CustomerCreationRequest;

@DisplayName("인증 관련 기능")
class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("로그인 성공")
    void login() {
        // given
        final String email = "email@email.com";
        final String password = "1q2w3e4r";

        final CustomerCreationRequest signUpRequest = new CustomerCreationRequest(email, password, "kun");
        postUser(signUpRequest);

        final LoginRequest request = new LoginRequest(email, password);

        // when
        final ValidatableResponse response = postLogin(request);

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("accessToken", Matchers.notNullValue(String.class));
    }

    @ParameterizedTest
    @DisplayName("로그인 양식이 잘못 되었을 때, 상태코드 400을 반환한다.")
    @CsvSource(value = {
            "kun#naver.com:12345667a:이메일 양식이 잘못 되었습니다.",
            "kun@naver.com:1234:비밀번호 양식이 잘못 되었습니다."}, delimiter = ':')
    void login_wrongForm_400(final String email, final String password, final String message) {

        // when
        final LoginRequest request = new LoginRequest(email, password);
        final ValidatableResponse response = postLogin(request);

        // then
        response.statusCode(HttpStatus.BAD_REQUEST.value())
                .body(ERROR_CODE, equalTo("1000"))
                .body(MESSAGE, equalTo(message));
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않을 경우, 상태코드 400을 반환한다.")
    void login_wrongPassword_400() {
        // given
        final String email = "kun@email.com";
        final CustomerCreationRequest request = new CustomerCreationRequest(email, "123456qwer", "kun");
        postUser(request);

        // when
        final LoginRequest loginRequest = new LoginRequest(email, "qwer123456");
        final ValidatableResponse response = postLogin(loginRequest);

        // then
        response.statusCode(HttpStatus.BAD_REQUEST.value())
                .body(ERROR_CODE, equalTo("1002"))
                .body(MESSAGE, equalTo("로그인에 실패했습니다."));
    }
}
