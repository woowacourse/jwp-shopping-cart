package cart.controller;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import cart.dto.ProductRequestDto;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwpCartControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("상품 목록 페이지를 조회한다.")
    void index() {
        RestAssured.given().log().all()
            .accept(MediaType.TEXT_HTML_VALUE)
            .when().get("/")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.TEXT_HTML_VALUE);
    }

    @Test
    @DisplayName("관리자 페이지를 조회한다.")
    void admin() {
        RestAssured.given().log().all()
            .accept(MediaType.TEXT_HTML_VALUE)
            .when().get("/admin")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.TEXT_HTML_VALUE);
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void addProduct() {
        ProductRequestDto productRequestDto = new ProductRequestDto("리오", "http://asdf.asdf", 3000);

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequestDto)
            .when().post("/admin/products")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }

    @ParameterizedTest
    @MethodSource("makeDto")
    @DisplayName("상품을 추가한다. - 잘못된 입력을 검증한다.")
    void addProductInvalidInput(ProductRequestDto productRequestDto) {

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequestDto)
            .when().post("/admin/products")
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    static Stream<Arguments> makeDto() {
        return Stream.of(Arguments.arguments(new ProductRequestDto("a".repeat(256), "https://naver.com", 1000)),
            Arguments.arguments(new ProductRequestDto("aaa", "https://naver" + "a".repeat(8001) + ".com", 1000)),
            Arguments.arguments(new ProductRequestDto("aaa", "https://naver.com", -1000)));
    }

    @Test
    @DisplayName("상품 정보를 수정한다.")
    void updateProduct() {
        ProductRequestDto productRequestDto = new ProductRequestDto("리오", "http://asdf.asdf", 3000);

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequestDto)
            .when().put("/admin/products/1")
            .then().log().all()
            .statusCode(HttpStatus.OK.value());
    }

    @ParameterizedTest
    @MethodSource("makeDto")
    @DisplayName("상품 정보를 수정한다. - 잘못된 입력을 검증한다.")
    void updateProductInvalidInput(ProductRequestDto productRequestDto) {

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequestDto)
            .when().put("/admin/products")
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteProduct() {
        RestAssured.given().log().all()
            .when().delete("/admin/products/1")
            .then().log().all()
            .statusCode(HttpStatus.OK.value());
    }
}
