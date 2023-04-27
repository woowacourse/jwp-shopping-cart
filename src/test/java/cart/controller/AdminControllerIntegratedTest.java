package cart.controller;

import cart.dto.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.validation.constraints.Null;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerIntegratedTest {
    @LocalServerPort
    private int port;
    
    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
    
    @Test
    void 모든_상품_목록을_관리자_페이지로_가져온다() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("관리자 페이지"));
    }
    
    @Test
    void 상품을_생성한다() {
        ProductRequest productRequest = new ProductRequest("아벨", "aaaa", 10000);
        RestAssured.given().log().all()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when().post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
    
    @Test
    void 상품을_수정한다() {
        ProductRequest productRequest = new ProductRequest("아벨", "aaaa", 10000);
        RestAssured.given().log().all()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when().put("/admin/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
    
    @Test
    void 상품을_삭제한다() {
        RestAssured.given().log().all()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/admin/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
    
    @ParameterizedTest(name = "{displayName} : name = {0}")
    @NullAndEmptySource
    void 상품_이름이_null_또는_empty일_시_예외_발생(final String name) {
        final ProductRequest productRequest = new ProductRequest(name, "asd", 10);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequest)
                .when().post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 상품 이름을 입력해주세요."));
    }
    
    @ParameterizedTest(name = "{displayName} : name = {0}")
    @NullAndEmptySource
    void 이미지_URL이_null_또는_empty일_시_예외_발생(final String imageUrl) {
        final ProductRequest productRequest = new ProductRequest("홍고", imageUrl, 10);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequest)
                .when().post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 이미지 URL을 입력해주세요."));
    }
    
    @Test
    void 가격이_null일_시_예외_발생() {
        final ProductRequest productRequest = new ProductRequest("홍고", "홍고", null);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequest)
                .when().post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격을 입력해주세요."));
    }
}
