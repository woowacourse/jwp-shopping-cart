package cart.controller.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("classpath:data-test.sql")
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
                    .body(containsString("입력한 정보의 회원은 존재하지 않습니다."));
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
                    .body(containsString("입력한 정보의 회원은 존재하지 않습니다."));
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
        void 삭제하려는_상품이_없는_경우_예외를_던진다() {
            //given
            memberJdbcInsert.execute(MEMBER_PARAMS);

            //expect
            given()
                    .log().all().header("Authorization", "Basic " + ENCODED_CREDENTIALS)
                    .when()
                    .delete("/carts/{id}", 99999L)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(containsString("접근하려는 데이터가 존재하지 않습니다."));
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
                    .body(containsString("입력한 정보의 회원은 존재하지 않습니다."));
        }
    }

    @Nested
    class AuthenticationTest {

        @Test
        void header에_인증_정보가_없는_경우_예외를_던진다() {
            given()
                    .log().all()
                    .when()
                    .get("/carts")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body(equalTo("header에 회원 정보가 입력되지 않았습니다."));
        }

        @ParameterizedTest
        @EmptySource
        void header의_인증_정보가_빈_경우_예외를_던진다(final String empty) {
            given()
                    .log().all().header("Authorization", empty)
                    .when()
                    .get("/carts")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body(equalTo("header에 회원 정보가 입력되지 않았습니다."));
        }

        @Test
        void header의_인증_방식이_Basic이_아닌_경우_예외를_던진다() {
            given()
                    .log().all().header("Authorization", "Bearer " + ENCODED_CREDENTIALS)
                    .when()
                    .get("/carts")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body(equalTo("지원하지 않는 인증 방식입니다. Basic 인증 방식을 사용해주세요."));
        }

        @Test
        void 회원_정보_입력_형식이_잘못된_경우_예외를_던진다() {
            final String wrongCredentials = "aHVjaHVAd29vd2FoYW4uY29tLDEyMzQ1NjdhIQ=="; // "huchu@woowahan.com,1234567a!"

            given()
                    .log().all().header("Authorization", "Basic " + wrongCredentials)
                    .when()
                    .get("/carts")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body(equalTo("회원 정보 입력 형식이 잘못되었습니다. \"email:password\"로 입력해주세요."));
        }

        @Test
        void 계정이_빈_값인_경우_예외를_던진다() {
            final String emptyCredentials = "OjEyMzQ1NjdhIQ=="; // ":1234567a!" encoded by Base64

            given()
                    .log().all().header("Authorization", "Basic " + emptyCredentials)
                    .when()
                    .get("/carts")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body(equalTo("입력한 정보의 회원은 존재하지 않습니다."));
        }

        @Test
        void 비밀번호가_빈_값인_경우_예외를_던진다() {
            final String emptyCredentials = "aHVjaHVAd29vd2FoYW4uY29tOg=="; // "huchu@woowahan.com:" encoded by Base64

            given()
                    .log().all().header("Authorization", "Basic " + emptyCredentials)
                    .when()
                    .get("/carts")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body(equalTo("입력한 정보의 회원은 존재하지 않습니다."));
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
                    .body(equalTo("입력한 정보의 회원은 존재하지 않습니다."));
        }
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM cart");
        jdbcTemplate.update("DELETE FROM member");
        jdbcTemplate.update("DELETE FROM product");
    }
}
