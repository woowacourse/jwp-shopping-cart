package woowacourse.fixture;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.MediaType;

public abstract class Fixture {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    public static ExtractableResponse<Response> get(String path, Object body) {
        return postProcess(preProcess(body).get(path));
    }

    public static ExtractableResponse<Response> get(String path, String token) {
        return postProcess(preProcess(token).get(path));
    }

    public static ExtractableResponse<Response> get(String path, String token, Object body) {
        return postProcess(preProcess(token, body).get(path));
    }

    public static ExtractableResponse<Response> post(String path, Object body) {
        return postProcess(preProcess(body).post(path));
    }

    public static ExtractableResponse<Response> post(String path, String token, Object body) {
        return postProcess(preProcess(token, body).post(path));
    }

    public static ExtractableResponse<Response> put(String path, Object body) {
        return postProcess(preProcess(body).put(path));
    }


    public static ExtractableResponse<Response> put(String path, String token, Object body) {
        return postProcess(preProcess(token, body).put(path));
    }


    public static ExtractableResponse<Response> delete(String path, Object body) {
        return postProcess(preProcess(body).delete(path));
    }

    public static ExtractableResponse<Response> delete(String path, String token) {
        return postProcess(preProcess(token).delete(path));
    }

    public static ExtractableResponse<Response> delete(String path, String token, Object... pathParams) {
        return postProcess(preProcess(token).delete(path, pathParams));
    }

    protected static RequestSpecification preProcess(Object body) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when();
    }

    protected static RequestSpecification preProcess(String token) {
        return given().log().all()
                .header(AUTHORIZATION, BEARER + token)
                .when();
    }

    protected static RequestSpecification preProcess(String token, Object body) {
        return given().log().all()
                .header(AUTHORIZATION, BEARER + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when();
    }

    protected static ExtractableResponse<Response> postProcess(Response response) {
        return response
                .then().log().all()
                .extract();
    }
}
