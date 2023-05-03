package cart.controller;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;


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

}
