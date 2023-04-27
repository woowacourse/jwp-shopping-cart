package cart.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import cart.controller.dto.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new ProductRequest("비버", "a", 100L))
                .post("/product");
    }

    @Test
    @DisplayName("POST(/product)")
    void createProduct() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .body(new ProductRequest("비버", "a", 100L))
                .when().post("/product")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("PUT(/product/{id})")
    void updateProduct() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .body(new ProductRequest("비버", "a", 100L))
                .when().put("/product/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("DELETE(/product/{id})")
    void deleteProduct() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .when().delete("/product/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}