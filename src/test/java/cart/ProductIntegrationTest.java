package cart;

import cart.controller.dto.ProductDto;
import cart.persistence.entity.ProductCategory;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @DisplayName("메인 페이지 - 상품 리스트를 조회한다")
    @Test
    void shoppingController_getProducts() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("상품 상세 페이지 - 단일 상품을 조회한다")
    @Test
    void shoppingController_getProduct() {
        // given
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN);

        // when
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(productDto)
                .post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // then
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/{id}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("관리자 메인 페이지 - 상품 리스트를 조회한다")
    @Test
    void adminController_getProducts() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("관리자 상품 추가 모달 - 상품을 추가한다")
    @Test
    void adminController_addProduct() {
        // given
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN);

        // when, then
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(productDto)
                .post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("관리자 상품 수정 모달 - 상품을 수정한다")
    @Test
    void adminController_updateProduct() {
        // given
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN);

        // when
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(productDto)
                .post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // then
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(productDto)
                .put("/admin/{id}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("관리자 상품 삭제 모달 - 상품을 삭제한다")
    @Test
    void adminController_deleteProduct() {
        // given
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN);

        // when
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(productDto)
                .post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // then
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/admin/{id}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
