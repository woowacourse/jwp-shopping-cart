package cart.controller.api;

import cart.dao.product.ProductDao;
import cart.dto.ProductCreationRequest;
import cart.dto.ProductModificationRequest;
import cart.domain.product.ProductEntity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import static cart.DummyData.dummyId;
import static cart.DummyData.dummyImage;
import static cart.DummyData.dummyName;
import static cart.DummyData.dummyPrice;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    private static final String path = "/products";

    @Autowired
    ProductDao productDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        resetTable();
    }

    @DisplayName("상품 전체 목록을 조회하면 상태코드 200을 반환하는지 확인한다")
    @Test
    void getProductTest() {
        RestAssured.given().log().all()
                .when().get("/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("", hasSize(2))
                .body("[0].id", is(1))
                .body("[0].name", is("mouse"))
                .body("[0].image", is("https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg"))
                .body("[0].price", is(100_000))
                .body("[1].id", is(2))
                .body("[1].name", is("keyboard"))
                .body("[1].image", is("https://i1.wp.com/blog.peoplefund.co.kr/wp-content/uploads/2020/01/진혁.jpg?fit=770%2C418&ssl=1"))
                .body("[1].price", is(250_000));
    }

    @DisplayName("상품을 등록하면 상태코드 201을 반환하는지 확인한다")
    @Test
    void postProductsTest() {
        final ProductCreationRequest request
                = new ProductCreationRequest(dummyImage, dummyImage, dummyPrice);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(path)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("상품을 등록할 때 잘못된 형식의 이름이 들어오면 상태코드 400을 반환하는지 확인한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void throwExceptionWhenInvalidNamePostProductsTest(final String nameInput) {
        final ProductCreationRequest request
                = new ProductCreationRequest(nameInput, dummyImage, dummyPrice);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(path)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품을 등록할 때 잘못된 형식의 이미지 경로가 들어오면 상태코드 400을 반환하는지 확인한다")
    @ParameterizedTest
    @ValueSource(strings = {" ", "test", "test:."})
    void throwExceptionWhenInvalidPricePostProductsTest(final String imageInput) {
        final ProductCreationRequest request
                = new ProductCreationRequest(dummyName, imageInput, dummyPrice);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(path)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품을 등록할 때 잘못된 범위의 가격이 들어오면 상태코드 400을 반환하는지 확인한다")
    @ParameterizedTest
    @ValueSource(ints = {0, 10_000_001})
    void throwExceptionWhenInvalidPricePostProductsTest(final Integer priceInput) {
        final ProductCreationRequest request
                = new ProductCreationRequest(dummyName, dummyImage, priceInput);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(path)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품을 수정하면 상태코드 204를 반환하는지 확인한다")
    @Test
    void patchProductsTest() {
        final ProductModificationRequest request
                = new ProductModificationRequest(dummyId, dummyName, dummyImage, dummyPrice);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch(path + "/" + request.getId())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("상품을 수정할 때 잘못된 형식의 이름이 들어오면 상태코드 400을 반환하는지 확인한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void throwExceptionWhenInvalidNamePatchProductsTest(final String nameInput) {
        final ProductModificationRequest request
                = new ProductModificationRequest(dummyId, nameInput, dummyImage, dummyPrice);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch(path + "/" + request.getId())
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품을 수정할 때 잘못된 형식의 이미지 경로가 들어오면 상태코드 400을 반환하는지 확인한다")
    @ParameterizedTest
    @ValueSource(strings = {" ", "test", "test:."})
    void throwExceptionWhenInvalidPricePatchProductsTest(final String imageInput) {
        final ProductModificationRequest request
                = new ProductModificationRequest(dummyId, dummyName, imageInput, dummyPrice);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch(path + "/" + request.getId())
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품을 수정할 때 잘못된 범위의 가격이 들어오면 상태코드 400을 반환하는지 확인한다")
    @ParameterizedTest
    @ValueSource(ints = {0, 10_000_001})
    void throwExceptionWhenInvalidPricePatchProductsTest(final int priceInput) {
        final ProductModificationRequest request
                = new ProductModificationRequest(dummyId, dummyName, dummyImage, priceInput);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch(path + "/" + request.getId())
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품을 삭제하면 상태코드 204를 반환하는지 확인한다")
    @Test
    void deleteProductsTest() {
        final long id = 1L;

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(path + "/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("예상하지 못한 에러가 일어난다면 상태코드 500을 반환하는지 확인한다")
    @Test
    void exceptionInternalServerErrorProductsTest() {
        RestAssured.given().log().all()
                .body("{\"error\":\"error\"}")
                .when().post()
                .then().statusCode(500);
    }

    private void resetTable() {
        final String deleteSql = "DELETE FROM product";
        jdbcTemplate.update(deleteSql);

        final String initializeIdSql = "ALTER TABLE product ALTER COLUMN ID RESTART WITH 1";
        jdbcTemplate.update(initializeIdSql);

        productDao.insert(ProductEntity.of("mouse", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 100000));
        productDao.insert(ProductEntity.of("keyboard", "https://i1.wp.com/blog.peoplefund.co.kr/wp-content/uploads/2020/01/진혁.jpg?fit=770%2C418&ssl=1", 250000));
    }
}