package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:testSchema.sql", "classpath:testData.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    protected ExtractableResponse createSignInResult(SignInRequest signInRequest, HttpStatus httpStatus) {
        return  RestAssured
                .given()
                .log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(httpStatus.value())
                .extract();
    }

    protected ExtractableResponse createSignUpResult(SignUpRequest signUpRequest) {
        return RestAssured
                .given()
                .log().all()
                .body(signUpRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/users")
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse createCustomerInformation(String accessToken, HttpStatus httpStatus) {
        return RestAssured
                .given()
                .log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/users/me")
                .then().log().all()
                .statusCode(httpStatus.value()).extract();
    }

    protected ExtractableResponse createChangePasswordResult(String accessToken, ChangePasswordRequest changePasswordRequest, HttpStatus httpStatus) {
        return RestAssured
                .given()
                .log().all()
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

    protected ExtractableResponse createDeleteCustomerResult(String accessToken, DeleteCustomerRequest deleteCustomerRequest, HttpStatus httpStatus) {
        return RestAssured
                .given()
                .log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(deleteCustomerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .delete("/users/me")
                .then().log().all()
                .statusCode(httpStatus.value()).extract();
    }

    protected ExtractableResponse createOneProductResult(Long productId, HttpStatus httpStatus) {
        return RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products/" + productId)
                .then().log().all()
                .statusCode(httpStatus.value())
                .extract();
    }

    protected ExtractableResponse createPagedProductResult(int page, int perPage, HttpStatus httpStatus) {
        return RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products?size=" + perPage + "&page=" + page)
                .then().log().all()
                .statusCode(httpStatus.value())
                .extract();
    }

    protected ExtractableResponse findCustomerCart(String accessToken, HttpStatus httpStatus) {
        return RestAssured
                .given()
                .log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/cart")
                .then().log().all()
                .statusCode(httpStatus.value()).extract();
    }

    protected ExtractableResponse createCartItem(String accessToken, AddCartItemRequest addCartItemRequest, HttpStatus httpStatus) {
        return RestAssured
                .given()
                .log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(addCartItemRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .post("/cart")
                .then().log().all()
                .statusCode(httpStatus.value()).extract();
    }

    protected ExtractableResponse updateCartItem(String accessToken, UpdateCartItemRequest updateCartItemRequest, HttpStatus httpStatus) {
        return RestAssured
                .given()
                .log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(updateCartItemRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .patch("/cart")
                .then().log().all()
                .statusCode(httpStatus.value()).extract();
    }

    protected ExtractableResponse deleteCartItem(String accessToken, DeleteCartItemRequest deleteCartItemRequest, HttpStatus httpStatus) {
        return RestAssured
                .given()
                .log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(deleteCartItemRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .delete("/cart")
                .then().log().all()
                .statusCode(httpStatus.value()).extract();
    }

    protected ExtractableResponse deleteCart(String accessToken, HttpStatus httpStatus) {
        return RestAssured
                .given()
                .log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .delete("/cart/all")
                .then().log().all()
                .statusCode(httpStatus.value()).extract();
    }

    protected ExtractableResponse autoLogin(String accessToken, HttpStatus httpStatus) {
        return RestAssured
                .given()
                .log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/token/refresh")
                .then().log().all()
                .statusCode(httpStatus.value())
                .extract();
    }

    protected ExtractableResponse findAllProducts(HttpStatus httpStatus) {
        return RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products")
                .then().log().all()
                .statusCode(httpStatus.value())
                .extract();
    }
}
