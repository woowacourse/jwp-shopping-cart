package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class RequestHandler {

    public static ExtractableResponse<Response> getRequest(String url) {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getRequest(String url, String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    public static <T> ExtractableResponse<Response> postRequest(String url, T requestBody) {
        return RestAssured.given().log().all()
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> postRequest(String url, String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    public static <T> ExtractableResponse<Response> putRequest(String url, T requestBody, String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(url)
                .then().log().all()
                .extract();
    }

    public static <T> ExtractableResponse<Response> patchRequest(String url, T requestBody, String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .patch(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> deleteRequest(String url) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }

    public static <T> ExtractableResponse<Response> deleteRequest(String url, T requestBody, String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }
}
