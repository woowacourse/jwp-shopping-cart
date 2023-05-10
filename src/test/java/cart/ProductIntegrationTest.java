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
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("Product를 생성한다.")
    @Test
    public void createProduct() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "치킨");
        params.put("imageUrl", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg");
        params.put("price", "1000");

        var result = given()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/products")
                .then().log().all()
                .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(result.header("Location")).isNotBlank();
    }

    @DisplayName("Product를 업데이트한다.")
    @Test
    public void putProduct() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "치킨");
        params.put("imageUrl", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg");
        params.put("price", "1000");

        var createResult = given()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/products")
                .then().log().all()
                .extract();

        char productId = createResult.header("Location").charAt(10);

        Map<String, String> updatedParams = new HashMap<>();
        updatedParams.put("name", "피자");
        updatedParams.put("imageUrl", "https://cdn.dominos.co.kr/admin/upload/goods/20210226_GYHC7RpD.jpg");
        updatedParams.put("price", "2000");

        var result = given()
                .body(updatedParams)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/products/{productId}", productId)
                .then()
                .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        Integer updatedCount = result.as(Integer.class);
        assertThat(updatedCount).isEqualTo(1);
    }

    @DisplayName("Product를 삭제한다.")
    @Test
    public void deleteProduct() {
        var result = given()
                .when()
                .delete("/products/{productId}", 1)
                .then().log().all()
                .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
