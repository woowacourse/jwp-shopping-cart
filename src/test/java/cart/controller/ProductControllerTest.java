package cart.controller;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.dto.ProductRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(@LocalServerPort final int port) {
        RestAssured.port = port;
    }

    @AfterEach
    void clear() {
        jdbcTemplate.execute("TRUNCATE TABLE product");
    }


    @DisplayName("POST /product")
    @Test
    void createProduct() {
        ProductRequest request = new ProductRequest("이오", 1000, null);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/product")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("PUT /product/{id}")
    @Test
    void updateProduct() {
        Long id = productDao.insert(new Product("이오", 1000, null));

        ProductRequest request = new ProductRequest("애쉬", 2000, "image");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/product/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        Product product = productDao.findById(id);

        assertAll(
                () -> assertThat(product).isNotNull(),
                () -> assertThat(product.getId()).isEqualTo(id),
                () -> assertThat(product.getName()).isEqualTo("애쉬"),
                () -> assertThat(product.getPrice()).isEqualTo(2000),
                () -> assertThat(product.getImage()).isEqualTo("image")
        );
    }

    @DisplayName("DELETE /product/{id}")
    @Test
    void deleteProduct() {
        Long id = productDao.insert(new Product("이오", 1000, null));

        RestAssured.given().log().all()
                .when().delete("/product/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        assertThat(productDao.findById(id)).isNull();
    }
}
