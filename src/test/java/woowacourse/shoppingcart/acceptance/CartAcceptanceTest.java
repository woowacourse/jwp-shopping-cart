package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.cart.dto.CartItemAdditionRequest;
import woowacourse.shoppingcart.cart.dto.QuantityChangingRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private static final String REQUEST_URL = "/users/me/carts";

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(final String token, final Long productId) {
        final Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(requestBody)
                .when().post("/customers/me/carts")
                .then().log().all()
                .extract();
    }

    public static Long 장바구니_아이템_추가되어_있음(final String token, final Long productId) {
        final ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, productId);
        return Long.parseLong(response.header("Location").split("/carts/")[1]);
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        // given
        final CartItemAdditionRequest request = new CartItemAdditionRequest(1L);

        // when
        final ValidatableResponse response = postCartItem(request);

        // then
        response.statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        // given
        postCartItem(new CartItemAdditionRequest(2L));
        postCartItem(new CartItemAdditionRequest(1L));

        // when
        final ValidatableResponse response = getCarts();

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("products.id", contains(2, 1))
                .body("products.name", contains("포도", "사과"))
                .body("products.image_url", hasSize(2))
                .body("products.price", contains(700, 1600))
                .body("products.quantity", contains(1, 1));
    }

    @Test
    @DisplayName("장바구니에 들어있는 상품의 수량을 수정한다.")
    void changeQuantity() {
        // given
        final Long productId = 5L;
        postCartItem(new CartItemAdditionRequest(productId));

        final int quantity = 3;
        final QuantityChangingRequest request = new QuantityChangingRequest(quantity);

        // when
        final ValidatableResponse response = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put(REQUEST_URL + "/{productId}", productId)
                .then().log().all();

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("id", equalTo(productId.intValue()))
                .body("quantity", equalTo(quantity));
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        // given
        final long productId = 1L;
        postCartItem(new CartItemAdditionRequest(productId));

        // when
        final ValidatableResponse response = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(REQUEST_URL + "/{productId}", productId)
                .then().log().all();

        final ValidatableResponse cartItemsResponse = getCarts();

        // then
        response.statusCode(HttpStatus.NO_CONTENT.value());
        cartItemsResponse.statusCode(HttpStatus.OK.value())
                .body("products", hasSize(0));
    }

    private ValidatableResponse postCartItem(CartItemAdditionRequest request) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post(REQUEST_URL)
                .then().log().all();
    }

    private ValidatableResponse getCarts() {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(REQUEST_URL)
                .then().log().all();
    }
}
