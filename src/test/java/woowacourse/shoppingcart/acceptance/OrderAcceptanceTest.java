package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가_요청;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;
import static woowacourse.utils.Fixture.signupRequest;
import static woowacourse.utils.Fixture.tokenRequest;
import static woowacourse.utils.Fixture.맥주;
import static woowacourse.utils.Fixture.치킨;
import static woowacourse.utils.RestAssuredUtils.httpPost;
import static woowacourse.utils.RestAssuredUtils.login;
import static woowacourse.utils.RestAssuredUtils.postWithToken;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.domain.order.Orders;
import woowacourse.shoppingcart.dto.cart.CartSetRequest;
import woowacourse.shoppingcart.dto.order.OrderRequest;
import woowacourse.shoppingcart.dto.order.OrderSaveRequest;
import woowacourse.utils.AcceptanceTest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private Long productId1;
    private Long productId2;
    private String token;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);
        token = loginResponse.jsonPath().getString("accessToken");

        productId1 = 상품_등록되어_있음(치킨, token);
        productId2 = 상품_등록되어_있음(맥주, token);

        장바구니_아이템_추가_요청(token, productId1, new CartSetRequest(100));
        장바구니_아이템_추가_요청(token, productId2, new CartSetRequest(100));
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        OrderSaveRequest orderSaveRequest = new OrderSaveRequest(List.of(productId1, productId2));
        ExtractableResponse<Response> response = postWithToken("/orders", token, orderSaveRequest);

        주문하기_성공함(response);
    }

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

    public static ExtractableResponse<Response> 주문하기_요청(String userName, List<OrderRequest> orderRequests) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequests)
                .when().post("/api/customers/{customerName}/orders", userName)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String userName) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/{customerName}/orders", userName)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String userName, Long orderId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/{customerName}/orders/{orderId}", userName, orderId)
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(String userName, List<OrderRequest> orderRequests) {
        ExtractableResponse<Response> response = 주문하기_요청(userName, orderRequests);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
        List<Long> resultOrderIds = response.jsonPath().getList(".", Orders.class).stream()
                .map(Orders::getId)
                .collect(Collectors.toList());
        assertThat(resultOrderIds).contains(orderIds);
    }

    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        Orders resultOrder = response.as(Orders.class);
        assertThat(resultOrder.getId()).isEqualTo(orderId);
    }
}
