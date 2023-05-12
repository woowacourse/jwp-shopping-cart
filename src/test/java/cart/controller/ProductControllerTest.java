package cart.controller;

import cart.dto.ProductRequest;
import cart.service.ProductService;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(value = {
    AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class ProductControllerTest {

    @Autowired
    ProductService service;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("상품 등록 정상 등록 테스트")
    @Test
    void successPostTest() {
        ProductRequest productRequest = new ProductRequest("케로로", 1000,
            "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .when().post("/products")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("상품 등록 실패 테스트 - 이름 길이 검증")
    @Test
    void validateNameLengthTest() {
        ProductRequest productRequest = new ProductRequest("", 1000,
            "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .when().post("/products")
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body(Matchers.containsStringIgnoringCase("상품 이름은 1~20자 사이어야 합니다."));
    }

    @DisplayName("상품 등록 실패 테스트 - 가격 범위 검증")
    @Test
    void validatePriceTest() {
        ProductRequest productRequest = new ProductRequest("케로로", 10,
            "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .when().post("/products")
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body(Matchers.containsStringIgnoringCase("가격은 100원 이상이어야 합니다."));
    }

    @DisplayName("상품 등록 실패 테스트 - url 형식 검증")
    @Test
    void validateUrlTest() {
        ProductRequest productRequest = new ProductRequest("캐로로", 1000, "http형식이아닌무언가문자열");

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .when().post("/products")
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body(Matchers.containsStringIgnoringCase("이미지는 Url 형식으로 입력해 주어야 합니다."));
    }

    @DisplayName("상품 수정 요청 확인 테스트")
    @Test
    void updateProduct() {
        ProductRequest productRequest = new ProductRequest("케로로", 1000,
            "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        service.addProduct(productRequest);

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .when().patch("/products/1")
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("상품 수정 실패 확인 테스트")
    @Test
    void failUpdateProduct() {
        ProductRequest productRequest = new ProductRequest("케로로", 1000,
            "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        service.addProduct(productRequest);

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .when().patch("/products/2")
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", Matchers.equalTo("존재하지 않는 상품입니다."));
    }

    @DisplayName("상품 삭제 요청 확인 테스트")
    @Test
    void deleteProduct() {
        ProductRequest productRequest = new ProductRequest("케로로", 1000,
            "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        service.addProduct(productRequest);

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/products/1")
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("상품 삭제 요청 실패 확인 테스트")
    @Test
    void failDeleteProduct() {
        ProductRequest productRequest = new ProductRequest("케로로", 1000,
            "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        service.addProduct(productRequest);

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/products/2")
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", Matchers.equalTo("존재하지 않는 상품입니다."));
    }
}
