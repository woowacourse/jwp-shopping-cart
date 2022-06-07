package woowacourse.shoppingcart.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.OrderDetailRequest;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.dto.OrdersRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.acceptance.AcceptanceTestFixture.코린;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가되어_있음;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private String accessToken;
    private long productId1;
    private long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        post("/signup", 코린);
        final ExtractableResponse<Response> signinResponse = post("/signin", new TokenRequest("hamcheeseburger", "Password123!"));
        accessToken = signinResponse.response().jsonPath().getString("accessToken");

        productId1 = 상품_등록되어_있음("치킨", 10_000, "imageUrl1");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "imageUrl2");

        장바구니_아이템_추가되어_있음(accessToken, productId1);
        장바구니_아이템_추가되어_있음(accessToken, productId2);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        final OrdersRequest ordersRequest = new OrdersRequest(
                Stream.of(productId1, productId2)
                        .map(productId -> new OrderDetailRequest(productId, 10))
                        .collect(Collectors.toList())
        );

        ExtractableResponse<Response> response = 주문하기_요청(accessToken, ordersRequest);

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        주문하기_요청_성공되어_있음(accessToken, new OrdersRequest(Collections.singletonList(new OrderDetailRequest(productId1, 2))));
        주문하기_요청_성공되어_있음(accessToken, new OrdersRequest(Collections.singletonList(new OrderDetailRequest(productId2, 5))));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(accessToken);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, 1L, 2L);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        주문하기_요청_성공되어_있음(accessToken, new OrdersRequest(Arrays.asList(
                new OrderDetailRequest(productId1, 2),
                new OrderDetailRequest(productId2, 4)
        )));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(accessToken, 1L);

        주문_조회_응답됨(response);
        OrderResponse order = new OrderResponse(
                1L,
                List.of(new OrderDetailResponse(productId1, "치킨", 20_000, 2, "imageUrl1"),
                        new OrderDetailResponse(productId2, "맥주", 80_000, 4, "imageUrl2")),
                100_000
        );
        주문_조회됨(response, order);
    }

    public static ExtractableResponse<Response> 주문하기_요청(String accessToken, OrdersRequest ordersRequest) {
        return post("/customers/orders", accessToken, ordersRequest);
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String accessToken) {
        return get("/customers/orders", accessToken);
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String accessToken, long orderId) {
        return get("/customers/orders/" + orderId, accessToken);
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static void 주문하기_요청_성공되어_있음(String accessToken, OrdersRequest orderRequests) {
        ExtractableResponse<Response> response = 주문하기_요청(accessToken, orderRequests);
    }

    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
        List<Long> resultOrderIds = response.jsonPath().getList("orders", OrderResponse.class).stream()
                .map(OrderResponse::getOrderId)
                .collect(Collectors.toList());
        assertThat(resultOrderIds).contains(orderIds);
    }

    private void 주문_조회됨(ExtractableResponse<Response> response, OrderResponse expected) {
        OrderResponse resultOrder = response.as(OrderResponse.class);
        assertThat(resultOrder).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
