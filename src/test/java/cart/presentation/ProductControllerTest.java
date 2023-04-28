package cart.presentation;

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
        // given
        Map<String, String> body = new HashMap<>();
        body.put("id", "1");
        body.put("name", "주디");
        body.put("url", "https://");
        body.put("price", "1");

        //when, then
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/product/create")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("product/update로 POST 요청을 보낼 수 있다")
    void test_update_request() {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("id", "1");
        body.put("name", "테오");
        body.put("url", "https://");
        body.put("price", "1");

        //when, then
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/product/update")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("product/delete로 DELETE 요청을 보낼 수 있다")
    void test_delete_request() {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("id", "1");
        body.put("name", "주디");
        body.put("url", "https://");
        body.put("price", "1");

        //when, then
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().delete("/product/delete")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
