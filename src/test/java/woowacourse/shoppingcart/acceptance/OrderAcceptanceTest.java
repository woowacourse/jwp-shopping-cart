package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CartItemAcceptanceTest.토큰으로_장바구니_아이템_추가되어_있음;
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
import woowacourse.auth.acceptance.AuthAcceptanceFixture;
import woowacourse.shoppingcart.dto.OrderRequest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {
    private static final String USER = "klay";
    private Long cartId1;
    private Long cartId2;
    private String token;

    public static ExtractableResponse<Response> 주문하기_요청(String userName, List<OrderRequest> orderRequests) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequests)
                .when().post("/api/customers/{customerName}/orders", userName)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 토큰으로_주문하기_요청(String accessToken, List<OrderRequest> orderRequests) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequests)
                .when().post("/api/customer/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 토큰으로_주문_내역_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customer/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 토큰으로_주문_단일_조회_요청(String accessToken, Long orderId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customer/orders/{orderId}", orderId)
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 토큰으로_주문하기_요청_성공되어_있음(String token, List<OrderRequest> orderRequests) {
        ExtractableResponse<Response> response = 토큰으로_주문하기_요청(token, orderRequests);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
        List<Long> resultOrderIds = response.jsonPath().getList("id", Long.class);
        assertThat(resultOrderIds).contains(orderIds);
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        token = AuthAcceptanceFixture.registerAndGetToken("klay", "klay@naver.com", "12345678");
        cartId1 = 토큰으로_장바구니_아이템_추가되어_있음(token, productId1);
        cartId2 = 토큰으로_장바구니_아이템_추가되어_있음(token, productId2);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        List<OrderRequest> orderRequests = Stream.of(cartId1, cartId2)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 토큰으로_주문하기_요청(token, orderRequests);

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 토큰으로_주문하기_요청_성공되어_있음(token, Collections.singletonList(new OrderRequest(cartId1, 2)));
        Long orderId2 = 토큰으로_주문하기_요청_성공되어_있음(token, Collections.singletonList(new OrderRequest(cartId2, 5)));

        ExtractableResponse<Response> response = 토큰으로_주문_내역_조회_요청(token);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
        주문_총_금액_포함됨(response, 20_000, 100_000);
    }

    private void 주문_총_금액_포함됨(final ExtractableResponse<Response> response, final Integer... totalPrice) {
        final List<Integer> actual = response.jsonPath().getList("totalPrice", Integer.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(Arrays.asList(totalPrice));
    }

    private void 주문_총_금액_일치함(final ExtractableResponse<Response> response, final int totalPrice) {
        final int actual = response.jsonPath().getInt("totalPrice");
        assertThat(actual).isEqualTo(totalPrice);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 토큰으로_주문하기_요청_성공되어_있음(token, Arrays.asList(
                new OrderRequest(cartId1, 2),
                new OrderRequest(cartId2, 4)
        ));

        ExtractableResponse<Response> response = 토큰으로_주문_단일_조회_요청(token, orderId);

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
        주문_총_금액_일치함(response, 100_000);
    }

    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        Long actual = response.jsonPath().getLong("id");
        assertThat(actual).isEqualTo(orderId);
    }
}
