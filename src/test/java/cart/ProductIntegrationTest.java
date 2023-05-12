package cart;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.request.CreateCartRequest;
import cart.dto.request.CreateProductRequest;
import cart.dto.request.UpdateProductRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductIntegrationTest {
    private static final String TOKEN_FIXTURE = "Z2F2aUB3b293YWhhbi5jb206MTIzNA==";
    private static final SqlParameterSource PRODUCT_PARAMS = new MapSqlParameterSource()
            .addValue("name", "치킨")
            .addValue("price", 1000)
            .addValue("image", "치킨 사진");
    private static final SqlParameterSource MEMBER_PARAMS = new MapSqlParameterSource()
            .addValue("email", "hucu@woowahan.com")
            .addValue("password", "1234");

    @LocalServerPort
    private int port;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ProductDao productDao;

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
                .get("/products")
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
                .body(new CreateProductRequest("치킨", 10_000, "치킨 사진"))
                .when()
                .post("/products")
                .then()
                .log().all()
                .extract().response();

        // when
        final Response userResponse = given()
                .log().all().accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .get("/products")
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
            softly.assertThat(userResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(userResponse.body().asString()).contains("치킨", "10000", "치킨 사진");
            softly.assertThat(adminResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(adminResponse.body().asString()).contains("치킨", "10000", "치킨 사진");
        });
    }

    @Test
    void 상품을_삭제하면_상품_목록_페이지와_관리자_페이지에서_사라진다() {
        // given
        final Long productId = productDao.insert(new Product("족발", 10_0000, "족발 사진"));

        final Response deleteResponse = given()
                .log().all().accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .delete("/products/" + productId)
                .then()
                .log().all()
                .extract().response();

        // when
        final Response userResponse = given()
                .log().all().accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .get("/products")
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
            softly.assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.ACCEPTED.value());
            softly.assertThat(userResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(userResponse.body().asString()).doesNotContain("족발", "100000", "족발 사진");
            softly.assertThat(adminResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(adminResponse.body().asString()).doesNotContain("족발", "100000", "족발 사진");
        });
    }

    @Test
    void 등록한_상품을_수정하면_상품_목록_페이지와_관리자_페이지에서_수정된다() {
        // given
        final Long productId = productDao.insert(new Product("치킨", 10_000, "치킨 사진"));

        final Response updateResponse = given()
                .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UpdateProductRequest("피자", 1_000, "피자 사진"))
                .when()
                .put("/products/" + productId)
                .then()
                .log().all()
                .extract().response();

        // when
        final Response userResponse = given()
                .log().all().accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .get("/products")
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
            softly.assertThat(userResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(userResponse.body().asString()).contains("피자", "1000", "피자 사진");
            softly.assertThat(adminResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(adminResponse.body().asString()).contains("피자", "1000", "피자 사진");
        });
    }

    @Test
    void 상품목록에서_담기를_누르면_장바구니에_상품이_추가된다() {
        // given
        final Long productId = productJdbcInsert.executeAndReturnKey(PRODUCT_PARAMS).longValue();

        // when
        given()
                .log().all()
                .header("Authorization", "Basic " + TOKEN_FIXTURE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CreateCartRequest(productId))
                .when()
                .post("/carts")
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value());

        final Response cartResponse = given()
                .log().all()
                .header("Authorization", "Basic " + TOKEN_FIXTURE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/carts")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().response();
        final ResponseBody cartResponseBody = cartResponse.body();

        // then
        assertSoftly(softly -> {
            softly.assertThat(cartResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(cartResponseBody.asString()).contains("치킨", "1000", "치킨 사진");
        });
    }

    @Test
    void 상품을_제거하면_장바구니에서_사라진다() {
        // given
        final Long memberId = memberJdbcInsert.executeAndReturnKey(MEMBER_PARAMS).longValue();
        final Long productId = productJdbcInsert.executeAndReturnKey(PRODUCT_PARAMS).longValue();
        final Long cartId = cartJdbcInsert.executeAndReturnKey(new MapSqlParameterSource()
                .addValue("member_id", memberId)
                .addValue("product_id", productId)).longValue();

        // when
        given()
                .log().all()
                .header("Authorization", "Basic " + TOKEN_FIXTURE)
                .contentType(ContentType.JSON)
                .when()
                .delete("/carts/" + cartId)
                .then()
                .log().all()
                .statusCode(HttpStatus.ACCEPTED.value());

        final Response cartResponse = given()
                .log().all()
                .header("Authorization", "Basic " + TOKEN_FIXTURE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/carts")
                .then()
                .log().all()
                .extract().response();

        // then
        assertSoftly(softly -> {
            softly.assertThat(cartResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(cartResponse.body().asString()).doesNotContain("치킨", "1000", "치킨 사진");
        });
    }
}
