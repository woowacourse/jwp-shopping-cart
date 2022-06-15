package woowacourse.helper.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class Request {

    public static ExtractableResponse<Response> post(Object params, String url) {
        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> postWithToken(Object params, String url, String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> put(Object params, String url) {
        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> putWithToken(Object params, String url, String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> putWithTokenAndPathValue(Object params, Object value, String url,
                                                                         String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(url, value)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> get(String url) {
        return RestAssured.given().log().all()
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getWithToken(String url, String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getWithTokenAndPathValue(Object value, String url, String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .when()
                .get(url, value)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> delete(Object params, String url) {
        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> deleteWithToken(Object params, String url, String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> deleteWithTokenAndPathValue(Object value, String url, String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(url, value)
                .then().log().all()
                .extract();
    }
}
