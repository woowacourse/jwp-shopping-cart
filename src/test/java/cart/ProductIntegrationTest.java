package cart;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.ProductRequest;
import cart.dto.RequestFixture;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void getProducts() {
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products")
                .then()
                .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void updateProduct() {
        final long id = 1L;
        final ProductRequest productRequest = RequestFixture.NUNU_REQUEST;
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when()
                .put("/products/" + id)
                .then()
                .extract();

        assertAll(
                () -> assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result.body().jsonPath().getLong("id")).isEqualTo(1),
                () -> assertThat(result.body().jsonPath().getString("name")).isEqualTo("누누"),
                () -> assertThat(result.body().jsonPath().getString("image")).isEqualTo("naver.com"),
                () -> assertThat(result.body().jsonPath().getInt("price")).isEqualTo(1)
        );
    }

    @Test
    void createProduct() {
        final long id = 1L;
        final ProductRequest productRequest = RequestFixture.NUNU_REQUEST;
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when()
                .post("/products")
                .then()
                .extract();

        assertAll(
                () -> assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(result.body().jsonPath().getLong("id")).isEqualTo(id),
                () -> assertThat(result.body().jsonPath().getString("name")).isEqualTo("누누"),
                () -> assertThat(result.body().jsonPath().getString("image")).isEqualTo("naver.com"),
                () -> assertThat(result.body().jsonPath().getInt("price")).isEqualTo(1)
        );
    }

    @Test
    void deleteProduct() {
        final long id = 1L;
        final var result = given()
                .when()
                .delete("/products/" + id)
                .then()
                .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
