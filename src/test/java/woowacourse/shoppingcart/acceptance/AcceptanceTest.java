package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.dto.CustomerUpdationRequest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AcceptanceTest {
    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    protected ValidatableResponse postUser(CustomerCreationRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/users")
                .then().log().all();
    }

    protected ValidatableResponse postLogin(TokenRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/login")
                .then().log().all();
    }

    protected ValidatableResponse getMe(String accessToken) {
        return RestAssured
                .given().log().all()
                .header(AuthorizationExtractor.AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + accessToken)
                .when().get("/users/me")
                .then().log().all();
    }

    protected ValidatableResponse putMe(String accessToken, CustomerUpdationRequest updateRequest) {
        return RestAssured
                .given().log().all()
                .header(AuthorizationExtractor.AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateRequest)
                .when().put("/users/me")
                .then().log().all();
    }

    protected ValidatableResponse deleteMe(String accessToken) {
        ValidatableResponse response = RestAssured
                .given().log().all()
                .header(AuthorizationExtractor.AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + accessToken)
                .when().delete("/users/me")
                .then().log().all();
        return response;
    }
}
