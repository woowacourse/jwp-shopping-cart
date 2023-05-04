package cart.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import cart.service.ProductService;
import cart.test.ProductRequestFixture;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql("/init.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductApiControllerTest {

    @Autowired
    ProductService productService;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("모든 상품 정보를 반환한다.")
    void findProducts() {
        final Long savedProductId = productService.registerProduct(ProductRequestFixture.request);

        RestAssured.given()
                .when()
                .get("/products")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1))
                .body("[0].id", equalTo(savedProductId.intValue()));
    }

    @Nested
    @DisplayName("상품 생성 시")
    class RegisterProduct {

        @Test
        @DisplayName("요청이 유효하다면 상품을 생성한다.")
        void registerProduct() {
            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(ProductRequestFixture.request)
                    .when()
                    .post("/products")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .header("Location", containsString("/products/"));
        }

        @Test
        @DisplayName("이미지 URL이 null이라면 400 상태를 반환한다.")
        void registerProductWithNullImage() {
            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(ProductRequestFixture.imageEmptyRequest)
                    .when()
                    .post("/products")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", containsString("이미지URL은 비어있을 수 없습니다."));
        }

        @Test
        @DisplayName("상품명의 길이가 50자를 초과하면 400 상태를 반환한다.")
        void registerProductWithOverLengthName() {
            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(ProductRequestFixture.overLengthNameRequest)
                    .when()
                    .post("/products")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", containsString("상품명은 1이상, 50이하여야 합니다."));
        }

        @Test
        @DisplayName("가격이 0보다 작으면 400 상태를 반환한다.")
        void registerProductWithLowPrice() {
            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(ProductRequestFixture.negativePriceRequest)
                    .when()
                    .post("/products")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", containsString("가격은 0원 이상 " + Integer.MAX_VALUE + "원 이하여야 합니다."));
        }

        @Test
        @DisplayName("카테고리를 선택하지 않으면 400 상태를 반환한다.")
        void registerProductWithEmptyCategory() {
            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(ProductRequestFixture.categoryNullRequest)
                    .when()
                    .post("/products")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", containsString("카테고리를 선택해야 합니다."));
        }
    }

    @Nested
    @DisplayName("상품 정보 수정 시")
    class UpdateProduct {

        @Test
        @DisplayName("유효한 상품 정보라면 상품 정보를 수정한다.")
        void updateProduct() {
            final Long savedProductId = productService.registerProduct(ProductRequestFixture.request);

            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(ProductRequestFixture.request)
                    .when()
                    .put("/products/" + savedProductId)
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        @DisplayName("상품 ID 타입으로 변환할 수 없으면 400 상태를 반환한다.")
        void updateProductWithInvalidParameterType() {
            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(ProductRequestFixture.request)
                    .when()
                    .put("/products/" + "hello")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        @DisplayName("이미지 URL이 null이라면 400 상태를 반환한다.")
        void updateProductWithNullImage() {
            final Long savedProductId = productService.registerProduct(ProductRequestFixture.request);

            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(ProductRequestFixture.imageEmptyRequest)
                    .when()
                    .put("/products/" + savedProductId)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", containsString("이미지URL은 비어있을 수 없습니다."));
        }

        @Test
        @DisplayName("상품명의 길이가 50자를 초과하면 400 상태를 반환한다.")
        void updateProductWithOverLengthName() {
            final Long savedProductId = productService.registerProduct(ProductRequestFixture.request);

            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(ProductRequestFixture.overLengthNameRequest)
                    .when()
                    .put("/products/" + savedProductId)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", containsString("상품명은 1이상, 50이하여야 합니다."));
        }

        @Test
        @DisplayName("가격이 0보다 작으면 400 상태를 반환한다.")
        void updateProductWithLowPrice() {
            final Long savedProductId = productService.registerProduct(ProductRequestFixture.request);

            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(ProductRequestFixture.negativePriceRequest)
                    .when()
                    .put("/products/" + savedProductId)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", containsString("가격은 0원 이상 " + Integer.MAX_VALUE + "원 이하여야 합니다."));
        }

        @Test
        @DisplayName("카테고리를 선택하지 않으면 400 상태를 반환한다.")
        void updateProductWithEmptyCategory() {
            final Long savedProductId = productService.registerProduct(ProductRequestFixture.request);

            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(ProductRequestFixture.categoryNullRequest)
                    .when()
                    .put("/products/" + savedProductId)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", containsString("카테고리를 선택해야 합니다."));
        }
    }

    @Nested
    @DisplayName("상품 삭제 시")
    class RemoveProduct {

        @Test
        @DisplayName("유효한 상품 ID라면 상품을 삭제한다.")
        void removeProduct() {
            final Long registeredId = productService.registerProduct(ProductRequestFixture.request);

            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .delete("/products/" + registeredId)
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        @DisplayName("상품 ID 타입으로 변환할 수 없으면 400 상태를 반환한다.")
        void removeProductWithInvalidParameterType() {
            RestAssured.given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .delete("/products/" + "hello")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
