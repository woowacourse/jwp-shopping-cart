package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.acceptance.AuthAcceptanceTest.회원_가입_후_로그인;
import static woowacourse.shoppingcart.acceptance.CartItemAcceptanceTest.장바구니_아이템_추가되어_있음;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.cartitem.CartItemSaveRequest;
import woowacourse.shoppingcart.dto.order.OrderResponse;
import woowacourse.shoppingcart.dto.order.OrderResponse.OrderResponseNested;
import woowacourse.shoppingcart.dto.order.OrderSaveRequest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {
    private String accessToken;
    private Long cartItemId1;
    private Long cartItemId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        accessToken = 회원_가입_후_로그인();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, 20, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, 30, "http://example.com/beer.jpg");

        cartItemId1 = 장바구니_아이템_추가되어_있음(accessToken, new CartItemSaveRequest(productId1, 5));
        cartItemId2 = 장바구니_아이템_추가되어_있음(accessToken, new CartItemSaveRequest(productId2, 6));
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        List<OrderSaveRequest> orderRequests = Stream.of(cartItemId1, cartItemId2)
                .map(OrderSaveRequest::new)
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(accessToken, orderRequests);

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공되어_있음(accessToken, Collections.singletonList(new OrderSaveRequest(cartItemId1)));
        Long orderId2 = 주문하기_요청_성공되어_있음(accessToken, Collections.singletonList(new OrderSaveRequest(cartItemId2)));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(accessToken);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공되어_있음(accessToken, Arrays.asList(
                new OrderSaveRequest(cartItemId1),
                new OrderSaveRequest(cartItemId2)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(accessToken, orderId);

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

    public static ExtractableResponse<Response> 주문하기_요청(String accessToken, List<OrderSaveRequest> orderRequests) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(orderRequests)
                .when().post("/api/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .when().get("/api/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String accessToken, Long orderId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .when().get("/api/orders/{orderId}", orderId)
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(String userName, List<OrderSaveRequest> orderRequests) {
        ExtractableResponse<Response> response = 주문하기_요청(userName, orderRequests);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
        List<Long> resultOrderIds = response.jsonPath().getList("orders.", OrderResponseNested.class).stream()
                .map(OrderResponseNested::getId)
                .collect(Collectors.toList());
        assertThat(resultOrderIds).contains(orderIds);
    }

    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        OrderResponse resultOrder = response.as(OrderResponse.class);
        assertThat(resultOrder.getOrder().getId()).isEqualTo(orderId);
    }
}
