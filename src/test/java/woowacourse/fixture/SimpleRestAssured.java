package woowacourse.fixture;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.MediaType;

public class SimpleRestAssured {

    public static SimpleResponse get(String path) {
        return new SimpleResponse(given()
                .when().get(path));
    }

    public static SimpleResponse getWithParam(String path, String paramName, Object value) {
        return new SimpleResponse(given()
                .params(paramName, value)
                .when().get(path));
    }

    public static SimpleResponse getWithToken(String path, String token) {
        return new SimpleResponse(givenWithToken(token)
                .when().get(path));
    }

    public static SimpleResponse post(String path, Object params) {
        return new SimpleResponse(given()
                .body(params)
                .when().post(path));
    }

    public static SimpleResponse postWithToken(String path, String token) {
        return new SimpleResponse(givenWithToken(token)
                .when().post(path));
    }

    public static SimpleResponse postWithToken(String path, String token, Object params) {
        return new SimpleResponse(givenWithToken(token)
                .body(params)
                .when().post(path));
    }

    public static SimpleResponse putWithToken(String path, String token, Object params) {
        return new SimpleResponse(givenWithToken(token)
                .body(params)
                .when().put(path));
    }

    public static SimpleResponse deleteWithToken(String path, String token) {
        return new SimpleResponse(givenWithToken(token)
                .when().delete(path));
    }

    public static SimpleResponse deleteWithToken(String path, String token, Object params) {
        return new SimpleResponse(givenWithToken(token)
                .body(params)
                .when().delete(path));
    }

    private static RequestSpecification given() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE);
    }

    private static RequestSpecification givenWithToken(String token) {
        return given()
                .auth().oauth2(token);
    }
}
