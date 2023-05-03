package cart.controller;

import cart.dto.ProductSaveRequestDto;
import cart.dto.ProductUpdateRequestDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    private ProductSaveRequestDto productSaveRequestDto;
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        productSaveRequestDto = new ProductSaveRequestDto("연어", "https://techblog.woowahan.com/wp-content/uploads/img/2020-04-10/pobi.png", 10000);
    }

    @DisplayName("상품 추가")
    @Test
    void createProduct() {
        given().log().uri()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productSaveRequestDto)
                .when().post("/product")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("상품이 null일 경우 예외 발생")
    @Test
    void createProduct_Exception() {
        productSaveRequestDto = new ProductSaveRequestDto(null, null, null);

        given().log().uri()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productSaveRequestDto)
                .when().post("/product")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품 전체 조회")
    @Test
    void getProducts() {
        //given
        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productSaveRequestDto)
                .when().post("/product");

        //then
        given().log().uri()
                .when().get("/products")
                .then().log().body()
                .statusCode(HttpStatus.OK.value())
                .body("name.get(0)", is("연어"))
                .body("price.get(0)", is(10000));
    }

    @DisplayName("상품 정보 업데이트")
    @Test
    void updateProduct() {
        //given
        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productSaveRequestDto)
                .when().post("/product");

        ProductUpdateRequestDto updateDto = new ProductUpdateRequestDto(1L, "오션", "", 50);

        //then
        given().log().uri()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateDto)
                .when().put("/product/{id}", 1L)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("상품 삭제")
    @Test
    void deleteProduct() {
        //given
        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productSaveRequestDto)
                .when().post("/product");

        //then
        given().log().uri()
                .when().delete("/product/{id}", 1)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
