package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
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

            @Test
            @DisplayName("회원가입에 성공하고 상태코드 204를 반환받는다.")
            void it_return_204() {
                // then
                ValidatableResponse response = postUser(request);
                response.statusCode(HttpStatus.NO_CONTENT.value());
            }
        }

        @Nested
        @DisplayName("이메일 양식이 잘못 되었을 때")
        class Context_wrong_email extends AcceptanceTest {

            @Test
            @DisplayName("회원가입에 실패하고 상태코드 400을 반환받는다.")
            void it_return_400() {
                // then
                ValidatableResponse response = postUser(wrongEmailRequest);
                response.statusCode(HttpStatus.BAD_REQUEST.value());
                response.body("errorCode", equalTo("1000"))
                        .body("message", equalTo("이메일 양식이 잘못 되었습니다."));
            }
        }

        @Nested
        @DisplayName("비밀번호 양식이 잘못 되었을 때")
        class Context_wrong_password extends AcceptanceTest {

            @Test
            @DisplayName("회원가입에 실패하고 상태코드 400을 반환받는다.")
            void it_return_400() {
                ValidatableResponse response = postUser(wrongPasswordRequest);
                response.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("errorCode", equalTo("1000"))
                        .body("message", equalTo("비밀번호 양식이 잘못 되었습니다."));
            }
        }

        @Nested
        @DisplayName("닉네임 양식이 잘못 되었을 때")
        class Context_wrong_nickname extends AcceptanceTest {

            @Test
            @DisplayName("회원가입에 실패하고 상태코드 400을 반환받는다.")
            void it_return_400() {
                ValidatableResponse response = postUser(wrongNicknameRequest);
                response.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("errorCode", equalTo("1000"))
                        .body("message", equalTo("닉네임 양식이 잘못 되었습니다."));
            }
        }

        @Nested
        @DisplayName("이메일이 중복 되었을 때")
        class Context_duplicate_email extends AcceptanceTest {

            @Test
            @DisplayName("회원가입에 실패하고 상태코드 400을 반환받는다.")
            void it_return_400() {
                postUser(duplicatedEmailRequest);
                ValidatableResponse response2 = postUser(duplicatedEmailRequest);

                response2.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("errorCode", equalTo("1001"))
                        .body("message", equalTo("이메일이 중복입니다."));
            }
        }
    }

    @Nested
    @DisplayName("내 정보 요청")
    class Describe_내정보요청 {
        String accessToken;
        String email = "kun@gmail.com";
        String nickname = "kun";
        String password = "1q2w3e4r";
        CustomerCreationRequest signUpRequest = new CustomerCreationRequest(email, password, nickname);
        TokenRequest tokenRequest = new TokenRequest(email, password);

        @BeforeEach
        void setUp() {
            postUser(signUpRequest);

            accessToken = postLogin(tokenRequest)
                    .extract()
                    .as(TokenResponse.class)
                    .getAccessToken();
        }

        @Nested
        @DisplayName("유효한 토큰으로 요청한다면")
        class Context_validateToken extends AcceptanceTest {

            @Test
            @DisplayName("상태코드 200과 이메일, 닉네임 정보를 전달받는다.")
            void it_return_email_nickname() {
                ValidatableResponse response = getMe(accessToken);

                response.statusCode(HttpStatus.OK.value())
                        .body("email", equalTo(email))
                        .body("nickname", equalTo(nickname));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 토큰으로 요청한다면")
        class Context_InvalidToken extends AcceptanceTest {

            @Test
            @DisplayName("정보 요청에 실패하고 상태코드 401을 전달받는다.")
            void it_return_401() {
                ValidatableResponse response = getMe("invalidToken");

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
        TokenRequest tokenRequest = new TokenRequest(email, password);
        String accessToken;
        String updatedNickname = "rick";
        CustomerUpdationRequest updateRequest = new CustomerUpdationRequest(updatedNickname, "qwerasdf321");
        CustomerUpdationRequest illegalRequest = new CustomerUpdationRequest("asdfqwesdf", "qwerasdf123");

        @BeforeEach
        void setUp() {
            postUser(signUpRequest);
            accessToken = postLogin(tokenRequest)
                    .extract()
                    .as(TokenResponse.class)
                    .getAccessToken();
        }

        @Nested
        @DisplayName("유효한 토큰으로 요청한다면")
        class Context_validateToken extends AcceptanceTest {

            @Test
            @DisplayName("정보 수정에 성공하고 상태코드 204가 반환된다.")
            void it_return_email_nickname() {
                ValidatableResponse response = putMe(accessToken, updateRequest);

                response.statusCode(HttpStatus.NO_CONTENT.value());
            }
        }

        @Nested
        @DisplayName("회원 정보 양식에 문제가 있다면")
        class Context_illegal_form extends AcceptanceTest {

            @Test
            @DisplayName("정보 수정에 실패하고 상태코드 400이 반환된다.")
            void it_return_email_nickname() {
                ValidatableResponse response = putMe(accessToken, illegalRequest);

                response.statusCode(HttpStatus.BAD_REQUEST.value())
                        .body("errorCode", equalTo("1000"))
                        .body("message", equalTo("닉네임 양식이 잘못 되었습니다."));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 토큰으로 요청한다면")
        class Context_InvalidToken extends AcceptanceTest {

            @Test
            @DisplayName("정보 수정에 실패하고 상태코드 401을 전달받는다.")
            void it_return_401() {
                ValidatableResponse response = putMe("invalidToken", updateRequest);

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
        TokenRequest tokenRequest = new TokenRequest(email, password);
        String accessToken;

        @BeforeEach
        void setUp() {
            postUser(createRequest);

            accessToken = postLogin(tokenRequest)
                    .extract()
                    .as(TokenResponse.class)
                    .getAccessToken();
        }

        @Nested
        @DisplayName("유효한 토큰으로 요청한다면")
        class Context_validateToken extends AcceptanceTest {

            @Test
            @DisplayName("회원탈퇴에 성공하고 상태코드 204를 반환받는다.")
            void it_return_204() {
                ValidatableResponse response = deleteMe(accessToken);
                response.statusCode(HttpStatus.NO_CONTENT.value());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 토큰으로 요청한다면")
        class Context_invalidToken extends AcceptanceTest {

            @Test
            @DisplayName("회원 탈퇴에 실패하고 상태코드 401을 전달받는다.")
            void it_return_401() {
                ValidatableResponse response = deleteMe("invalidToken");
                response.statusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }
    }
}
