package cart.controller;

import cart.dto.ProductCreationRequest;
import cart.dto.ProductModificationRequest;
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

    @DisplayName("상품을 수정하면 상태코드 204를 반환하는지 확인한다")
    @Test
    void putProductsTest() {
        ProductModificationRequest request = new ProductModificationRequest(1L, "pbo", "image", 10000000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/admin/products/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("상품을 삭제하면 상태코드 204를 반환하는지 확인한다")
    @Test
    void deleteProductsTest() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/admin/products/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
