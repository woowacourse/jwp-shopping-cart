package cart.controller;

import cart.service.ProductService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    private static final String DEFAULT_PATH = "/products/";
    
    @MockBean
    private ProductService productService;
    private HashMap<Object, Object> productRequestMapper;
    
    @BeforeEach
    void setUp() {
        productRequestMapper  = new HashMap<>();
        
        RestAssuredMockMvc.standaloneSetup(
                MockMvcBuilders.standaloneSetup(new ProductController(productService))
                .setControllerAdvice(new GlobalExceptionHandler()));
    }
    
    @Test
    void 상품을_생성한다() {
        // given
        normalInput();
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .assertThat()
                .status(HttpStatus.CREATED);
        
        then(productService).should(only()).save(any());
    }
    
    @Test
    void 상품을_수정한다() {
        // given
        normalInput();
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .assertThat()
                .status(HttpStatus.NO_CONTENT);
        
        then(productService).should(only()).update(anyLong(), any());
    }
    
    private void normalInput() {
        productRequestMapper.put("name", "book");
        productRequestMapper.put("imageUrl", "aaa");
        productRequestMapper.put("price", 10_000);
    }
    
    @Test
    void 상품을_삭제한다() {
        // expect
        RestAssuredMockMvc.given().log().all()
                .when().delete(DEFAULT_PATH + "1")
                .then().log().all()
                .assertThat()
                .status(HttpStatus.NO_CONTENT);
        
        then(productService).should(only()).delete(anyLong());
    }
    
    @ParameterizedTest(name = "{displayName} : name = {0}")
    @NullAndEmptySource
    void 상품_저장_시_상품_이름이_null_또는_empty일_시_예외_발생(final String name) {
        // given
        productRequestMapper.put("name", name);
        productRequestMapper.put("imageUrl", "aaaa");
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .contentType(ContentType.JSON)
                .status(HttpStatus.BAD_REQUEST)
                .body("message", is("[ERROR] 상품 이름을 입력해주세요."));
    }
    
    @Test
    void 상품_저장_시_상품_이름_길이가_255초과일때_예외_발생() {
        // given
        productRequestMapper.put("name", "a".repeat(256));
        productRequestMapper.put("imageUrl", "aaaa");
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 상품 이름은 255자까지 입력가능합니다."));
    }
    
    @ParameterizedTest(name = "{displayName} : name = {0}")
    @NullAndEmptySource
    void 상품_저장_시_이미지_URL이_null_또는_empty일_시_예외_발생(final String imageUrl) {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", imageUrl);
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 이미지 URL을 입력해주세요."));
    }
    
    @Test
    void 상품_저장_시_이미지_URL_길이가_255초과일때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a".repeat(256));
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 이미지 URL은 255자까지 입력가능합니다."));
    }
    
    @Test
    void 상품_저장_시_가격이_null일_시_예외_발생() {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", "홍고");
        productRequestMapper.put("price", null);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격을 입력해주세요."));
    }
    
    @Test
    void 상품_저장_시_가격이_1원_미만일때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a");
        productRequestMapper.put("price", 0);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격의 최소 금액은 1원입니다."));
    }
    
    @Test
    void 상품_저장_시_가격이_천만원_초과일때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a");
        productRequestMapper.put("price", 10_000_001);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격의 최대 금액은 1000만원입니다."));
    }
    
    @Test
    void 상품_저장_시_price의_자릿수가_Integer_범위를_초과했을_때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a");
        productRequestMapper.put("price", "10000001000");
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격의 최대 금액은 1000만원입니다."));
    }
    
    @ParameterizedTest(name = "{displayName} : name = {0}")
    @NullAndEmptySource
    void 상품_수정_시_상품_이름이_null_또는_empty일_시_예외_발생(final String name) {
        // given
        productRequestMapper.put("name", name);
        productRequestMapper.put("imageUrl", "aaaa");
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 상품 이름을 입력해주세요."));
    }
    
    @Test
    void 상품_수정_시_상품_이름_길이가_255초과일때_예외_발생() {
        // given
        productRequestMapper.put("name", "a".repeat(256));
        productRequestMapper.put("imageUrl", "aaaa");
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 상품 이름은 255자까지 입력가능합니다."));
    }
    
    @ParameterizedTest(name = "{displayName} : name = {0}")
    @NullAndEmptySource
    void 상품_수정_시_이미지_URL이_null_또는_empty일_시_예외_발생(final String imageUrl) {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", imageUrl);
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 이미지 URL을 입력해주세요."));
    }
    
    @Test
    void 상품_수정_시_이미지_URL_길이가_255초과일때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a".repeat(256));
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 이미지 URL은 255자까지 입력가능합니다."));
    }
    
    @Test
    void 상품_수정_시_가격이_null일_시_예외_발생() {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", "홍고");
        productRequestMapper.put("price", null);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격을 입력해주세요."));
    }
    
    @Test
    void 상품_수정_시_가격이_1원_미만일때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a");
        productRequestMapper.put("price", 0);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격의 최소 금액은 1원입니다."));
    }
    
    @Test
    void 상품_수정_시_가격이_천만원_초과일때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a");
        productRequestMapper.put("price", 10_000_001);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격의 최대 금액은 1000만원입니다."));
    }
    
    @Test
    void 상품_수정_시_price의_자릿수가_Integer_범위를_초과했을_때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "a");
        productRequestMapper.put("price", "10000001000");
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 가격의 최대 금액은 1000만원입니다."));
    }
}
