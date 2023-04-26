package cart;

import cart.controller.dto.ProductDto;
import cart.persistence.entity.ProductCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void shoppingController_getProducts() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void adminController_getProducts() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void adminController_addProduct() {
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(productDto)
                .post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.FOUND.value());
    }

    @Test
    public void adminController_updateProduct() {
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(productDto)
                .post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.FOUND.value());


        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(productDto)
                .put("/admin/{id}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.FOUND.value());
    }

    @Test
    public void adminController_deleteProduct() {
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(productDto)
                .post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.FOUND.value());


        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/admin/{id}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.FOUND.value());
    }
}
