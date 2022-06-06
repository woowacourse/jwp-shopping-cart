package woowacourse.support;

import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SimpleRestAssured {

    public static ExtractableResponse<Response> get(String path, Header header) {
        return thenExtract(given()
            .header(header)
            .when()
            .get(path)
        );
    }

    public static ExtractableResponse<Response> post(String path, Object request) {
        return thenExtract(given()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .post(path)
        );
    }

    public static ExtractableResponse<Response> put(String path, Header header, Object request) {
        return thenExtract(given()
            .header(header)
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .put(path)
        );
    }

    public static ExtractableResponse<Response> delete(String path, Header header) {
        return thenExtract(given()
            .header(header)
            .delete(path)
        );
    }

    private static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    private static ExtractableResponse<Response> thenExtract(Response response) {
        return response
            .then().log().all()
            .extract();
    }

    public static <T> T toObject(ExtractableResponse<Response> response, Class<T> clazz) {
        return response.body().jsonPath().getObject(".", clazz);
    }

    public static Long getId(ExtractableResponse<Response> response) {
        final String[] locations = response.header("Location").split("/");
        return Long.parseLong(locations[locations.length - 1]);
    }
}
