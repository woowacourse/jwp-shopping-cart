package cart.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import cart.TestFixture;
import cart.controller.dto.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new ProductRequest("비버", TestFixture.IMAGE_BEAVER, 100L))
                .post("/products");
    }

    @Test
    @DisplayName("상품을 추가한다")
    void createProduct() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .body(new ProductRequest("비버", TestFixture.IMAGE_BEAVER, 100L))
                .when().post("/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("기 상품을 수정한다")
    void updateProduct() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .body(new ProductRequest("비버", TestFixture.IMAGE_BEAVER, 100L))
                .when().put("/products/" + getGreatestId())
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("기 상품을 삭제한다")
    void deleteProduct() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .when().delete("/products/" + getGreatestId())
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    private Integer getGreatestId() {
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM product", Integer.class);
    }
}