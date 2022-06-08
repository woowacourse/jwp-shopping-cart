package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.*;

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
import woowacourse.auth.dto.LoginTokenResponse;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemUpdateRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private static final long CUSTOMER_ID = 1L;

    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg", 10);
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        //given
        String token = 로그인_요청_및_토큰발급(new LoginRequest("puterism@naver.com", "12349053145"));

        //when
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, CUSTOMER_ID, productId1, 5);

        //then
        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        //given
        String token = 로그인_요청_및_토큰발급(new LoginRequest("puterism@naver.com", "12349053145"));

        장바구니_아이템_추가_요청(token, CUSTOMER_ID, productId1, 5);
        장바구니_아이템_추가_요청(token, CUSTOMER_ID, productId2, 5);

        //when
        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token, CUSTOMER_ID);

        //then
        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 상품 삭제")
    @Test
    void deleteCartItem() {
        //given
        String token = 로그인_요청_및_토큰발급(new LoginRequest("puterism@naver.com", "12349053145"));
        장바구니_아이템_추가_요청(token, CUSTOMER_ID, productId1, 5);

        //when
        ExtractableResponse<Response> response = 장바구니_아이템_삭제_요청(token, CUSTOMER_ID, productId1);

        //then
        장바구니_아이템_삭제됨(response);
    }

    @Test
    @DisplayName("장바구니 상품 구매 수 업데이트")
    void updateCount() {
        //given
        String token = 로그인_요청_및_토큰발급(new LoginRequest("puterism@naver.com", "12349053145"));

        //then
        ExtractableResponse<Response> response = 장바구니_아이템_구매_수_업데이트(token, CUSTOMER_ID, productId1, 7);

        //then
        장바구니_아이템_구매_수_업데이트됨(response);
    }

    private String 로그인_요청_및_토큰발급(LoginRequest request) {
        ExtractableResponse<Response> loginResponse = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/auth/login")
            .then().log().all()
            .extract();

        LoginTokenResponse loginTokenResponse = loginResponse.body().as(LoginTokenResponse.class);
        return loginTokenResponse.getAccessToken();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String token, long customerId, Long productId,
        int count) {
        CartItemRequest request = new CartItemRequest(productId, count);

        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/customers/{customerId}/carts", customerId)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_삭제_요청(String token, long customerId, long productId) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/api/customers/{customerId}/carts?productId={productId}", customerId, productId)
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 장바구니_아이템_구매_수_업데이트(String token, long customerId, Long productId, int count) {
        CartItemUpdateRequest request = new CartItemUpdateRequest(count);
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().patch("/api/customers/{customerId}/carts?productId={productId}", customerId, productId)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String token, long customerId) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/customers/{customerId}/carts", customerId)
            .then().log().all()
            .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", CartItemResponse.class).stream()
            .map(CartItemResponse::getProductId)
            .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_아이템_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private void 장바구니_아이템_구매_수_업데이트됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
