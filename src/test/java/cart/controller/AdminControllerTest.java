package cart.controller;

import cart.dto.ProductRequestDto;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("상품 정상 등록 테스트")
    @Test
    void successPostTest() {
        ProductRequestDto productRequestDto = new ProductRequestDto("케로로", 1000, "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .when().post("/admin/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("상품 실패 테스트 - 이름 길이 검증")
    @Test
    void validateNameLengthTest() {
        ProductRequestDto productRequestDto = new ProductRequestDto("", 1000, "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .when().post("/admin/products")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(Matchers.containsStringIgnoringCase("상품 이름은 1~20자 사이어야 합니다."));
    }

    @DisplayName("상품 실패 테스트 - 가격 범위 검증")
    @Test
    void validatePriceTest() {
        ProductRequestDto productRequestDto = new ProductRequestDto("케로로", 10, "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .when().post("/admin/products")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(Matchers.containsStringIgnoringCase("가격은 100원 이상이어야 합니다."));
    }

    @DisplayName("상품 실패 테스트 - url 형식 검증")
    @Test
    void validateUrlTest() {
        ProductRequestDto productRequestDto = new ProductRequestDto("캐로로", 1000, "http형식이아닌무언가문자열");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .when().post("/admin/products")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(Matchers.containsStringIgnoringCase("이미지는 Url 형식으로 입력해 주어야 합니다."));
    }

    @DisplayName("상품 수정 요청 확인 테스트")
    @Test
    void updateProduct() {
        ProductRequestDto productRequestDto = new ProductRequestDto("케로로", 1000, "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .when().put("/admin/products/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("상품 삭제 요청 확인 테스트")
    @Test
    void deleteProduct() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/admin/products/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
