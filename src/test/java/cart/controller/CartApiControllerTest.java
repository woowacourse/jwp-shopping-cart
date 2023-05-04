package cart.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {

    private static final String ENCODED_CREDENTIALS = "aHVjaHVAd29vd2FoYW4uY29tOjEyMzQ1NjdhIQ=="; // huchu@woowahan.com:1234567a!
    private static final String UNAUTHORIZED_ENCODED_CREDENTIALS = "bm9Ad29vd2FoYW4uY29tOjEyMzQ1NjdhIQ=="; // no@woowahan.com:1234567a!
    private static final SqlParameterSource PRODUCT_PARAMS = new MapSqlParameterSource()
            .addValue("name", "치킨")
            .addValue("price", 1_000)
            .addValue("image", "치킨 사진");
    private static final SqlParameterSource MEMBER_PARAMS = new MapSqlParameterSource()
            .addValue("email", "huchu@woowahan.com")
            .addValue("password", "1234567a!");

    @LocalServerPort
    int port;

    @Autowired
    DataSource dataSource;

    private SimpleJdbcInsert productJdbcInsert;
    private SimpleJdbcInsert memberJdbcInsert;
    private SimpleJdbcInsert cartJdbcInsert;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        productJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("product").usingGeneratedKeyColumns("id");
        memberJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("member").usingGeneratedKeyColumns("id");
        cartJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("cart");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Nested
    class ReadTest {

        @Test
        void 장바구니의_상품을_읽는다() {
            //given
            final Long memberId = memberJdbcInsert.executeAndReturnKey(MEMBER_PARAMS).longValue();
            final Long productId = productJdbcInsert.executeAndReturnKey(PRODUCT_PARAMS).longValue();
            final SqlParameterSource cartParams = new MapSqlParameterSource()
                    .addValue("member_id", memberId)
                    .addValue("product_id", productId);
            cartJdbcInsert.execute(cartParams);

            //expect
            given()
                    .log().all().header("Authorization", "Basic " + ENCODED_CREDENTIALS)
                    .when()
                    .get("/carts")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.OK.value())
                    .body("size", is(1))
                    .body("[0].id", equalTo(productId.intValue()))
                    .body("[0].name", equalTo("치킨"))
                    .body("[0].price", equalTo(1000))
                    .body("[0].image", equalTo("치킨 사진"));
        }

        @Test
        void 존재하지_않는_회원인_경우_예외를_던진다() {
            given()
                    .log().all().header("Authorization", "Basic " + UNAUTHORIZED_ENCODED_CREDENTIALS)
                    .when()
                    .get("/carts")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body(containsString("no@woowahan.com 계정의 회원은 존재하지 않습니다."));
        }
    }

    @Nested
    class CreateTest {

        @Test
        void 장바구니에_상품을_추가한다() {
            //given
            memberJdbcInsert.execute(MEMBER_PARAMS);
            final Long productId = productJdbcInsert.executeAndReturnKey(PRODUCT_PARAMS).longValue();

            //expect
            given()
                    .log().all().header("Authorization", "Basic " + ENCODED_CREDENTIALS)
                    .when()
                    .post("/carts/{id}", productId)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void 존재하지_않는_회원인_경우_예외를_던진다() {
            final Long productId = productJdbcInsert.executeAndReturnKey(PRODUCT_PARAMS).longValue();

            given()
                    .log().all().header("Authorization", "Basic " + UNAUTHORIZED_ENCODED_CREDENTIALS)
                    .when()
                    .post("/carts/{id}", productId)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body(containsString("no@woowahan.com 계정의 회원은 존재하지 않습니다."));
        }
    }

    @Nested
    class deleteTest {

        @Test
        void 장바구니의_상품을_삭제한다() {
            //given
            final Long memberId = memberJdbcInsert.executeAndReturnKey(MEMBER_PARAMS).longValue();
            final Long productId = productJdbcInsert.executeAndReturnKey(PRODUCT_PARAMS).longValue();
            final SqlParameterSource cartParams = new MapSqlParameterSource()
                    .addValue("member_id", memberId)
                    .addValue("product_id", productId);
            cartJdbcInsert.execute(cartParams);

            //expect
            given()
                    .log().all().header("Authorization", "Basic " + ENCODED_CREDENTIALS)
                    .when()
                    .delete("/carts/{id}", productId)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void 존재하지_않는_회원인_경우_예외를_던진다() {
            final Long productId = productJdbcInsert.executeAndReturnKey(PRODUCT_PARAMS).longValue();

            given()
                    .log().all().header("Authorization", "Basic " + UNAUTHORIZED_ENCODED_CREDENTIALS)
                    .when()
                    .delete("/carts/{id}", productId)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body(containsString("no@woowahan.com 계정의 회원은 존재하지 않습니다."));
        }
    }


    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM cart");
        jdbcTemplate.update("DELETE FROM member");
        jdbcTemplate.update("DELETE FROM product");
    }
}
