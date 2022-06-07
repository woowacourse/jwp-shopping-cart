package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.*;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.*;

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

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.OrderRequest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private static final long CUSTOMER_ID = 1L;

    private Long cartId1;
    private Long cartId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg", 10);

        cartId1 = 장바구니_아이템_추가되어_있음(CUSTOMER_ID, productId1);
        cartId2 = 장바구니_아이템_추가되어_있음(CUSTOMER_ID, productId2);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        List<OrderRequest> orderRequests = Stream.of(cartId1, cartId2)
            .map(cartId -> new OrderRequest(cartId, 10))
            .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(CUSTOMER_ID, orderRequests);

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공되어_있음(CUSTOMER_ID, Collections.singletonList(new OrderRequest(cartId1, 2)));
        Long orderId2 = 주문하기_요청_성공되어_있음(CUSTOMER_ID, Collections.singletonList(new OrderRequest(cartId2, 5)));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(CUSTOMER_ID);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공되어_있음(CUSTOMER_ID, Arrays.asList(
            new OrderRequest(cartId1, 2),
            new OrderRequest(cartId2, 4)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(CUSTOMER_ID, orderId);

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

    public static ExtractableResponse<Response> 주문하기_요청(long customerId, List<OrderRequest> orderRequests) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(orderRequests)
            .when().post("/api/customers/{customerId}/orders", customerId)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(long customerId) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/customers/{customerId}/orders", customerId)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(long customerId, Long orderId) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/customers/{customerId}/orders/{orderId}", customerId, orderId)
            .then().log().all()
            .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(long customerId, List<OrderRequest> orderRequests) {
        ExtractableResponse<Response> response = 주문하기_요청(customerId, orderRequests);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
        List<Long> resultOrderIds = response.jsonPath().getList(".", Orders.class).stream()
            .map(Orders::getId)
            .collect(Collectors.toList());
        assertThat(resultOrderIds).contains(orderIds);
    }

    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        Orders resultOrder = response.as(Orders.class);
        assertThat(resultOrder.getId()).isEqualTo(orderId);
    }
}
