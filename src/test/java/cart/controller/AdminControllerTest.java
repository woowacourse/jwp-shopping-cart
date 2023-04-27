package cart.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.RequestCreateProductDto;
import cart.dto.RequestUpdateProductDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 상품을_등록할_수_있다() {
        given()
            .log().all().contentType(ContentType.JSON)
            .body(new RequestCreateProductDto("치킨", 10000, "치킨 사진"))
        .when()
            .post("/admin/product")
        .then()
            .log().all()
            .statusCode(HttpStatus.OK.value());
    }


    @ParameterizedTest
    @NullAndEmptySource
    void 빈_상품을_등록할_수_없다(final String name) {
        given()
            .log().all().contentType(ContentType.JSON)
            .body(new RequestCreateProductDto(name, 10000, "치킨 사진"))
        .when()
            .post("/admin/product")
        .then()
            .log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body(containsString("상품 이름이 입력되지 않았습니다."));
    }

    @Test
    void 이름의_최대_길이를_넘긴_상품은_등록할_수_없다() {
        final String overName = "가비".repeat(50);

        given()
            .log().all().contentType(ContentType.JSON)
            .body(new RequestCreateProductDto(overName, 10000, "치킨 사진"))
        .when()
            .post("/admin/product")
        .then()
            .log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body(equalTo("상품 이름은 50자를 넘길 수 없습니다."));
    }

    @ParameterizedTest
    @NullSource
    void 가격이_빈_상품을_등록할_수_없다(final Integer price) {
        given()
            .log().all().contentType(ContentType.JSON)
            .body(new RequestCreateProductDto("치킨", price, "치킨 사진"))
        .when()
            .post("/admin/product")
        .then()
            .log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body(containsString("가격이 입력되지 않았습니다."));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MAX_VALUE, Integer.MIN_VALUE})
    void 유효한_가격_범위를_넘긴_상품은_등록할_수_없다(final Integer price) {
        given()
            .log().all().contentType(ContentType.JSON)
            .body(new RequestCreateProductDto("치킨", price, "치킨 사진"))
        .when()
            .post("/admin/product")
        .then()
            .log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body(equalTo("가격은 0 미만이거나, 1000000000 초과일 수 없습니다."));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 이미지_주소가_없는_상품을_등록할_수_없다(final String image) {
        given()
            .log().all().contentType(ContentType.JSON)
            .body(new RequestCreateProductDto("치킨", 1000, image))
        .when()
            .post("/admin/product")
        .then()
            .log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body(containsString("상품 이미지 주소가 입력되지 않았습니다."));
    }

    @Test
    void 유효한_이미지_주소_길이를_넘긴_상품은_등록할_수_없다() {
        final String image = "후추".repeat(2001);

        given()
            .log().all().contentType(ContentType.JSON)
            .body(new RequestCreateProductDto("치킨", 1_000, image))
        .when()
            .post("/admin/product")
        .then()
            .log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body(equalTo("이미지 주소는 2000자를 넘길 수 없습니다."));
    }

    @Test
    void 상품을_수정할_수_있다() {
        final Long insertedId = productDao.insert(new Product("치킨", 1_000, "치킨 사진"));

        given()
            .log().all().contentType(ContentType.JSON)
            .body(new RequestUpdateProductDto(insertedId,"피자", 10_000, "피자 사진"))
        .when()
            .put("/admin/product")
        .then()
            .log().all()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 존재하지_않는_id의_상품은_수정할_수_없다() {
        given()
            .log().all().contentType(ContentType.JSON)
            .body(new RequestUpdateProductDto(0L,"치킨", 10000, "치킨 사진"))
        .when()
            .put("/admin/product")
        .then()
            .log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 상품을_삭제할_수_있다() {
        final Long insertedId = productDao.insert(new Product("치킨", 1_000, "치킨 사진"));

        given()
            .log().all()
        .when()
            .delete("/admin/product/" + insertedId)
        .then()
            .log().all()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 존재하지_않는_id의_상품은_삭제할_수_없다() {
        given()
            .log().all()
        .when()
            .delete("/admin/product/" + 0)
        .then()
            .log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
