package cart.controller;

import cart.dto.RequestCreateProductDto;
import cart.dto.RequestUpdateProductDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
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
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductApiControllerTest {

    private static final SqlParameterSource PARAMS = new MapSqlParameterSource()
            .addValue("name", "치킨")
            .addValue("price", 1_000)
            .addValue("image", "치킨 사진");

    @LocalServerPort
    int port;

    @Autowired
    DataSource dataSource;

    private SimpleJdbcInsert simpleJdbcInsert;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    @Nested
    class CreationTest {

        @Test
        void 상품을_등록한다() {
            given()
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RequestCreateProductDto("치킨", 10_000, "치킨 사진"))
                    .when()
                    .post("/admin/product")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.CREATED.value());
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 이름이_빈_상품을_등록할_수_없다(final String name) {
            given()
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RequestCreateProductDto(name, 10_000, "치킨 사진"))
                    .when()
                    .post("/admin/product")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(containsString("상품 이름이 입력되지 않았습니다."));
        }

        @Test
        void 이름이_유효_길이를_넘긴_상품을_등록할_수_없다() {
            final String overName = "치킨".repeat(26);

            given()
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RequestCreateProductDto(overName, 10_000, "치킨 사진"))
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
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RequestCreateProductDto("치킨", price, "치킨 사진"))
                    .when()
                    .post("/admin/product")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(containsString("가격이 입력되지 않았습니다."));
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 1000000001})
        void 가격이_유효_범위를_넘긴_상품을_등록할_수_없다(final Integer price) {
            given()
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
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
        void 이미지_주소가_빈_상품을_등록할_수_없다(final String image) {
            given()
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RequestCreateProductDto("치킨", 1_000, image))
                    .when()
                    .post("/admin/product")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(containsString("상품 이미지 주소가 입력되지 않았습니다."));
        }

        @Test
        void 이미지_주소가_유효_길이를_넘긴_상품을_등록할_수_없다() {
            final String image = "치킨 사진".repeat(401);

            given()
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RequestCreateProductDto("치킨", 1_000, image))
                    .when()
                    .post("/admin/product")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("이미지 주소는 2000자를 넘길 수 없습니다."));
        }
    }

    @Nested
    class UpdateTest {

        private Long id;

        @BeforeEach
        void setUp() {
            id = simpleJdbcInsert.executeAndReturnKey(PARAMS).longValue();
        }

        @Test
        void 상품을_수정한다() {
            given()
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RequestUpdateProductDto(id, "피자", 10_000, "피자 사진"))
                    .when()
                    .put("/admin/product/{id}", id)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void 존재하지_않는_id의_상품은_수정할_수_없다() {
            final long wrongId = 0L;

            given()
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RequestUpdateProductDto(wrongId, "치킨", 10_000, "치킨 사진"))
                    .when()
                    .put("/admin/product/{id}", wrongId)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("접근하려는 데이터가 존재하지 않습니다."));
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 이름이_빈_상품을_수정할_수_없다(final String name) {
            given()
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RequestUpdateProductDto(id, name, 10_000, "피자 사진"))
                    .when()
                    .put("/admin/product/{id}", id)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(containsString("상품 이름이 입력되지 않았습니다."));
        }

        @Test
        void 이름이_유효_길이를_넘긴_상품을_수정할_수_없다() {
            final String overName = "피자".repeat(26);

            given()
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RequestUpdateProductDto(id, overName, 10_000, "피자 사진"))
                    .when()
                    .put("/admin/product/{id}", id)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("상품 이름은 50자를 넘길 수 없습니다."));
        }

        @ParameterizedTest
        @NullSource
        void 가격이_빈_상품을_수정할_수_없다(final Integer price) {
            given()
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RequestUpdateProductDto(id, "피자", price, "피자 사진"))
                    .when()
                    .put("/admin/product/{id}", id)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(containsString("가격이 입력되지 않았습니다."));
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 1000000001})
        void 가격이_유효_범위를_넘긴_상품을_수정할_수_없다(final Integer price) {
            given()
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RequestUpdateProductDto(id, "피자", price, "피자 사진"))
                    .when()
                    .put("/admin/product/{id}", id)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("가격은 0 미만이거나, 1000000000 초과일 수 없습니다."));
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 이미지_주소가_빈_상품을_수정할_수_없다(final String image) {
            given()
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RequestUpdateProductDto(id, "피자", 10_000, image))
                    .when()
                    .put("/admin/product/{id}", id)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(containsString("상품 이미지 주소가 입력되지 않았습니다."));
        }

        @Test
        void 이미지_주소가_유효_길이를_넘긴_상품을_수정할_수_없다() {
            final String image = "피자 사진".repeat(401);

            given()
                    .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new RequestUpdateProductDto(id, "피자", 10_000, image))
                    .when()
                    .put("/admin/product/{id}", id)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("이미지 주소는 2000자를 넘길 수 없습니다."));
        }
    }

    @Nested
    class DeleteTest {

        private Long id;

        @BeforeEach
        void setUp() {
            id = simpleJdbcInsert.executeAndReturnKey(PARAMS).longValue();
        }

        @Test
        void 상품을_삭제한다() {
            given()
                    .log().all()
                    .when()
                    .delete("/admin/product/{id}", id)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void 존재하지_않는_id의_상품은_삭제할_수_없다() {
            given()
                    .log().all()
                    .when()
                    .delete("/admin/product/{id}", 0)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("접근하려는 데이터가 존재하지 않습니다."));
        }
    }
}
