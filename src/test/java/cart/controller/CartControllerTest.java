package cart.controller;

import cart.controller.dto.AddCartRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;

@Sql(value = "/test.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("GET /cart-products 요청시 존재하는 아이디 비밀번호일 시 OK 반환")
    @Test
    void authenticateOk() {
        given().log().all()
                .auth().preemptive().basic("user1@woowa.com", "123456")
                .when()
                .get("/cart-products")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK);
    }

    @DisplayName("GET /cart-products 요청시 존재하지 않는 아이디 비밀번호일 시 unauthorized 반환")
    @Test
    void authenticateBad() {
        given().log().all()
                .auth().preemptive().basic("user1@woowa.co", "123456")
                .when()
                .get("/cart-products")
                .then().log().all()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @DisplayName("POST /cart-products 요청 시 성공하면 status Created 반환")
    @Test
    void addCartProductTest() {
        given().log().all()
                .contentType(ContentType.JSON)
                .body(new AddCartRequest(1L))
                .auth().preemptive().basic("user1@woowa.com", "123456")
                .when()
                .post("/cart-products")
                .then().log().all()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @DisplayName("DELETE /cart-products/{id} 요청 시 성공하면 status no content 반환")
    @Test
    void deleteCartProductTest() {
        given().log().all()
                .auth().preemptive().basic("user1@woowa.com", "123456")
                .when()
                .delete("/cart-products/1")
                .then().log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

}
