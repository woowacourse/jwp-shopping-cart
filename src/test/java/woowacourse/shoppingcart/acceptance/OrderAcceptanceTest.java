package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static woowacourse.auth.support.AuthorizationExtractor.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.acceptance.fixture.CustomerAcceptanceFixture;
import woowacourse.shoppingcart.application.dto.OrderResponse;
import woowacourse.shoppingcart.ui.dto.OrderDetailRequest;
import woowacourse.support.SimpleRestAssured;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private static final String USER = "puterism";

    @Autowired
    private JwtTokenProvider provider;

    private Long cartId1;
    private Long cartId2;

    private String token;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        final Long customerId = SimpleRestAssured.getId(CustomerAcceptanceFixture.saveCustomerWithName(USER));
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        token = BEARER_TYPE + provider.createToken(customerId.toString());
        cartId1 = 장바구니_아이템_추가되어_있음(token, productId1);
        cartId2 = 장바구니_아이템_추가되어_있음(token, productId2);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        List<OrderDetailRequest> orderDetailRequests = Stream.of(cartId1, cartId2)
            .map(cartId -> new OrderDetailRequest(cartId, 10))
            .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(token, orderDetailRequests);

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공되어_있음(token, Collections.singletonList(new OrderDetailRequest(cartId1, 2)));
        Long orderId2 = 주문하기_요청_성공되어_있음(token, Collections.singletonList(new OrderDetailRequest(cartId2, 5)));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(token);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공되어_있음(token, Arrays.asList(
            new OrderDetailRequest(cartId1, 2),
            new OrderDetailRequest(cartId2, 4)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(token, orderId);

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

    public static ExtractableResponse<Response> 주문하기_요청(String token, List<OrderDetailRequest> orderDetailRequests) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(new Header("Authorization", token))
            .body(orderDetailRequests)
            .when().post("/api/customers/me/orders")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String token) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(new Header("Authorization", token))
            .when().get("/api/customers/me/orders")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String token, Long orderId) {
        return RestAssured
            .given().log().all()
            .header(new Header("Authorization", token))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/customers/me/orders/{orderId}", orderId)
            .then().log().all()
            .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(String token, List<OrderDetailRequest> orderDetailRequests) {
        ExtractableResponse<Response> response = 주문하기_요청(token, orderDetailRequests);
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
        OrderResponse resultOrderResponse = response.as(OrderResponse.class);
        assertThat(resultOrderResponse.getId()).isEqualTo(orderId);
    }
}
