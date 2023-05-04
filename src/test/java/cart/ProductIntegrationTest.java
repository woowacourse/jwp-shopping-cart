package cart;

import cart.dto.RequestCreateProductDto;
import cart.dto.RequestUpdateProductDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductIntegrationTest {

    private static final String ENCODED_CREDENTIALS = "aHVjaHVAd29vd2FoYW4uY29tOjEyMzQ1NjdhIQ=="; //huchu@woowahan.com:1234567a!
    private static final SqlParameterSource PRODUCT_PARAMS = new MapSqlParameterSource()
            .addValue("name", "치킨")
            .addValue("price", 10000)
            .addValue("image", "치킨 사진");
    private static final SqlParameterSource MEMBER_PARAMS = new MapSqlParameterSource()
            .addValue("email", "huchu@woowahan.com")
            .addValue("password", "1234567a!");

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private SimpleJdbcInsert productJdbcInsert;
    private SimpleJdbcInsert memberJdbcInsert;
    private SimpleJdbcInsert cartJdbcInsert;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        productJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("product").usingGeneratedKeyColumns("id");
        memberJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("member").usingGeneratedKeyColumns("id");
        cartJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("cart").usingGeneratedKeyColumns("id");
    }

    @Test
    void 메인화면에_네비게이션_바가_표시된다() {
        final Response response = given()
                .log().all().accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .get("/")
                .then()
                .log().all()
                .extract().response();

        assertThat(response.getBody().asString()).contains("상품목록", "장바구니", "설정", "관리자");
    }

    @Test
    void 메인화면에서_관리자를_클릭하면_관리자_페이지가_반환된다() {
        final Response response = given()
                .log().all().accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .get("/admin")
                .then()
                .log().all()
                .extract().response();

        assertThat(response.getBody().asString()).contains("ID", "이름", "가격", "이미지", "Actions", "상품 추가");
    }

    @Test
    void 상품을_등록하면_상품_목록_페이지와_관리자_페이지에_추가된다() {
        // given
        final Response createResponse = given()
                .log().all().contentType(ContentType.JSON)
                .body(new RequestCreateProductDto("치킨", 10_000, "치킨 사진"))
                .when()
                .post("/admin/product")
                .then()
                .log().all()
                .extract().response();

        // when
        final Response memberResponse = given()
                .log().all().accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .get("/")
                .then()
                .log().all()
                .extract().response();

        final Response adminResponse = given()
                .log().all().accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .get("/admin")
                .then()
                .log().all()
                .extract().response();

        // then
        assertSoftly(softly -> {
            softly.assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            softly.assertThat(memberResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(memberResponse.body().asString()).contains("치킨", "10000", "치킨 사진");
            softly.assertThat(adminResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(adminResponse.body().asString()).contains("치킨", "10000", "치킨 사진");
        });
    }

    @Test
    void 상품을_삭제하면_상품_목록_페이지와_관리자_페이지에서_사라진다() {
        // given
        final Long id = productJdbcInsert.executeAndReturnKey(PRODUCT_PARAMS).longValue();

        final Response deleteResponse = given()
                .log().all().accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .delete("/admin/product/{id}", id)
                .then()
                .log().all()
                .extract().response();

        // when
        final Response memberResponse = given()
                .log().all().accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .get("/")
                .then()
                .log().all()
                .extract().response();

        final Response adminResponse = given()
                .log().all().accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .get("/admin")
                .then()
                .log().all()
                .extract().response();

        // then
        assertSoftly(softly -> {
            softly.assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(memberResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(memberResponse.body().asString()).doesNotContain("치킨", "10000", "치킨 사진");
            softly.assertThat(adminResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(adminResponse.body().asString()).doesNotContain("치킨", "10000", "치킨 사진");
        });
    }

    @Test
    void 등록한_상품을_수정하면_상품_목록_페이지와_관리자_페이지에서_수정된다() {
        // given
        final Long id = productJdbcInsert.executeAndReturnKey(PRODUCT_PARAMS).longValue();

        final Response updateResponse = given()
                .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new RequestUpdateProductDto(id, "피자", 1_000, "피자 사진"))
                .when()
                .put("/admin/product/{id}", id)
                .then()
                .log().all()
                .extract().response();

        // when
        final Response memberResponse = given()
                .log().all().accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .get("/")
                .then()
                .log().all()
                .extract().response();

        final Response adminResponse = given()
                .log().all().accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .get("/admin")
                .then()
                .log().all()
                .extract().response();

        // then
        assertSoftly(softly -> {
            softly.assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(memberResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(memberResponse.body().asString()).contains("피자", "1000", "피자 사진");
            softly.assertThat(adminResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(adminResponse.body().asString()).contains("피자", "1000", "피자 사진");
        });
    }

    @Test
    void 상품목록에서_담기를_누르면_장바구니에_상품이_추가된다() {
        // given
        memberJdbcInsert.execute(MEMBER_PARAMS);
        final Long productId = productJdbcInsert.executeAndReturnKey(PRODUCT_PARAMS).longValue();

        // when
        given()
                .log().all().header("Authorization", "Basic " + ENCODED_CREDENTIALS)
                .when()
                .post("/carts/{productId}", productId)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());

        // then
        given()
                .log().all().header("Authorization", "Basic " + ENCODED_CREDENTIALS)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/carts")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size", is(1))
                .body("[0].id", equalTo(productId.intValue()))
                .body("[0].name", equalTo("치킨"))
                .body("[0].price", equalTo(10000))
                .body("[0].image", equalTo("치킨 사진"));
    }

    @Test
    void 장바구니의_상품을_제거하면_장바구니에서_상품이_제거된다() {
        // given
        final Long memberId = memberJdbcInsert.executeAndReturnKey(MEMBER_PARAMS).longValue();
        final Long productId = productJdbcInsert.executeAndReturnKey(PRODUCT_PARAMS).longValue();
        final SqlParameterSource cartParams = new MapSqlParameterSource()
                .addValue("member_id", memberId)
                .addValue("product_id", productId);
        cartJdbcInsert.execute(cartParams);

        // when
        given()
                .log().all().header("Authorization", "Basic " + ENCODED_CREDENTIALS)
                .when()
                .delete("/carts/{productId}", productId)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());

        final Response cartReadResponse = given()
                .log().all().header("Authorization", "Basic " + ENCODED_CREDENTIALS)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/carts")
                .then()
                .log().all()
                .extract().response();

        // then
        assertSoftly(softly -> {
            softly.assertThat(cartReadResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(cartReadResponse.body().asString()).doesNotContain("치킨", "10000", "치킨 사진");
        });
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM cart");
        jdbcTemplate.update("DELETE FROM product");
        jdbcTemplate.update("DELETE FROM member");
    }
}
