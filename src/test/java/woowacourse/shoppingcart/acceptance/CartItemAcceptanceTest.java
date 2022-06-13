package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.CUSTOMER_REQUEST_1;
import static woowacourse.Fixtures.EXPIRED_TOKEN;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.AcceptanceTest;
import woowacourse.auth.acceptance.AuthAcceptanceTest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.request.CartItemRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponse;

@DisplayName("장바구니 관련 기능")
@Sql("classpath:schema.sql")
public class CartItemAcceptanceTest extends AcceptanceTest {
    private String accessToken;

    private Long productId1;
    private Long productId2;

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, Long productId) {
        CartItemRequest cartItemRequest = new CartItemRequest(productId, 10);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemRequest)
                .when().post("/api/customers/cart")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/cart")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String accessToken, Long cartItemId) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/cart/{cartItemId}", cartItemId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_수정_요청(String accessToken, Long cartItemId,
                                                               CartItemRequest cartItemRequest) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemRequest)
                .when().put("/api/customers/cart/{cartItemId}", cartItemId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니에_상품_존재여부_조회(String accessToken, Long productId) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/cart/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 장바구니_아이템_추가되어_있음(String accessToken, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId);
        return Long.parseLong(response.header("Location").split("/cart/")[1]);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", CartItemResponse.class).stream()
                .map(cartItemResponse -> cartItemResponse.getCartItemId())
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 장바구니_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 장바구니에_상품_존재함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getBoolean("exists")).isTrue();
    }

    public static void NOT_FOUND_에러(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    public static void BAD_REQUEST_에러(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        AuthAcceptanceTest.회원가입_요청_후_생성된_아이디_반환(CUSTOMER_REQUEST_1);
        TokenResponse tokenResponse = AuthAcceptanceTest.로그인_요청_후_토큰_DTO_반환(CUSTOMER_REQUEST_1.getEmail(),
                CUSTOMER_REQUEST_1.getPassword());
        accessToken = tokenResponse.getAccessToken();

        productId1 = 상품_등록되어_있음("치킨", "치킨 입니다.", 10_000, 10, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", "맥주 입니다.", 20_000, 10, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니에 아이템 추가 성공")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId1);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니에 아이템 추가 실패 - 유효하지 않은 토큰")
    @ValueSource(strings = {"", "abcd"})
    @ParameterizedTest
    void addCartItem_failWithInvalidToken(String invalidToken) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(invalidToken, productId1);
        AuthAcceptanceTest.토큰이_유효하지_않음(response);
    }

    @DisplayName("장바구니에 아이템 추가 실패 - 만료된 토큰")
    @Test
    void addCartItem_failWithExpiredToken() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(EXPIRED_TOKEN, productId1);
        AuthAcceptanceTest.토큰이_만료됨(response);
    }

    @DisplayName("장바구니에 아이템 추가시 제품 아이디가 존재하지 않는 경우 404에러 발생")
    @Test
    void addCartItem_productNotFound() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId1 + 999);
        NOT_FOUND_에러(response);
    }

    @DisplayName("장바구니에 이미 담긴 상품을 또 담으면 400에러 발생")
    @Test
    void addCartItem_addSameProduct() {
        장바구니_아이템_추가_요청(accessToken, productId1);
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId1);
        BAD_REQUEST_에러(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음(accessToken, productId1);
        장바구니_아이템_추가되어_있음(accessToken, productId2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 아이템 목록 조회 실패 - 유효하지 않은 토큰")
    @ValueSource(strings = {"", "abcd"})
    @ParameterizedTest
    void getCartItems_failWithInvalidToken(String invalidToken) {
        장바구니_아이템_추가되어_있음(accessToken, productId1);
        장바구니_아이템_추가되어_있음(accessToken, productId2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(invalidToken);

        AuthAcceptanceTest.토큰이_유효하지_않음(response);
    }

    @DisplayName("장바구니 아이템 목록 조회 실패 - 만료된 토큰")
    @Test
    void getCartItems_failWithExpiredToken() {
        장바구니_아이템_추가되어_있음(accessToken, productId1);
        장바구니_아이템_추가되어_있음(accessToken, productId2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(EXPIRED_TOKEN);

        AuthAcceptanceTest.토큰이_만료됨(response);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        Long cartItemId = 장바구니_아이템_추가되어_있음(accessToken, productId1);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(accessToken, cartItemId);

        장바구니_삭제됨(response);
    }

    @DisplayName("장바구니 삭제 실패 - 유효하지 않은 토큰")
    @ValueSource(strings = {"", "abcd"})
    @ParameterizedTest
    void deleteCartItem_failWithInvalidToken(String invalidToken) {
        Long cartItemId = 장바구니_아이템_추가되어_있음(accessToken, productId1);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(invalidToken, cartItemId);

        AuthAcceptanceTest.토큰이_유효하지_않음(response);
    }

    @DisplayName("장바구니 삭제 실패 - 만료된 토큰")
    @Test
    void deleteCartItem_failWithExpiredToken() {
        Long cartItemId = 장바구니_아이템_추가되어_있음(accessToken, productId1);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(EXPIRED_TOKEN, cartItemId);

        AuthAcceptanceTest.토큰이_만료됨(response);
    }

    @DisplayName("장바구니에 특정 상품이 존재하는지 확인")
    @Test
    void isProductExisting() {
        장바구니_아이템_추가되어_있음(accessToken, productId1);

        ExtractableResponse<Response> response = 장바구니에_상품_존재여부_조회(accessToken, productId1);

        장바구니에_상품_존재함(response);
    }

    @DisplayName("장바구니에 특정 상품이 존재하는지 확인 실패 - 유효하지 않은 토큰")
    @ValueSource(strings = {"", "abcd"})
    @ParameterizedTest
    void isProductExisting_failWithInvalidToken(String invalidToken) {
        장바구니_아이템_추가되어_있음(accessToken, productId1);

        ExtractableResponse<Response> response = 장바구니에_상품_존재여부_조회(invalidToken, productId1);

        AuthAcceptanceTest.토큰이_유효하지_않음(response);
    }

    @DisplayName("장바구니에 특정 상품이 존재하는지 확인 실패 - 만료된 토큰")
    @Test
    void isProductExisting_failWithExpiredToken() {
        장바구니_아이템_추가되어_있음(accessToken, productId1);

        ExtractableResponse<Response> response = 장바구니에_상품_존재여부_조회(EXPIRED_TOKEN, productId1);

        AuthAcceptanceTest.토큰이_만료됨(response);
    }

    @DisplayName("장바구니 상품 수정")
    @Test
    void updateCartItem() {
        Long cartItemId = 장바구니_아이템_추가되어_있음(accessToken, productId1);
        CartItemRequest newCartItemRequest = new CartItemRequest(productId1, 500);

        ExtractableResponse<Response> response = 장바구니_아이템_수정_요청(accessToken, cartItemId, newCartItemRequest);

        장바구니_수정됨(response);
    }

    @DisplayName("장바구니에 특정 상품이 존재하는지 확인 실패 - 유효하지 않은 토큰")
    @ValueSource(strings = {"", "abcd"})
    @ParameterizedTest
    void updateCartItem_failWithInvalidToken(String invalidToken) {
        Long cartItemId = 장바구니_아이템_추가되어_있음(accessToken, productId1);
        CartItemRequest newCartItemRequest = new CartItemRequest(productId1, 500);

        ExtractableResponse<Response> response = 장바구니_아이템_수정_요청(invalidToken, cartItemId, newCartItemRequest);

        AuthAcceptanceTest.토큰이_유효하지_않음(response);
    }

    @DisplayName("장바구니에 특정 상품이 존재하는지 확인 실패 - 만료된 토큰")
    @Test
    void updateCartItem_failWithExpiredToken() {
        Long cartItemId = 장바구니_아이템_추가되어_있음(accessToken, productId1);
        CartItemRequest newCartItemRequest = new CartItemRequest(productId1, 500);

        ExtractableResponse<Response> response = 장바구니_아이템_수정_요청(EXPIRED_TOKEN, cartItemId, newCartItemRequest);

        AuthAcceptanceTest.토큰이_만료됨(response);
    }

    @DisplayName("장바구니 상품 수정 시 존재하지 않는 장바구니일 경우 404에러가 발생")
    @Test
    void updateCartItem_notFoundCartItem() {
        Long cartItemId = 장바구니_아이템_추가되어_있음(accessToken, productId1);
        CartItemRequest newCartItemRequest = new CartItemRequest(productId1, 500);

        ExtractableResponse<Response> response = 장바구니_아이템_수정_요청(accessToken, cartItemId + 1, newCartItemRequest);

        NOT_FOUND_에러(response);
    }

    @DisplayName("장바구니 상품 수정 시 Body로 전달된 productId와 실제 cartItem의 productId가 일치하지 않을 경우 400에러 발생")
    @Test
    void updateCartItem_notSameProductId() {
        Long cartItemId = 장바구니_아이템_추가되어_있음(accessToken, productId1);
        CartItemRequest newCartItemRequest = new CartItemRequest(productId2, 500);

        ExtractableResponse<Response> response = 장바구니_아이템_수정_요청(accessToken, cartItemId, newCartItemRequest);

        BAD_REQUEST_에러(response);
    }

    @DisplayName("장바구니 상품 제거 시 존재하지 않는 장바구니일 경우 404에러가 발생")
    @Test
    void deleteCartItem_notFoundCartItem() {
        Long cartItemId = 장바구니_아이템_추가되어_있음(accessToken, productId1);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(accessToken, cartItemId + 1);

        NOT_FOUND_에러(response);
    }
}
