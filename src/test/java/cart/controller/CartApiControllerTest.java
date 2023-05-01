package cart.controller;

import cart.service.ProductService;
import cart.test.ProductRequestFixture;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Base64;

import static org.hamcrest.Matchers.containsString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {

    @Autowired
    private ProductService productService;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Nested
    @DisplayName("장바구니에 상품을 담을 시")
    class PutInCart {

        @Test
        @DisplayName("멤버, 상품이 유효하다면 장바구니에 추가한다.")
        void putInCart() {
            final String encodedAuth = new String(Base64.getEncoder().encode("a@a.com:password1".getBytes()));
            final Long savedProductId = productService.registerProduct(ProductRequestFixture.request);

            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Basic " + encodedAuth)
                    .queryParam("productId", savedProductId)
                    .when()
                    .post("/carts")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .header("Location", containsString("/carts/"));
        }

        @Test
        @DisplayName("유효하지 않은 멤버 인증이라면 예외를 던진다.")
        void putInCartWithInvalidAuth() {
            final String encodedAuth = new String(Base64.getEncoder().encode("a@a.com".getBytes()));
            final Long savedProductId = productService.registerProduct(ProductRequestFixture.request);

            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Basic " + encodedAuth)
                    .queryParam("productId", savedProductId)
                    .when()
                    .post("/carts")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    @DisplayName("장바구니 목록 조회 시")
    class FindCartProductsByMember {

        @Test
        @DisplayName("멤버 정보가 유효하다면 장바구니 목록을 조회한다.")
        void findCartProductsByMember() {
            final String encodedAuth = new String(Base64.getEncoder().encode("a@a.com:password1".getBytes()));

            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Basic " + encodedAuth)
                    .when()
                    .get("/carts")
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        @DisplayName("멤버 정보가 유효하지 않으면 예외를 던진다.")
        void findCartProductsByInvalidMember() {
            final String encodedAuth = new String(Base64.getEncoder().encode("a@a.com".getBytes()));

            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Basic " + encodedAuth)
                    .when()
                    .get("/carts")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
