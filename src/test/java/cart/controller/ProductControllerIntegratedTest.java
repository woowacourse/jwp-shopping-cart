package cart.controller;

import cart.config.DBTransactionExecutor;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegratedTest {
    private static final String DEFAULT_PATH = "/products/";
    
    @LocalServerPort
    private int port;
    
    @RegisterExtension
    private DBTransactionExecutor dbTransactionExecutor;
    
    private HashMap<String, Object> productRequestMapper;
    
    @Autowired
    public ProductControllerIntegratedTest(final JdbcTemplate jdbcTemplate) {
        this.dbTransactionExecutor = new DBTransactionExecutor(jdbcTemplate);
    }
    
    @BeforeEach
    void setUp() {
        productRequestMapper  = new HashMap<>();
        RestAssured.port = port;
    }
    
    @Test
    void 상품을_생성한다() {
        // given
        productRequestMapper.put("name", "product");
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.CREATED.value());
    }
    
    @Test
    void 상품을_수정한다() {
        // given
        final Integer id = saveAndGetProductId();
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
    
    @Test
    void 상품을_삭제한다() {
        // given
        final Integer id = saveAndGetProductId();
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete(DEFAULT_PATH + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
    
    private Integer saveAndGetProductId() {
        // given
        productRequestMapper.put("name", "product");
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", 1000);
        
        // expect
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .extract()
                .response()
                .path("id");
    }
    
    @Test
    void 상품을_수정할_시_존재하지_않는_product_id를_전달하면_예외가_발생한다() {
        // given
        productRequestMapper.put("name", "product");
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + 1)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 존재하지 않는 product id 입니다."));
    }
    
    @Test
    void 상품을_삭제할_시_존재하지_않는_product_id를_전달하면_예외가_발생한다() {
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete(DEFAULT_PATH + 1)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 존재하지 않는 product id 입니다."));
    }
    
    @ParameterizedTest(name = "{displayName} : name = {0}")
    @NullAndEmptySource
    void 상품_저장_시_상품_이름이_null_또는_empty일_시_예외_발생(final String name) {
        // given
        productRequestMapper.put("name", name);
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", 1000);
        
        // expect
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
        // given
        productRequestMapper.put("name", "a".repeat(256));
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 상품 이름은 255자까지 입력가능합니다."));
    }
    
    @Test
    void 상품_저장_시_이미지_URL이_null일_시_예외_발생() {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", null);
        productRequestMapper.put("price", 1000);
        
        // expect
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
    void 상품_저장_시_이미지_URL이_empty일_시_예외_발생() {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", "");
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", containsString("[ERROR] 이미지 URL을 입력해주세요."))
                .body("message", containsString("[ERROR] 이미지 URL의 형식이 올바르지 않습니다."));
    }
    
    @Test
    void 상품_저장_시_이미지_URL_길이가_255초과일때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a".repeat(256) + ".com");
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", containsString("[ERROR] 이미지 URL은 255자까지 입력가능합니다."))
                .body("message", containsString("[ERROR] 이미지 URL의 형식이 올바르지 않습니다."));
    }
    
    @ParameterizedTest(name = "{displayName} : imageUrl = {0}")
    @ValueSource(strings = {"http://abel.com", "https://abel.com", "http://www.abel.com", "https://www.abel.com", "www.abel.com", "abel.com"})
    void 상품_저장_시_이미지_URL_올바른_형식_입력(String imageUrl) {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", imageUrl);
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.CREATED.value());
    }
    
    @ParameterizedTest(name = "{displayName} : imageUrl = {0}")
    @ValueSource(strings = {"httpa://abel.com", "htt://abel.com", "https:/www.abel.com", "abel"})
    void 상품_저장_시_이미지_URL_형식이_올바르지_않을_시_예외_처리(String imageUrl) {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", imageUrl);
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("[ERROR] 이미지 URL의 형식이 올바르지 않습니다."));
    }
    
    @Test
    void 상품_저장_시_가격이_null일_시_예외_발생() {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", null);
        
        // expect
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
    void 상품_저장_시_가격이_0원_미만일때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", -1);
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격의 최소 금액은 0원입니다."));
    }
    
    @Test
    void 상품_저장_시_가격이_천만원_초과일때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", 10_000_001);
        
        // expect
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
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", "10000001000");
        
        // expect
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
        // given
        productRequestMapper.put("name", name);
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", 1000);
        
        // expect
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
        // given
        productRequestMapper.put("name", "a".repeat(256));
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 상품 이름은 255자까지 입력가능합니다."));
    }
    
    @Test
    void 상품_수정_시_이미지_URL이_null일_시_예외_발생() {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", null);
        productRequestMapper.put("price", 1000);
        
        // expect
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
    void 상품_수정_시_이미지_URL이_empty일_시_예외_발생() {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", "");
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", containsString("[ERROR] 이미지 URL을 입력해주세요."))
                .body("message", containsString("[ERROR] 이미지 URL의 형식이 올바르지 않습니다."));
    }
    
    @Test
    void 상품_수정_시_이미지_URL_길이가_255초과일때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a".repeat(256) + ".com");
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", containsString("[ERROR] 이미지 URL의 형식이 올바르지 않습니다."))
                .body("message", containsString("[ERROR] 이미지 URL은 255자까지 입력가능합니다."));
    }
    
    @ParameterizedTest(name = "{displayName} : imageUrl = {0}")
    @ValueSource(strings = {"http://abel.com", "https://abel.com", "http://www.abel.com", "https://www.abel.com", "www.abel.com", "abel.com"})
    void 상품_수정_시_이미지_URL_올바른_형식_입력(String imageUrl) {
        // given
        final Integer productId = saveAndGetProductId();
        
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", imageUrl);
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + productId)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
    
    @ParameterizedTest(name = "{displayName} : imageUrl = {0}")
    @ValueSource(strings = {"httpa://abel.com", "htt://abel.com", "https:/www.abel.com", "abel"})
    void 상품_수정_시_이미지_URL_형식이_올바르지_않을_시_예외_처리(String imageUrl) {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", imageUrl);
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("[ERROR] 이미지 URL의 형식이 올바르지 않습니다."));
    }
    
    @Test
    void 상품_수정_시_가격이_null일_시_예외_발생() {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", null);
        
        // expect
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
    void 상품_수정_시_가격이_0원_미만일때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", -1);
        
        // expect
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격의 최소 금액은 0원입니다."));
    }
    
    @Test
    void 상품_수정_시_가격이_천만원_초과일때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", 10_000_001);
        
        // expect
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
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", "10000001000");
        
        // expect
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
