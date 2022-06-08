package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.TMember.MARU;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.CartQuantityUpdateRequest;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        MARU.register();
        MARU.login();
        final String token = MARU.getToken();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, new CartRequest(productId1));

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        MARU.register();
        MARU.login();
        final String token = MARU.getToken();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        장바구니_아이템_추가되어_있음(token, new CartRequest(productId1));
        장바구니_아이템_추가되어_있음(token, new CartRequest(productId2));

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 물품 개수 변경")
    @Test
    void updateCartQuantity(){
        MARU.register();
        MARU.login();
        final String token = MARU.getToken();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        final Long cartId = 장바구니_아이템_추가되어_있음(token, new CartRequest(productId1));
        장바구니_아이템_개수_변경됨(token, cartId, new CartQuantityUpdateRequest(2));
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        MARU.register();
        MARU.login();
        final String token = MARU.getToken();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        Long cartId = 장바구니_아이템_추가되어_있음(token, new CartRequest(productId1));

        ExtractableResponse<Response> response = 장바구니_삭제_요청(token, cartId);

        장바구니_삭제됨(response);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String token, CartRequest cartRequest) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequest)
                .when().post("/api/members/me/carts")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 장바구니_아이템_변경_요청(final String token, final Long cartId,
                                                         final CartQuantityUpdateRequest cartQuantityUpdateRequest) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartQuantityUpdateRequest)
                .when().put("/api/members/me/carts/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/members/me/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String token, Long cartId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/members/me/carts/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 장바구니_아이템_추가되어_있음(String token, CartRequest cartRequest) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, cartRequest);
        return Long.parseLong(response.header("Location").split("/carts/")[1]);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", CartResponse.class).stream()
                .map(CartResponse::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    private void 장바구니_아이템_개수_변경됨(final String token, final Long cartId, final CartQuantityUpdateRequest cartQuantityUpdateRequest) {
        ExtractableResponse<Response> response = 장바구니_아이템_변경_요청(token, cartId, cartQuantityUpdateRequest);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
