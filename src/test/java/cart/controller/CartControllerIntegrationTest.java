package cart.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import cart.service.dto.CartRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Sql("/cart_initialize.sql")
class CartControllerIntegrationTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    private long productId = 1L;
    private long customerId = 1L;
    private String email = "baron@gmail.com";
    private String pwd = "password";

    @DisplayName("장바구니 상품 등록 API 호출 시 상품을 추가한다.")
    @Test
    void addProductToCart() {
        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartRequest(productId))
                .auth().preemptive().basic(email, pwd)
                .when()
                .post("/cart")
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("장바구니 조회 API 호출 시, 로그인 된 사용자의 장바구니가 조회된다.")
    @Test
    void viewAllCartOfCustomer() {
        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartRequest(productId))
                .auth().preemptive().basic(email, pwd)
                .when()
                .post("/cart");

        given().log().all()
                .auth().preemptive().basic(email, pwd)
                .when()
                .get("/cart/products")
                .then()
                .log().all()
                .body("size()", is(1));
    }

    @DisplayName("장바구니 상품 삭제 API 호출 시, 상품이 삭제된다.")
    @Test
    void deleteCartProduct() {
        String baseUrl = "/cart/";
        String redirectURI = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartRequest(productId))
                .auth().preemptive().basic(email, pwd)
                .when()
                .post(baseUrl)
                .then()
                .extract().response().getHeader("Location");
        long savedId = Long.parseLong(redirectURI.replace(baseUrl, ""));

        given().log().all()
                .when()
                .delete(baseUrl + savedId)
                .then()
                .log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("로그인하지 않고 장바구니 조회 시, 예외가 발생한다.")
    @Test
    void exceptionWhenNotLoginCustomer() {
        given()
                .log().all()
                .when()
                .get("/cart/products")
                .then()
                .log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}