package cart.controller;

import static cart.fixture.ProductFixture.PRODUCT_A;
import static cart.fixture.ProductRequestFixture.PRODUCT_REQUEST_A;
import static cart.fixture.ProductRequestFixture.PRODUCT_REQUEST_B;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.request.ProductRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductApiControllerTest {

    private static final String PRODUCT_API_URL = "/products";

    @LocalServerPort
    private int port;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Nested
    @DisplayName("[GET] /prdocuts/{id}")
    class findProduct {

        @Test
        @DisplayName("정상적으로 단일 상품을 조회한다.")
        void find_by_id_success() throws JsonProcessingException {
            Long id = productDao.save(PRODUCT_A);
            Product findProduct = productDao.findById(id);
            String expected = objectMapper.writeValueAsString(findProduct);

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get(PRODUCT_API_URL + "/" + id)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.OK.value())
                    .body(is(expected));
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("path가 null이면 400을 반환한다.")
        void find_by_id_fail_by_null_path(String nullValue) {
            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get(PRODUCT_API_URL + "/" + nullValue)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @ParameterizedTest
        @ValueSource(strings = {"a", "bbbb"})
        @DisplayName("path가 숫자가 아니라면 400을 반환한다.")
        void find_by_id_fail_by_path_type_mismatch(String wrongTypeValue) {
            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get(PRODUCT_API_URL + "/" + wrongTypeValue)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message",
                            equalTo("잘못된 타입을 입력하였습니다. 입력 타입 : class java.lang.String, 요구 타입: class java.lang.Long"));
        }

        @Test
        @DisplayName("존재하지 않는 상품 ID를 경로로 설정시 404을 반환한다.")
        void find_by_id_fail_by_not_exists_product_id() {
            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get(PRODUCT_API_URL + "/" + 100000L)
                    .then()
                    .log().all()
                    .body("message", equalTo("존재하지 않는 리소스입니다."))
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

    @Nested
    @DisplayName("[POST] /products :")
    class PostProduct {

        @DisplayName("정상적으로 생성한다.")
        @Test
        void create_success() {
            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(PRODUCT_REQUEST_A)
                    .when()
                    .post(PRODUCT_API_URL)
                    .then()
                    .header("location", notNullValue())
                    .log().all()
                    .statusCode(HttpStatus.CREATED.value());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("상품의 이름이 공백이거나 null이면 400을 반환한다.")
        void create_fail_by_wrong_product_name(String wrongValue) {
            ProductRequest request = new ProductRequest(wrongValue, 1000, "www.naver.com");

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post(PRODUCT_API_URL)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("이름을 입력해주세요."));
        }

        @Test
        @DisplayName("상품 이름의 길이가 20자 이하가 아닌 경우 400을 반환한다..")
        void create_fail_by_product_name_length() {
            ProductRequest request = new ProductRequest("123456789012345678901", 1000, "www.naver.com");

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post(PRODUCT_API_URL)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("message", equalTo("상품의 이름은 1자 이상, 20자 이하입니다."));
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("상품의 가격이 null이면 400을 반환한다.")
        void create_fail_by_null_price(Integer wrongPrice) {
            ProductRequest request = new ProductRequest("name", wrongPrice, "www.naver.com");

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post(PRODUCT_API_URL)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("가격을 입력해주세요."));
        }

        @ParameterizedTest
        @ValueSource(ints = {999, 0, -1000})
        @DisplayName("상품 가격이 1000원 미만이면 400을 반환한다.")
        void create_fail_by_product_wrong_price_range(Integer wrongPrice) {
            ProductRequest request = new ProductRequest("name", wrongPrice, "www.naver.com");

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post(PRODUCT_API_URL)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("message", equalTo("상품의 최소 가격은 1000원 이상입니다."));
        }

        @ParameterizedTest
        @ValueSource(ints = {1050, 1150})
        @DisplayName("상품 가격이 100원 단위가 아닌경우 400을 반환한다")
        void create_fail_by_product_wrong_unit_of_price(Integer wrongPrice) {
            ProductRequest request = new ProductRequest("name", wrongPrice, "www.naver.com");

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post(PRODUCT_API_URL)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("message", equalTo("상품의 가격 단위는 100원 단위입니다."));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("이미지 URL이 비어있거나 null이면 400을 반환한다.")
        void create_fail_by_wrong_image_url(String wrongValue) {
            ProductRequest request = new ProductRequest("name", 1000, wrongValue);

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post(PRODUCT_API_URL)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("이미지 URL을 입력해주세요."));
        }
    }

    @Nested
    @DisplayName("[PUT] /products/{id} :")
    class UpdateProduct {

        @DisplayName("정상적으로 수정한다.")
        @Test
        void update_success() {
            Long savedId = productDao.save(PRODUCT_A);

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(PRODUCT_REQUEST_B)
                    .when()
                    .put(PRODUCT_API_URL + "/" + savedId)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("path가 null이면 400을 반환한다.")
        void update_fail_by_null_path(String wrongValue) {
            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(PRODUCT_REQUEST_A)
                    .when()
                    .put(PRODUCT_API_URL + "/" + wrongValue)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @ParameterizedTest
        @ValueSource(strings = {"a", "bbbb"})
        @DisplayName("path가 숫자가 아니라면 400을 반환한다.")
        void update_fail_by_path_type_mismatch(String wrongTypeValue) {
            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(PRODUCT_REQUEST_A)
                    .when()
                    .put(PRODUCT_API_URL + "/" + wrongTypeValue)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message",
                            is("잘못된 타입을 입력하였습니다. 입력 타입 : class java.lang.String, 요구 타입: class java.lang.Long"));
        }

        @Test
        @DisplayName("존재하지 않는 상품 ID를 경로로 설정시 404을 반환한다.")
        void update_fail_by_not_exists_product_id() {
            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(PRODUCT_REQUEST_A)
                    .when()
                    .put(PRODUCT_API_URL + "/" + 100000L)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("message", equalTo("존재하지 않는 리소스입니다."));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("상품의 이름이 공백이거나 null이면 400을 반환한다.")
        void update_fail_by_wrong_product_name(String wrongValue) {
            Long savedId = productDao.save(PRODUCT_A);
            ProductRequest request = new ProductRequest(wrongValue, 1000, "www.naver.com");

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .put(PRODUCT_API_URL + "/" + savedId)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("이름을 입력해주세요."));
        }

        @Test
        @DisplayName("상품 이름의 길이가 20자 이하가 아닌 경우 400을 반환한다..")
        void update_fail_by_product_name_length() {
            Long savedId = productDao.save(PRODUCT_A);
            ProductRequest request = new ProductRequest("123456789012345678901", 1000, "www.naver.com");

            given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .put(PRODUCT_API_URL + "/" + savedId)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("message", equalTo("상품의 이름은 1자 이상, 20자 이하입니다."));
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("상품의 가격이 null이면 400을 반환한다.")
        void update_fail_by_null_price(Integer wrongPrice) {
            Long savedId = productDao.save(PRODUCT_A);
            ProductRequest request = new ProductRequest("name", wrongPrice, "www.naver.com");

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .put(PRODUCT_API_URL + "/" + savedId)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("가격을 입력해주세요."));
        }

        @ParameterizedTest
        @ValueSource(ints = {999, 0, -1000})
        @DisplayName("상품 가격이 1000원 미만이면 400을 반환한다.")
        void update_fail_by_product_wrong_price_range(Integer wrongPrice) {
            Long savedId = productDao.save(PRODUCT_A);
            ProductRequest request = new ProductRequest("name", wrongPrice, "www.naver.com");

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .put(PRODUCT_API_URL + "/" + savedId)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("message", equalTo("상품의 최소 가격은 1000원 이상입니다."));
        }

        @ParameterizedTest
        @ValueSource(ints = {1050, 1150})
        @DisplayName("상품 가격이 100원 단위가 아닌경우 400을 반환한다")
        void update_fail_by_product_wrong_unit_of_price(Integer wrongPrice) {
            Long savedId = productDao.save(PRODUCT_A);
            ProductRequest request = new ProductRequest("name", wrongPrice, "www.naver.com");

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .put(PRODUCT_API_URL + "/" + savedId)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("message", equalTo("상품의 가격 단위는 100원 단위입니다."));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("이미지 URL이 비어있거나 null이면 400을 반환한다.")
        void update_fail_by_wrong_image_url(String wrongValue) {
            Long savedId = productDao.save(PRODUCT_A);
            ProductRequest request = new ProductRequest("name", 1000, wrongValue);

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .put(PRODUCT_API_URL + "/" + savedId)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", equalTo("이미지 URL을 입력해주세요."));
        }
    }

    @Nested
    @DisplayName("[DELETE] /products/{id} :")
    class DeleteProduct {

        @DisplayName("정상적으로 삭제한다.")
        @Test
        void delete_success() {
            Long savedId = productDao.save(PRODUCT_A);

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .delete(PRODUCT_API_URL + "/" + savedId)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("path가 null이면 400을 반환한다.")
        void delete_fail_by_null_path(String nullValue) {
            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .delete(PRODUCT_API_URL + "/" + nullValue)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @ParameterizedTest
        @ValueSource(strings = {"a", "bbbb"})
        @DisplayName("path가 숫자가 아니라면 400을 반환한다.")
        void delete_fail_by_path_type_mismatch(String wrongTypeValue) {
            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .delete(PRODUCT_API_URL + "/" + wrongTypeValue)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message",
                            is("잘못된 타입을 입력하였습니다. 입력 타입 : class java.lang.String, 요구 타입: class java.lang.Long"));
        }


        @Test
        @DisplayName("존재하지 않는 상품 ID를 경로로 설정시 404를 반환한다.")
        void delete_fail_by_not_exists_product_id() {
            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .delete(PRODUCT_API_URL + "/" + 100000L)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("message", equalTo("존재하지 않는 리소스입니다."));
        }
    }
}
