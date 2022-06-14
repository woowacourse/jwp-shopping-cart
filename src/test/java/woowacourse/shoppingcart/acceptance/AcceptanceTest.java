package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@Sql("/schema.sql")
public class AcceptanceTest {

    @LocalServerPort
    int port;

    public static <T> RequestSpecification createBody(String token, T request) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .body(request);
    }

    public static ValidatableResponse requestHttpGet(String token, String uri) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .when()
                .get(uri)
                .then().log().all();
    }

    public static <T> ValidatableResponse requestHttpPost(String token, T request, String uri) {
        return createBody(token, request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(uri)
                .then().log().all();
    }

    public static <T> ValidatableResponse requestHttpPut(String token, T request, String uri) {
        return createBody(token, request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(uri)
                .then().log().all();
    }

    public static ValidatableResponse requestHttpDelete(String token, String uri) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .when()
                .delete(uri)
                .then().log().all();
    }

    public static <T> ValidatableResponse requestHttpDelete(String token, T request, String uri) {
        return createBody(token, request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(uri)
                .then().log().all();
    }

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }
}
