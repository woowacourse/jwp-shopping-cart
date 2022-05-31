package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public abstract class AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    protected ValidatableResponse postUser(final CustomerCreationRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/users")
                .then().log().all();
    }

    protected ValidatableResponse postLogin(final LoginRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/login")
                .then().log().all();
    }

    protected ValidatableResponse getMe(final String accessToken) {
        return RestAssured
                .given().log().all()
                .header(AuthorizationExtractor.AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + accessToken)
                .when().get("/users/me")
                .then().log().all();
    }
}
