package cart;

import cart.dto.ProductDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 상품을_조회한다() {
        given()
                .when().get("/admin")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 상품을_추가한다() {
        final ProductDto productDto = new ProductDto("하디", "https://github.com/", 10000);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productDto)
                .when().post("/admin/products")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 상품을_업데이트한다() {
        final ProductDto productDto = new ProductDto("하디", "https://github.com/", 100000);

        final ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productDto)
                .when().post("/admin/products")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        final String location = response.header("location");
        final String[] parsedLocation = location.split("/");
        final Long id = Long.parseLong(parsedLocation[parsedLocation.length - 1]);
        final ProductDto updatedProductDto = new ProductDto("코코닥", "https://github.com/", 10000);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updatedProductDto)
                .when().put("/admin/products/{product_id}", id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 상품을_삭제한다() {
        final ProductDto productDto = new ProductDto("하디", "https://github.com/", 100000);

        final ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productDto)
                .when().post("/admin/products")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        final String location = response.header("location");
        final String[] parsedLocation = location.split("/");
        final Long id = Long.parseLong(parsedLocation[parsedLocation.length - 1]);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/admin/products/{product_id}", id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
