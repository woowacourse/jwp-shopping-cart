package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.createCustomer;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.getTokenResponse;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;
import static woowacourse.shoppingcart.fixture.CartItemFixtures.CART_REQUEST_2;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_REQUEST_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_REQUEST_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_REQUEST_2;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_REQUEST_3;
import static woowacourse.shoppingcart.fixture.ProductFixtures.getProductRequestParam;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.AcceptanceTest;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.OrderResponse;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {
    public static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjU0MzUyNzMwLCJleHAiOjE2NTQzNTI3MzB9.OvlNgJk_dG30BL_JWj_DQRPmepqLMLl6Djwtlp2hBWw";
    public static final String INVALID_TOKEN = "invalidToken";

    private String token;
    private List<CartRequest> orderRequest1;
    private List<CartRequest> orderRequest2;

    public static ExtractableResponse<Response> 주문하기_요청(List<CartRequest> orderRequest, String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequest)
                .when().post("/api/customers/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(Long orderId, String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/orders/{orderId}", orderId)
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
    public static Long 주문하기_요청_성공되어_있음(List<CartRequest> orderRequest, String token) {
        ExtractableResponse<Response> response = 주문하기_요청(orderRequest, token);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }


    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, int orderCount) {
        final List<OrderResponse> orders = response.jsonPath().getList(".", OrderResponse.class);
        assertThat(orders.size()).isEqualTo(orderCount);
    }

    private void 주문_조회됨(ExtractableResponse<Response> response) {
        OrderResponse resultOrder = response.as(OrderResponse.class);
        assertAll(
                () -> assertThat(resultOrder.getTotalPrice()).isEqualTo(
                        PRODUCT_REQUEST_1.getPrice() * 3 + PRODUCT_REQUEST_3.getPrice()),
                () -> assertThat(resultOrder.getProducts()).hasSize(2)
        );
    }

    private void 주문_조회_응답_안됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        createCustomer(CUSTOMER_REQUEST_1);
        token = getTokenResponse(CUSTOMER_REQUEST_1.getEmail(),
                CUSTOMER_REQUEST_1.getPassword()).getAccessToken();

        final Long productId1 = 상품_등록되어_있음(getProductRequestParam(PRODUCT_REQUEST_1));
        final Long productId2 = 상품_등록되어_있음(getProductRequestParam(PRODUCT_REQUEST_2));
        final Long productId3 = 상품_등록되어_있음(getProductRequestParam(PRODUCT_REQUEST_3));

        orderRequest1 = List.of(new CartRequest(productId1, 3), new CartRequest(productId3, 1));
        orderRequest2 = List.of(new CartRequest(productId2, 10));
    }


    @DisplayName("주문하기")
    @Test
    void addOrder() {
        ExtractableResponse<Response> response = 주문하기_요청(orderRequest1, token);

        주문하기_성공함(response);
    }

    @DisplayName("없는 상품을 주문할 때 주문하기 실패")
    @Test
    void addOrderByInvalidProduct() {
        ExtractableResponse<Response> response = 주문하기_요청(List.of(new CartRequest(999999L, 3), CART_REQUEST_2), token);

        주문하기_실패함(response);
    }

    @DisplayName("유효하지 않은 토큰으로 주문 시 실패")
    @Test
    void addOrderByInvalidToken() {
        ExtractableResponse<Response> response = 주문하기_요청(orderRequest1, INVALID_TOKEN);

        유효하지_않은_토큰_응답(response);
    }

    @DisplayName("만료된 토큰으로 주문 시 실패")
    @Test
    void addOrderByExpiredToken() {
        ExtractableResponse<Response> response = 주문하기_요청(orderRequest1, EXPIRED_TOKEN);

        만료된_토큰_응답(response);
    }


    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        주문하기_요청_성공되어_있음(orderRequest1, token);
        주문하기_요청_성공되어_있음(orderRequest2, token);

        ExtractableResponse<Response> response = 주문_내역_조회_요청(token);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, 2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        final Long orderId = 주문하기_요청_성공되어_있음(orderRequest1, token);

        ExtractableResponse<Response> response = 주문_단일_조회_요청(orderId, token);

        주문_조회_응답됨(response);
        주문_조회됨(response);
    }

    @DisplayName("잘못된 주문 정보 단일 조회 실패")
    @Test
    void getOrderByInvalidOrderId() {
        final Long orderId = 주문하기_요청_성공되어_있음(orderRequest1, token);

        ExtractableResponse<Response> response = 주문_단일_조회_요청(orderId + 1, token);

        주문_조회_응답_안됨(response);
    }

}
