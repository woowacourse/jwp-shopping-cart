package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.acceptance.AuthAcceptanceTest.로그인_요청;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.사용자_생성_요청;
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
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CartQuantityRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.LoginRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

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
        TokenResponse tokenResponse = 토큰_생성_요청();

        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(productId1, tokenResponse.getAccessToken());

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        TokenResponse tokenResponse = 토큰_생성_요청();

        장바구니_아이템_추가되어_있음(productId1, tokenResponse.getAccessToken());
        장바구니_아이템_추가되어_있음(productId2, tokenResponse.getAccessToken());

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(tokenResponse.getAccessToken());

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        TokenResponse tokenResponse = 토큰_생성_요청();

        Long cartId = 장바구니_아이템_추가되어_있음(productId1, tokenResponse.getAccessToken()).getId();

        ExtractableResponse<Response> response = 장바구니_삭제_요청(cartId, tokenResponse.getAccessToken());

        장바구니_삭제됨(response);
    }

    @DisplayName("장바구니 전체 삭제")
    @Test
    void deleteAllCartItem() {
        TokenResponse tokenResponse = 토큰_생성_요청();

        장바구니_아이템_추가되어_있음(productId1, tokenResponse.getAccessToken());
        장바구니_아이템_추가되어_있음(productId2, tokenResponse.getAccessToken());

        ExtractableResponse<Response> response = 장바구니_전체_삭제_요청(tokenResponse.getAccessToken());

        장바구니_삭제됨(response);
    }

    @DisplayName("장바구니 수량 수정")
    @Test
    void updateCartItem() {
        TokenResponse tokenResponse = 토큰_생성_요청();

        Long cartId = 장바구니_아이템_추가되어_있음(productId1, tokenResponse.getAccessToken()).getId();
        CartQuantityRequest cartQuantityRequest = new CartQuantityRequest(5);

        ExtractableResponse<Response> response = 장바구니_수량_수정_요청(tokenResponse.getAccessToken(), cartId, cartQuantityRequest);

        장바구니_수량_수정됨(response);
    }

    public static TokenResponse 토큰_생성_요청() {
        사용자_생성_요청("loginId", "seungpapang", "12345678aA!");
        LoginRequest loginRequest = new LoginRequest("loginId", "12345678aA!");
        ExtractableResponse<Response> loginResponse = 로그인_요청(loginRequest);
        return loginResponse.as(TokenResponse.class);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(Long productId, String accessToken) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(requestBody)
                .when().post("/customers/me/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().get("/customers/me/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(Long cartId, String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().delete("/customers/me/carts/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_전체_삭제_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().delete("/customers/me/carts")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 장바구니_수량_수정_요청(String accessToken, Long cartId, CartQuantityRequest cartQuantityRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(cartQuantityRequest)
                .when().put("/customers/me/carts/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static CartResponse 장바구니_아이템_추가되어_있음(Long productId, String accessToken) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(productId, accessToken);

        return response.body().as(CartResponse.class);
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

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private void 장바구니_수량_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
