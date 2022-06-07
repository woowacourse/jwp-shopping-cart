package woowacourse.auth.acceptance;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
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
        TokenRequest tokenRequest = new TokenRequest(email, password);
        TokenRequest invalidEmailRequest = new TokenRequest("email@email.com", password);
        TokenRequest invalidPasswordRequest = new TokenRequest(email, "qwerasdf1");
        TokenRequest illegalRequest = new TokenRequest("asdf", "qwerasdf1");

        @BeforeEach
        void setUp() {
            postUser(signUpRequest);
        }

        @Nested
        @DisplayName("이메일과 비밀번호를 알맞게 작성한다면")
        class Context_validateEmailPassword extends AcceptanceTest {

            @Test
            @DisplayName("상태코드 200과 토큰을 전달받는다.")
            void it_return_email_nickname() {
                ValidatableResponse response = postLogin(tokenRequest);

                response.statusCode(HttpStatus.OK.value())
                        .body("accessToken", Matchers.notNullValue(String.class));
            }
        }

        @Nested
        @DisplayName("이메일을 알맞게 작성하지 않으면")
        class Context_InvalidEmail extends AcceptanceTest {

            @Test
            @DisplayName("로그인에 실패하고 상태코드 400을 전달받는다.")
            void it_return_401() {
                ValidatableResponse response = postLogin(invalidEmailRequest);

                response.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("errorCode", equalTo("1002"))
                        .body("message", equalTo("로그인에 실패했습니다."));
            }
        }

        @Nested
        @DisplayName("비밀번호를 알맞게 작성하지 않으면")
        class Context_invalidPassword extends AcceptanceTest {

            @Test
            @DisplayName("로그인에 실패하고 상태코드 400을 전달받는다.")
            void it_return_401() {
                ValidatableResponse response = postLogin(invalidPasswordRequest);

                response.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("errorCode", equalTo("1002"))
                        .body("message", equalTo("로그인에 실패했습니다."));
            }
        }

        @Nested
        @DisplayName("회원 정보 양식이 잘못 됐다면")
        class Context_illegal_form extends AcceptanceTest {

            @Test
            @DisplayName("로그인에 실패하고 상태코드 400을 전달받는다.")
            void it_return_401() {
                ValidatableResponse response = postLogin(illegalRequest);

                response.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("errorCode", equalTo("1000"))
                        .body("message", equalTo("이메일 양식이 잘못 되었습니다."));
            }
        }
    }
}
