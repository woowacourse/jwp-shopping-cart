package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import woowacourse.auth.application.dto.TokenResponse;
import woowacourse.auth.ui.dto.LoginRequest;
import woowacourse.shoppingcart.ui.dto.*;

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
        LoginRequest request = new LoginRequest(email, password);
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/login")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> patchCustomers(TokenResponse tokenResponse, String zero) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .body(new CustomerChangeRequest(zero))
                .patch("/api/customers")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> patchPasswordCustomers(TokenResponse tokenResponse, String prePassword, String newPassword) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CustomerChangePasswordRequest(prePassword, newPassword))
                .patch("/api/customers/password")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> deleteCustomers(TokenResponse tokenResponse) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .delete("/api/customers")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> postProduct(String name, int price, String imageUrl) {
        ProductAddRequest request = new ProductAddRequest(name, price, imageUrl);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getProducts() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getProductById(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products/" + productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getCustomers(TokenResponse tokenResponse) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .get("/api/customers/me")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> postCart(TokenResponse tokenResponse, Long productId) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .when().post("/api/carts/products/" + productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getCarts(TokenResponse tokenResponse) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .get("/api/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> deleteCart(TokenResponse tokenResponse, Long productId) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .delete("/api/carts/products/" + productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> updateCart(TokenResponse tokenResponse, Long productId, int quantity) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .body(new ProductChangeRequest(quantity))
                .patch("/api/carts/products/" + productId)
                .then().log().all()
                .extract();
    }
}
