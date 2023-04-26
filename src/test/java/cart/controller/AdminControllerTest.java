package cart.controller;

import cart.dto.ProductCreationRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("상품 전체 목록을 조회하면 상태코드 200을 반환하는지 확인한다")
    @Test
    void getAdminTest() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("상품을 등록하면 상태코드 201을 반환하는지 확인한다")
    @Test
    void postProductsTest() {
        ProductCreationRequest request = new ProductCreationRequest("pbo", "image", 10000000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/admin/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}
