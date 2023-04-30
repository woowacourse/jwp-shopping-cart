package cart.controller;

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

import java.util.HashMap;

import static org.hamcrest.Matchers.is;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegratedTest {
    private static final String DEFAULT_PATH = "/products/";
    
    @LocalServerPort
    private int port;
    private HashMap<String, Object> productRequestMapper;
    
    @BeforeEach
    void setUp() {
        productRequestMapper  = new HashMap<>();
        RestAssured.port = port;
    }
    
    @Test
    void 상품을_생성한다() {
        productRequestMapper.put("name", "product");
        productRequestMapper.put("imageUrl", "aaaa");
        productRequestMapper.put("price", 1000);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
    
    @Test
    void 상품을_수정한다() {
        productRequestMapper.put("name", "product");
        productRequestMapper.put("imageUrl", "aaaa");
        productRequestMapper.put("price", 1000);
        
        RestAssured.given().log().all()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
    
    @Test
    void 상품을_삭제한다() {
        RestAssured.given().log().all()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .when().delete(DEFAULT_PATH + "1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
    
    @ParameterizedTest(name = "{displayName} : name = {0}")
    @NullAndEmptySource
    void 상품_저장_시_상품_이름이_null_또는_empty일_시_예외_발생(final String name) {
        productRequestMapper.put("name", name);
        productRequestMapper.put("imageUrl", "aaaa");
        productRequestMapper.put("price", 1000);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 상품 이름을 입력해주세요."));
    }
    
    @Test
    void 상품_저장_시_상품_이름_길이가_255초과일때_예외_발생() {
        productRequestMapper.put("name", "a".repeat(256));
        productRequestMapper.put("imageUrl", "aaaa");
        productRequestMapper.put("price", 1000);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 상품 이름은 255자까지 입력가능합니다."));
    }
    
    @ParameterizedTest(name = "{displayName} : name = {0}")
    @NullAndEmptySource
    void 상품_저장_시_이미지_URL이_null_또는_empty일_시_예외_발생(final String imageUrl) {
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", imageUrl);
        productRequestMapper.put("price", 1000);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 이미지 URL을 입력해주세요."));
    }
    
    @Test
    void 상품_저장_시_이미지_URL_길이가_255초과일때_예외_발생() {
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a".repeat(256));
        productRequestMapper.put("price", 1000);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 이미지 URL은 255자까지 입력가능합니다."));
    }
    
    @Test
    void 상품_저장_시_가격이_null일_시_예외_발생() {
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", "홍고");
        productRequestMapper.put("price", null);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격을 입력해주세요."));
    }
    
    @Test
    void 상품_저장_시_가격이_1원_미만일때_예외_발생() {
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a");
        productRequestMapper.put("price", 0);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격의 최소 금액은 1원입니다."));
    }
    
    @Test
    void 상품_저장_시_가격이_천만원_초과일때_예외_발생() {
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a");
        productRequestMapper.put("price", 10_000_001);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격의 최대 금액은 1000만원입니다."));
    }
    
    @Test
    void 상품_저장_시_price의_자릿수가_Integer_범위를_초과했을_때_예외_발생() {
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a");
        productRequestMapper.put("price", "10000001000");
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격의 최대 금액은 1000만원입니다."));
    }
    
    @ParameterizedTest(name = "{displayName} : name = {0}")
    @NullAndEmptySource
    void 상품_수정_시_상품_이름이_null_또는_empty일_시_예외_발생(final String name) {
        productRequestMapper.put("name", name);
        productRequestMapper.put("imageUrl", "aaaa");
        productRequestMapper.put("price", 1000);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 상품 이름을 입력해주세요."));
    }
    
    @Test
    void 상품_수정_시_상품_이름_길이가_255초과일때_예외_발생() {
        productRequestMapper.put("name", "a".repeat(256));
        productRequestMapper.put("imageUrl", "aaaa");
        productRequestMapper.put("price", 1000);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 상품 이름은 255자까지 입력가능합니다."));
    }
    
    @ParameterizedTest(name = "{displayName} : name = {0}")
    @NullAndEmptySource
    void 상품_수정_시_이미지_URL이_null_또는_empty일_시_예외_발생(final String imageUrl) {
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", imageUrl);
        productRequestMapper.put("price", 1000);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 이미지 URL을 입력해주세요."));
    }
    
    @Test
    void 상품_수정_시_이미지_URL_길이가_255초과일때_예외_발생() {
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a".repeat(256));
        productRequestMapper.put("price", 1000);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 이미지 URL은 255자까지 입력가능합니다."));
    }
    
    @Test
    void 상품_수정_시_가격이_null일_시_예외_발생() {
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", "홍고");
        productRequestMapper.put("price", null);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격을 입력해주세요."));
    }
    
    @Test
    void 상품_수정_시_가격이_1원_미만일때_예외_발생() {
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a");
        productRequestMapper.put("price", 0);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격의 최소 금액은 1원입니다."));
    }
    
    @Test
    void 상품_수정_시_가격이_천만원_초과일때_예외_발생() {
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a");
        productRequestMapper.put("price", 10_000_001);
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격의 최대 금액은 1000만원입니다."));
    }
    
    @Test
    void 상품_수정_시_price의_자릿수가_Integer_범위를_초과했을_때_예외_발생() {
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a");
        productRequestMapper.put("price", "10000001000");
        
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격의 최대 금액은 1000만원입니다."));
    }
}
