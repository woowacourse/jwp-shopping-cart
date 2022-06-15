package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdateMeRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;
import woowacourse.shoppingcart.dto.response.MeResponse;
import woowacourse.shoppingcart.dto.response.UniqueUsernameResponse;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("회원 관련 기능")
class CustomerAcceptanceTest extends AcceptanceTest2 {

    private static final String 유효한_아이디 = "유효한_아이디";
    private static final String 유효한_비밀번호 = "password1@";
    private static final String 유효하지_않은_비밀번호 = "password1!";
    private static final SignUpRequest 유효한_회원가입_요청 = new SignUpRequest(유효한_아이디, 유효한_비밀번호, "닉네임", 15);
    private static final TokenRequest 유효한_로그인_요청 = new TokenRequest(유효한_아이디, 유효한_비밀번호);

    @Test
    void 회원가입() {
        ExtractableResponse<Response> response = 회원가입_요청(유효한_회원가입_요청);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.headers().getValue("location")).isEqualTo("/customers/me");
    }

    @DisplayName("GET /customers/me - 고객 본인의 정보 조회")
    @Nested
    class GetMeTest {

        @Test
        void 로그인된_경우_200() {
            회원가입_요청(유효한_회원가입_요청);
            String 유효한_토큰 = 로그인_성공_시_토큰_반환(유효한_로그인_요청);

            ExtractableResponse<Response> response = 내_정보_조회_요청(유효한_토큰);
            MeResponse actualBody = response.body().jsonPath().getObject(".", MeResponse.class);
            MeResponse expectedBody = new MeResponse("유효한_아이디", "닉네임", 15);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(actualBody).isEqualTo(expectedBody);
        }

        private ExtractableResponse<Response> 내_정보_조회_요청(String accessToken) {
            return RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .when().get("/customers/me")
                    .then().log().all()
                    .extract();
        }
    }

    @DisplayName("PUT /customers/me - 고객 본인의 정보 수정")
    @Nested
    class UpdateMeTest {

        @Test
        void 로그인된_경우_200() {
            회원가입_요청(유효한_회원가입_요청);
            String 유효한_토큰 = 로그인_성공_시_토큰_반환(유효한_로그인_요청);
            UpdateMeRequest 수정된_고객 = new UpdateMeRequest("새로운_닉네임", 20);

            ExtractableResponse<Response> response = 내_정보_수정_요청(수정된_고객, 유효한_토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        private ExtractableResponse<Response> 내_정보_수정_요청(UpdateMeRequest json, String accessToken) {
            return RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(json)
                    .when().put("/customers/me")
                    .then().log().all()
                    .extract();
        }
    }

    @DisplayName("DELETE /customers/me - 회원탈퇴")
    @Nested
    class DeleteMeTest {

        @Test
        void 로그인되었고_회원탈퇴_성공시_204() {
            회원가입_요청(유효한_회원가입_요청);
            String 유효한_토큰 = 로그인_성공_시_토큰_반환(유효한_로그인_요청);

            ExtractableResponse<Response> response = 회원탈퇴_요청(유효한_토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void 존재하지_않는_회원의_토큰으로_탈퇴하려는_경우_404() {
            회원가입_요청(유효한_회원가입_요청);
            String 유효한_토큰 = 로그인_성공_시_토큰_반환(유효한_로그인_요청);
            회원탈퇴_요청(유효한_토큰);

            ExtractableResponse<Response> response = 회원탈퇴_요청(유효한_토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        private ExtractableResponse<Response> 회원탈퇴_요청(String accessToken) {
            return RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .when().delete("/customers/me")
                    .then().log().all()
                    .extract();
        }
    }

    @DisplayName("PUT /customers/me/password - 고객 본인의 비밀번호 수정")
    @Nested
    class UpdatePasswordTest {

        @Test
        void 로그인되었고_현재_비밀번호를_제대로_입력한_경우_200() {
            회원가입_요청(유효한_회원가입_요청);
            String 유효한_토큰 = 로그인_성공_시_토큰_반환(유효한_로그인_요청);
            UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(유효한_비밀번호, "password!1");

            ExtractableResponse<Response> response = 내_비밀번호_수정_요청(updatePasswordRequest, 유효한_토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void 로그인되었으나_현재_비밀번호를_잘못_입력한_경우_400() {
            회원가입_요청(유효한_회원가입_요청);
            String 유효한_토큰 = 로그인_성공_시_토큰_반환(유효한_로그인_요청);
            UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(유효하지_않은_비밀번호, "새로운_비밀번호");

            ExtractableResponse<Response> response = 내_비밀번호_수정_요청(updatePasswordRequest, 유효한_토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        private ExtractableResponse<Response> 내_비밀번호_수정_요청(UpdatePasswordRequest json, String accessToken) {
            return RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(json)
                    .when().put("/customers/me/password")
                    .then().log().all()
                    .extract();
        }
    }

    @DisplayName("GET /customers/username/duplication - 아이디 중복 조회")
    @Nested
    class CheckUniqueUsernameTest {

        @Test
        void 존재하지_않는_아이디인_경우_참_200() {
            String 존재하지_않는_아이디 = "존재하지_않는_아이디";

            ExtractableResponse<Response> response = 아이디_중복_조회_요청(존재하지_않는_아이디);
            UniqueUsernameResponse actualBody = response.body().jsonPath().getObject(".", UniqueUsernameResponse.class);
            UniqueUsernameResponse expectedBody = new UniqueUsernameResponse(true);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(actualBody).isEqualTo(expectedBody);
        }

        @Test
        void 이미_존재하는_아이디인_경우_거짓_200() {
            회원가입_요청(유효한_회원가입_요청);
            String 이미_저장된_아이디 = 유효한_아이디;

            ExtractableResponse<Response> response = 아이디_중복_조회_요청(이미_저장된_아이디);
            UniqueUsernameResponse actualBody = response.body().jsonPath().getObject(".", UniqueUsernameResponse.class);
            UniqueUsernameResponse expectedBody = new UniqueUsernameResponse(false);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(actualBody).isEqualTo(expectedBody);
        }

        private ExtractableResponse<Response> 아이디_중복_조회_요청(String username) {
            return RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/customers/username/uniqueness?username=" + username)
                    .then().log().all()
                    .extract();
        }
    }

    private ExtractableResponse<Response> 회원가입_요청(SignUpRequest json) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(json)
                .when().post("/customers")
                .then().log().all()
                .extract();
    }

    private String 로그인_성공_시_토큰_반환(TokenRequest tokenRequest) {
        return RestAssured.given().log().all()
                .body(tokenRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract()
                .as(TokenResponse.class)
                .getAccessToken();
    }
}
