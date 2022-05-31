package woowacourse.shoppingcart.acceptance.fixture;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.MediaType;

public abstract class Fixture {

    public static ExtractableResponse<Response> get(String path, Object body) {
        return afterMethod(beforeMethod(body).get(path));
    }

    public static ExtractableResponse<Response> get(String path, String token) {
        return afterMethod(beforeMethod(token).get(path));
    }

    public static ExtractableResponse<Response> post(String path, Object body) {
        return afterMethod(beforeMethod(body).post(path));
    }

    public static ExtractableResponse<Response> put(String path, Object body) {
        return afterMethod(beforeMethod(body).put(path));
    }

    public static ExtractableResponse<Response> put(String path, String token, Object body) {
        return afterMethod(beforeMethod(token, body).put(path));
    }


    public static ExtractableResponse<Response> delete(String path, Object body) {
        return afterMethod(beforeMethod(body).delete(path));
    }

    public static ExtractableResponse<Response> delete(String path, String token) {
        return afterMethod(beforeMethod(token).delete(path));
    }

    protected static RequestSpecification beforeMethod(Object body) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when();
    }

    protected static RequestSpecification beforeMethod(String token) {
        return given().log().all()
                .header("Authorization", "Bearer " + token)
                .when();
    }

    protected static RequestSpecification beforeMethod(String token, Object body) {
        return given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when();
    }

    protected static ExtractableResponse<Response> afterMethod(Response response) {
        return response
                .then().log().all()
                .extract();
    }
}
