package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가되어_있음;
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
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.request.CreateOrderDetailRequest;
import woowacourse.shoppingcart.dto.response.OrderResponse;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private static final String USER_NAME = "puterism";
    private static final String PASSWORD = "Shopping123!";
    private String accessToken;
    private Long cartId1;
    private Long cartId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        accessToken = 로그인_및_토큰_발급(USER_NAME, PASSWORD);

        cartId1 = 장바구니_아이템_추가되어_있음(accessToken, productId1, 3);
        cartId2 = 장바구니_아이템_추가되어_있음(accessToken, productId2, 10);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        // given
        List<CreateOrderDetailRequest> orderRequests = Stream.of(cartId1, cartId2)
                .map(cartId -> new CreateOrderDetailRequest(cartId, 10))
                .collect(Collectors.toList());

        // when
        ExtractableResponse<Response> response = 주문하기_요청(orderRequests, accessToken);

        // then
        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        // given
        Long orderId1 = 주문하기_요청_성공되어_있음(Collections.singletonList(new CreateOrderDetailRequest(cartId1, 2)),
                accessToken);
        Long orderId2 = 주문하기_요청_성공되어_있음(Collections.singletonList(new CreateOrderDetailRequest(cartId2, 5)),
                accessToken);

        // when
        ExtractableResponse<Response> response = 주문_내역_조회_요청(accessToken);

        // then
        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        // given
        Long orderId = 주문하기_요청_성공되어_있음(Arrays.asList(
                new CreateOrderDetailRequest(cartId1, 2),
                new CreateOrderDetailRequest(cartId2, 4)
        ), accessToken);

        // when
        ExtractableResponse<Response> response = 주문_단일_조회_요청(orderId, accessToken);

        // then
        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

    private String 로그인_및_토큰_발급(String name, String password) {
        return RestAssured
                .given().log().all()
                .body(new TokenRequest(name, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all().extract()
                .as(TokenResponse.class).getAccessToken();
    }

    public static ExtractableResponse<Response> 주문하기_요청(List<CreateOrderDetailRequest> orderRequests,
                                                        String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequests)
                .when().post("/api/customers/me/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(Long orderId, String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me/orders/{orderId}", orderId)
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(List<CreateOrderDetailRequest> orderRequests, String accessToken) {
        ExtractableResponse<Response> response = 주문하기_요청(orderRequests, accessToken);
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
        OrderResponse resultOrder = response.as(OrderResponse.class);
        assertThat(resultOrder.getId()).isEqualTo(orderId);
    }
}
