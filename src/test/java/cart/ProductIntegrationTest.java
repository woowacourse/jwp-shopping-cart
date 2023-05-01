package cart;

import static io.restassured.RestAssured.given;

import cart.domain.Product;
import cart.dto.CreateProductRequest;
import cart.dto.UpdateProductRequest;
import cart.repository.dao.ProductDao;
import cart.repository.entity.ProductEntity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @Autowired
    private ProductDao productDao;

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
        final CreateProductRequest request = new CreateProductRequest("하디", "imageUrl", 10000);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/admin/products")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 상품을_업데이트한다() {
        productDao.save(ProductEntity.from(new Product("하디", "imageUrl", 100000)));

        final Long id = 1L;
        final UpdateProductRequest request = new UpdateProductRequest("코코닥", "imageUrl", 10000);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch("/admin/products/{product_id}", id)
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 상품을_삭제한다() {
        productDao.save(ProductEntity.from(new Product("하디", "imageUrl", 100000)));

        final Long id = 1L;

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/admin/products/{product_id}", id)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
