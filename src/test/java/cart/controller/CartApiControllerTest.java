package cart.controller;

import static org.hamcrest.Matchers.equalTo;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.domain.Member;
import cart.domain.Product;
import cart.dto.request.CartAddRequest;
import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {
    public static final CartAddRequest REQUEST_1 = new CartAddRequest(1L);
    public static final CartAddRequest REQUEST_2 = new CartAddRequest(5L);
    public static final Product PRODUCT = Product.builder()
            .name("오감자")
            .price(1_000)
            .imageUrl("imageUrl")
            .build();
    private final String EMAIL = "test@email.com";
    private final String PASSWORD = "12345678";
    private final Member MEMBER = Member.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .build();
    private long cartId;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;
    private ProductDao productDao;
    private CartDao cartDao;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        memberDao = new MemberDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        cartDao = new CartDao(jdbcTemplate);
    }

    @Test
    @DisplayName("/cart로 POST 요청을 보내면 HTTP 201 코드와 함께 카트에 상품이 추가되어야 한다.")
    void add_success() {
        MemberEntity memberEntity = memberDao.save(MEMBER);
        ProductEntity productEntity = productDao.save(PRODUCT);
        cartId = cartDao.add(memberEntity.getId(), productEntity.getId());

        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(REQUEST_1)
                .when().post("/cart")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/cart/" + (cartId + 1));
    }

    @Test
    @DisplayName("/cart로 존재하지 않는 productId로 POST 요청을 보내면 HTTP 400 코드를 응답한다.")
    void add_notExistProductId() {
        MemberEntity memberEntity = memberDao.save(MEMBER);
        ProductEntity productEntity = productDao.save(PRODUCT);
        cartDao.add(memberEntity.getId(), productEntity.getId());

        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(REQUEST_2)
                .when().post("/cart")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("/cart로 POST 요청을 보낼 때, 사용자 인증 정보가 없으면 HTTP 401 코드를 응답한다.")
    void add_unAuthorized() {
        MemberEntity memberEntity = memberDao.save(MEMBER);
        ProductEntity productEntity = productDao.save(PRODUCT);
        cartId = cartDao.add(memberEntity.getId(), productEntity.getId());

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(REQUEST_1)
                .when().post("/cart")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("/cart/{cartId}로 DELETE 요청을 보내면 HTTP 200 코드와 함께 카트에 담긴 상품이 삭제되어야 한다.")
    void delete_success() {
        MemberEntity memberEntity = memberDao.save(MEMBER);
        ProductEntity productEntity = productDao.save(PRODUCT);
        cartId = cartDao.add(memberEntity.getId(), productEntity.getId());

        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart/" + cartId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("/cart/{cartId}로 존재하지 않는 cartId로 DELETE 요청을 보내면, HTTP 400 코드를 반환한다.")
    void delete_notExistCartId() {
        MemberEntity memberEntity = memberDao.save(MEMBER);
        ProductEntity productEntity = productDao.save(PRODUCT);
        cartId = cartDao.add(memberEntity.getId(), productEntity.getId());

        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart/" + (cartId + 1))
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("/cart/{cartId}로 사용자 인증 정보 없이 DELETE 요청을 보내면, HTTP 401 코드로 응답한다.")
    void delete_unAuthorized() {
        MemberEntity memberEntity = memberDao.save(MEMBER);
        ProductEntity productEntity = productDao.save(PRODUCT);
        cartId = cartDao.add(memberEntity.getId(), productEntity.getId());

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart/" + cartId)
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }


    @Test
    @DisplayName("/cart/items로 GET 요청을 보내면 HTTP 200 코드와 함께 카트에 담긴 모든 상품을 응답해야 한다.")
    void findAll_success() {
        MemberEntity memberEntity = memberDao.save(MEMBER);
        ProductEntity productEntity = productDao.save(PRODUCT);
        cartDao.add(memberEntity.getId(), productEntity.getId());

        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/items")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .rootPath("[0]")
                .body("id", equalTo(1),
                        "name", equalTo("오감자"),
                        "price", equalTo(1000));
    }

    @Test
    @DisplayName("/cart/items로 사용자 인증 정보 없이 GET 요청을 보내면 HTTP 401 코드로 응답한다.")
    void findAll_unAuthorized() {
        MemberEntity memberEntity = memberDao.save(MEMBER);
        ProductEntity productEntity = productDao.save(PRODUCT);
        cartId = cartDao.add(memberEntity.getId(), productEntity.getId());

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/items")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
