package cart;

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

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("멤버의 장바구니에 있는 Products를 조회한다.")
    @Test
    public void getProducts() {
        var result = given()
                .header("Authorization", "Basic YUBhLmNvbTpwYXNzd29yZDE=")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/cart/products")
                .then().log().all()
                .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("멤버의 장바구니에 Product를 추가한다.")
    @Test
    public void addProduct() {
        Map<String, String> params = new HashMap<>();
        params.put("productId", "3");

        var result = given()
                .header("Authorization", "Basic YUBhLmNvbTpwYXNzd29yZDE=")
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/cart/products")
                .then().log().all()
                .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(result.header("Location")).isNotBlank();
    }

    @DisplayName("멤버의 장바구니에 Product를 삭제한다.")
    @Test
    public void deleteProduct() {
        Map<String, String> params = new HashMap<>();
        params.put("productId", "3");

        var addResult = given()
                .header("Authorization", "Basic YUBhLmNvbTpwYXNzd29yZDE=")
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/cart/products")
                .then().log().all()
                .extract();

        var result = given()
                .header("Authorization", "Basic YUBhLmNvbTpwYXNzd29yZDE=")
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/cart/products/{productId}", 3)
                .then().log().all()
                .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
