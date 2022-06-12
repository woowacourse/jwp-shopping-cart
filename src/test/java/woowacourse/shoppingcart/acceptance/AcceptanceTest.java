package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.SignInRequestDto;
import woowacourse.shoppingcart.dto.SignUpDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class AcceptanceTest {
    public static final Header EMPTY_HEADER = new Header("", "");

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    protected ExtractableResponse<Response> createCustomer(final SignUpDto signUpDto) {
        return post("/api/customers", EMPTY_HEADER, signUpDto);
    }

    protected ExtractableResponse<Response> loginCustomer(final String email, final String password) {
        final SignInRequestDto signInDto = new SignInRequestDto(email, password);
        return post("/api/auth/login", EMPTY_HEADER, signInDto);
    }

    protected ExtractableResponse<Response> post(final String uri, final Header header, final Object body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .body(body)
                .when().post(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> get(final String uri, final Header header) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .when().get(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> put(final String uri, final Header header, final Object body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .body(body)
                .when().put(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> patch(final String uri, final Header header, final Object body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .body(body)
                .when().patch(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> delete(final String uri, final Header header) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .when().delete(uri)
                .then().log().all()
                .extract();
    }
}
