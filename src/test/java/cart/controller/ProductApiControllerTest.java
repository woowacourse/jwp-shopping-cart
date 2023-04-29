package cart.controller;

import static cart.fixture.ProductRequestFixture.PRODUCT_REQUEST_A;
import static cart.fixture.ProductRequestFixture.PRODUCT_REQUEST_B;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import cart.dao.ProductDao;
import cart.dto.request.ProductRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        jdbcTemplate.update("delete from product");
    }

    @Nested
    @DisplayName("[POST] /product :")
    class PostProduct {

        @DisplayName("정상적으로 생성한다.")
        @Test
        void create_success() {
            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(PRODUCT_REQUEST_A)
                    .when().post("/product")
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("상품의 이름이 공백이거나 null이면 400을 반환한다.")
        void create_fail_by_wrong_product_name(String wrongValue) {
            ProductRequest request = new ProductRequest(wrongValue, 1000, "www.naver.com");

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/product")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("이름을 입력해주세요."));
        }

        @Test
        @DisplayName("상품 이름의 길이가 20자 이하가 아닌 경우 400을 반환한다..")
        void create_fail_by_product_name_length() {
            ProductRequest request = new ProductRequest("123456789012345678901", 1000, "www.naver.com");

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/product")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("상품의 이름은 1자 이상, 20자 이하입니다."));
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("상품의 가격이 null이면 400을 반환한다.")
        void create_fail_by_null_price(Integer wrongPrice) {
            ProductRequest request = new ProductRequest("name", wrongPrice, "www.naver.com");

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/product")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("가격을 입력해주세요."));
        }

        @ParameterizedTest
        @ValueSource(ints = {999, 0, -1000})
        @DisplayName("상품 가격이 1000원 미만이면 400을 반환한다.")
        void create_fail_by_product_wrong_price_range(Integer wrongPrice) {
            ProductRequest request = new ProductRequest("name", wrongPrice, "www.naver.com");

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/product")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("상품의 최소 가격은 1000원 이상입니다."));
        }

        @ParameterizedTest
        @ValueSource(ints = {1050, 1150})
        @DisplayName("상품 가격이 100원 단위가 아닌경우 400을 반환한다")
        void create_fail_by_product_wrong_unit_of_price(Integer wrongPrice) {
            ProductRequest request = new ProductRequest("name", wrongPrice, "www.naver.com");

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/product")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("상품의 가격 단위는 100원 단위입니다."));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("이미지 URL이 비어있거나 null이면 400을 반환한다.")
        void create_fail_by_wrong_image_url(String wrongValue) {
            ProductRequest request = new ProductRequest("name", 1000, wrongValue);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/product")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("이미지 URL을 입력해주세요."));
        }
    }

    @Nested
    @DisplayName("[PUT] /product/{id} :")
    class UpdateProduct {

        @DisplayName("정상적으로 수정한다.")
        @Test
        void update_success() {
            Long savedId = productDao.save(PRODUCT_REQUEST_A.toEntity());

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(PRODUCT_REQUEST_B)
                    .when().put("/product/" + savedId)
                    .then().log().all()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("path가 null이면 400을 반환한다.")
        void update_fail_by_null_path(String wrongValue) {
            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(PRODUCT_REQUEST_A)
                    .when().put("/product/" + wrongValue)
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @ParameterizedTest
        @ValueSource(strings = {"a", "bbbb"})
        @DisplayName("path가 숫자가 아니라면 400을 반환한다.")
        void update_fail_by_path_type_mismatch(String wrongTypeValue) {
            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(PRODUCT_REQUEST_A)
                    .when().put("/product/" + wrongTypeValue)
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", is("잘못된 타입을 입력하였습니다. 입력 타입 : class java.lang.String, 요구 타입: class java.lang.Long"));
        }

        @Test
        @DisplayName("존재하지 않는 상품 ID를 경로로 설정시 400을 반환한다.")
        void update_fail_by_not_exists_product_id() {
            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(PRODUCT_REQUEST_A)
                    .when().put("/product/" + 100000L)
                    .then().log().all()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("message", equalTo("존재하지 않는 상품입니다."));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("상품의 이름이 공백이거나 null이면 400을 반환한다.")
        void update_fail_by_wrong_product_name(String wrongValue) {
            Long savedId = productDao.save(PRODUCT_REQUEST_A.toEntity());
            ProductRequest request = new ProductRequest(wrongValue, 1000, "www.naver.com");

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/product/" + savedId)
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("이름을 입력해주세요."));
        }

        @Test
        @DisplayName("상품 이름의 길이가 20자 이하가 아닌 경우 400을 반환한다..")
        void update_fail_by_product_name_length() {
            Long savedId = productDao.save(PRODUCT_REQUEST_A.toEntity());
            ProductRequest request = new ProductRequest("123456789012345678901", 1000, "www.naver.com");

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/product/" + savedId)
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("상품의 이름은 1자 이상, 20자 이하입니다."));
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("상품의 가격이 null이면 400을 반환한다.")
        void update_fail_by_null_price(Integer wrongPrice) {
            Long savedId = productDao.save(PRODUCT_REQUEST_A.toEntity());
            ProductRequest request = new ProductRequest("name", wrongPrice, "www.naver.com");

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/product/" + savedId)
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("가격을 입력해주세요."));
        }

        @ParameterizedTest
        @ValueSource(ints = {999, 0, -1000})
        @DisplayName("상품 가격이 1000원 미만이면 400을 반환한다.")
        void update_fail_by_product_wrong_price_range(Integer wrongPrice) {
            Long savedId = productDao.save(PRODUCT_REQUEST_A.toEntity());
            ProductRequest request = new ProductRequest("name", wrongPrice, "www.naver.com");

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/product/" + savedId)
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("상품의 최소 가격은 1000원 이상입니다."));
        }

        @ParameterizedTest
        @ValueSource(ints = {1050, 1150})
        @DisplayName("상품 가격이 100원 단위가 아닌경우 400을 반환한다")
        void update_fail_by_product_wrong_unit_of_price(Integer wrongPrice) {
            Long savedId = productDao.save(PRODUCT_REQUEST_A.toEntity());
            ProductRequest request = new ProductRequest("name", wrongPrice, "www.naver.com");

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/product/" + savedId)
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("상품의 가격 단위는 100원 단위입니다."));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("이미지 URL이 비어있거나 null이면 400을 반환한다.")
        void update_fail_by_wrong_image_url(String wrongValue) {
            Long savedId = productDao.save(PRODUCT_REQUEST_A.toEntity());
            ProductRequest request = new ProductRequest("name", 1000, wrongValue);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/product/" + savedId)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("이미지 URL을 입력해주세요."));
        }
    }

    @Nested
    @DisplayName("[DELETE] /product/{id} :")
    class DeleteProduct {

        @DisplayName("정상적으로 삭제한다.")
        @Test
        void delete_success() {
            Long savedId = productDao.save(PRODUCT_REQUEST_A.toEntity());

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().delete("/product/" + savedId)
                    .then().log().all()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("path가 null이면 400을 반환한다.")
        void delete_fail_by_null_path(String nullValue) {
            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().delete("/product/" + nullValue)
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @ParameterizedTest
        @ValueSource(strings = {"a", "bbbb"})
        @DisplayName("path가 숫자가 아니라면 400을 반환한다.")
        void delete_fail_by_path_type_mismatch(String wrongTypeValue) {
            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().delete("/product/" + wrongTypeValue)
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", is("잘못된 타입을 입력하였습니다. 입력 타입 : class java.lang.String, 요구 타입: class java.lang.Long"));
        }


        @Test
        @DisplayName("존재하지 않는 상품 ID를 경로로 설정시 400을 반환한다.")
        void delete_fail_by_not_exists_product_id() {
            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().delete("/product/" + 100000L)
                    .then().log().all()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("message", equalTo("존재하지 않는 상품입니다."));
        }
    }
}
