package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.로그인;
import static woowacourse.Fixtures.조조그린_로그인_요청;
import static woowacourse.Fixtures.조조그린_요청;
import static woowacourse.Fixtures.치킨;
import static woowacourse.Fixtures.피자;
import static woowacourse.Fixtures.회원가입;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가되어_있음;

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
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.OrderRequest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {
    private String 토큰;
    private Long 치킨카트_아이디;
    private Long 피자카트_아이디;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        회원가입(조조그린_요청);
        토큰 = 로그인(조조그린_로그인_요청)
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        치킨카트_아이디 = 장바구니_아이템_추가되어_있음(토큰, 치킨.getId());
        피자카트_아이디 = 장바구니_아이템_추가되어_있음(토큰, 피자.getId());
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        List<OrderRequest> orderRequests = Stream.of(치킨카트_아이디, 피자카트_아이디)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(토큰, orderRequests);

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공되어_있음(토큰, Collections.singletonList(new OrderRequest(치킨카트_아이디, 2)));
        Long orderId2 = 주문하기_요청_성공되어_있음(토큰, Collections.singletonList(new OrderRequest(피자카트_아이디, 5)));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(토큰);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공되어_있음(토큰, Arrays.asList(
                new OrderRequest(치킨카트_아이디, 2),
                new OrderRequest(피자카트_아이디, 4)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(토큰, orderId);

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

    public static ExtractableResponse<Response> 주문하기_요청(String userName, List<OrderRequest> orderRequests) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequests)
                .when().post("/customers/{customerName}/orders", userName)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String userName) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/{customerName}/orders", userName)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String userName, Long orderId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/{customerName}/orders/{orderId}", userName, orderId)
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
