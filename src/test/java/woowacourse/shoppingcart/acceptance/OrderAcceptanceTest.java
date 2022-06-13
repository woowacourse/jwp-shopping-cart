package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.acceptance.AuthAcceptanceTest.회원_가입_후_로그인;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가되어_있음;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemAddRequest;
import woowacourse.shoppingcart.dto.order.OrderResponse;
import woowacourse.shoppingcart.dto.order.OrdersResponse.OrderInnerResponse;
import woowacourse.shoppingcart.dto.product.ProductAddRequest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {
    private static final String USER = "puterism";
    private String token;
    private Long cartItemId1;
    private Long cartItemId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        token = 회원_가입_후_로그인();

        Long productId1 = 상품_등록되어_있음(
                new ProductAddRequest("치킨", 10_000, 100, "chicken.png"));
        Long productId2 = 상품_등록되어_있음(
                new ProductAddRequest("치킨", 10_000, 100, "chicken.png"));

        cartItemId1 = 장바구니_아이템_추가되어_있음(new CartItemAddRequest(productId1, 1), token);
        cartItemId2 = 장바구니_아이템_추가되어_있음(new CartItemAddRequest(productId2, 1), token);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        List<OrderRequest> orderRequests = Stream.of(cartItemId1, cartItemId2)
                .map(OrderRequest::new)
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(orderRequests, token);

        응답_CREATED_헤더_Location_존재(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공되어_있음(List.of(new OrderRequest(cartItemId1)), token);
        Long orderId2 = 주문하기_요청_성공되어_있음(List.of(new OrderRequest(cartItemId2)), token);

        ExtractableResponse<Response> response = 주문_내역_조회_요청(token);

        응답_OK(response);
        바디_주문_ID_목록_포함(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공되어_있음(List.of(new OrderRequest(cartItemId1), new OrderRequest(cartItemId2)), token);

        ExtractableResponse<Response> response = 주문_단일_조회_요청(orderId, token);

        응답_OK(response);
        바디_주문_id_일치(response, orderId);
    }

    public static ExtractableResponse<Response> 주문하기_요청(List<OrderRequest> request, String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(Long orderId, String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/orders/" + orderId)
                .then().log().all()
                .extract();
    }

    private void 응답_CREATED_헤더_Location_존재(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    private Long 주문하기_요청_성공되어_있음(List<OrderRequest> orderRequests, String token) {
        ExtractableResponse<Response> response = 주문하기_요청(orderRequests, token);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    private void 응답_OK(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 바디_주문_ID_목록_포함(ExtractableResponse<Response> response, Long... orderIds) {
        List<Long> resultOrderIds = response.jsonPath().getList("orders.", OrderInnerResponse.class).stream()
                .map(OrderInnerResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultOrderIds).contains(orderIds);
    }

    private void 바디_주문_id_일치(ExtractableResponse<Response> response, Long orderId) {
        OrderResponse result = response.as(OrderResponse.class);
        assertThat(result.getId()).isEqualTo(orderId);
    }
}
