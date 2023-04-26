package cart.controller;

import cart.controller.dto.ProductRequestDto;
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

import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class AdminApiControllerTest {

    private static final String NAME = "name";
    private static final int PRICE = 0;
    private static final String IMAGE = "https://i.namu.wiki/i/JP6VNIonP7Vbfs0ahYFE1NILgujcmmjemRN7lgHZEx1W7kFRcSvJ5buMh884QOP54Eg39LS7Ep-rizpE3Ny-obiZDGRHcy89Rtr4wEXzZmTW_qCORTIYmHmd9F8qavqkuXv-BvCR4zKkNwFm2MJ5LA.webp";

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setPort() {
        RestAssured.port = port;

        jdbcTemplate.execute("truncate table products");
        jdbcTemplate.execute("alter table products alter id restart with 1");
    }

    @Test
    @DisplayName("상품 추가 성공 테스트")
    void insertProductSuccess_test() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto(NAME, PRICE, IMAGE))
                .when().post("/admin/product")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @ParameterizedTest(name = "상품 추가시 {0} 실패 테스트")
    @MethodSource("invalidParameterProvider")
    void insertProductFail_test(final String wrongCase, final ProductRequestDto requestBody, final String errorMessage) {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/admin/product")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is(errorMessage));
    }

    private static Stream<Arguments> invalidParameterProvider() {
        return Stream.of(
                Arguments.of("이름 누락",new ProductRequestDto(null, PRICE, IMAGE), "name 필드가 있어야 합니다."),
                Arguments.of("금액 누락",new ProductRequestDto(NAME, null, IMAGE), "price 필드가 있어야 합니다."),
                Arguments.of("금액 음수",new ProductRequestDto(NAME, -1000, IMAGE), "price는 음수가 될 수 없습니다."),
                Arguments.of("이미지 누락",new ProductRequestDto(NAME, PRICE, null), "image 필드가 있어야 합니다."),
                Arguments.of("이미지 주소 형식 오류",new ProductRequestDto(NAME, PRICE, "wrongImageSource"), "image가 url형식에 맞지 않습니다.")
        );
    }

    @Test
    @DisplayName("상품 정보 변경 성공 테스트")
    void updateProductSuccess_test() {
        insertTestData();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto("달리", PRICE, IMAGE))
                .when().put("/admin/product/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    private void insertTestData() {
        final String sql = "insert into products(product_name, product_price, product_image) values (?, ?, ?)";
        jdbcTemplate.update(sql, NAME, PRICE, IMAGE);
    }

    @ParameterizedTest(name = "상품 정보 변경 시 {0} 실패 테스트")
    @MethodSource("invalidParameterProvider")
    void updateProductFail_test(final String wrongCase, final ProductRequestDto requestBody, final String errorMessage) {
        insertTestData();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().put("/admin/product/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is(errorMessage));
    }

    @Test
    @DisplayName("없는 상품 정보 변경 시 실패 테스트")
    void nonProductUpdateFail_test(){
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto("달리", PRICE, IMAGE))
                .when().put("/admin/product/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("해당 상품이 없습니다."));
    }

    @Test
    @DisplayName("상품 제거 성공 테스트")
    void deleteProductSuccess_test(){
        insertTestData();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete("/admin/product/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
