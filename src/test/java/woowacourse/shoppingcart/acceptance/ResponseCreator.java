package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.ui.dto.TokenRequest;
import woowacourse.shoppingcart.ui.dto.CustomerSignUpRequest;

public class ResponseCreator {

    public static ExtractableResponse<Response> postCustomers(String email, String password, String nickname) {
        CustomerSignUpRequest request = new CustomerSignUpRequest(email, password, nickname);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(request)
                .post("/api/customers")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> postLogin(String email, String password) {
        TokenRequest request = new TokenRequest(email, password);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(request)
                .post("/api/login")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getCustomers(TokenResponse tokenResponse) {
        return RestAssured.given().log().all()
                .when()
                .header("Authorization", "Bearer " + tokenResponse)
                .get("/api/customers/me")
                .then().log().all()
                .extract();
    }
}
