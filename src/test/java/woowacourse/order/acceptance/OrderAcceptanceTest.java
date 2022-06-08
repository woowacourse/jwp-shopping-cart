package woowacourse.order.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.cartitem.acceptance.CartItemAcceptanceTest.장바구니_아이템_추가되어_있음;
import static woowacourse.customer.acceptance.CustomerAcceptanceTest.로그인되어_토큰_가져옴;
import static woowacourse.customer.acceptance.CustomerAcceptanceTest.회원_가입_요청;
import static woowacourse.product.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

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
import woowacourse.auth.dto.LoginRequest;
import woowacourse.cartitem.dto.CartItemAddRequest;
import woowacourse.customer.dto.SignupRequest;
import woowacourse.order.dto.OrderAddRequest;
import woowacourse.order.dto.OrderResponse;
import woowacourse.order.dto.OrderResponse.InnerOrderResponse;
import woowacourse.order.dto.OrderResponses;
import woowacourse.product.dto.ProductRequest;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private String accessToken;
    private Long productId1;
    private Long productId2;
    private Long cartItemId1;
    private Long cartItemId2;
    private List<OrderAddRequest> orderRequests;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        회원_가입_요청(new SignupRequest("jjanggu", "password1234", "01022223333", "서울시 여러분"));
        accessToken = 로그인되어_토큰_가져옴(new LoginRequest("jjanggu", "password1234"));
        productId1 = 상품_등록되어_있음(new ProductRequest("짱구", 100_000_000, 10, "jjanggu.jpg"));
        productId2 = 상품_등록되어_있음(new ProductRequest("짱아", 10_000_000, 10, "jjanga.jpg"));
        cartItemId1 = 장바구니_아이템_추가되어_있음(accessToken, new CartItemAddRequest(productId2, 1));
        cartItemId2 = 장바구니_아이템_추가되어_있음(accessToken, new CartItemAddRequest(productId2, 1));
        orderRequests = Stream.of(cartItemId1, cartItemId2)
            .map(OrderAddRequest::new)
            .collect(Collectors.toList());
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        final ExtractableResponse<Response> response = 주문하기_요청(accessToken, orderRequests);

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        final Long orderId1 = 주문하기_요청_성공되어_있음(accessToken, orderRequests);

        final ExtractableResponse<Response> response = 주문_내역_조회_요청(accessToken);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        final Long orderId = 주문하기_요청_성공되어_있음(accessToken, orderRequests);

        final ExtractableResponse<Response> response = 주문_단일_조회_요청(accessToken, orderId);

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

    public static ExtractableResponse<Response> 주문하기_요청(final String accessToken,
        final List<OrderAddRequest> orderRequests) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(orderRequests)
            .when().post("/api/orders")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(final String accessToken) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/orders")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(final String accessToken, final Long orderId) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/orders/{orderId}", orderId)
            .then().log().all()
            .extract();
    }

    public static void 주문하기_성공함(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(final String accessToken, final List<OrderAddRequest> orderAddRequests) {
        final ExtractableResponse<Response> response = 주문하기_요청(accessToken, orderAddRequests);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    public static void 주문_조회_응답됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 주문_내역_포함됨(final ExtractableResponse<Response> response, final Long... orderIds) {
        final List<Long> resultOrderIds = response.as(OrderResponses.class).getOrders().stream()
                .map(InnerOrderResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultOrderIds).contains(orderIds);
    }

    private void 주문_조회됨(final ExtractableResponse<Response> response, final Long orderId) {
        final OrderResponse resultOrder = response.as(OrderResponse.class);
        assertThat(resultOrder.getOrder().getId()).isEqualTo(orderId);
    }
}
