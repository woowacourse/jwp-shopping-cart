package cart.controller;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dao.entity.CartEntity;
import cart.dao.entity.ProductEntity;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {

    private static final String EMAIL = "a@a.com";
    private static final String PASSWORD = "password1";


    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 카트에_담긴_상품을_조회할_수_있다() {
        // given
        Long productId = insertProduct("치킨", 10_000, "치킨 이미지");
        insertProductToCart(1L, productId);

        // when
        ExtractableResponse<Response> response = given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().get("/carts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        // then
        assertThat(response.body().asString()).contains("치킨", "치킨 이미지", "10000");
    }

    @Test
    void 카트에_상품을_추가할_수_있다() {
        // given
        Long productId = insertProduct("피자", 100000, "피자 사진");

        RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().put("/carts/" + productId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 중복된_상품은_추가할_수_없다() {
        // given
        Long productId = insertProduct("피자", 100000, "피자 사진");
        insertProductToCart(1, productId);

        // when
        RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().put("/carts/" + productId)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();
    }

    @Test
    void 상품을_삭제할_수_있다() {
        // given
        Long productId = insertProduct("피자", 100000, "피자 사진");
        insertProductToCart(1, productId);

        // when
        RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().delete("/carts/" + productId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(cartDao.hasSameProduct(1L, productId)).isFalse();
    }

    @Test
    void 카트에_추가되지_않은_상품은_삭제할_수_없다() {
        // given
        Long productId = 0L;

        // when
        RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().delete("/carts/" + productId)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();
    }

    private Long insertProductToCart(long memberId, long productId) {
        final CartEntity cartEntity = new CartEntity.Builder()
                .memberId(memberId)
                .productId(productId)
                .build();
        return cartDao.insert(cartEntity);
    }

    private Long insertProduct(String name, int price, String image) {
        final ProductEntity productEntity = new ProductEntity.Builder()
                .name(name)
                .price(price)
                .image(image)
                .build();
        return productDao.insert(productEntity);
    }
}
