package cart.controller;

import cart.dto.ProductRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;

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
}
