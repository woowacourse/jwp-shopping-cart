package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.acceptance.AuthAcceptanceTest.로그인_후_토큰_획득;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.회원_추가되어_있음;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemsResponse;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductsRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private Long productId1;
    private Long productId2;
    private String token;

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String token, CartRequest cartRequest) {
        return requestHttpPost(token, cartRequest, "/customers/carts").extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String token) {
        return requestHttpGet(token, "/customers/carts").extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String token, ProductsRequest productsRequest) {
        return requestHttpDelete(token, productsRequest, "/customers/carts").extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        CartResponse cart = response.as(CartResponse.class);
        assertThat(cart).extracting("productId", "quantity")
                .containsExactly(1L, 10);
    }

    public static void 장바구니_아이템_추가되어_있음(String token, CartRequest cartRequest) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, cartRequest);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        CartItemsResponse cartItems = response.jsonPath().getObject(".", CartItemsResponse.class);
        List<Long> resultProductIds = cartItems.getCarts().stream()
                .map(CartItemResponse::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        회원_추가되어_있음();
        token = 로그인_후_토큰_획득();
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, new CartRequest(productId1, 10));

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음(token, new CartRequest(productId1, 10));
        장바구니_아이템_추가되어_있음(token, new CartRequest(productId2, 20));

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 수량 업데이트")
    @Test
    void updateCartItemQuantity() {
        장바구니_아이템_추가되어_있음(token, new CartRequest(productId1, 10));

        ExtractableResponse<Response> response = 장바구니_아이템_수량_업데이트_요청(token, new CartRequest(productId1, 5));

        장바구니_아이템_수량이_업데이트됨(response);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        장바구니_아이템_추가되어_있음(token, new CartRequest(productId1, 10));

        ExtractableResponse<Response> response = 장바구니_삭제_요청(token, new ProductsRequest(List.of(productId1)));

        장바구니_삭제됨(response);
    }

    private ExtractableResponse<Response> 장바구니_아이템_수량_업데이트_요청(String token, CartRequest cartRequest) {
        return createBody(token, cartRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .patch("/customers/carts")
                .then().log().all()
                .extract();
    }

    private void 장바구니_아이템_수량이_업데이트됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
