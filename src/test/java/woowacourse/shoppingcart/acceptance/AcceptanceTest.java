package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;

import static woowacourse.fixture.TokenFixture.BEARER;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/schema.sql", "/data.sql"})
public class AcceptanceTest {
    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    protected ExtractableResponse<Response> 로그인을_한다(final String userName, final String password) {
        TokenRequest tokenRequest = new TokenRequest(userName, password);
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/api/login")
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> 회원가입을_한다(final String userName, final String password) {
        CustomerRequest request =
                new CustomerRequest(userName, password);
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> 내_정보를_조회한다(final String tokenResponse) {
        return RestAssured
                .given().log().all()
                .header("Authorization", BEARER + tokenResponse)
                .when().get("/api/customers/me")
                .then().log().all()
                .extract();
    }

}
