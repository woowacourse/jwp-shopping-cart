package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가되어_있음;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.fixture.SimpleResponse;
import woowacourse.fixture.SimpleRestAssured;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.CartItemsRequest;
import woowacourse.shoppingcart.dto.OrderRequest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {
    private static final String USERNAME = "puterism";
    private static final String PASSWORD = "password123!";
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        장바구니_아이템_추가되어_있음(productId1);
        장바구니_아이템_추가되어_있음(productId2);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        CartItemsRequest cartItemsRequest = new CartItemsRequest(List.of(productId1, productId2));

        SimpleResponse response = 주문하기_요청(cartItemsRequest);
        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공되어_있음(new CartItemsRequest(List.of(productId1)));
        Long orderId2 = 주문하기_요청_성공되어_있음(new CartItemsRequest(List.of(productId2)));

        SimpleResponse response = 주문_내역_조회_요청();

        //주문_조회_응답됨(response);
        //주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공되어_있음(new CartItemsRequest(List.of(productId1, productId2)));

//        ExtractableResponse<Response> response = 주문_단일_조회_요청(orderId);

//        주문_조회_응답됨(response);
//        주문_조회됨(response, orderId);
    }

    public static SimpleResponse 주문하기_요청(CartItemsRequest cartItemsRequest) {
        return SimpleRestAssured.postWithToken("/orders", getTokenByLogin(), cartItemsRequest);
    }

    public static SimpleResponse 주문_내역_조회_요청() {
        return SimpleRestAssured.getWithToken("/orders", getTokenByLogin());
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String userName, Long orderId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/{customerName}/orders/{orderId}", userName, orderId)
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(SimpleResponse response) {
        response.assertStatus(HttpStatus.CREATED);
        response.assertHeader("Location", "/orders/1");
    }

    public static Long 주문하기_요청_성공되어_있음(CartItemsRequest cartItemsRequest) {
        SimpleResponse response = 주문하기_요청(cartItemsRequest);
        return response.getIdFromLocation("/orders/");
    }

    public static void 주문_조회_응답됨(SimpleResponse response) {
        response.assertStatus(HttpStatus.OK);
    }
//
//    public static void 주문_내역_포함됨(SimpleResponse response, Long... orderIds) {
//        List<Long> resultOrderIds = response.jsonPath().getList(".", Orders.class).stream()
//                .map(Orders::getId)
//                .collect(Collectors.toList());
//        assertThat(resultOrderIds).contains(orderIds);
//    }

    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        Orders resultOrder = response.as(Orders.class);
        assertThat(resultOrder.getId()).isEqualTo(orderId);
    }

    private static String getTokenByLogin() {
        TokenRequest tokenRequest = new TokenRequest(USERNAME, PASSWORD);
        return SimpleRestAssured.post("/login", tokenRequest)
                .toObject(TokenResponse.class)
                .getAccessToken();
    }
}
