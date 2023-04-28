package cart.presentation;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/ 로 GET 요청을 보낼 수 있다")
    void test_home() {
        //when, then
        RestAssured.given()
                .when().get("/")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("/admin 으로 GET 요청을 보낼 수 있다")
    void test_admin() {
        //when, then
        RestAssured.given()
                .when().get("/admin")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
