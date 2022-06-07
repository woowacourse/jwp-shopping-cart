package woowacourse.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class RestAssuredUtils {

    public static ExtractableResponse<Response> httpPost(String path, Object object) {
        return RestAssured.
                given().log().all()
                .contentType(ContentType.JSON)
                .body(object)
                .when().post(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> httpGet(String path) {
        return RestAssured.
                given().log().all()
                .when().get(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> httpDelete(String path) {
        return RestAssured.
                given().log().all()
                .when().delete(path)
                .then().log().all().extract();
    }


    public static ExtractableResponse<Response> signOut(String path, String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .when().delete(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> login(String path, Object object) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(object)
                .when().post(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> deleteWithToken(String path, String token, Object object) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(object)
                .auth().oauth2(token)
                .when().delete(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> patchWithToken(String path, String token, Object object) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(object)
                .auth().oauth2(token)
                .when().patch(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> getWithToken(String path, String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .when().get(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> postWithToken(String path, String token, Object object) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(object)
                .auth().oauth2(token)
                .when().post(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> putWithToken(String path, String token, Object object) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(object)
                .auth().oauth2(token)
                .when().put(path)
                .then().log().all().extract();
    }
}
