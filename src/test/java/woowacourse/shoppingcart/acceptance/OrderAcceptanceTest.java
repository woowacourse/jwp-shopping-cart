package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.acceptance.AuthAcceptanceTest.로그인_후_토큰_획득;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가되어_있음;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.회원_추가되어_있음;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private String token;

    public static ExtractableResponse<Response> 주문하기_요청(String token, OrderRequest orderRequest) {
        return requestHttpPost(token, orderRequest, "/customers/orders").extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String token) {
        return requestHttpGet(token, "/customers/orders").extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String token, Long orderId) {
        return requestHttpGet(token, "/customers/orders/" + orderId).extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(String userName, OrderRequest orderRequest) {
        ExtractableResponse<Response> response = 주문하기_요청(userName, orderRequest);
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

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        회원_추가되어_있음();
        token = 로그인_후_토큰_획득();

        장바구니_아이템_추가되어_있음(token, new CartRequest(1L, 10));
        장바구니_아이템_추가되어_있음(token, new CartRequest(2L, 20));
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        ExtractableResponse<Response> response = 주문하기_요청(token, new OrderRequest(1L, 10));

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공되어_있음(token, new OrderRequest(1L, 2));
        Long orderId2 = 주문하기_요청_성공되어_있음(token, new OrderRequest(2L, 5));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(token);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공되어_있음(token, new OrderRequest(2L, 4));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(token, orderId);

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        OrderResponse resultOrder = response.as(OrderResponse.class);
        assertThat(resultOrder.getId()).isEqualTo(orderId);
    }
}
