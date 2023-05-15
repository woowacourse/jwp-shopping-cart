package cart.controller.api;

import cart.controller.dto.CartRequest;
import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.domain.Cart;
import cart.domain.Member;
import cart.domain.Product;
import cart.service.CartService;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CartService cartService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    private final Member 회원 =
            new Member("chaechae@woowa.com", "1111");

    private final Product 상품 =
            new Product("상품1", "https://aa", 1000);

    @Test
    public void 장바구니에_저장() {
        // given
        Long 회원_아이디 = 회원을_저장한다(회원);
        Long 상품_아이디 = 상품을_저장한다(상품);
        final CartRequest request = new CartRequest(상품_아이디);

        // when
        ExtractableResponse<Response> response = 장바구니에_추가_요청(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void 장바구니_조회() {
        // given
        Long 회원_아이디 = 회원을_저장한다(회원);
        Long 상품_아이디1 = 상품을_저장한다(상품);
        Long 상품_아이디2 = 상품을_저장한다(상품);
        final CartRequest request1 = new CartRequest(상품_아이디1);
        final CartRequest request2 = new CartRequest(상품_아이디2);
        장바구니에_추가_요청(request1);
        장바구니에_추가_요청(request2);

        ExtractableResponse<Response> response = 장바구니_조회_요청();
        JsonPath jsonPath = response.jsonPath();

        assertThat(jsonPath.getList("id")).hasSize(cartDao.findByMemberId(회원_아이디).size());
    }

    private ExtractableResponse<Response> 장바구니_조회_요청() {
        return given()
                .log().all()
                .header(new Header("Authorization", Basic_인증_토큰(회원)))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/carts")
                .then()
                .extract();
    }

    private ExtractableResponse<Response> 장바구니에_추가_요청(CartRequest request) {
        return given()
                .log().all()
                .header(new Header("Authorization", Basic_인증_토큰(회원)))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/carts")
                .then().log().all()
                .extract();
    }

    private String Basic_인증_토큰(final Member member) {
        final String email = member.getEmail();
        final String password = member.getPassword();
        return "Basic " + new String(Base64Utils.encode((email + ":" + password).getBytes()));
    }

    private Long 상품을_저장한다(final Product product) {
        return productDao.save(product);
    }

    private Long 회원을_저장한다(final Member member) {
        return memberDao.save(member);
    }

    private Long 회원_아이디(final String email) {
        return memberDao.findByEmail(email).getId();
    }
}
