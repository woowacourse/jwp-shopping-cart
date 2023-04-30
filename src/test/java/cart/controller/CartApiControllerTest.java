package cart.controller;

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

import static org.hamcrest.Matchers.containsString;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {

    @Autowired
    ProductService productService;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("상품 정보를 받아서 상품을 생성한다.")
    void create() {
        //given
        //when
        //then
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
    @DisplayName("상품의 정보를 수정하여 상품을 업테이트한다.")
    void update() {
        //given
        final Long registeredId = productService.registerProduct(ProductRequestFixture.request);

        //when
        //then
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(ProductRequestFixture.updateRequest)
                .when()
                .put("/products/" + registeredId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("상품 ID를 통해 상품을 삭제한다.")
    void delete() {
        //given
        final Long registeredId = productService.registerProduct(ProductRequestFixture.request);

        //when
        //then
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/products/" + registeredId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Nested
    @DisplayName("상품을 생성시 상품 정보가 유효한지 체크한다.")
    class Create {

        @Test
        @DisplayName("이미지 URL이 null 일 때")
        void nullImageURL() {
            //given
            //when
            //then
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
        @DisplayName("상품명의 길이가 50자를 초과할 때")
        void nameOverMaxLength() {
            //given
            //when
            //then
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
        @DisplayName("가격이 0보다 작을 때")
        void priceUnderOne() {
            //given
            //when
            //then
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
        @DisplayName("중복선택한 카테고리에 없음이 존재할 때")
        void categoryEmptyUnique() {
            //given
            //when
            //then
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
}
