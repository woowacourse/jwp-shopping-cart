package cart.controller;

import cart.auth.AuthSubjectArgumentResolver;
import cart.product.service.ProductMemoryService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    private static final String DEFAULT_PATH = "/products/";
    
    @MockBean
    private ProductMemoryService productMemoryService;
    @MockBean
    private AuthSubjectArgumentResolver resolver;
    
    private HashMap<Object, Object> productRequestMapper;
    
    @BeforeEach
    void setUp() {
        productRequestMapper = new HashMap<>();
        
        RestAssuredMockMvc.standaloneSetup(
                MockMvcBuilders.standaloneSetup(new ProductController(productMemoryService))
                        .setControllerAdvice(new GlobalExceptionHandler())
        );
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
        
        then(productMemoryService).should(only()).save(any());
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
        
        then(productMemoryService).should(only()).update(anyLong(), any());
    }
    
    private void normalInput() {
        productRequestMapper.put("name", "book");
        productRequestMapper.put("imageUrl", "abel.com");
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
        
        then(productMemoryService).should(only()).delete(anyLong());
    }
    
    @ParameterizedTest(name = "{displayName} : name = {0}")
    @NullAndEmptySource
    void 상품_저장_시_상품_이름이_null_또는_empty일_시_예외_발생(final String name) {
        // given
        productRequestMapper.put("name", name);
        productRequestMapper.put("imageUrl", "abel.com");
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
        productRequestMapper.put("imageUrl", "abel.com");
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
    
    @Test
    void 상품_저장_시_이미지_URL이_null일_시_예외_발생() {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", null);
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
    void 상품_저장_시_이미지_URL이_empty일_시_예외_발생() {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", "");
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", containsString("[ERROR] 이미지 URL의 형식이 올바르지 않습니다."))
                .body("message", containsString("[ERROR] 이미지 URL을 입력해주세요."));
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
                .body("message", containsString("[ERROR] 이미지 URL의 형식이 올바르지 않습니다."))
                .body("message", containsString("[ERROR] 이미지 URL은 255자까지 입력가능합니다."));
    }
    
    @ParameterizedTest(name = "{displayName} : imageUrl = {0}")
    @ValueSource(strings = {"http://abel.com", "https://abel.com", "http://www.abel.com", "https://www.abel.com", "www.abel.com", "abel.com"})
    void 상품_저장_시_이미지_URL_올바른_형식_입력(String imageUrl) {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", imageUrl);
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .contentType(ContentType.JSON)
                .status(HttpStatus.CREATED);
    }
    
    @ParameterizedTest(name = "{displayName} : imageUrl = {0}")
    @ValueSource(strings = {"httpa://abel.com", "htt://abel.com", "https:/www.abel.com", "abel"})
    void 상품_저장_시_이미지_URL_형식이_올바르지_않을_시_예외_처리(String imageUrl) {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", imageUrl);
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .contentType(ContentType.JSON)
                .status(HttpStatus.BAD_REQUEST)
                .body("message", is("[ERROR] 이미지 URL의 형식이 올바르지 않습니다."));
    }
    
    @Test
    void 상품_저장_시_가격이_null일_시_예외_발생() {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", "abel.com");
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
    void 상품_저장_시_가격이_0원_미만일때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", -1);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().post(DEFAULT_PATH)
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
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
        productRequestMapper.put("imageUrl", "abel.com");
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
        productRequestMapper.put("imageUrl", "abel.com");
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
        productRequestMapper.put("imageUrl", "abel.com");
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
    
    @Test
    void 상품_수정_시_이미지_URL이_null일_시_예외_발생() {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", null);
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
    void 상품_수정_시_이미지_URL이_empty일_시_예외_발생() {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", "");
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("message", containsString("[ERROR] 이미지 URL을 입력해주세요."))
                .body("message", containsString("[ERROR] 이미지 URL의 형식이 올바르지 않습니다."));
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
                .body("message", containsString("[ERROR] 이미지 URL의 형식이 올바르지 않습니다."))
                .body("message", containsString("[ERROR] 이미지 URL은 255자까지 입력가능합니다."));
    }
    
    @ParameterizedTest(name = "{displayName} : imageUrl = {0}")
    @ValueSource(strings = {"http://abel.com", "https://abel.com", "http://www.abel.com", "https://www.abel.com", "www.abel.com", "abel.com"})
    void 상품_수정_시_이미지_URL_올바른_형식_입력(String imageUrl) {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", imageUrl);
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .assertThat()
                .status(HttpStatus.NO_CONTENT);
    }
    
    @ParameterizedTest(name = "{displayName} : imageUrl = {0}")
    @ValueSource(strings = {"httpa://abel.com", "htt://abel.com", "https:/www.abel.com", "abel"})
    void 상품_수정_시_이미지_URL_형식이_올바르지_않을_시_예외_처리(String imageUrl) {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", imageUrl);
        productRequestMapper.put("price", 1000);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .contentType(ContentType.JSON)
                .status(HttpStatus.BAD_REQUEST)
                .body("message", is("[ERROR] 이미지 URL의 형식이 올바르지 않습니다."));
    }
    
    @Test
    void 상품_수정_시_가격이_null일_시_예외_발생() {
        // given
        productRequestMapper.put("name", "홍고");
        productRequestMapper.put("imageUrl", "abel.com");
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
    void 상품_수정_시_가격이_0원_미만일때_예외_발생() {
        // given
        productRequestMapper.put("name", "아벨");
        productRequestMapper.put("imageUrl", "abel.com");
        productRequestMapper.put("price", -1);
        
        // expect
        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(productRequestMapper)
                .when().put(DEFAULT_PATH + "1")
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
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
