package woowacourse.auth.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.customer.CustomerRegisterRequest;

public class AuthAcceptanceFixture {

    public static ExtractableResponse<Response> loginCustomer(final TokenRequest request) {
        return RestAssured.given().log().all().body(request).contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE).when().post("/api/login").then().log().all().extract();
    }

    public static ExtractableResponse<Response> registerCustomer(final CustomerRegisterRequest request) {
        return RestAssured.given().log().all().body(request).contentType(MediaType.APPLICATION_JSON_VALUE).when()
                .post("/api/customer").then().log().all().extract();
    }

    public static String registerAndGetToken(final String name, final String email, final String password) {
        registerCustomer(new CustomerRegisterRequest(name, email, password));
        return loginCustomer(new TokenRequest(email, password))
                .as(TokenResponse.class)
                .getAccessToken();
    }
}
