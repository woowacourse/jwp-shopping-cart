package cart;

import static io.restassured.RestAssured.given;

import cart.dto.ProductDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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
                .when().get("/admin/products")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 상품을_추가한다() {
        final ProductDto productDto = new ProductDto("하디", "imageUrl", 10000);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productDto)
                .when().post("/admin/products")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 상품을_업데이트한다() {
        final ProductDto productDto = new ProductDto("하디", "image", 100000);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productDto)
                .when().post("/admin/products")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        final Long id = 1L;
        final ProductDto updatedProductDto = new ProductDto("코코닥", "imageUrl", 10000);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updatedProductDto)
                .when().patch("/admin/products/{product_id}", id)
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 상품을_삭제한다() {
        final ProductDto productDto = new ProductDto("하디", "image", 100000);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productDto)
                .when().post("/admin/products")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        final Long id = 1L;

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/admin/products/{product_id}", id)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
