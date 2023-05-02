package cart;

import static cart.dto.RequestFixture.NUNU_REQUEST;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("SpellCheckingInspection")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductIntegrationTest {

    @BeforeEach
    void setUp(@LocalServerPort final int port) {
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
        //given
        final long id = 1L;

        //when
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(NUNU_REQUEST)
                .when()
                .put("/products/" + id)
                .then()
                .extract();

        //then
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
        //given
        final long id = 1L;

        //when
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(NUNU_REQUEST)
                .when()
                .post("/products")
                .then()
                .extract();

        //then
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
        //given
        final long id = 1L;

        //when
        final var result = given()
                .when()
                .delete("/products/" + id)
                .then()
                .extract();

        //then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
