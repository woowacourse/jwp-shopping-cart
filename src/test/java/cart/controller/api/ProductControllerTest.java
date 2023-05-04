package cart.controller.api;

import cart.dao.product.ProductDao;
import cart.dto.ProductCreationRequest;
import cart.dto.ProductModificationRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
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
import org.springframework.test.context.jdbc.Sql;

import static cart.DummyData.DUMMY_PRODUCT_ONE;
import static cart.DummyData.INITIAL_PRODUCT_ONE;
import static cart.DummyData.INITIAL_PRODUCT_TWO;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Sql("/reset-product-data.sql")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
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
    }

    @Test
    void 상품을_등록하면_상태코드_201을_반환하는지_확인한다() {
        final ProductCreationRequest request = new ProductCreationRequest(
                DUMMY_PRODUCT_ONE.getName(),
                DUMMY_PRODUCT_ONE.getImageUrl(),
                DUMMY_PRODUCT_ONE.getPrice()
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(path)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 상품을_등록할_때_잘못된_형식의_이름이_들어오면_상태코드_400을_반환하는지_확인한다(final String nameInput) {
        final ProductCreationRequest request = new ProductCreationRequest(
                nameInput,
                DUMMY_PRODUCT_ONE.getImageUrl(),
                DUMMY_PRODUCT_ONE.getPrice()
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(path)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "test", "test:."})
    void 상품을_등록할_때_잘못된_형식의_이미지_경로가_들어오면_상태코드_400을_반환하는지_확인한다(final String imageInput) {
        final ProductCreationRequest request = new ProductCreationRequest(
                DUMMY_PRODUCT_ONE.getName(),
                imageInput,
                DUMMY_PRODUCT_ONE.getPrice()
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(path)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10_000_001})
    void 상품을_등록할_때_잘못된_범위의_가격이_들어오면_상태코드_400을_반환하는지_확인한다(final Integer priceInput) {
        final ProductCreationRequest request = new ProductCreationRequest(
                DUMMY_PRODUCT_ONE.getName(),
                DUMMY_PRODUCT_ONE.getImageUrl(),
                priceInput
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(path)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 상품_전체_목록을_조회하면_상태코드_200을_반환하는지_확인한다() {
        RestAssured.given().log().all()
                .when().get("/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("", hasSize(2))
                .body("[0].id", is(INITIAL_PRODUCT_ONE.getId().intValue()))
                .body("[0].name", is(INITIAL_PRODUCT_ONE.getName()))
                .body("[0].image", is(INITIAL_PRODUCT_ONE.getImageUrl()))
                .body("[0].price", is(INITIAL_PRODUCT_ONE.getPrice()))
                .body("[1].id", is(INITIAL_PRODUCT_TWO.getId().intValue()))
                .body("[1].name", is(INITIAL_PRODUCT_TWO.getName()))
                .body("[1].image", is(INITIAL_PRODUCT_TWO.getImageUrl()))
                .body("[1].price", is(INITIAL_PRODUCT_TWO.getPrice()));
    }

    @Test
    void 상품을_수정하면_상태코드_204를_반환하는지_확인한다() {
        final ProductModificationRequest request = new ProductModificationRequest(
                DUMMY_PRODUCT_ONE.getId(),
                DUMMY_PRODUCT_ONE.getName(),
                DUMMY_PRODUCT_ONE.getImageUrl(),
                DUMMY_PRODUCT_ONE.getPrice()
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch(path + "/" + request.getId())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 상품을_수정할_때_잘못된_형식의_이름이_들어오면_상태코드_400을_반환하는지_확인한다(final String nameInput) {
        final ProductModificationRequest request = new ProductModificationRequest(
                DUMMY_PRODUCT_ONE.getId(),
                nameInput,
                DUMMY_PRODUCT_ONE.getImageUrl(),
                DUMMY_PRODUCT_ONE.getPrice()
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch(path + "/" + request.getId())
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "test", "test:."})
    void 상품을_수정할_때_잘못된_형식의_이미지_경로가_들어오면_상태코드_400을_반환하는지_확인한다(final String imageInput) {
        final ProductModificationRequest request = new ProductModificationRequest(
                DUMMY_PRODUCT_ONE.getId(),
                DUMMY_PRODUCT_ONE.getName(),
                imageInput,
                DUMMY_PRODUCT_ONE.getPrice()
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch(path + "/" + request.getId())
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10_000_001})
    void 상품을_수정할_때_잘못된_범위의_가격이_들어오면_상태코드_400을_반환하는지_확인한다(final int priceInput) {
        final ProductModificationRequest request = new ProductModificationRequest(
                DUMMY_PRODUCT_ONE.getId(),
                DUMMY_PRODUCT_ONE.getName(),
                DUMMY_PRODUCT_ONE.getImageUrl(),
                priceInput
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch(path + "/" + request.getId())
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 상품을_삭제하면_상태코드_204를_반환하는지_확인한다() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(path + "/" + DUMMY_PRODUCT_ONE.getId())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 예상하지_못한_에러가_일어난다면_상태코드_500을_반환하는지_확인한다() {
        RestAssured.given().log().all()
                .body("{\"error\":\"error\"}")
                .when().post()
                .then().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
