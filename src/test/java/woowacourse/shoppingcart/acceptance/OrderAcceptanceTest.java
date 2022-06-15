package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_상품_추가되어_있음;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.로그인_토큰_발급;
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
import woowacourse.shoppingcart.ui.customer.dto.request.CustomerRegisterRequest;
import woowacourse.shoppingcart.ui.order.dto.request.OrderRequest;
import woowacourse.shoppingcart.ui.order.dto.response.OrderDetailsResponse;

@DisplayName("주문 관련 기능")
class OrderAcceptanceTest extends AcceptanceTest {

    private static final String NAME = "클레이";
    private static final String EMAIL = "djwhy5510@naver.com";
    private static final String PASSWORD = "1234567891";

    private Long cartId1;
    private Long cartId2;
    private String accessToken;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        final Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        final Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        requestPostWithBody("/api/customer", new CustomerRegisterRequest(NAME, EMAIL, PASSWORD));
        accessToken = 로그인_토큰_발급();

        cartId1 = 장바구니_상품_추가되어_있음(accessToken, productId1);
        cartId2 = 장바구니_상품_추가되어_있음(accessToken, productId2);
    }

    @Test
    @DisplayName("장바구니 상품들을 주문한다.")
    void addOrder() {
        // given 회원가입, 로그인 후 장바구니에 상품을 담고
        final List<OrderRequest> orderRequests = Stream.of(cartId1, cartId2)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());

        // when 주문하기를 요청하면
        ExtractableResponse<Response> response = 주문하기_요청(accessToken, orderRequests);

        // then 정상적으로 주문이 요청된다.
        주문하기_성공함(response);
    }

    @DisplayName("단일 주문을 조회한다.")
    @Test
    void getOrder() {
        // given 회원가입, 로그인 후 장바구니에 상품을 담고 주문하기 요청을 완료하여
        final Long orderId = 주문하기_요청_성공되어_있음(accessToken, Arrays.asList(
                new OrderRequest(cartId1, 2),
                new OrderRequest(cartId2, 4)
        ));

        // when 해당 주문에 대한 조회를 요청하면
        ExtractableResponse<Response> response = 주문_단일_조회_요청(accessToken, orderId);

        // then 정상적으로 주문이 조회된다.
        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

    @DisplayName("단일 주문 조회시 존재하지 않는 주문일 경우 404 NOT_FOUND 를 응답한다.")
    @Test
    void getOrder_invalidOrder_returnsNotFound() {
        // given 회원가입, 로그인 후 장바구니에 상품을 담고 주문하기 요청을 완료하여
        주문하기_요청_성공되어_있음(accessToken, Arrays.asList(
                new OrderRequest(cartId1, 2),
                new OrderRequest(cartId2, 4)
        ));

        // when 존재하지 않는 주문 id로 조회를 요청하면
        ExtractableResponse<Response> response = 주문_단일_조회_요청(accessToken, 1000L);

        // then 404 NOT_FOUND 를 응답한다.
        요청이_NOT_FOUND_응답함(response);
    }

    @Test
    @DisplayName("주문 내역을 조회한다.")
    void getOrders() {
        // given 회원가입, 로그인 후 장바구니에 상품을 담고 주문하기 요청을 완료하여
        Long orderId1 = 주문하기_요청_성공되어_있음(accessToken, Collections.singletonList(new OrderRequest(cartId1, 2)));
        Long orderId2 = 주문하기_요청_성공되어_있음(accessToken, Collections.singletonList(new OrderRequest(cartId2, 5)));

        // when 주문 내역을 조회하면
        ExtractableResponse<Response> response = 주문_내역_조회_요청(accessToken);

        // 정상적으로 조회된다.
        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    public static ExtractableResponse<Response> 주문하기_요청(String accessToken, List<OrderRequest> orderRequests) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequests)
                .when().post("/api/customer/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String accessToken, Long orderId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customer/orders/{orderId}", orderId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customer/orders")
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(String accessToken, List<OrderRequest> orderRequests) {
        ExtractableResponse<Response> response = 주문하기_요청(accessToken, orderRequests);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        OrderDetailsResponse resultOrder = response.as(OrderDetailsResponse.class);
        assertThat(resultOrder.getId()).isEqualTo(orderId);
    }

    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
        List<Long> resultOrderIds = response.jsonPath().getList(".", OrderDetailsResponse.class).stream()
                .map(OrderDetailsResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultOrderIds).contains(orderIds);
    }
}
