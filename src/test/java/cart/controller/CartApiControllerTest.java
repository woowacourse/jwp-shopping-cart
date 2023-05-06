package cart.controller;

import static cart.fixture.SqlFixture.MEMBER_INSERT_SQL;
import static cart.fixture.SqlFixture.PRODUCT_INSERT_SQL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import cart.dto.request.CartProductRequest;
import cart.persistnece.dao.CartProductDao;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {

    private static final long PRODUCT_ID = 1L;
    private static final long MEMBER_ID = 1L;

    private static final String PASSWORD = "password";
    private static final String EMAIL = "email@naver.com";
    private final String CART_API_URL = "/cart";

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CartProductDao cartProductDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        jdbcTemplate.update("delete from cart_product");
        jdbcTemplate.update(" delete from product");
        jdbcTemplate.update("delete from member");
    }

    @Nested
    @DisplayName("[LOGIN TEST] - ")
    class Auth {

        @DisplayName("reqeust시 Authorization header가 없다면 401 반환한다.")
        @Test
        void login_fail_by_not_have_authorization_header() {
            given()
                    .log().all()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get(CART_API_URL)
                    .then().log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body("message", equalTo("Authorization Header가 존재하지 않습니다."));
        }

        @DisplayName("reqeust시 Authorization header의 양식이 옳지 않다면 예외를 반환한다.")
        @Test
        void login_fail_by_header_prefix() {
            given()
                    .log().all()
                    .auth().oauth2("anyValue")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get(CART_API_URL)
                    .then().log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body("message", equalTo("Authorization Header는 'Basic '로 시작해야 합니다."));
        }

        @DisplayName("해당하는 email의 member가 존재하지 않으면 401를 반환한다.")
        @Test
        void login_fail_by_member_not_found() {
            given()
                    .log().all()
                    .auth().preemptive().basic("anyValue" +EMAIL, PASSWORD )
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get(CART_API_URL)
                    .then().log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body("message", equalTo("해당하는 email의 회원이 존재하지 않습니다."));
        }

        @DisplayName("전달받은 패스워드와 실제 멤버의 패스워드가 일치하지 않으면 401을 반환한다.")
        @Test
        void login_fail_by_invalid_password() {
            saveProductAndMember(MEMBER_ID, PRODUCT_ID);
            cartProductDao.save(MEMBER_ID, PRODUCT_ID);

            given()
                    .log().all()
                    .auth().preemptive().basic(EMAIL, PASSWORD + "abc")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get(CART_API_URL)
                    .then().log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body("message", equalTo( "패스워드가 일치하지 않습니다."));
        }
    }

    @DisplayName("정상적으로 로그인하여, 특정한 회원의 cartProducts를 반환한다.")
    @Test
    void login_and_find_cart_products_by_member() {
        saveProductAndMember(MEMBER_ID, PRODUCT_ID);
        cartProductDao.save(MEMBER_ID, PRODUCT_ID);

       givenLogin()
                .when()
                .get(CART_API_URL)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("정상적으로 로그인하여, 회원의 장바구니에 CartProduct를 추가한다.")
    @Test
    void login_and_post_cart_product()  {
        saveProductAndMember(MEMBER_ID, PRODUCT_ID);

        givenLogin()
                .body(new CartProductRequest(PRODUCT_ID))
                .when()
                .post(CART_API_URL)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("정상적으로 로그인하여, 회원의 특정한 CartProduct를 삭제한다.")
    @Test
    void login_and_delete_cart_product() {
        saveProductAndMember(MEMBER_ID, PRODUCT_ID);
        Long cartProductId = cartProductDao.save(MEMBER_ID, PRODUCT_ID);

        givenLogin()
                .when()
                .delete(CART_API_URL + "/" + cartProductId)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void saveProductAndMember(Long memberId, Long productId) {
        jdbcTemplate.update(MEMBER_INSERT_SQL, memberId,  EMAIL, PASSWORD);
        jdbcTemplate.update(PRODUCT_INSERT_SQL, productId, "name", 1000, "url");
    }

    private RequestSpecification givenLogin() {
        return given()
                .log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
