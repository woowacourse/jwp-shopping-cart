package cart.integration;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.member.MemberDao;
import cart.dao.member.MemberEntity;
import cart.dao.product.ProductDao;
import cart.dao.product.ProductEntity;
import cart.global.exception.common.ExceptionStatus;
import cart.service.dto.cart.CartAddProductRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.Header;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.Base64Utils;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@Sql("/schema.sql")
@Sql("/truncate.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CartIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ObjectMapper objectMapper;

    private final MemberEntity 멤버 = new MemberEntity(
            "vero",
            "veroPassword"
    );

    private final ProductEntity 상품 = new ProductEntity(
            "피자",
            100000,
            "https://img.freepik.com/free-photo/top-view-of-pepperoni-pizza-with-mushroom-sausages-bell-pepper-olive-and-corn-on-black-wooden_141793-2158.jpg"
    );

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void 장바구니에_상품을_등록한다() {
        멤버를_생성한다();
        final Long 상품_ID = 상품을_등록한다(상품);
        final CartAddProductRequest 요청 = 장바구니_상품_등록_요청을_생성한다(상품_ID);
        ExtractableResponse<Response> response = 장바구니_상품_등록_요청(멤버, 요청);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header(HttpHeaders.LOCATION)).isEqualTo("/carts/products/" + 상품_ID)
        );
    }

    private CartAddProductRequest 장바구니_상품_등록_요청을_생성한다(final Long 상품_ID) {
        return new CartAddProductRequest(상품_ID);
    }

    private ExtractableResponse<Response> 장바구니_상품_등록_요청(final MemberEntity 멤버, final CartAddProductRequest 요청) {
        return given().header(인증_헤더(멤버))
                .contentType(JSON)
                .body(toJson(요청))
                .when().post("/carts/products")
                .then()
                .log().all()
                .extract();
    }

    @Test
    public void 장바구니에서_상품을_삭제한다() {
        멤버를_생성한다();
        final Long 상품_ID = 상품을_등록한다(상품);

        ExtractableResponse<Response> response = 장바구니_상품_삭제_요청(멤버, 상품_ID);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private ExtractableResponse<Response> 장바구니_상품_삭제_요청(final MemberEntity 멤버, final Long 상품_ID) {
        return given().header(인증_헤더(멤버))
                .contentType(JSON)
                .when().delete("/carts/products/" + 상품_ID)
                .then()
                .log().all()
                .extract();
    }

    @Test
    public void 장바구니에_존재하지_않는_상품을_삭제하려는_경우_예외가_발생한다() {
        멤버를_생성한다();
        상품을_등록한다(상품);

        final Long 없는_상품_ID = 2L;

        ExtractableResponse<Response> response = 장바구니_상품_삭제_요청(멤버, 없는_상품_ID);

        assertAll(

                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(response.body().asString()).isEqualTo(ExceptionStatus.NOT_FOUND_CART_PRODUCT.getMessage())
        );
    }

    @Test
    public void 장바구니에서_상품을_조회한다() {
        멤버를_생성한다();
        상품을_등록한다(상품);
        상품을_등록한다(new ProductEntity("치킨", 20000,
                "https://pelicana.co.kr/resources/images/menu/best_menu03_200623.jpg"));

        ExtractableResponse<Response> response = 장바구니_상품_조회_요청(멤버);
        System.out.println();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.body().jsonPath().getList("$")).hasSize(2)
        );
    }

    private ExtractableResponse<Response> 장바구니_상품_조회_요청(final MemberEntity 멤버) {
        return given().header(인증_헤더(멤버))
                .contentType(JSON)
                .when().get("/carts/products")
                .then()
                .log().all()
                .extract();
    }

    @Test
    public void 없는_사용자로_장바구니를_조회하면_예외가_발생한다() {
        final MemberEntity 없는_멤버 = new MemberEntity("wrongVero", "wrongVeroPassword");

        ExtractableResponse<Response> response = 장바구니_상품_조회_요청(없는_멤버);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.body().asString()).isEqualTo(ExceptionStatus.INVALID_AUTHORIZATION.getMessage())
        );
    }

    private void 멤버를_생성한다() {
        memberDao.save(멤버);
    }

    private Long 상품을_등록한다(final ProductEntity 상품) {
        return productDao.save(상품);
    }

    private String toJson(final CartAddProductRequest cartAddProductRequest) {
        try {
            return objectMapper.writeValueAsString(cartAddProductRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Header 인증_헤더(final MemberEntity 회원) {
        return new Header(HttpHeaders.AUTHORIZATION, Basic_인증_토큰(회원));
    }

    private String Basic_인증_토큰(final MemberEntity member) {
        final String email = member.getEmail();
        final String password = member.getPassword();
        return "Basic " + new String(Base64Utils.encode((email + ":" + password).getBytes()));
    }
}
