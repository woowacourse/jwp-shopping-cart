package woowacourse.fixture;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.MediaType;

public abstract class Fixture {
    public static final String AUTHORIZATION = "Authorization";

    public static final String BEARER = "Bearer ";

    public static <T> List<T> covertTypeList(ExtractableResponse<Response> response, Class<T> clazz) {
        return response.jsonPath().getList("", clazz);
    }

    public static <T> ExtractableResponse<Response> get(String path, T body) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().get(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> get(String path, String token) {
        return given().log().all()
                .header(AUTHORIZATION, BEARER + token)
                .when().get(path)
                .then().log().all()
                .extract();
    }

    public static <T> ExtractableResponse<Response> get(String path, String token, T body) {
        return given().log().all()
                .header(AUTHORIZATION, BEARER + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().get(path)
                .then().log().all()
                .extract();
    }

    public static <T> ExtractableResponse<Response> post(String path, T body) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post(path)
                .then().log().all()
                .extract();
    }

    public static <T> ExtractableResponse<Response> post(String path, String token, T body) {
        return given().log().all()
                .header(AUTHORIZATION, BEARER + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post(path)
                .then().log().all()
                .extract();
    }

    public static <T> ExtractableResponse<Response> put(String path, T body) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().put(path)
                .then().log().all()
                .extract();
    }


    public static <T> ExtractableResponse<Response> put(String path, String token, T body) {
        return given().log().all()
                .header(AUTHORIZATION, BEARER + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().put(path)
                .then().log().all()
                .extract();
    }


    public static <T> ExtractableResponse<Response> delete(String path, T body) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().delete(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> delete(String path, String token) {
        return given().log().all()
                .header(AUTHORIZATION, BEARER + token)
                .when().delete(path)
                .then().log().all()
                .extract();
    }

    public static <T> ExtractableResponse<Response> delete(String path, String token, T body) {
        return given().log().all()
                .header(AUTHORIZATION, BEARER + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().delete(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> delete(String path, String token, Object... pathParams) {
        return given().log().all()
                .header(AUTHORIZATION, BEARER + token)
                .when().delete(path, pathParams)
                .then().log().all()
                .extract();
    }
}
