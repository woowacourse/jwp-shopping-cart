package cart.integration.cart;

import cart.cart.repository.CartRepository;
import cart.config.IntegrationTestConfig;
import cart.member.domain.Member;
import cart.member.domain.MemberId;
import cart.member.repository.MemberRepository;
import cart.product.domain.Product;
import cart.product.domain.ProductId;
import cart.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

class CartIntegrationTest extends IntegrationTestConfig {
    private static final Member MEMBER = new Member("헤나", "email@test.com", "password");
    private static final Product CHICKEN = new Product("치킨", 10000, "image-url");
    private static final String BASIC_MEMBER = "Basic ZW1haWxAdGVzdC5jb206cGFzc3dvcmQ=";

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    MemberId memberId;
    ProductId productId;

    @BeforeEach
    void setUp() {
        memberId = memberRepository.save(MEMBER);
        productId = productRepository.save(CHICKEN);
    }

    @DisplayName("Authorization Basic 토큰값이 유효할 때 회원의 장바구니 상품 목록을 조회할 수 있다.")
    @Test
    void getCartProducts() {
        cartRepository.saveByMemberId(memberId, productId);

        given(this.spec)
                .log().all()
                .header("Authorization", BASIC_MEMBER)
                .filter(document(METHOD_NAME,
                        responseFields(
                                fieldWithPath("[].id").description("상품 번호"),
                                fieldWithPath("[].name").description("상품명"),
                                fieldWithPath("[].price").description("상품 가격"),
                                fieldWithPath("[].image").description("상품 이미지 url")
                        )))
                .contentType(APPLICATION_JSON_VALUE)

        .when()
                .get("/carts")

        .then()
                .statusCode(OK.value());
    }

    @DisplayName("Authorization Basic 토큰값이 유효할 때 회원의 장바구니에 상품을 추가할 수 있다.")
    @Test
    void addProductInCart() {
        given(this.spec)
                .log().all()
                .header("Authorization", BASIC_MEMBER)
                .filter(document(METHOD_NAME))
                .contentType(APPLICATION_JSON_VALUE)

        .when()
                .post("/carts/{id}", productId.getId())

        .then()
                .statusCode(CREATED.value());
    }

    @DisplayName("Authorization Basic 토큰값이 유효할 때 회원의 장바구니에 이미 존재하는 상품을 추가할 경우 BAD_REQUEST를 응답한다.")
    @Test
    void addCartProductDuplicated() {
        cartRepository.saveByMemberId(memberId, productId);

        given(this.spec)
                .log().all()
                .header("Authorization", BASIC_MEMBER)
                .filter(document(METHOD_NAME))
                .contentType(APPLICATION_JSON_VALUE)

        .when()
                .post("/carts/{id}", productId.getId())

        .then()
                .statusCode(BAD_REQUEST.value());
    }

    @DisplayName("Authorization Basic 토큰값이 유효할 때 회원의 장바구니에 존재하는 상품을 제거할 수 있다.")
    @Test
    void removeProductInCart() {
        cartRepository.saveByMemberId(memberId, productId);

        given(this.spec)
                .log().all()
                .header("Authorization", BASIC_MEMBER)
                .filter(document(METHOD_NAME))
                .contentType(APPLICATION_JSON_VALUE)

        .when()
                .delete("/carts/{id}", productId.getId())

        .then()
                .statusCode(NO_CONTENT.value());
    }
}
