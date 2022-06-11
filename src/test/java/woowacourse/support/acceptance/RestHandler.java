package woowacourse.support.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.support.dto.ExceptionResponse;
import woowacourse.support.exception.ShoppingCartExceptionCode;

public class RestHandler {

    protected static ExtractableResponse<Response> getRequest(final String url) {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> getRequest(final String url, final String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected static <T> ExtractableResponse<Response> postRequest(final String url, final T requestBody) {
        return RestAssured.given().log().all()
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> postRequest(final String url, final String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected static <T> ExtractableResponse<Response> postRequest(final String url, final T requestBody,
                                                                   final String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected static <T> ExtractableResponse<Response> putRequest(final String url, final T requestBody) {
        return RestAssured.given().log().all()
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(url)
                .then().log().all()
                .extract();
    }

    protected static <T> ExtractableResponse<Response> putRequest(final String url, final T requestBody,
                                                                  final String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(url)
                .then().log().all()
                .extract();
    }

    protected static <T> ExtractableResponse<Response> patchRequest(final String url, final T requestBody,
                                                                    final String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .patch(url)
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> deleteRequest(final String url) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }

    protected static <T> ExtractableResponse<Response> deleteRequest(final String url, final T requestBody,
                                                                     final String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }

    public static <T> T extractResponse(final ExtractableResponse<Response> response, final Class<T> clazz) {
        return response.jsonPath().getObject(".", clazz);
    }

    protected static <T> void assertThatException(final ExtractableResponse<Response> response,
                                                  final ShoppingCartExceptionCode exceptionCode) {
        final ExceptionResponse exceptionResponse = extractResponse(response, ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage()).isEqualTo(exceptionCode.getMessage());
    }
}
