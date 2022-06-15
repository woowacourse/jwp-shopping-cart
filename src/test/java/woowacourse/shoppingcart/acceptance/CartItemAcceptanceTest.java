package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.cart.CartItemResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

@DisplayName("장바구니 관련 기능")
public class CartItemAcceptanceTest extends AcceptanceTest {
    private Long productId1;
    private Long productId2;
    private String token;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        회원가입_요청("email@email.com", "12345678a", "tonic");
        token = 토큰_요청("email@email.com", "12345678a");
    }

    @DisplayName("A계정으로 장바구니 아이템 추가 후 B계정으로 동일한 아이템 추가 시 성공")
    @Test
    void addSameCartItemWithDifferentAccount() {
        회원가입_요청("email2@email.com", "12345678a", "nick");
        String otherToken = 토큰_요청("email2@email.com", "12345678a");

        장바구니_아이템_추가_요청(productId1, token);
        장바구니_아이템_추가_요청(productId2, token);

        ExtractableResponse response = 장바구니_아이템_추가_요청(productId2, otherToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("장바구니 아이템 추가 시 잘못된 토큰이면 401 반환")
    @Test
    void addCartItemWithUnauthorizedToken() {
        ExtractableResponse response = 장바구니_아이템_추가_요청(productId1, "invalidToken");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("장바구니 아이템 추가 시 토큰이 없으면 401 반환")
    @Test
    void addCartItemWithoutToken() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId1);

        ExtractableResponse response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/users/me/carts")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("정상 토큰으로 장바구니 아이템 추가 시 204 반환")
    @Test
    void addCartItemWithValidToken() {
        ExtractableResponse response = 장바구니_아이템_추가_요청(productId1, token);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("존재하지 않는 상품을 장바구니 아이템 추가시 404 반환")
    @Test
    void addNotExistedCartItem() {
        Long notExistId = 0L;
        ExtractableResponse response = 장바구니_아이템_추가_요청(notExistId, token);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("중복된 상품을 장바구니에 담을 경우 400 반환")
    @Test
    void addDuplicateCartItem() {
        장바구니_아이템_추가_요청(productId1, token);
        ExtractableResponse response = 장바구니_아이템_추가_요청(productId1, token);

        BAD_REQUEST_400_응답됨(response, CART_DUPLICATE_ERROR_CODE);
    }

    @DisplayName("잘못된 토큰으로 장바구니 아이템 목록 조회할 경우 401 반환")
    @Test
    void getCartItemsWithInvalidToken() {
        장바구니_아이템_추가_요청(productId1, token);
        장바구니_아이템_추가_요청(productId2, token);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청("invalidToken");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("토큰 없이 장바구니 아이템 목록 조회할 경우 401 반환")
    @Test
    void getCartItemsWithoutToken() {
        장바구니_아이템_추가_요청(productId1, token);
        장바구니_아이템_추가_요청(productId2, token);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/users/me/carts")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("장바구니 아이템 목록 조회 성공시 200 반환")
    @Test
    void getCartItemsWithValidToken() {
        장바구니_아이템_추가_요청(productId1, token);
        장바구니_아이템_추가_요청(productId2, token);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("잘못된 토큰으로 장바구니 아이템 삭제시 401 반환")
    @Test
    void deleteCartItemWithInvalidToken() {
        장바구니_아이템_추가_요청(productId1, token);

        ExtractableResponse<Response> response = 장바구니_아이템_삭제("invalidToken", productId1);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("DB에 존재하지 않는 상품일 경우 404 반환")
    @Test
    void deleteNotExistedProductItem() {
        Long notExistProductId = 0L;

        ExtractableResponse<Response> response = 장바구니_아이템_삭제(token, notExistProductId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
    
    @DisplayName("장바구니 삭제 정상 케이스인 경우 204 반환")
    @Test
    void deleteCartItem() {
        장바구니_아이템_추가_요청(productId1, token);

        ExtractableResponse<Response> response = 장바구니_아이템_삭제(token, productId1);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("장바구니에 없는 물품을 삭제할 경우 400 반환")
    @Test
    void deleteCartItemNotExistedInCart() {
        장바구니_아이템_추가_요청(productId1, token);

        장바구니_아이템_삭제(token, productId1);
        ExtractableResponse<Response> response = 장바구니_아이템_삭제(token, productId1);

        BAD_REQUEST_400_응답됨(response, CART_NOT_EXISTED_ERROR_CODE);
    }

    @DisplayName("잘못된 토큰으로 장바구니 아이템 수정 요청시 401 반환")
    @Test
    void updateCartItemQuantityWithInvalidToken() {
        장바구니_아이템_추가_요청(productId1, token);

        ExtractableResponse<Response> response = 장바구니_아이템_수량_수정_요청("invalidToken", productId1,3);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("DB에 없는 productId로 장바구니 아이템 수정 요청시 404 반환")
    @Test
    void updateNotExistedCartItem() {
        ExtractableResponse<Response> response = 장바구니_아이템_수량_수정_요청(token, 0L,3);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("장바구니에 없는 productId로 장바구니 아이템 수정 요청시 400 반환")
    @Test
    void updateItemNotExistedInCart() {
        ExtractableResponse<Response> response = 장바구니_아이템_수량_수정_요청(token, productId1,3);

        BAD_REQUEST_400_응답됨(response, 1102);
    }

    @DisplayName("음수 범위의 수량으로 장바구니 아이템 수정 요청 시 400 반환")
    @Test
    void updateItemWithNegativeQuantity() {
        장바구니_아이템_추가_요청(productId1, token);

        ExtractableResponse<Response> response = 장바구니_아이템_수량_수정_요청(token, productId1,-3);

        BAD_REQUEST_400_응답됨(response, 1100);
    }

    @DisplayName("정상적인 장바구니 아이템 수량 수정 요청시 200 반환")
    @Test
    void updateCartItemQuantity() {
        장바구니_아이템_추가_요청(productId1, token);

        ExtractableResponse<Response> response = 장바구니_아이템_수량_수정_요청(token, productId1,3);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getLong("id")).isEqualTo(productId1);
        assertThat(response.jsonPath().getInt("quantity")).isEqualTo(3);
    }

    @DisplayName("아이템 수량 수정 요청 후 아이템 목록 재요청시 수량 확인")
    @Test
    void updateCartItemQuantityAndCheck() {
        장바구니_아이템_추가_요청(productId1, token);

        장바구니_아이템_수량_수정_요청(token, productId1,3);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token);

        List<CartItemResponse> productList = response.jsonPath().getList("cartList", CartItemResponse.class);

        assertThat(productList.size()).isOne();
        assertThat(productList.get(0).getId()).isEqualTo(productId1);
        assertThat(productList.get(0).getQuantity()).isEqualTo(3);
    }

    private void BAD_REQUEST_400_응답됨(ExtractableResponse<Response> response, int errorCode) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(errorCode);
        assertThat(response.jsonPath().getString("message")).isNotBlank();
    }

    private ExtractableResponse<Response> 장바구니_아이템_삭제(String token, Long productId1) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when().log().all()
                .delete("users/me/carts/" + productId1)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList("cartList", CartItemResponse.class).stream()
                .map(CartItemResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    private ExtractableResponse<Response> 장바구니_아이템_수량_수정_요청(String token, Long productId, int quantity) {
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(Map.of("quantity", quantity))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .put("users/me/carts/" + productId)
                .then().log().all()
                .extract();
        return response;
    }
}
