package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.GetMeResponse;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.UpdateMeRequest;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("회원 관련 기능")
class CustomerAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원가입() {
        SignUpRequest newCustomer = new SignUpRequest("아이디", "비밀번호", "닉네임", 15);

        ExtractableResponse<Response> response = 회원가입_요청(newCustomer);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 고객_본인의_정보_조회() {
        SignUpRequest newCustomer = new SignUpRequest("아이디", "비밀번호", "닉네임", 15);
        회원가입_요청(newCustomer);

        ExtractableResponse<Response> response = 내_정보_조회_요청();
        GetMeResponse actualBody = response.body().jsonPath().getObject(".", GetMeResponse.class);
        GetMeResponse expectedBody = new GetMeResponse("아이디", "닉네임", 15);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualBody).isEqualTo(expectedBody);
    }

    @Test
    void 고객_본인의_정보_수정() {
        SignUpRequest customer = new SignUpRequest("아이디", "비밀번호", "닉네임", 15);
        UpdateMeRequest updatedCustomer = new UpdateMeRequest("새로운_아이디", "비밀번호", "새로운_닉네임", 20);
        회원가입_요청(customer);

        ExtractableResponse<Response> response =  내_정보_수정_요청(updatedCustomer);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 고객_본인의_비밀번호_수정() {
        SignUpRequest customer = new SignUpRequest("아이디", "비밀번호", "닉네임", 15);
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("비밀번호", "새로운_비밀번호");
        회원가입_요청(customer);

        ExtractableResponse<Response> response = 내_비밀번호_수정_요청(updatePasswordRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 회원탈퇴() {
        SignUpRequest newCustomer = new SignUpRequest("아이디", "비밀번호", "닉네임", 15);
        회원가입_요청(newCustomer);

        ExtractableResponse<Response> response = 회원탈퇴_요청();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private ExtractableResponse<Response> 회원가입_요청(SignUpRequest requestBody) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
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

    private ExtractableResponse<Response> 내_정보_수정_요청(UpdateMeRequest requestBody) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().put("/customers/me")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 내_비밀번호_수정_요청(UpdatePasswordRequest requestBody) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
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
