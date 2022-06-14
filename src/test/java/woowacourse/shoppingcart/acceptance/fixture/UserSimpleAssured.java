package woowacourse.shoppingcart.acceptance.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.UserUpdateRequest;

public class UserSimpleAssured {

    public static ExtractableResponse<Response> 회원가입_요청(SignUpRequest signUpRequest) {
        return RestAssured.given().log().all()
                .body(signUpRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/users")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 로그인_요청(TokenRequest tokenRequest) {
        return RestAssured.given().log().all()
                .body(tokenRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract();
    }

    public static String 토큰_요청(TokenRequest tokenRequest) {
        return 로그인_요청(tokenRequest).jsonPath().getString("accessToken");
    }

    public static ExtractableResponse<Response> 회원정보_요청(String token) {
        return RestAssured.given().log().all()
                .when().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .get("/users/me")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 회원탈퇴_요청(String token) {
        return RestAssured.given().log().all()
                .when().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .delete("/users/me")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 회원정보_수정_요청(UserUpdateRequest userUpdateRequest, String token) {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(userUpdateRequest)
                .put("/users/me")
                .then().log().all().extract();
        return response;
    }
}
