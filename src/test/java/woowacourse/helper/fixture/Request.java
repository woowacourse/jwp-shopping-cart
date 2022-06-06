package woowacourse.helper.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class Request {

    protected ExtractableResponse<Response> post(Object params, String url) {
        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> put(Object params, String url) {
        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(url)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> putWithToken(Object params, String url, String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(url)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> get(String url) {
        return RestAssured.given().log().all()
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> getWithToken(String url, String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> delete(String url) {
        return RestAssured.given().log().all()
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }

    public ExtractableResponse<Response> deleteWithToken(String url, String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }
}
