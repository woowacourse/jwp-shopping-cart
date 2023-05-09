package cart.controller;

import cart.controller.dto.response.ProductResponse;
import cart.database.dao.ProductDao;
import cart.entity.ProductEntity;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    ProductDao productDao;

    @LocalServerPort
    int port;

    private String productName;
    private String imageUrl;
    private int price;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        productName = "product1";
        imageUrl = "test";
        price = 1000;
        productDao.create(new ProductEntity(0, productName, imageUrl, price));
    }

    @DisplayName("물품 조회")
    @Test
    void requestProducts() {
        ValidatableResponse response = RestAssured.given().log().all()
                .when().get("/products")
                .then().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .statusCode(HttpStatus.OK.value());

        List<ProductResponse> productResponses = response.extract().body().jsonPath().getList("", ProductResponse.class);
        ProductResponse lastOne = productResponses.get(productResponses.size() - 1);
        assertAll(
                () -> assertEquals(productName, lastOne.getName()),
                () -> assertEquals(imageUrl, lastOne.getImageUrl()),
                () -> assertEquals(price, lastOne.getPrice())
        );
    }
}
