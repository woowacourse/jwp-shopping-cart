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
import woowacourse.shoppingcart.dto.GetMeResponse;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.UpdateMeRequest;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("회원 관련 기능")
class CustomerAcceptanceTest extends AcceptanceTest2 {

    @Test
    void 회원가입() {
        SignUpRequest newCustomer = new SignUpRequest("유효한_아이디", "비밀번호", "닉네임", 15);

        ExtractableResponse<Response> response = 회원가입_요청(newCustomer);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 고객_본인의_정보_조회() {
        회원가입_요청(new SignUpRequest("유효한_아이디", "비밀번호", "닉네임", 15));

        ExtractableResponse<Response> response = 내_정보_조회_요청();
        GetMeResponse actualBody = response.body().jsonPath().getObject(".", GetMeResponse.class);
        GetMeResponse expectedBody = new GetMeResponse("유효한_아이디", "닉네임", 15);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualBody).isEqualTo(expectedBody);
    }

    // TODO: 정말로 비밀번호 재확인이 필요한지 논의 필요. 회원탈퇴 및 비밀번호 수정시에만 확인하는 것은 어떠한가?
    @Test
    void 고객_본인의_정보_수정() {
        SignUpRequest 고객 = new SignUpRequest("유효한_아이디", "비밀번호", "닉네임", 15);
        UpdateMeRequest 수정된_고객 = new UpdateMeRequest("새로운_아이디", "비밀번호", "새로운_닉네임", 20);
        회원가입_요청(고객);

        ExtractableResponse<Response> response = 내_정보_수정_요청(수정된_고객);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("고객 본인의 비밀번호 수정")
    @Nested
    class UpdatePasswordTest {

        @Test
        void 현재_비밀번호를_제대로_입력한_경우_200() {
            String 기존_비밀번호 = "비밀번호";
            SignUpRequest customer = new SignUpRequest("유효한_아이디", 기존_비밀번호, "닉네임", 15);
            회원가입_요청(customer);
            UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(기존_비밀번호, "새로운_비밀번호");

            ExtractableResponse<Response> response = 내_비밀번호_수정_요청(updatePasswordRequest);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void 현재_비밀번호를_잘못_입력한_경우_400() {
            String 기존_비밀번호 = "비밀번호";
            String 틀린_비밀번호 = "비밀번호!";
            SignUpRequest customer = new SignUpRequest("유효한_아이디", 기존_비밀번호, "닉네임", 15);
            회원가입_요청(customer);
            UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(틀린_비밀번호, "새로운_비밀번호");

            ExtractableResponse<Response> response = 내_비밀번호_수정_요청(updatePasswordRequest);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @DisplayName("회원탈퇴")
    @Nested
    class DeleteMeTest {

        @Test
        void 회원탈퇴_성공시_204() {
            SignUpRequest newCustomer = new SignUpRequest("유효한_아이디", "비밀번호", "닉네임", 15);
            회원가입_요청(newCustomer);

            ExtractableResponse<Response> response = 회원탈퇴_요청();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void 존재하지_않는_회원의_토큰으로_탈퇴하려는_경우_404() {
            ExtractableResponse<Response> response = 회원탈퇴_요청();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }
    }

    private ExtractableResponse<Response> 회원가입_요청(SignUpRequest json) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(json)
                .when().post("/customers")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 내_정보_조회_요청() {
        return RestAssured
                .given().log().all()
                .when().get("/customers/me")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 내_정보_수정_요청(UpdateMeRequest json) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(json)
                .when().put("/customers/me")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 내_비밀번호_수정_요청(UpdatePasswordRequest json) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(json)
                .when().patch("/customers/me/password")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 회원탈퇴_요청() {
        return RestAssured
                .given().log().all()
                .when().delete("/customers/me")
                .then().log().all()
                .extract();
    }
}
