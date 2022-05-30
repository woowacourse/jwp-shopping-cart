package woowacourse.shoppingcart.acceptance;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class RequestHandler {

    public static ExtractableResponse<Response> getRequest(String url) {
        return RestAssured.given().log().all()
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

    public static <T> ExtractableResponse<Response> putRequest(String url, T requestBody) {
        return RestAssured.given().log().all()
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(url)
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
}
