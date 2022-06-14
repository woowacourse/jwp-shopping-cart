package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.cart.dto.CartItemAdditionRequest;
import woowacourse.shoppingcart.cart.dto.QuantityChangingRequest;
import woowacourse.shoppingcart.order.dto.OrderCreationRequest;

class OrderAcceptanceTest extends AcceptanceTest {

    private static final String REQUEST_URL = "/users/me/orders";

    @Test
    @DisplayName("장바구니에 담긴 상품을 주문한다.")
    void addOrder() {
        // given
        postCartItem(new CartItemAdditionRequest(2L));
        postCartItem(new CartItemAdditionRequest(5L));
        postCartItem(new CartItemAdditionRequest(4L));
        postCartItem(new CartItemAdditionRequest(1L));

        final List<OrderCreationRequest> request = List.of(
                new OrderCreationRequest(2L),
                new OrderCreationRequest(5L),
                new OrderCreationRequest(4L)
        );

        // when
        final ValidatableResponse response = postOrder(request);

        // then
        response.statusCode(HttpStatus.CREATED.value())
                .header(HttpHeaders.LOCATION, REQUEST_URL + "/" + 1);
    }

    @Test
    @DisplayName("장바구니에 담겨있지 않은 상품을 주문하면 404를 반환한다.")
    void addOrder_notContains_404() {
        // given
        postCartItem(new CartItemAdditionRequest(2L));
        postCartItem(new CartItemAdditionRequest(5L));

        final List<OrderCreationRequest> request = List.of(
                new OrderCreationRequest(2L),
                new OrderCreationRequest(5L),
                new OrderCreationRequest(4L)
        );

        // when
        final ValidatableResponse response = postOrder(request);

        // then
        response.statusCode(HttpStatus.NOT_FOUND.value())
                .body("errorCode", equalTo("1200"))
                .body("message", equalTo("장바구니에 포함되지 않은 상품이 존재합니다."));
    }

    @Test
    @DisplayName("ID로 주문 단건 조회를 한다.")
    void findOrder() {
        // given
        postCartItem(new CartItemAdditionRequest(2L));
        putCartItemQuantity(2L, new QuantityChangingRequest(4));

        postCartItem(new CartItemAdditionRequest(5L));

        postCartItem(new CartItemAdditionRequest(4L));
        putCartItemQuantity(4L, new QuantityChangingRequest(7));

        postCartItem(new CartItemAdditionRequest(1L));

        final List<OrderCreationRequest> request = List.of(
                new OrderCreationRequest(2L),
                new OrderCreationRequest(5L),
                new OrderCreationRequest(4L)
        );
        final String orderId = postOrder(request)
                .extract()
                .header(HttpHeaders.LOCATION)
                .split(REQUEST_URL + "/")[1];

        // when
        final ValidatableResponse response = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .when()
                .get(REQUEST_URL + "/{orderId}", orderId)
                .then().log().all();

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("id", equalTo(Integer.parseInt(orderId)))
                .body("orderDetails.productId", contains(2, 5, 4))
                .body("orderDetails.quantity", contains(4, 1, 7))
                .body("orderDetails.price", contains(700, 540, 2100))
                .body("orderDetails.name", contains("포도", "오렌지", "딸기"))
                .body("orderDetails.imageUrl", contains("podo.do", "orange.org", "strawberry.org"));
    }

    @Test
    @DisplayName("ID에 해당하는 주문이 존재하지 않으면 404를 반환한다.")
    void findOrder_notExistOrder_404() {
        // when
        final ValidatableResponse response = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .when()
                .get(REQUEST_URL + "/{orderId}", 999L)
                .then().log().all();

        // then
        response.statusCode(HttpStatus.NOT_FOUND.value())
                .body("errorCode", equalTo("2000"))
                .body("message", equalTo("주문이 존재하지 않습니다."));
    }

    private ValidatableResponse postOrder(List<OrderCreationRequest> request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .when()
                .post(REQUEST_URL)
                .then().log().all();
    }
}
