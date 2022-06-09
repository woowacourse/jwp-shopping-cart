package woowacourse.acceptance;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.LogInResponse;

public class RestAssuredFixture {

    private RestAssuredFixture() {
    }

    public static ValidatableResponse post(Object request, String path, int status) {
        return RestAssured
                .given().log().all()
                .config(RestAssuredConfig.newConfig().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .baseUri("https://127.0.0.1")
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(path)
                .then().log().all()
                .statusCode(status);
    }

    public static LogInResponse postAutoSignIn(String token, String path, int status) {
        return RestAssured
                .given().log().all()
                .config(RestAssuredConfig.newConfig().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .baseUri("https://127.0.0.1")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(path)
                .then().log().all()
                .statusCode(status).extract().as(LogInResponse.class);
    }

    public static ValidatableResponse postCart(Object request, String token, String path, int status) {
        return RestAssured
                .given().log().all()
                .config(RestAssuredConfig.newConfig().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .baseUri("https://127.0.0.1")
                .contentType(ContentType.JSON)
                .body(request)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(path)
                .then().log().all().statusCode(status);
    }

    public static ValidatableResponse get(String token, String path, int status) {
        return RestAssured
                .given().log().all()
                .config(RestAssuredConfig.newConfig().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .baseUri("https://127.0.0.1")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path)
                .then().log().all().statusCode(status);
    }

    public static LogInResponse getSignInResponse(Object request, String path) {
        return RestAssured
                .given().log().all()
                .config(RestAssuredConfig.newConfig().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .baseUri("https://127.0.0.1")
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(path)
                .then().log().all()
                .extract()
                .as(LogInResponse.class);
    }

    public static ValidatableResponse getProducts(String path, int status) {
        return RestAssured
                .given().log().all()
                .config(RestAssuredConfig.newConfig().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .baseUri("https://127.0.0.1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path)
                .then().log().all().statusCode(status);
    }

    public static ValidatableResponse getProduct(String path, int status, Long id) {
        return RestAssured
                .given().log().all()
                .config(RestAssuredConfig.newConfig().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .baseUri("https://127.0.0.1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path, id)
                .then().log().all().statusCode(status);
    }

    public static void patch(Object request, String token, String path, int status) {
        RestAssured
                .given().log().all()
                .config(RestAssuredConfig.newConfig().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .baseUri("https://127.0.0.1")
                .contentType(ContentType.JSON)
                .body(request)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().patch(path)
                .then().log().all().statusCode(status);
    }

    public static void delete(Object request, String token, String path, int status) {
        RestAssured
                .given().log().all()
                .config(RestAssuredConfig.newConfig().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .baseUri("https://127.0.0.1")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .when().delete(path)
                .then().log().all()
                .statusCode(status);
    }

    public static void deleteAllCart(String token, String path, int status) {
        RestAssured
                .given().log().all()
                .config(RestAssuredConfig.newConfig().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .baseUri("https://127.0.0.1")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when().delete(path)
                .then().log().all()
                .statusCode(status);
    }

    public static void deleteProduct(String path, int status) {
        RestAssured
                .given().log().all()
                .config(RestAssuredConfig.newConfig().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .baseUri("https://127.0.0.1")
                .contentType(ContentType.JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(path)
                .then().log().all()
                .statusCode(status);
    }
}
