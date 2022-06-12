package woowacourse.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.request.SignUpRequest;
import woowacourse.auth.dto.request.TokenRequest;
import woowacourse.auth.dto.request.UpdateMeRequest;
import woowacourse.auth.dto.request.UpdatePasswordRequest;
import woowacourse.auth.dto.response.GetMeResponse;
import woowacourse.auth.dto.response.TokenResponse;
import woowacourse.auth.dto.response.UniqueUsernameResponse;
import woowacourse.setup.AcceptanceTest;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("회원 관련 기능")
class CustomerAcceptanceTest extends AcceptanceTest {

    private static final String 유효한_아이디 = "username";
    private static final String 유효한_비밀번호 = "password1@";
    private static final String 유효한_닉네임 = "닉네임";
    private static final SignUpRequest 유효한_사용자 = new SignUpRequest(유효한_아이디, 유효한_비밀번호, 유효한_닉네임, 15);

    @DisplayName("POST /customers - 회원가입")
    @Nested
    class SignUpTest {

        @Test
        void 회원가입_성공() {
            ExtractableResponse<Response> response = 회원가입_요청(유효한_사용자);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        }

        @Test
        void 필요한_정보가_하나라도_누락된_경우_400() {
            Map<String, Object> 아이디와_나이_누락_요청 = new HashMap<>() {{
                put("password", 유효한_비밀번호);
                put("nickname", 유효한_닉네임);
            }};

            ExtractableResponse<Response> response = 회원가입_요청(아이디와_나이_누락_요청);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @DisplayName("GET /customers/me - 고객 본인의 정보 조회")
    @Nested
    class GetMeTest {

        @Test
        void 로그인된_경우_200() {
            String 유효한_토큰 = 회원가입_요청_후_토큰_반환(유효한_사용자);

            ExtractableResponse<Response> response = 내_정보_조회_요청(유효한_토큰);
            GetMeResponse actualBody = response.as(GetMeResponse.class);
            GetMeResponse expectedBody = new GetMeResponse(유효한_아이디, 유효한_닉네임, 15);

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

        private final SignUpRequest 고객 = new SignUpRequest(유효한_아이디, "password123@", 유효한_닉네임, 15);

        @Test
        void 로그인된_경우_200() {
            String 유효한_토큰 = 회원가입_요청_후_토큰_반환(고객);
            UpdateMeRequest 수정된_고객 = new UpdateMeRequest("새로운닉네임", 20);

            ExtractableResponse<Response> response = 내_정보_수정_요청(수정된_고객, 유효한_토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void 필요한_정보가_하나라도_누락된_경우_400() {
            String 유효한_토큰 = 회원가입_요청_후_토큰_반환(고객);
            Map<String, Object> 나이_누락_요청 = new HashMap<>() {{
                put("nickname", 유효한_닉네임);
            }};

            ExtractableResponse<Response> response = 내_정보_수정_요청(나이_누락_요청, 유효한_토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        private ExtractableResponse<Response> 내_정보_수정_요청(Object json, String accessToken) {
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
            String 유효한_토큰 = 회원가입_요청_후_토큰_반환(유효한_사용자);

            ExtractableResponse<Response> response = 회원탈퇴_요청(유효한_토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void 존재하지_않는_회원의_토큰으로_탈퇴하려는_경우_인증에_실패하므로_401() {
            String 유효한_토큰 = 회원가입_요청_후_토큰_반환(유효한_사용자);
            회원탈퇴_요청(유효한_토큰);

            ExtractableResponse<Response> response = 회원탈퇴_요청(유효한_토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
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
            String 기존_비밀번호 = 유효한_비밀번호;
            SignUpRequest customer = new SignUpRequest(유효한_아이디, 기존_비밀번호, 유효한_닉네임, 15);
            UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(기존_비밀번호, "newpw123@#$");
            String 유효한_토큰 = 회원가입_요청_후_토큰_반환(customer);

            ExtractableResponse<Response> response = 내_비밀번호_수정_요청(updatePasswordRequest, 유효한_토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void 로그인되었으나_현재_비밀번호를_잘못_입력한_경우_400() {
            String 틀린_비밀번호 = "wrong123@#";
            SignUpRequest customer = new SignUpRequest(유효한_아이디, 유효한_비밀번호, 유효한_닉네임, 15);
            UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(틀린_비밀번호, "새로운_비밀번호");
            String 유효한_토큰 = 회원가입_요청_후_토큰_반환(customer);

            ExtractableResponse<Response> response = 내_비밀번호_수정_요청(updatePasswordRequest, 유효한_토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 필요한_정보가_하나라도_누락된_경우_400() {
            String 유효한_토큰 = 회원가입_요청_후_토큰_반환(new SignUpRequest(유효한_아이디, 유효한_비밀번호, 유효한_닉네임, 15));
            Map<String, Object> 기존_비밀번호_누락_요청 = new HashMap<>() {{
                put("newPassword", "asdfg1234@");
            }};

            ExtractableResponse<Response> response = 내_비밀번호_수정_요청(기존_비밀번호_누락_요청, 유효한_토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        private ExtractableResponse<Response> 내_비밀번호_수정_요청(Object json, String accessToken) {
            return RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(json)
                    .when().put("/customers/me/password")
                    .then().log().all()
                    .extract();
        }
    }

    @DisplayName("GET /customers/username/uniqueness - 아이디 중복 조회")
    @Nested
    class CheckUniqueUsernameTest {

        @Test
        void 아직_존재하지_않는_아이디인_경우_참_200() {
            String 존재하지_않는_아이디 = "존재하지_않는_아이디";

            ExtractableResponse<Response> response = 아이디_중복_조회_요청(존재하지_않는_아이디);
            UniqueUsernameResponse actualBody = response.as(UniqueUsernameResponse.class);
            UniqueUsernameResponse expectedBody = new UniqueUsernameResponse(true);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(actualBody).isEqualTo(expectedBody);
        }

        @Test
        void 이미_존재하는_아이디인_경우_거짓_200() {
            회원가입_요청(유효한_사용자);
            String 이미_저장된_아이디 = 유효한_아이디;

            ExtractableResponse<Response> response = 아이디_중복_조회_요청(이미_저장된_아이디);
            UniqueUsernameResponse actualBody = response.as(UniqueUsernameResponse.class);
            UniqueUsernameResponse expectedBody = new UniqueUsernameResponse(false);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(actualBody).isEqualTo(expectedBody);
        }

        @Test
        void 조회하려는_사용자명이_누락된_경우_400() {
            ExtractableResponse<Response> response = 아이디_중복_조회_요청("");

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        private ExtractableResponse<Response> 아이디_중복_조회_요청(String username) {
            return RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .params("username", username)
                    .when().get("/customers/username/uniqueness")
                    .then().log().all()
                    .extract();
        }
    }

    private ExtractableResponse<Response> 회원가입_요청(Object json) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(json)
                .when().post("/customers")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 회원가입_요청(SignUpRequest json) {
        return 회원가입_요청((Object) json);
    }

    private String 회원가입_요청_후_토큰_반환(SignUpRequest signupRequest) {
        회원가입_요청(signupRequest);
        TokenRequest tokenRequest = new TokenRequest(signupRequest.getUsername(), signupRequest.getPassword());
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
