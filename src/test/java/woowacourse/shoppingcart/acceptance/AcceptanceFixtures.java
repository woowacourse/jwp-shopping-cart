package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;

public class AcceptanceFixtures {

    public static ExtractableResponse<Response> 회원가입(final CustomerRequest customerRequest) {
        return RestAssured
                .given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers/signUp")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인(final CustomerLoginRequest customerLoginRequest) {
        return RestAssured
                .given().log().all()
                .body(customerLoginRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers/login")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 나의_정보조회(final String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/auth/customers/profile")
                .then().log().all()
                .extract();
    }
}
