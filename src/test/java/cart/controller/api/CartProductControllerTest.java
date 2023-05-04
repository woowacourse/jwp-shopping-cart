package cart.controller.api;

import cart.dao.cart.CartDao;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static cart.DummyData.INITIAL_MEMBER_ONE;
import static cart.DummyData.INITIAL_PRODUCT_ONE;
import static cart.DummyData.INITIAL_PRODUCT_TWO;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Sql("/reset-cart_product-data.sql")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CartProductControllerTest {

    private static final String path = "/cart/products";

    @Autowired
    CartDao cartDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 유저가_상품을_담으면_상태코드_201을_반환하는지_확인한다() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(INITIAL_MEMBER_ONE.getEmail(), INITIAL_MEMBER_ONE.getPassword())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(INITIAL_PRODUCT_ONE.getId())
                .when().post(path)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 유저의_상품_전체_목록을_조회하면_상태코드_200을_반환하는지_확인한다() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(INITIAL_MEMBER_ONE.getEmail(), INITIAL_MEMBER_ONE.getPassword())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("", hasSize(1))
                .body("[0].id", is(INITIAL_PRODUCT_TWO.getId().intValue()))
                .body("[0].name", is(INITIAL_PRODUCT_TWO.getName()))
                .body("[0].image", is(INITIAL_PRODUCT_TWO.getImageUrl()))
                .body("[0].price", is(INITIAL_PRODUCT_TWO.getPrice()));
    }

    @Test
    void 유저가_장바구니_상품을_삭제할_경우_상태코드_204를_반환하는지_확인한다() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(INITIAL_MEMBER_ONE.getEmail(), INITIAL_MEMBER_ONE.getPassword())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(path + "/" + INITIAL_PRODUCT_TWO.getId())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
