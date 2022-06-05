package woowacourse.auth.acceptance;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("로그인")
    class Describe_로그인 {
        String email = "kun@gmail.com";
        String nickname = "kun";
        String password = "1q2w3e4r";
        CustomerCreationRequest signUpRequest = new CustomerCreationRequest(email, password, nickname);
        ValidatableResponse response = postUser(signUpRequest);

        TokenRequest tokenRequest = new TokenRequest(email, password);
        TokenRequest invalidEmailRequest = new TokenRequest("email@email.com", password);
        TokenRequest invalidPasswordRequest = new TokenRequest(email, "qwerasdf1");

        @Nested
        @DisplayName("이메일과 비밀번호를 알맞게 작성한다면")
        class Context_validateEmailPassword extends AcceptanceTest {
            ValidatableResponse response = postLogin(tokenRequest);

            @Test
            @DisplayName("상태코드 200과 토큰을 전달받는다.")
            void it_return_email_nickname() {
                response.statusCode(HttpStatus.OK.value())
                        .body("accessToken", Matchers.notNullValue(String.class));
            }
        }

        @Nested
        @DisplayName("이메일을 알맞게 작성하지 않으면")
        class Context_InvalidEmail extends AcceptanceTest {
            ValidatableResponse response = postLogin(invalidEmailRequest);

            @Test
            @DisplayName("로그인에 실패하고 상태코드 400을 전달받는다.")
            void it_return_401() {
                response.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("errorCode", equalTo("1002"))
                        .body("message", equalTo("로그인에 실패했습니다."));
            }
        }

        @Nested
        @DisplayName("비밀번호를 알맞게 작성하지 않으면")
        class Context_invalidPassword extends AcceptanceTest {
            ValidatableResponse response = postLogin(invalidPasswordRequest);

            @Test
            @DisplayName("로그인에 실패하고 상태코드 400을 전달받는다.")
            void it_return_401() {
                response.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("errorCode", equalTo("1002"))
                        .body("message", equalTo("로그인에 실패했습니다."));
            }
        }
    }
}
