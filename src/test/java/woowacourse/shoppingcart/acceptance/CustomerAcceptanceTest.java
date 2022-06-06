package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.dto.CustomerUpdationRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("회원 가입")
    class Describe_회원가입 {
        CustomerCreationRequest request = new CustomerCreationRequest("kun@naver.com", "1q2w3e4r", "kun");
        CustomerCreationRequest wrongEmailRequest = new CustomerCreationRequest("kun#naver.com", "1234567aa", "쿤aa");
        CustomerCreationRequest wrongPasswordRequest = new CustomerCreationRequest("kun@gmail.com", "1234", "쿤aa");
        CustomerCreationRequest wrongNicknameRequest = new CustomerCreationRequest("kun@yahoo.com", "qwerasdf123", "쿤");
        CustomerCreationRequest duplicatedEmailRequest = new CustomerCreationRequest("kun@naver.com", "1q2w3e4r",
                "kun");

        @Nested
        @DisplayName("이메일, 비밀번호, 닉네임 모든 양식에 맞게 작성하면")
        class Context_success extends AcceptanceTest {
            ValidatableResponse response = postUser(request);

            @Test
            @DisplayName("회원가입에 성공하고 상태코드 204를 반환받는다.")
            void it_return_204() {
                // then
                response.statusCode(HttpStatus.NO_CONTENT.value());
            }
        }

        @Nested
        @DisplayName("이메일 양식이 잘못 되었을 때")
        class Context_wrong_email extends AcceptanceTest {
            ValidatableResponse response = postUser(wrongEmailRequest);

            @Test
            @DisplayName("회원가입에 실패하고 상태코드 400을 반환받는다.")
            void it_return_400() {
                // then
                response.statusCode(HttpStatus.BAD_REQUEST.value());
                response.body("errorCode", equalTo("1000"))
                        .body("message", equalTo("이메일 양식이 잘못 되었습니다."));
            }
        }

        @Nested
        @DisplayName("비밀번호 양식이 잘못 되었을 때")
        class Context_wrong_password extends AcceptanceTest {
            ValidatableResponse response = postUser(wrongPasswordRequest);

            @Test
            @DisplayName("회원가입에 실패하고 상태코드 400을 반환받는다.")
            void it_return_400() {
                response.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("errorCode", equalTo("1000"))
                        .body("message", equalTo("비밀번호 양식이 잘못 되었습니다."));
            }
        }

        @Nested
        @DisplayName("닉네임 양식이 잘못 되었을 때")
        class Context_wrong_nickname extends AcceptanceTest {
            ValidatableResponse response = postUser(wrongNicknameRequest);

            @Test
            @DisplayName("회원가입에 실패하고 상태코드 400을 반환받는다.")
            void it_return_400() {
                response.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("errorCode", equalTo("1000"))
                        .body("message", equalTo("닉네임 양식이 잘못 되었습니다."));
            }
        }

        @Nested
        @DisplayName("이메일이 중복 되었을 때")
        class Context_duplicate_email extends AcceptanceTest {
            ValidatableResponse response = postUser(duplicatedEmailRequest);
            ValidatableResponse response2 = postUser(duplicatedEmailRequest);


            @Test
            @DisplayName("회원가입에 실패하고 상태코드 400을 반환받는다.")
            void it_return_400() {
                response2.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("errorCode", equalTo("1001"))
                        .body("message", equalTo("이메일이 중복입니다."));
            }
        }
    }

    @Nested
    @DisplayName("내 정보 요청")
    class Describe_내정보요청 {
        String email = "kun@gmail.com";
        String nickname = "kun";
        String password = "1q2w3e4r";
        CustomerCreationRequest signUpRequest = new CustomerCreationRequest(email, password, nickname);
        ValidatableResponse response = postUser(signUpRequest);

        TokenRequest tokenRequest = new TokenRequest(email, password);
        String accessToken = postLogin(tokenRequest)
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();

        @Nested
        @DisplayName("유효한 토큰으로 요청한다면")
        class Context_validateToken extends AcceptanceTest {
            ValidatableResponse response = getMe(accessToken);

            @Test
            @DisplayName("상태코드 200과 이메일, 닉네임 정보를 전달받는다.")
            void it_return_email_nickname() {
                response.statusCode(HttpStatus.OK.value())
                        .body("email", equalTo(email))
                        .body("nickname", equalTo(nickname));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 토큰으로 요청한다면")
        class Context_InvalidToken extends AcceptanceTest {
            ValidatableResponse response = getMe("invalidToken");

            @Test
            @DisplayName("정보 요청에 실패하고 상태코드 401을 전달받는다.")
            void it_return_401() {
                response.statusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }
    }

    @Nested
    @DisplayName("내 정보 수정")
    class Describe_내정보수정 {
        String email = "kun@gmail.com";
        String nickname = "kun";
        String password = "1q2w3e4r";
        CustomerCreationRequest signUpRequest = new CustomerCreationRequest(email, password, nickname);
        ValidatableResponse response = postUser(signUpRequest);

        TokenRequest tokenRequest = new TokenRequest(email, password);
        String accessToken = postLogin(tokenRequest)
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();
        String updatedNickname = "rick";
        CustomerUpdationRequest updateRequest = new CustomerUpdationRequest(updatedNickname, "qwerasdf321");

        @Nested
        @DisplayName("유효한 토큰으로 요청한다면")
        class Context_validateToken extends AcceptanceTest {
            ValidatableResponse response = putMe(accessToken, updateRequest);

            @Test
            @DisplayName("정보 수정에 성공하고 상태코드 204가 반환된다.")
            void it_return_email_nickname() {
                response.statusCode(HttpStatus.NO_CONTENT.value());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 토큰으로 요청한다면")
        class Context_InvalidToken extends AcceptanceTest {
            ValidatableResponse response = putMe("invalidToken", updateRequest);

            @Test
            @DisplayName("정보 수정에 실패하고 상태코드 401을 전달받는다.")
            void it_return_401() {
                response.statusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }
    }

    @Nested
    @DisplayName("회원 탈퇴")
    class Describe_회원탈퇴 {
        String email = "kun@email.com";
        String password = "qwerasdf123";
        CustomerCreationRequest createRequest = new CustomerCreationRequest(email, password, "kun");
        ValidatableResponse response = postUser(createRequest);

        TokenRequest tokenRequest = new TokenRequest(email, password);
        String accessToken = postLogin(tokenRequest)
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();

        @Nested
        @DisplayName("유효한 토큰으로 요청한다면")
        class Context_validateToken extends AcceptanceTest {
            ValidatableResponse response = deleteMe(accessToken);

            @Test
            @DisplayName("회원탈퇴에 성공하고 상태코드 204를 반환받는다.")
            void it_return_204() {
                response.statusCode(HttpStatus.NO_CONTENT.value());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 토큰으로 요청한다면")
        class Context_invalidToken extends AcceptanceTest {
            ValidatableResponse response = deleteMe("invalidToken");

            @Test
            @DisplayName("회원 탈퇴에 실패하고 상태코드 401을 전달받는다.")
            void it_return_401() {
                response.statusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }
    }
}
