package cart.integration;

import static io.restassured.RestAssured.given;

import cart.controller.dto.ProductDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("상품 리스트를 조회한다.")
    void getProducts() {
        given()
            .when()
            .get("/admin")
            .then().log().all()
            .contentType(ContentType.HTML)
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void addProduct() {
        // given
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, "KOREAN");

        // when, then
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .body(productDto)
            .post("/admin")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("상품을 수정한다.")
    @Sql("classpath:init.sql")
    void updateProduct() {
        // given
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, "KOREAN");
        addSampleProduct();

        // when, then
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .body(productDto)
            .put("/admin/{productId}", 1L)
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    @Sql("classpath:init.sql")
    void deleteProduct() {
        // given
        addSampleProduct();

        // when, then
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .delete("/admin/{productId}", 1L)
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void addSampleProduct() {
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, "KOREAN");

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .body(productDto)
            .post("/admin")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }
}
