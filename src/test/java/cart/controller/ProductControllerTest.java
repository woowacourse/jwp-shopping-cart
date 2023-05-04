package cart.controller;

import cart.database.dao.ProductDao;
import cart.entity.ProductEntity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    ProductDao productDao;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        productDao.create(new ProductEntity(null, "product1", "test", 1000));
    }

    @DisplayName("물품 조회")
    @Test
    void requestProducts() {
        RestAssured.given().log().all()
                .when().get("/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
