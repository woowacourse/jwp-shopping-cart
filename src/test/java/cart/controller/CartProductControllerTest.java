package cart.controller;

import cart.dao.CartProductDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.domain.CartProduct;
import cart.dto.CartProductRequest;
import cart.dto.ProductResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static cart.fixture.MemberFixture.FIRST_MEMBER;
import static cart.fixture.ProductFixture.FIRST_PRODUCT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartProductControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CartProductDao cartProductDao;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        memberDao.deleteAll();
        productDao.deleteAll();
        cartProductDao.deleteAll();

        namedParameterJdbcTemplate.getJdbcTemplate().execute("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
        namedParameterJdbcTemplate.getJdbcTemplate().execute("ALTER TABLE product ALTER COLUMN id RESTART WITH 1");
        namedParameterJdbcTemplate.getJdbcTemplate().execute("ALTER TABLE cart_product ALTER COLUMN id RESTART WITH 1");

        memberDao.save(FIRST_MEMBER.MEMBER);
        productDao.save(FIRST_PRODUCT.PRODUCT);
    }

    @Test
    void 장바구니_목록을_조회한다() {
        cartProductDao.save(new CartProduct(1L, 1L));

        List<ProductResponse> productResponses = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().preemptive().basic(FIRST_MEMBER.EMAIL, FIRST_MEMBER.PASSWORD)
                .when()
                .get("/cart-products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().getList("", ProductResponse.class);

        ProductResponse productResponse = productResponses.get(0);
        assertThat(productResponse).usingRecursiveComparison()
                .isEqualTo(FIRST_PRODUCT.RESPONSE);
    }

    @Test
    void 장바구니에_상품을_저장한다() {
        RestAssured.given().log().all()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .auth().preemptive().basic(FIRST_MEMBER.EMAIL, FIRST_MEMBER.PASSWORD)
                .body(new CartProductRequest(1L))
                .when().post("/cart-products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/cart-products/1");
    }

    @Test
    void 장바구니에서_상품을_삭제한다() {
        cartProductDao.save(new CartProduct(1L, 1L));

        RestAssured.given().log().all()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .auth().preemptive().basic(FIRST_MEMBER.EMAIL, FIRST_MEMBER.PASSWORD)
                .when().delete("/cart-products/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 장바구니에_상품_저장시_상품_ID가_NULL이면_예외_발생() {
        final CartProductRequest cartProductRequest = new CartProductRequest();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic(FIRST_MEMBER.EMAIL, FIRST_MEMBER.PASSWORD)
                .body(cartProductRequest)
                .when().post("/cart-products")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is("[ERROR] 상품 아이디를 입력해주세요."));
    }

    @Test
    void 인증된_사용자가_아니면_예외_발생() {
        final CartProductRequest cartProductRequest = new CartProductRequest(1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("nothing@adf.com", "efefe")
                .body(cartProductRequest)
                .when().get("/cart-products")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
