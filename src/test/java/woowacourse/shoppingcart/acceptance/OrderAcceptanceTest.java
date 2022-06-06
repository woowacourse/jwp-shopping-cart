package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.createCustomer;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.getTokenResponse;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_REQUEST_1;
import static woowacourse.shoppingcart.fixture.OrderFixtures.ORDER_REQUEST_1;
import static woowacourse.shoppingcart.fixture.OrderFixtures.ORDER_REQUEST_INVALID_PRODUCT;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_REQUEST_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.getProductRequestParam;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.AcceptanceTest;
import woowacourse.shoppingcart.dto.OrderRequest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {
    public static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjU0MzUyNzMwLCJleHAiOjE2NTQzNTI3MzB9.OvlNgJk_dG30BL_JWj_DQRPmepqLMLl6Djwtlp2hBWw";
    public static final String INVALID_TOKEN = "invalidToken";

    private String token;

    public static ExtractableResponse<Response> 주문하기_요청(OrderRequest orderRequest, String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequest)
                .when().post("/api/customers/orders")
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    private void 주문하기_실패함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private void 유효하지_않은_토큰_응답(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private void 만료된_토큰_응답(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    //
    //    public static ExtractableResponse<Response> 주문_내역_조회_요청(String userName) {
    //        return RestAssured
    //                .given().log().all()
    //                .contentType(MediaType.APPLICATION_JSON_VALUE)
    //                .when().get("/api/customers/{customerName}/orders", userName)
    //                .then().log().all()
    //                .extract();
    //    }
    //
    //    public static ExtractableResponse<Response> 주문_단일_조회_요청(String userName, Long orderId) {
    //        return RestAssured
    //                .given().log().all()
    //                .contentType(MediaType.APPLICATION_JSON_VALUE)
    //                .when().get("/api/customers/{customerName}/orders/{orderId}", userName, orderId)
    //                .then().log().all()
    //                .extract();
    //    }
    //
    //
    //    public static Long 주문하기_요청_성공되어_있음(String userName, List<OrderRequest> orderRequests) {
    //        ExtractableResponse<Response> response = 주문하기_요청(userName, orderRequests);
    //        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    //    }
    //
    //    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
    //        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    //    }
    //
    //    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
    //        List<Long> resultOrderIds = response.jsonPath().getList(".", Orders.class).stream()
    //                .map(Orders::getId)
    //                .collect(Collectors.toList());
    //        assertThat(resultOrderIds).contains(orderIds);
    //    }
    //
    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        createCustomer(CUSTOMER_REQUEST_1);
        token = getTokenResponse(CUSTOMER_REQUEST_1.getEmail(),
                CUSTOMER_REQUEST_1.getPassword()).getAccessToken();

        상품_등록되어_있음(getProductRequestParam(PRODUCT_REQUEST_1));
        상품_등록되어_있음(getProductRequestParam(PRODUCT_REQUEST_1));

    }

//

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        ExtractableResponse<Response> response = 주문하기_요청(ORDER_REQUEST_1, token);

        주문하기_성공함(response);
    }

    @DisplayName("없는 상품을 주문할 때 주문하기 실패")
    @Test
    void addOrderByInvalidProduct() {
        ExtractableResponse<Response> response = 주문하기_요청(ORDER_REQUEST_INVALID_PRODUCT, token);

        주문하기_실패함(response);
    }

    @DisplayName("유효하지 않은 토큰으로 주문 시 실패")
    @Test
    void addOrderByInvalidToken() {
        ExtractableResponse<Response> response = 주문하기_요청(ORDER_REQUEST_INVALID_PRODUCT, INVALID_TOKEN);

        유효하지_않은_토큰_응답(response);
    }

    @DisplayName("만료된 토큰으로 주문 시 실패")
    @Test
    void addOrderByExpiredToken() {
        ExtractableResponse<Response> response = 주문하기_요청(ORDER_REQUEST_INVALID_PRODUCT, EXPIRED_TOKEN);

        만료된_토큰_응답(response);
    }

//
//    @DisplayName("주문 내역 조회")
//    @Test
//    void getOrders() {
//        Long orderId1 = 주문하기_요청_성공되어_있음(USER, Collections.singletonList(new OrderRequest(cartId1, 2)));
//        Long orderId2 = 주문하기_요청_성공되어_있음(USER, Collections.singletonList(new OrderRequest(cartId2, 5)));
//
//        ExtractableResponse<Response> response = 주문_내역_조회_요청(USER);
//
//        주문_조회_응답됨(response);
//        주문_내역_포함됨(response, orderId1, orderId2);
//    }
//
//    @DisplayName("주문 단일 조회")
//    @Test
//    void getOrder() {
//        Long orderId = 주문하기_요청_성공되어_있음(USER, Arrays.asList(
//                new OrderRequest(cartId1, 2),
//                new OrderRequest(cartId2, 4)
//        ));
//
//        ExtractableResponse<Response> response = 주문_단일_조회_요청(USER, orderId);
//
//        주문_조회_응답됨(response);
//        주문_조회됨(response, orderId);
//    }
//
//    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
//        Orders resultOrder = response.as(Orders.class);
//        assertThat(resultOrder.getId()).isEqualTo(orderId);
//    }
}
