package cart;

import cart.controller.dto.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class ProductsApiAcceptanceTest {

    private static final String NAME = "name";
    private static final int PRICE = 0;
    private static final String IMAGE = "https://i.namu.wiki/i/JP6VNIonP7Vbfs0ahYFE1NILgujcmmjemRN7lgHZEx1W7kFRcSvJ5buMh884QOP54Eg39LS7Ep-rizpE3Ny-obiZDGRHcy89Rtr4wEXzZmTW_qCORTIYmHmd9F8qavqkuXv-BvCR4zKkNwFm2MJ5LA.webp";

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @BeforeEach
    void setPort() {
        RestAssured.port = port;

        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");
    }

    @Test
    @DisplayName("상품 추가 성공 테스트")
    void insertProductSuccess_test() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProductRequest(NAME, PRICE, IMAGE))
                .when().post("/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @ParameterizedTest(name = "상품 추가시 {0} 실패 테스트")
    @MethodSource("invalidParameterProvider")
    void insertProductFail_test(final String wrongCase, final ProductRequest requestBody, final String errorMessage) {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/products")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is(errorMessage));
    }

    private static Stream<Arguments> invalidParameterProvider() {
        return Stream.of(
                Arguments.of("이름 누락",new ProductRequest(null, PRICE, IMAGE), "name 필드가 있어야 합니다."),
                Arguments.of("금액 누락",new ProductRequest(NAME, null, IMAGE), "price 필드가 있어야 합니다."),
                Arguments.of("금액 음수",new ProductRequest(NAME, -1000, IMAGE), "price는 음수가 될 수 없습니다."),
                Arguments.of("이미지 누락",new ProductRequest(NAME, PRICE, null), "imageUrl 필드가 있어야 합니다."),
                Arguments.of("이미지 주소 형식 오류",new ProductRequest(NAME, PRICE, "wrongImageSource"), "imageUrl형식에 맞지 않습니다.")
        );
    }

    @Test
    @DisplayName("상품 정보 변경 성공 테스트")
    void updateProductSuccess_test() {
        final long id = insertTestData();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProductRequest("달리", PRICE, IMAGE))
                .when().put("/products/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    private long insertTestData() {
        final MapSqlParameterSource parameterMap = new MapSqlParameterSource()
                .addValue("product_name", NAME)
                .addValue("product_price", PRICE)
                .addValue("product_image", IMAGE);
        return simpleJdbcInsert.executeAndReturnKey(parameterMap).longValue();
    }

    @ParameterizedTest(name = "상품 정보 변경 시 {0} 실패 테스트")
    @MethodSource("invalidParameterProvider")
    void updateProductFail_test(final String wrongCase, final ProductRequest requestBody, final String errorMessage) {
        final long id = insertTestData();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().put("/products/" + id)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is(errorMessage));
    }

    @Test
    @DisplayName("없는 상품 정보 변경 시 실패 테스트")
    void nonProductUpdateFail_test(){
        final long impossibleId = 0;

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProductRequest("달리", PRICE, IMAGE))
                .when().put("/products/" + impossibleId)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("해당 상품이 없습니다. 입략된 상품 id : " + impossibleId));
    }

    @Test
    @DisplayName("상품 제거 성공 테스트")
    void deleteProductSuccess_test(){
        final long id = insertTestData();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete("/products/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
