package woowacourse;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class AcceptanceTestFixture {

    public static ExtractableResponse<Response> getMethodRequest(String path) {
        return RestAssured.given().log().all()
                .when()
                .get(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> getMethodRequestWithBearerAuth(String token, String path) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> postMethodRequest(Object request, String path) {
        return RestAssured.given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> postMethodRequestWithBearerAuth(Object request, String token,
                                                                                String path) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> patchMethodRequestWithBearerAuth(Object request, String token,
                                                                                 String path) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .patch(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> deleteMethodRequest(String path) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> deleteMethodRequestWithBearerAuthAndBody(Object request, String token,
                                                                                         String path) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> deleteMethodRequestWithBearerAuth(String token, String path) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(path)
                .then().log().all()
                .extract();
    }
}
