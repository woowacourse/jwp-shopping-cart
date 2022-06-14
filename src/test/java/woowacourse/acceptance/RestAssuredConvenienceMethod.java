package woowacourse.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.springframework.http.MediaType;

public class RestAssuredConvenienceMethod {

    public static ValidatableResponse postRequestWithToken(String accessToken,Object body, String path) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
                .then().log().all();
    }

    public static ValidatableResponse postRequestWithoutToken(Object body, String path) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when()
                .post(path)
                .then().log().all();
    }

    public static ValidatableResponse getRequestWithToken(String accessToken, String path) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(path)
                .then().log().all();
    }

    public static ValidatableResponse getRequestWithoutToken(String path) {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(path)
                .then().log().all();
    }

    public static ValidatableResponse deleteRequestWithToken(String accessToken, Object body, String path) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(path)
                .then().log().all();
    }

    public static ValidatableResponse deleteRequestWithoutToken(String path) {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(path)
                .then().log().all();
    }

    public static ValidatableResponse putRequestWithToken(String accessToken, Object body, String path) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(path)
                .then().log().all();
    }

    public static ValidatableResponse putRequestWithoutToken(Object body, String path) {
        return RestAssured.given().log().all()
                .body(body)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(path)
                .then().log().all();
    }
}
