package cart;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import cart.domain.persistence.dao.CartDao;
import cart.domain.persistence.dao.MemberDao;
import cart.domain.persistence.dao.ProductDao;
import cart.domain.persistence.entity.CartEntity;
import cart.domain.persistence.entity.MemberEntity;
import cart.domain.persistence.entity.ProductEntity;
import cart.web.cart.dto.PostCartRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:schema-truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CartApiEndToEndTest {

    @LocalServerPort
    private int port;

    private final MemberDao memberDao;
    private final ProductDao productDao;
    private final CartDao cartDao;

    @Autowired
    public CartApiEndToEndTest(final MemberDao memberDao, final ProductDao productDao, final CartDao cartDao) {
        this.memberDao = memberDao;
        this.productDao = productDao;
        this.cartDao = cartDao;
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 유효한_인증_정보로_cart_GET_성공_테스트() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new MemberEntity(email, password));

        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .auth().preemptive().basic(email, password)
            .when().get("/cart")
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 잘못된_이메일_정보로_cart_GET_예외_발생_테스트() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new MemberEntity(email, password));

        ExtractableResponse<Response> response = RestAssured.given()
            .auth().preemptive().basic("b@a.com", password)
            .when().get("/cart")
            .then()
            .extract();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            softAssertions.assertThat(response.body().asString()).contains("아이디 또는 비밀번호가 잘못되었습니다.");
        });
    }

    @Test
    void 잘못된_비밀번호로_cart_GET_예외_발생_테스트() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new MemberEntity(email, password));

        ExtractableResponse<Response> response = RestAssured.given()
            .auth().preemptive().basic("b@a.com", "password2")
            .when().get("/cart")
            .then()
            .extract();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            softAssertions.assertThat(response.body().asString()).contains("아이디 또는 비밀번호가 잘못되었습니다.");
        });
    }

    @Test
    void cart_POST로_유효한_상품_추가_성공_테스트() {
        final String email = "a@a.com";
        final String password = "password1";
        final long memberId = memberDao.save(new MemberEntity(email, password));
        long pizzaId = productDao.save(new ProductEntity("pizza", 20000, "https://a.com"));
        final PostCartRequest pizzaRequest = new PostCartRequest(pizzaId);

        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .auth().preemptive().basic(email, password)
            .body(pizzaRequest)
            .when().post("/cart")
            .then()
            .statusCode(HttpStatus.OK.value());

        assertThat(cartDao.findAllByMemberId(memberId).size()).isEqualTo(1);
    }


    @Test
    void cart_POST시_authentication_정보_누락_예외_발생_테스트() {
        long pizzaId = productDao.save(new ProductEntity("pizza", 20000, "https://a.com"));
        final PostCartRequest pizzaRequest = new PostCartRequest(pizzaId);

        ExtractableResponse<Response> response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(pizzaRequest)
            .when().post("/cart")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract();

        assertThat(response.body().asString()).contains("인증 정보가 필요합니다.");
    }

    @Test
    void cart_POST로_음수의_상품_id_추가_예외_발생_테스트() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new MemberEntity(email, password));
        final PostCartRequest postCartRequest = new PostCartRequest(-1L);

        ExtractableResponse<Response> response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .auth().preemptive().basic(email, password)
            .body(postCartRequest)
            .when().post("/cart")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract();

        assertThat(response.body().asString()).contains("유효한 product id를 입력해주세요");
    }

    @Test
    void cart_POST로_존재하지_않는_상품_id_추가_예외_발생_테스트() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new MemberEntity(email, password));
        final PostCartRequest postCartRequest = new PostCartRequest(2L);

        ExtractableResponse<Response> response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .auth().preemptive().basic(email, password)
            .body(postCartRequest)
            .when().post("/cart")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract();

        assertThat(response.body().asString()).contains("존재하지 않는 product id입니다.");
    }

    @Test
    void cart_DELETE_정상_동작_테스트() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new MemberEntity(email, password));
        final PostCartRequest postCartRequest = new PostCartRequest(2L);
        long cartId = cartDao.save(new CartEntity(1, 1));

        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .auth().preemptive().basic(email, password)
            .body(postCartRequest)
            .when().delete("/cart/" + cartId)
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void cart_DELETE시_authentication_정보_누락_예외_발생_테스트() {
        long cartId = cartDao.save(new CartEntity(1, 1));

        ExtractableResponse<Response> response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/cart/" + cartId)
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract();

        assertThat(response.body().asString()).contains("인증 정보가 필요합니다.");
    }

    @Test
    void cart_DELETE시_authentication_잘못된_비밀번호_예외_발생_테스트() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new MemberEntity(email, password));
        final PostCartRequest postCartRequest = new PostCartRequest(2L);
        long cartId = cartDao.save(new CartEntity(1, 1));

        ExtractableResponse<Response> response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .auth().preemptive().basic(email, "password2")
            .body(postCartRequest)
            .when().delete("/cart/" + cartId)
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract();

        assertThat(response.body().asString()).contains("아이디 또는 비밀번호가 잘못되었습니다.");
    }
}
