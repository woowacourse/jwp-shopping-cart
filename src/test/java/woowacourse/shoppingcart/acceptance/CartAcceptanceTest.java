package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
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
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.DeleteProductIds;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdateProductQuantityRequest;
import woowacourse.shoppingcart.dto.response.AlreadyExistCartItemResponse;
import woowacourse.shoppingcart.dto.response.CartItemsResponse;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest2 {
    private static final String 유효한_아이디 = "유효한_아이디";
    private static final String 유효한_비밀번호 = "password1@";
    private static final SignUpRequest 유효한_회원가입_요청 = new SignUpRequest(유효한_아이디, 유효한_비밀번호, "닉네임", 15);
    private static final TokenRequest 유효한_로그인_요청 = new TokenRequest(유효한_아이디, 유효한_비밀번호);

    private static String 유효한_토큰;

    private Long productId1;
    private Long productId2;

    @BeforeEach
    public void setUp() {
        회원가입_요청(유효한_회원가입_요청);
        유효한_토큰 = 로그인_성공_시_토큰_반환(유효한_로그인_요청);

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(productId1);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("이미 존재하는 장바구이 아이템을 추가하면 400을 던진다")
    @Test
    void addCartItem_already_exist() {
        장바구니_아이템_추가_요청(productId1);

        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(productId1);

        이미_존재하는_장바구니_아이템(response);
    }

    private void 이미_존재하는_장바구니_아이템(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        AlreadyExistCartItemResponse body = response.jsonPath().getObject(".", AlreadyExistCartItemResponse.class);
        assertThat(body.getRedirect()).isTrue();
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가_요청(productId1);
        장바구니_아이템_추가_요청(productId2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청();

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 품목 수량 변경")
    @Test
    void updateCartItemQuantity() {
        int 수량 = 3;
        장바구니_아이템_추가_요청(productId1);

        ExtractableResponse<Response> response = 장바구니_품복_수량_변경(productId1, 수량);

        장바구니_아이템_수량_응답됨(response);
    }

    private void 장바구니_아이템_수량_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> 장바구니_품복_수량_변경(Long productId, int 수량) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(유효한_토큰)
                .body(new UpdateProductQuantityRequest(수량))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/cart/{productId}/quantity", productId)
                .then().log().all()
                .extract();
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        장바구니_아이템_추가_요청(productId1);
        장바구니_아이템_추가_요청(productId2);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(new DeleteProductIds(List.of(productId1)));

        장바구니_삭제됨(response);
        assertThat(장바구니_목록_조회().size()).isEqualTo(1);
    }

    @DisplayName("장바구니 비우기")
    @Test
    void deleteAllCartItem() {
        장바구니_아이템_추가_요청(productId1);
        장바구니_아이템_추가_요청(productId2);

        ExtractableResponse<Response> response = 장바구니_비우기_요청();

        장바구니_삭제됨(response);
        assertThat(장바구니_목록_조회().size()).isZero();
    }

    private ExtractableResponse<Response> 장바구니_비우기_요청() {
        return RestAssured
                .given().log().all()
                .auth().oauth2(유효한_토큰)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart")
                .then().log().all()
                .extract();
    }

    private List<CartItem> 장바구니_목록_조회() {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(유효한_토큰)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart")
                .then().log().all()
                .extract();

        return response.jsonPath()
                .getObject(".", CartItemsResponse.class)
                .getCartItems();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(Long productId) {

        return RestAssured
                .given().log().all()
                .auth().oauth2(유효한_토큰)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청() {
        return RestAssured
                .given().log().all()
                .auth().oauth2(유효한_토큰)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(DeleteProductIds deleteProductIds) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(유효한_토큰)
                .body(deleteProductIds)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart/products")
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static Long 장바구니_아이템_추가되어_있음(Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(productId);
        return Long.parseLong(response.header("Location").split("/carts/")[1]);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        CartItemsResponse cartItemsResponse = response.jsonPath().getObject(".", CartItemsResponse.class);
        List<CartItem> cartItems = cartItemsResponse.getCartItems();
        List<Long> ids = cartItems.stream()
                .map(CartItem::getProduct)
                .map(Product::getId)
                .collect(Collectors.toList());
        assertThat(ids).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private ExtractableResponse<Response> 회원가입_요청(SignUpRequest json) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(json)
                .when().post("/customers")
                .then().log().all()
                .extract();
    }

    private String 로그인_성공_시_토큰_반환(TokenRequest tokenRequest) {
        return RestAssured.given().log().all()
                .body(tokenRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract()
                .as(TokenResponse.class)
                .getAccessToken();
    }
}
