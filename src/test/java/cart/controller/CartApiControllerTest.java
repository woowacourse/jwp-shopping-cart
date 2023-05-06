package cart.controller;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/test.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {
    private final String EMAIL = "test@email.com";
    private final String PASSWORD = "12345678";

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/cart/{productId}로 POST 요청을 보내면 HTTP 201 코드와 함께 카트에 상품이 추가되어야 한다.")
    void add_success() {
        long productId = 1L;
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart/" + productId)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/cart/2");
    }

    @Test
    @DisplayName("/cart/{productId}로 존재하지 않는 productId로 POST 요청을 보내면 HTTP 400 코드를 응답한다.")
    void add_notExistProductId() {
        long productId = 10L;
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart/" + productId)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("/cart/{cartId}로 DELETE 요청을 보내면 HTTP 200 코드와 함께 카트에 담긴 상품이 삭제되어야 한다.")
    void delete_success() {
        long cartId = 1L;
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart/" + cartId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("/cart/{cartId}로 존재하지 않는 cartId로 DELETE 요청을 보내면, HTTP 400 코드를 반환한다.")
    void delete_notExistCartId() {
        long cartId = 2L;
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart/" + cartId)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("/cart/items로 GET 요청을 보내면 HTTP 200 코드와 함께 카트에 담긴 모든 상품을 응답해야 한다.")
    void findAll_success() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/items")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .rootPath("[0]")
                .body("id", equalTo(1),
                        "name", equalTo("오감자"),
                        "price", equalTo(1000));
    }
}
