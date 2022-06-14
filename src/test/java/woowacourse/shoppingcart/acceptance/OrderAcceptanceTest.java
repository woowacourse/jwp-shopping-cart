package woowacourse.shoppingcart.acceptance;

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
import woowacourse.shoppingcart.order.dto.OrderCreationRequest;

class OrderAcceptanceTest extends AcceptanceTest {

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
        final ValidatableResponse response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .when()
                .post("/users/me/orders")
                .then().log().all();

        // then
        response.statusCode(HttpStatus.CREATED.value())
                .header(HttpHeaders.LOCATION, "/users/me/orders/" + 1);
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
        final ValidatableResponse response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .when()
                .post("/users/me/orders")
                .then().log().all();

        // then
        response.statusCode(HttpStatus.NOT_FOUND.value())
                .body("errorCode", equalTo("1200"))
                .body("message", equalTo("장바구니에 포함되지 않은 상품이 존재합니다."));
    }
}
