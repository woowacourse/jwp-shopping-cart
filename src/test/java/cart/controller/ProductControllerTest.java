package cart.controller;

import cart.dao.ProductJdbcDao;
import cart.dto.ProductRequest;
import cart.entity.ProductEntity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {
    @Autowired
    private ProductJdbcDao productJdbcDao;

    @LocalServerPort
    private int port;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured
                .given()
                .body(new ProductRequest("비버", "a", 100L))
                .post("/products");
    }

    @Test
    @DisplayName("POST(/products)")
    void createProduct() {
        final Integer Id = productJdbcDao.insert(new ProductEntity("비버", "a", 100L));


        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .body(new ProductRequest("비버", "a", 100L))
                .when().post("/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        final Optional<ProductEntity> ProductEntity = productJdbcDao.findById(Id);

        assertAll(
                () -> assertThat(ProductEntity.get().getName()).isEqualTo("비버"),
                () -> assertThat(ProductEntity.get().getImage()).isEqualTo("a"),
                () -> assertThat(ProductEntity.get().getPrice()).isEqualTo(100L)
        );
    }

    @Test
    @DisplayName("PUT(/products/{id})")
    void updateProduct() {
        final Integer id = productJdbcDao.insert(new ProductEntity("비버", "a", 100L));

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .body(new ProductRequest("비버", "abc", 1000L))
                .when().put("/products/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        final Optional<ProductEntity> productEntity = productJdbcDao.findById(id);

        assertAll(
                () -> assertThat(productEntity.get().getName()).isEqualTo("비버"),
                () -> assertThat(productEntity.get().getImage()).isEqualTo("abc"),
                () -> assertThat(productEntity.get().getPrice()).isEqualTo(1000L)
        );
    }

    @Test
    @DisplayName("DELETE(/products/{id})")
    void deleteProduct() {
        final Integer id = productJdbcDao.insert(new ProductEntity("비버", "a", 100L));

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .when().delete("/products/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        final Optional<ProductEntity> productEntity = productJdbcDao.findById(id);

        assertThat(productEntity).isEmpty();
    }
}