package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerCreateRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/data-test.sql")
public class AcceptanceTest {
    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    public ExtractableResponse<Response> createCustomer(CustomerCreateRequest customerCreateRequest) {
        return RestAssured.given()
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .body(customerCreateRequest)
          .when()
          .post("/api/customers")
          .then()
          .extract();
    }

    public String login(CustomerCreateRequest customerCreateRequest) {
        TokenRequest tokenRequest = new TokenRequest(customerCreateRequest.getEmail(), customerCreateRequest.getPassword());
        TokenResponse tokenResponse = RestAssured.given()
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .body(tokenRequest)
          .post("/api/login")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().as(TokenResponse.class);
        return tokenResponse.getAccessToken();
    }
}
