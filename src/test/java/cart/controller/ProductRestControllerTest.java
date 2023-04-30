package cart.controller;

import cart.dto.ProductRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductRestControllerTest {

    private ProductRequest productRequest;
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        productRequest = new ProductRequest("연어", "https://techblog.woowahan.com/wp-content/uploads/img/2020-04-10/pobi.png", 10000);
    }

    @Nested
    @DisplayName("상품 추가 테스트")
    class CreateTest {
        @DisplayName("상품을 추가할 수 있다")
        @Test
        void createProduct() {
            given().log().uri()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productRequest)
                    .when().post("/products")
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @DisplayName("상품 이름이 null일 경우 예외 발생")
        @Test
        void createProduct_Exception1() {
            productRequest = new ProductRequest(null, "이미지주소", 1000);

            given().log().uri()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productRequest)
                    .when().post("/products")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("상품 이미지가 null일 경우 예외 발생")
        @Test
        void createProduct_Exception2() {
            productRequest = new ProductRequest("연어", null, 1000);

            given().log().uri()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productRequest)
                    .when().post("/products")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("상품 가격이 null일 경우 예외 발생")
        @Test
        void createProduct_Exception3() {
            productRequest = new ProductRequest("연어", "이미지주소", null);

            given().log().uri()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productRequest)
                    .when().post("/products")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @DisplayName("상품 전체 조회")
    @Test
    void getProducts() {
        //given
        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when().post("/products");

        //then
        given().log().uri()
                .when().get("/products/all")
                .then().log().body()
                .statusCode(HttpStatus.OK.value())
                .body("name.get(0)", is("연어"))
                .body("price.get(0)", is(10000));
    }

    @Nested
    @DisplayName("상품 업데이트 테스트")
    class UpdateTest {
        @DisplayName("상품 정보를 업데이트할 수 있다")
        @Test
        void updateProduct() {
            //given
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productRequest)
                    .when().post("/products");

            Integer productId = given()
                    .when().get("/products/all")
                    .then()
                    .extract()
                    .body().jsonPath().get("id.get(0)");

            ProductRequest updateDto = new ProductRequest("오션", "hi", 50);

            //then
            given().log().uri()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(updateDto)
                    .when().put("/products/{id}", productId)
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @DisplayName("업데이트 하려는 상품이 없으면 400이 반환된다")
        @Test
        void updateProduct_NotExist() {
            //given
            ProductRequest updateDto = new ProductRequest("연어", "hi", 50);

            //then
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(updateDto)
                    .when().put("/products/{id}", 0)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    @DisplayName("상품 삭제 테스트")
    class DeleteTest {
        @DisplayName("등록된 상품을 삭제할 수 있다")
        @Test
        void deleteProduct() {
            //given
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productRequest)
                    .when().post("/products");

            Integer productId = given()
                    .when().get("/products/all")
                    .then()
                    .extract()
                    .body().jsonPath().get("id[0]");

            //then
            given().log().uri()
                    .when().delete("/products/{id}", productId)
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @DisplayName("삭제하려는 상품이 없으면 400이 반환된다")
        @Test
        void deleteProduct_notExist() {
            //then
            given().log().uri()
                    .when().delete("/products/{id}", 0)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
