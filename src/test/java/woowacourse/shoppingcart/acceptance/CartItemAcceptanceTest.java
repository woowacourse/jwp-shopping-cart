package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemsResponse;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.SignupRequest;

@DisplayName("장바구니 관련 기능")
public class CartItemAcceptanceTest extends AcceptanceTest {
    private static final String USER = "puterism";
    private Long productId1;
    private Long productId2;
    private String accessToken;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        회원가입_되어있음(signupRequest);
        LoginRequest loginRequest = new LoginRequest("dongho108", "ehdgh1234");
        accessToken = 로그인하고_토큰받아옴(loginRequest);

        ProductRequest 치킨 = new ProductRequest("치킨", 10_000, 10, "http://example.com/chicken.jpg");
        ProductRequest 맥주 = new ProductRequest("맥주", 20_000, 10, "http://example.com/beer.jpg");
        productId1 = 상품_등록되어_있음_DTO(치킨);
        productId2 = 상품_등록되어_있음_DTO(맥주);
    }

    @DisplayName("장바구니 아이템 추가")
    @Disabled
    @Test
    void addCartItem() {
        CartItemRequest cartItemRequest = new CartItemRequest(productId1, 5);
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청_토큰(cartItemRequest, accessToken);

        장바구니_아이템_추가됨_OK(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Disabled
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음_토큰(productId1);
        장바구니_아이템_추가되어_있음_토큰(productId2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청_토큰(accessToken);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨_토큰(response, productId1, productId2);
    }

    @DisplayName("장바구니 삭제")
    @Disabled
    @Test
    void deleteCartItem() {
        Long cartId = 장바구니_아이템_추가되어_있음(USER, productId1);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(USER, cartId);

        장바구니_삭제됨(response);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String userName, Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/api/customers/{customerName}/cartItems", userName)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청_토큰(CartItemRequest cartItemRequest, String token) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .auth().oauth2(token)
            .body(cartItemRequest)
            .when().post("/api/customers/cartItems")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String customerName) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/customers/{customerName}/cartItems", customerName)
            .then().log().all()
            .extract();
    }

    public ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청_토큰(String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().get("/api/customers/cartItems")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String userName, Long cartId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/{customerName}/cartItems/{cartId}", userName, cartId)
                .then().log().all()
                .extract();
    }

    public void 장바구니_아이템_추가됨_OK(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        CartItemResponse cartItemResponse = response.as(CartItemResponse.class);

        assertAll(
            () -> assertThat(cartItemResponse.getId()).isNotNull(),
            () -> assertThat(cartItemResponse.getProductId()).isEqualTo(productId1),
            () -> assertThat(cartItemResponse.getName()).isEqualTo("치킨"),
            () -> assertThat(cartItemResponse.getPrice()).isEqualTo(10_000),
            () -> assertThat(cartItemResponse.getImageURL()).isEqualTo("http://example.com/chicken.jpg")
        );
    }

    public static Long 장바구니_아이템_추가되어_있음(String userName, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(userName, productId);
        return Long.parseLong(response.header("Location").split("/cartItems/")[1]);
    }

    public void 장바구니_아이템_추가되어_있음_토큰(Long productId) {
        CartItemRequest cartItemRequest = new CartItemRequest(productId, 1);
        장바구니_아이템_추가_요청_토큰(cartItemRequest, accessToken);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public void 장바구니_아이템_목록_포함됨_토큰(ExtractableResponse<Response> response, Long... productIds) {
        CartItemsResponse cartItemsResponse = response.jsonPath().getObject(".", CartItemsResponse.class);
        List<Long> resultProductIds = cartItemsResponse.getCartItemResponseList().stream()
            .map(CartItemResponse::getId)
            .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
