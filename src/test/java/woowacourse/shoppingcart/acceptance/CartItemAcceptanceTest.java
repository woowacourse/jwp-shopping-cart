package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.acceptance.AuthAcceptanceFixture;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.cart.DeleteCartItemRequest;
import woowacourse.shoppingcart.dto.cart.UpdateCartItemRequest;

@DisplayName("장바구니 관련 기능")
public class CartItemAcceptanceTest extends AcceptanceTest {

    private String token;
    private Long productId1;
    private Long productId2;


    public static ExtractableResponse<Response> 토큰으로_장바구니_아이템_추가_요청(final String accessToken, Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/api/customer/cartItems")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 토큰으로_장바구니_아이템_목록_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customer/cartItems")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 토큰으로_장바구니_삭제_요청(String token, DeleteCartItemRequest data) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(data)
                .when().delete("/api/customer/cartItems")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 토큰으로_장바구니_갯수_수정_요청(String token, UpdateCartItemRequest data) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(data)
                .when().put("/api/customer/cartItems")
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
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

    public static void 장바구니_제품_갯수_수정됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
        token = AuthAcceptanceFixture.registerAndGetToken("klay", "klay@gamil.com", "12345678");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 토큰으로_장바구니_아이템_추가_요청(token, productId1);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        토큰으로_장바구니_아이템_추가_요청(token, productId1);
        토큰으로_장바구니_아이템_추가_요청(token, productId2);

        ExtractableResponse<Response> response = 토큰으로_장바구니_아이템_목록_조회_요청(token);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        토큰으로_장바구니_아이템_추가_요청(token, productId1);
        토큰으로_장바구니_아이템_추가_요청(token, productId2);

        ExtractableResponse<Response> response
                = 토큰으로_장바구니_삭제_요청(token, new DeleteCartItemRequest(List.of(1L, 2L)));

        장바구니_삭제됨(response);
    }

    @Test
    @DisplayName("장바구니 제품 갯수 수정")
    void updateCartItemQuantity() {
        // given
        토큰으로_장바구니_아이템_추가_요청(token, productId1);

        // when
        final ExtractableResponse<Response> response = 토큰으로_장바구니_갯수_수정_요청(token,
                new UpdateCartItemRequest(1L, 2));

        // then
        장바구니_제품_갯수_수정됨(response);
    }
}
