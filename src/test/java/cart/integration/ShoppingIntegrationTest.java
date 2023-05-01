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
public class ShoppingIntegrationTest {

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
            .get("/")
            .then().log().all()
            .contentType(ContentType.HTML)
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("단일 상품을 조회한다.")
    @Sql("classpath:init.sql")
    void getProduct() {
        // given
        addSampleProduct();

        // when, then
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/{id}", 1L)
            .then().log().all()
            .statusCode(HttpStatus.OK.value());
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
