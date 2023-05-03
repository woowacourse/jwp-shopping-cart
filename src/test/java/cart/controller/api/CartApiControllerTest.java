package cart.controller.api;

import cart.controller.dto.request.ProductCreateRequest;
import cart.controller.dto.request.ProductIdRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("상품 추가")
    @Test
    void createProduct() {
        ProductIdRequest productIdRequest = new ProductIdRequest(1L);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(1L)
                .body(productIdRequest)
                .when().post("/cart/product")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

}
