package woowacourse;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.shoppingcart.customer.dto.CustomerRequest;

public class AcceptanceTestFixture {

    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "password1!";
    private static final String USER_NAME = "이스트";

    public static ExtractableResponse<Response> getMethodRequest(String path) {
        return RestAssured.given().log().all()
                .when()
                .get(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getMethodRequestWithBearerAuth(String token, String path) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> postMethodRequest(Object request, String path) {
        return RestAssured.given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> postMethodRequestWithBearerAuth(Object request, String token, String path) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> patchMethodRequestWithBearerAuth(Object request, String token, String path) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .patch(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> deleteMethodRequest(Object request, String path) {
        return RestAssured.given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> deleteMethodRequestWithBearerAuth(Object request, String token, String path) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(path)
                .then().log().all()
                .extract();
    }

    public static void join() {
        final CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, USER_NAME);
        postMethodRequest(customerRequest, "/api/customers");
    }

    public static String login() {
        final LoginRequest loginRequest = new LoginRequest(EMAIL, PASSWORD);
        final ExtractableResponse<Response> response = postMethodRequest(loginRequest, "/api/auth/login");
        return response.jsonPath().getString("accessToken");
    }

}
