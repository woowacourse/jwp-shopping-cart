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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.cartitem.CartItemResponse;
import woowacourse.shoppingcart.dto.cartitem.CartItemSaveRequest;

@DisplayName("장바구니 관련 기능")
public class CartItemAcceptanceTest extends AcceptanceTest {
    private String accessToken;
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        accessToken = 회원_가입_후_로그인();
        productId1 = 상품_등록되어_있음("치킨", 10_000, 20, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, 10, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, new CartItemSaveRequest(productId1, 5));

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 추가 수량 초과 시 실패")
    @Test
    void addCartItemLargeQuantity_fail() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, new CartItemSaveRequest(productId1, 21));

        장바구니_아이템_추가_실패함(response);
    }


    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음(accessToken, new CartItemSaveRequest(productId1, 5));
        장바구니_아이템_추가되어_있음(accessToken, new CartItemSaveRequest(productId2, 6));

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 수량 수정")
    @Nested
    class UpdateCartItemQuantity extends AcceptanceTest {

        private Long cartItemId;

        @BeforeEach
        void prepare() {
            cartItemId = 장바구니_아이템_추가되어_있음(accessToken, new CartItemSaveRequest(productId1, 5));
        }

        @DisplayName("장바구니 수량 수정")
        @Test
        void sucess() {
            ExtractableResponse<Response> response = 장바구니_수량_변경_요청(accessToken, cartItemId, 10);

            장바구니_수량_변경됨(response);
        }

        @DisplayName("장바구니 수량 수정 수량 초과 시 실패")
        @Test
        void LargeQuantity_fail() {
            ExtractableResponse<Response> response = 장바구니_수량_변경_요청(accessToken, cartItemId, 21);

            장바구니_수량_변경_실패함(response);
        }
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        Long cartId = 장바구니_아이템_추가되어_있음(accessToken, new CartItemSaveRequest(productId1, 5));

        ExtractableResponse<Response> response = 장바구니_삭제_요청(accessToken, cartId);

        장바구니_삭제됨(response);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, CartItemSaveRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(request)
                .when().post("/api/cartItems")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .when().get("/api/cartItems")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_수량_변경_요청(String accessToken, Long cartItemId, int quantity) {
        return RestAssured
                .given().log().all()
                .param("quantity", quantity)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .when().patch("/api/cartItems/{cartItemId}", cartItemId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String accessToken, Long cartId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .when().delete("/api/cartItems/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static void 장바구니_아이템_추가_실패함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    public static Long 장바구니_아이템_추가되어_있음(String userName, CartItemSaveRequest request) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(userName, request);
        return Long.parseLong(response.header("Location").split("/cartItems/")[1]);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList("cartItems.", CartItemResponse.class).stream()
                .map(CartItemResponse::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_수량_변경됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_수량_변경_실패함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
