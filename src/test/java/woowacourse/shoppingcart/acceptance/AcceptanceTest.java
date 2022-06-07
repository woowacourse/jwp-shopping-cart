package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    protected static ExtractableResponse<Response> post(String uri, Object param) {
        return RestAssured.given().log().all()
                .body(param)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(uri)
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> post(String uri, String token, Object param) {
        return RestAssured.given().log().all()
                .body(param)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .header(new Header("Authorization", "BEARER " + token))
                .post(uri)
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> get(String uri) {
        return RestAssured.given().log().all()
                .when()
                .get(uri)
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> get(String uri, String token) {
        return RestAssured.given().log().all()
                .when()
                .header(new Header("Authorization", "BEARER " + token))
                .get(uri)
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> put(String uri, Object param) {
        return RestAssured.given().log().all()
                .body(param)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(uri)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> put(String uri, String token, Object param) {
        return RestAssured.given().log().all()
                .header(new Header("Authorization", "BEARER " + token))
                .body(param)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(uri)
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> delete(String uri) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(uri)
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> delete(String uri, Object param) {
        return RestAssured.given().log().all()
                .body(param)
                .contentType(ContentType.JSON)
                .when()
                .delete(uri)
                .then().log().all()
                .extract();
    }

    protected static ExtractableResponse<Response> delete(String uri, String token, Object param) {
        return RestAssured.given().log().all()
                .header(new Header("Authorization", "BEARER " + token))
                .body(param)
                .contentType(ContentType.JSON)
                .when()
                .delete(uri)
                .then().log().all()
                .extract();
    }
}
