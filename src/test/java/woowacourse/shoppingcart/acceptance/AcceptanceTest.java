package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignInRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/init.sql"})
@ActiveProfiles("test")
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    protected ExtractableResponse createSignInResult(final SignInRequest signInRequest, final HttpStatus httpStatus) {
        return RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(httpStatus.value())
                .extract();
    }

    protected ExtractableResponse createSignUpResult(final SignUpRequest signUpRequest) {
        return RestAssured
                .given().log().all()
                .body(signUpRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/users")
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse createCustomerInformation(final String accessToken, final HttpStatus httpStatus) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/users/me")
                .then().log().all()
                .statusCode(httpStatus.value()).extract();
    }

    protected ExtractableResponse createChangePasswordResult(final String accessToken,
                                                             final ChangePasswordRequest changePasswordRequest,
                                                             final HttpStatus httpStatus) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(changePasswordRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .patch("/users/me")
                .then().log().all()
                .statusCode(httpStatus.value())
                .extract();
    }

    protected ExtractableResponse createDeleteCustomerResult(final String accessToken,
                                                             final DeleteCustomerRequest deleteCustomerRequest,
                                                             final HttpStatus httpStatus) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(deleteCustomerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .delete("/users/me")
                .then().log().all()
                .statusCode(httpStatus.value()).extract();
    }
}
