package cart.controller;

import cart.dto.request.ProductSaveRequest;
import cart.dto.request.ProductUpdateRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductControllerAcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void save_and_update_test() {

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ProductSaveRequest("pizza", "image", 12000L))
                .when().post("/product")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ProductUpdateRequest(1L, "chicken!!!!!!", "image2", 12000L))
                .when().put("/product")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void updateProduct_실패_유효하지_않은_id() {
        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ProductUpdateRequest(-5L, "pdpd2", "image2", 15000L))
                .when().put("/product")
                .then().statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errorMessage", is("유효하지 않은 id 입니다."));
    }
}
