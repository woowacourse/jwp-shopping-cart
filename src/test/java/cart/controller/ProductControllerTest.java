package cart.controller;

import cart.dto.ProductRequestDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/admin/products 로 요청을 보내 상품을 추가하는 경우, Status Created 를 반환")
    void addProduct() {
        ProductRequestDto productRequestDto = new ProductRequestDto("밋엉", 10000, "미성씨");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("/admin/products 로 요청을 보내 상품을 추가하는 경우, price 가 양수가 아닌 경우에 Bad Request 반환")
    void validProductOfPostMappingByPrice() {
        ProductRequestDto productRequestDto = new ProductRequestDto("김재연", 0, "미성씨");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("products")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("/admin/products 로 요청을 보내 상품을 추가하는 경우, imageUrl 과 name 이 null 인 경우 Bad Request 반환")
    void validProductOfPostMappingByNameAndImageUrl() {
        ProductRequestDto productRequestDto = new ProductRequestDto(null, 2, null);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/products")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("/admin/products/{id} 로 요청을 보내 상품을 수정하는 경우, Status Ok 를 반환")
    void modifyProduct() {
        ProductRequestDto productRequestDto = new ProductRequestDto("샐러드", 10000, "밋밋엉");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().put("products/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("/admin/products/{id} 로 요청을 보내 상품을 수정하는 경우, price 가 양수가 아닌 경우에 Bad Request 반환")
    void validProductOfPutMappingByPrice() {
        ProductRequestDto productRequestDto = new ProductRequestDto("샐러드", 0, "밋밋엉");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().put("products/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("/admin/products/{id} 로 요청을 보내 상품을 수정하는 경우, imageUrl 과 name 이 null 인 경우 Bad Request 반환")
    void validProductOfPutMappingByNameAndImageUrl() {
        ProductRequestDto productRequestDto = new ProductRequestDto(null, 10000, null);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().put("products/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("/admin/products/{id} 로 요청을 보내 상품을 삭제하는 경우, Status noContent 를 반환")
    void removeProduct() {
        RestAssured.given().log().all()
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().delete("products/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @AfterEach
    void afterEach() {
        jdbcTemplate.update("TRUNCATE TABLE product");
    }

}
