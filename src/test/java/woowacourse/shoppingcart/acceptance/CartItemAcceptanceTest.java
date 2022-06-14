package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.AcceptanceFixture.createCustomer;
import static woowacourse.Fixture.페퍼;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemUpdateRequest;

@DisplayName("장바구니 관련 기능")
class CartItemAcceptanceTest extends AcceptanceTest {
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        String accessToken = 인증토큰_생성();
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId1);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        String accessToken = 인증토큰_생성();
        장바구니_아이템_추가되어_있음(accessToken, productId2);
        장바구니_아이템_추가되어_있음(accessToken, productId1);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 상품 수량 수정")
    @Test
    void updateQuantity() {
        String accessToken = 인증토큰_생성();
        Long cartItemId = 장바구니_아이템_추가되어_있음(accessToken, productId1);
        CartItemUpdateRequest request = new CartItemUpdateRequest(10);

        ExtractableResponse<Response> response = 장바구니_아이템_수량_수정_요청(accessToken, request, cartItemId);

        장바구니_아이템_수량_응답됨(response);
    }

    @DisplayName("장바구니 모두 삭제")
    @Test
    void deleteAllCartItem() {
        String accessToken = 인증토큰_생성();
        장바구니_아이템_추가되어_있음(accessToken, productId1);
        장바구니_아이템_추가되어_있음(accessToken, productId2);

        ExtractableResponse<Response> response = 장바구니_전체_삭제_요청(accessToken);

        장바구니_삭제됨(response);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        String accessToken = 인증토큰_생성();
        Long cartId = 장바구니_아이템_추가되어_있음(accessToken, productId1);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(accessToken, cartId);

        장바구니_삭제됨(response);
    }

    public static String 인증토큰_생성() {
        createCustomer(페퍼);
        return RestAssured
                .given().log().all()
                .body(new TokenRequest(페퍼_아이디, 페퍼_비밀번호))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .extract().as(TokenResponse.class).getAccessToken();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, Long productId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartItemCreateRequest(productId))
                .when().post("/customers/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/carts")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 장바구니_아이템_수량_수정_요청(String accessToken, CartItemUpdateRequest request,
                                                            Long cartItemId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/customers/carts/" + cartItemId)
                .then().log().all()
                .extract();
    }

    private void 장바구니_아이템_수량_응답됨(ExtractableResponse<Response> response) {
        CartItemResponse cartItemResponse = response.as(CartItemResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(cartItemResponse.getQuantity()).isEqualTo(10)
        );
    }

    private ExtractableResponse<Response> 장바구니_전체_삭제_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/customers/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String accessToken, Long cartId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/customers/carts/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        CartItem cartItem = response.as(CartItem.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(cartItem.getName()).isEqualTo("치킨"),
                () -> assertThat(cartItem.getPrice()).isEqualTo(10_000),
                () -> assertThat(cartItem.getImageUrl()).isEqualTo("http://example.com/chicken.jpg")
        );
    }

    public static Long 장바구니_아이템_추가되어_있음(String accessToken, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId);
        return response.as(CartItem.class).getId();
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", CartItem.class).stream()
                .map(CartItem::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
