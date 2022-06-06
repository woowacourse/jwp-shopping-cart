package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CreateCustomerRequest;

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

    protected String loginAndGetAccessToken(TokenRequest tokenRequest) {
        return sendLoginRequest(tokenRequest.getEmail(), tokenRequest.getPassword())
                .as(TokenResponse.class)
                .getAccessToken();
    }

    protected ExtractableResponse<Response> sendLoginRequest(String email, String password) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(email, password))
                .when().log().all()
                .post("/api/login")
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> createCustomer(
            CreateCustomerRequest customerCreateRequest) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerCreateRequest)
                .when()
                .post("/api/customers")
                .then()
                .extract();
    }
}
