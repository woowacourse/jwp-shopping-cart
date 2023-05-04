package cart.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CartControllerTest {

    private static final String EMAIL = "email1@email.com";
    private static final String PASSWORD = "email1";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("장바구니 목록을 조회한다.")
    void cartItemList() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/carts/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addCartItem() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/carts/products/3")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("장바구니에서 상품을 삭제한다.")
    void removeCartItem() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/carts/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
