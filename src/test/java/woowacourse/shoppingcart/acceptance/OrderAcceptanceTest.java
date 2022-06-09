package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.acceptance.AuthAcceptanceTest.로그인_요청;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가되어_있음;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.사용자_생성_요청;
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
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.LoginRequest;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrdersResponse;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {
    private Long cartId1;
    private Long cartId2;

    private TokenResponse tokenResponse;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        사용자_생성_요청("loginId", "seungpapang", "12345678aA!");
        LoginRequest loginRequest = new LoginRequest("loginId", "12345678aA!");
        ExtractableResponse<Response> loginResponse = 로그인_요청(loginRequest);
        tokenResponse = loginResponse.as(TokenResponse.class);

        cartId1 = 장바구니_아이템_추가되어_있음(productId1, tokenResponse.getAccessToken()).getId();
        cartId2 = 장바구니_아이템_추가되어_있음(productId2, tokenResponse.getAccessToken()).getId();
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        List<OrderRequest> orderRequests = Stream.of(cartId1, cartId2)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(orderRequests, tokenResponse.getAccessToken());

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공되어_있음(Collections.singletonList(new OrderRequest(cartId1, 2)),
                tokenResponse.getAccessToken());
        Long orderId2 = 주문하기_요청_성공되어_있음(Collections.singletonList(new OrderRequest(cartId2, 5)),
                tokenResponse.getAccessToken());

        ExtractableResponse<Response> response = 주문_내역_조회_요청(tokenResponse.getAccessToken());

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공되어_있음(Arrays.asList(
                new OrderRequest(cartId1, 2),
                new OrderRequest(cartId2, 4)
        ), tokenResponse.getAccessToken());

        ExtractableResponse<Response> response = 주문_단일_조회_요청(orderId, tokenResponse.getAccessToken());

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

    public static ExtractableResponse<Response> 주문하기_요청(List<OrderRequest> orderRequests, String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(orderRequests)
                .when().post("/customers/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().get("/customers/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(Long orderId, String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().get("/customers/orders/{orderId}", orderId)
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(List<OrderRequest> orderRequests, String accessToken) {
        ExtractableResponse<Response> response = 주문하기_요청(orderRequests, accessToken);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
        List<Long> resultOrderIds = response.jsonPath().getList(".", OrdersResponse.class).stream()
                .map(OrdersResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultOrderIds).contains(orderIds);
    }

    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        OrdersResponse resultOrder = response.as(OrdersResponse.class);
        assertThat(resultOrder.getId()).isEqualTo(orderId);
    }
}
