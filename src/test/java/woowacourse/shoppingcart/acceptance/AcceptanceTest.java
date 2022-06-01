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
import woowacourse.auth.dto.SignInDto;
import woowacourse.shoppingcart.dto.SignUpDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AcceptanceTest {

    protected static final String TEST_EMAIL = "test@test.com";
    protected static final String TEST_PASSWORD = "testtest";
    protected static final String TEST_USERNAME = "test";
    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    protected void createCustomer() {
        SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signUpDto)
                .when().post("/api/customers")
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> loginCustomer(final String email, final String password) {
        final SignInDto signInDto = new SignInDto(email, password);
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signInDto)
                .when().post("/api/auth/login")
                .then().log().all()
                .extract();
    }
}
