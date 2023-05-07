package cart.controller.api;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import cart.controller.dto.CartProductRequest;
import cart.dao.CartProductDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.domain.CartProduct;
import cart.domain.Member;
import cart.domain.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.Base64Utils;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("CartProductController 는")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/truncate.sql")
class CartProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private CartProductDao cartProductDao;

    private final Member 회원
            = new Member("mallang@woowa.com", "1234");

    private final Product 상품
            = new Product("상품", "https://woowa.chat", 1000);

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void 장바구니에_상품을_등록한다() {
        // given
        회원을_저장한다(회원);
        final Long 상품_ID = 상품을_저장한다(상품);
        final CartProductRequest request = 장바구니에_등록할_상품_요청을_생성한다(상품_ID);

        // when
        final ExtractableResponse<Response> response = 장바구니에_상품_등록_요청(회원, request);

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        assertThat(response.header("location"))
                .isEqualTo(format("http://localhost:%d/cart-products/%s", port, 생성된_ID(response)));
    }

    @Test
    public void 동일인이_동일한_상품을_중복해서_등록하면_오류() {
        // given
        회원을_저장한다(회원);
        final Long 상품_ID = 상품을_저장한다(상품);
        final CartProductRequest request = 장바구니에_등록할_상품_요청을_생성한다(상품_ID);
        장바구니에_상품_등록_요청(회원, request);

        // when
        final ExtractableResponse<Response> response = 장바구니에_상품_등록_요청(회원, request);

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
    }

    private ExtractableResponse<Response> 장바구니에_상품_등록_요청(final Member 회원, final CartProductRequest request) {
        return given()
                .header(인증_헤더(회원))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(toJson(request))
                .when()
                .post("/cart-products")
                .then()
                .log().all()
                .extract();
    }

    @Test
    void 장바구니에서_상품을_제거할_수_있다() {
        // given
        final Long 회원_ID = 회원을_저장한다(회원);
        final Long 상품_ID = 상품을_저장한다(상품);
        final Long id = cartProductDao.save(new CartProduct(회원_ID, 상품_ID));

        // when
        final ExtractableResponse<Response> response = 장바구니_상품_제거_요청(회원, id);

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(cartProductDao.findAllByMemberId(회원_ID))
                .hasSize(0);
    }

    private ExtractableResponse<Response> 장바구니_상품_제거_요청(final Member 회원, final Long id) {
        return given()
                .header(인증_헤더(회원))
                .when()
                .delete("/cart-products/{id}", id)
                .then()
                .log().all()
                .extract();
    }

    @Test
    void 해당_회원의_장바구니에_담긴_모든_상품을_조회한다() {
        // given
        final Long 회원_ID = 회원을_저장한다(회원);
        final Long 상품_ID = 상품을_저장한다(상품);
        final Long 상품2_ID = 상품을_저장한다(new Product("상품2", "https://woowa.chat", 100));
        cartProductDao.save(new CartProduct(회원_ID, 상품_ID));
        cartProductDao.save(new CartProduct(회원_ID, 상품2_ID));

        // when
        final ExtractableResponse<Response> response = 장바구니_상품_조회_요청(회원);

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(response.jsonPath().getInt("size()")).isEqualTo(2);
    }

    private ExtractableResponse<Response> 장바구니_상품_조회_요청(final Member 회원) {
        return given()
                .header(인증_헤더(회원))
                .when()
                .get("/cart-products")
                .then()
                .log().all()
                .extract();
    }


    private String toJson(final CartProductRequest request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Long 상품을_저장한다(final Product product) {
        return productDao.save(product);
    }

    private Long 회원을_저장한다(final Member member) {
        return memberDao.save(member);
    }

    private CartProductRequest 장바구니에_등록할_상품_요청을_생성한다(final Long productId) {
        return new CartProductRequest(productId);
    }

    private Header 인증_헤더(final Member 회원) {
        return new Header("Authorization", Basic_인증_토큰(회원));
    }

    private String Basic_인증_토큰(final Member member) {
        final String email = member.getEmail();
        final String password = member.getPassword();
        return "Basic " + new String(Base64Utils.encode((email + ":" + password).getBytes()));
    }

    private String 생성된_ID(final ExtractableResponse<Response> 응답) {
        return 응답.body().asString();
    }
}
