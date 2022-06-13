package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.acceptance.AuthAcceptanceTest.회원_가입_후_로그인;
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
import woowacourse.shoppingcart.dto.cartItem.CartItemAddRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemsResponse.CartItemInnerResponse;
import woowacourse.shoppingcart.dto.product.ProductAddRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    private static final String USER = "puterism";
    private Long productId1;
    private Long productId2;
    private String token;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        token = 회원_가입_후_로그인();

        productId1 = 상품_등록되어_있음(
                new ProductAddRequest("치킨", 10_000, 100, "chicken.png"));
        productId2 = 상품_등록되어_있음(
                new ProductAddRequest("치킨", 10_000, 100, "chicken.png"));
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(new CartItemAddRequest(productId1, 1), token);

        응답_OK_헤더_Location_존재(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음(new CartItemAddRequest(productId1, 1), token);
        장바구니_아이템_추가되어_있음(new CartItemAddRequest(productId2, 1), token);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token);

        응답_OK(response);
        바디_아이템_id_목록_존재(response, productId1, productId2);
    }
    
    @DisplayName("장바구니 아이템 수량 정정")
    @Test
    void updateCartItemQuantity() {
        Long cartItemId = 장바구니_아이템_추가되어_있음(new CartItemAddRequest(productId1, 1), token);

        ExtractableResponse<Response> response = 장바구니_아이템_수량_수정_요청(cartItemId, 2, token);
        응답_OK(response);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        Long cartItemId = 장바구니_아이템_추가되어_있음(new CartItemAddRequest(productId1, 1), token);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(cartItemId, token);

        응답_NO_CONTENT(response);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(CartItemAddRequest request, String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/cartItems")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_수량_수정_요청(Long cartItemId, int quantity, String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/api/cartItems/" + cartItemId + "?quantity=" + quantity)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/cartItems")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(Long cartItemId, String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/cartItems/" + cartItemId)
                .then().log().all()
                .extract();
    }

    public static void 응답_OK_헤더_Location_존재(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 장바구니_아이템_추가되어_있음(CartItemAddRequest request, String token) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(request, token);
        String[] path = response.header("Location").split("/");
        return Long.parseLong(path[path.length - 1]);
    }

    private void 응답_OK(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 바디_아이템_id_목록_존재(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList("cartItems", CartItemInnerResponse.class).stream()
                .map(CartItemInnerResponse::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    private void 응답_NO_CONTENT(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
