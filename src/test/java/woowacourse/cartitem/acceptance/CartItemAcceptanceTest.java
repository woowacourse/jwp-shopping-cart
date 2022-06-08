package woowacourse.cartitem.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.customer.acceptance.CustomerAcceptanceTest.로그인되어_토큰_가져옴;
import static woowacourse.customer.acceptance.CustomerAcceptanceTest.회원_가입_요청;
import static woowacourse.product.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.cartitem.dto.CartItemAddRequest;
import woowacourse.cartitem.dto.CartItemResponse;
import woowacourse.cartitem.dto.CartItemResponse.InnerCartItemResponse;
import woowacourse.cartitem.dto.CartItemResponses;
import woowacourse.customer.dto.SignupRequest;
import woowacourse.product.dto.ProductRequest;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("장바구니 관련 기능")
public class CartItemAcceptanceTest extends AcceptanceTest {

    private String accessToken;
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        회원_가입_요청(new SignupRequest("jjanggu", "password1234", "01022223333", "서울시 여러분"));
        accessToken = 로그인되어_토큰_가져옴(new LoginRequest("jjanggu", "password1234"));
        productId1 = 상품_등록되어_있음(new ProductRequest("짱구", 100_000_000, 10, "jjanggu.jpg"));
        productId2 = 상품_등록되어_있음(new ProductRequest("짱아", 10_000_000, 10, "jjanga.jpg"));
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음(accessToken, new CartItemAddRequest(productId1, 1));
        장바구니_아이템_추가되어_있음(accessToken, new CartItemAddRequest(productId2, 1));

        final ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        final CartItemAddRequest cartItemAddRequest = new CartItemAddRequest(productId1, 1);
        final ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, cartItemAddRequest);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 수량 수정")
    @Test
    void updateCartItemByQuantity() {
        final Long cartItemId = 장바구니_아이템_추가되어_있음(accessToken, new CartItemAddRequest(productId2, 1));
        final int updatedQuantity = 5;

        final ExtractableResponse<Response> response = 장바구니_아이템_수량_수정_요청(accessToken, cartItemId, updatedQuantity);

        장바구니_아이템_수량_수정됨(response);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        final Long cartId = 장바구니_아이템_추가되어_있음(accessToken, new CartItemAddRequest(productId1, 1));

        final ExtractableResponse<Response> response = 장바구니_삭제_요청(accessToken, cartId);

        장바구니_삭제됨(response);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(final String accessToken) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/cartItems")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(
        final String accessToken,
        final CartItemAddRequest cartItemAddRequest
    ) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(cartItemAddRequest)
            .when().post("/api/cartItems")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_수량_수정_요청(
        final String accessToken, final Long cartItemId, final int updatedQuantity
    ) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().patch("/api/cartItems/" + cartItemId + "?quantity=" + updatedQuantity)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(final String accessToken, final Long cartId) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/api/cartItems/{cartItemId}", cartId)
            .then().log().all()
            .extract();
    }

    public static void 장바구니_아이템_추가됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().as(CartItemResponse.class).getCartItem().getId()).isNotNull();
    }

    public static Long 장바구니_아이템_추가되어_있음(
        final String accessToken,
        final CartItemAddRequest cartItemAddRequest
    ) {
        final ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, cartItemAddRequest);
        return response.as(CartItemResponse.class).getCartItem().getId();
    }

    public static void 장바구니_아이템_목록_응답됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(final ExtractableResponse<Response> response, final Long... productIds) {
        final List<Long> resultProductIds = response.as(CartItemResponses.class).getCartItems().stream()
            .map(InnerCartItemResponse::getProductId)
            .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_아이템_수량_수정됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    }

    public static void 장바구니_삭제됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
