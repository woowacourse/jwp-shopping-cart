package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.cart.dto.CartItemAdditionRequest;
import woowacourse.shoppingcart.cart.dto.QuantityChangingRequest;

@DisplayName("장바구니 관련 기능")
class CartItemAcceptanceTest extends AcceptanceTest {

    private static final String REQUEST_URL = "/users/me/cartItems";

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
    void getCart_2Items_200() {
        // given
        postCartItem(new CartItemAdditionRequest(2L));
        postCartItem(new CartItemAdditionRequest(1L));

        // when
        final ValidatableResponse response = getCart();

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("cartList.id", contains(2, 1))
                .body("cartList.name", contains("포도", "사과"))
                .body("cartList.imageUrl", hasSize(2))
                .body("cartList.price", contains(700, 1600))
                .body("cartList.quantity", contains(1, 1));
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
        final ValidatableResponse response = putCartItemQuantity(productId, request);

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
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(REQUEST_URL + "/{productId}", productId)
                .then().log().all();

        final ValidatableResponse cartResponse = getCart();

        // then
        response.statusCode(HttpStatus.NO_CONTENT.value());
        cartResponse.statusCode(HttpStatus.OK.value())
                .body("cartList", hasSize(0));
    }

    private ValidatableResponse getCart() {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(REQUEST_URL)
                .then().log().all();
    }
}
