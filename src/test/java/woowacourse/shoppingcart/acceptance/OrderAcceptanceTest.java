package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.TMember.MARU;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가되어_있음;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        MARU.register();
        MARU.login();
        final String token = MARU.getToken();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        Long cartId1 = 장바구니_아이템_추가되어_있음(token, new CartRequest(productId1));
        Long cartId2 = 장바구니_아이템_추가되어_있음(token, new CartRequest(productId2));

        List<OrderRequest> orderRequests = Stream.of(cartId1, cartId2)
                .map(OrderRequest::new)
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(token, orderRequests);

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        MARU.register();
        MARU.login();
        final String token = MARU.getToken();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        Long cartId1 = 장바구니_아이템_추가되어_있음(token, new CartRequest(productId1));
        Long cartId2 = 장바구니_아이템_추가되어_있음(token, new CartRequest(productId2));

        Long orderId1 = 주문하기_요청_성공되어_있음(token, Collections.singletonList(new OrderRequest(cartId1)));
        Long orderId2 = 주문하기_요청_성공되어_있음(token, Collections.singletonList(new OrderRequest(cartId2)));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(token);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        MARU.register();
        MARU.login();
        final String token = MARU.getToken();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        Long cartId1 = 장바구니_아이템_추가되어_있음(token, new CartRequest(productId1));
        Long cartId2 = 장바구니_아이템_추가되어_있음(token, new CartRequest(productId2));

        Long orderId = 주문하기_요청_성공되어_있음(token, Arrays.asList(
                new OrderRequest(cartId1),
                new OrderRequest(cartId2)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(token, orderId);

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

    public static ExtractableResponse<Response> 주문하기_요청(String token, List<OrderRequest> orderRequests) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequests)
                .when().post("/api/members/me/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/members/me/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String token, Long orderId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/members/me/orders/{orderId}", orderId)
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(String token, List<OrderRequest> orderRequests) {
        ExtractableResponse<Response> response = 주문하기_요청(token, orderRequests);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
        List<Long> resultOrderIds = response.jsonPath().getList(".", OrderResponse.class).stream()
                .map(OrderResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultOrderIds).contains(orderIds);
    }

    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        OrderResponse orderResponse = response.as(OrderResponse.class);
        assertThat(orderResponse.getId()).isEqualTo(orderId);
    }
}
