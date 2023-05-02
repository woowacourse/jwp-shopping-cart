package cart.presentation;

import cart.presentation.dto.ProductRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("product/create로 POST 요청을 보낼 수 있다")
    void test_create_request() {
        //when, then
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ProductRequest("주디", "https://", 1))
                .when().post("/products")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("product/update로 POST 요청을 보낼 수 있다")
    void test_update_request() {
        // given
        //when, then
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ProductRequest("테오", "https://", 1))
                .when().put("/products/" + 1)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("product/delete로 DELETE 요청을 보낼 수 있다")
    void test_delete_request() {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("name", "주디");
        body.put("url", "https://");
        body.put("price", "1");

        //when, then
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ProductRequest("주디", "https://", 1))
                .when().delete("/products/" + 1)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
