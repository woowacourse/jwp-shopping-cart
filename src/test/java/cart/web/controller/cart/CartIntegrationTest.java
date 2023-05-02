package cart.web.controller.cart;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("카트 페이지를 정상적으로 반환한다.")
    @Test
    void renderCart() {
        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML);
    }
}
