package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.fixture.PasswordFixture.plainBasicPassword;
import static woowacourse.fixture.TokenFixture.BEARER;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.OrdersResponse;

@DisplayName("주문 관련 기능")
@SuppressWarnings("NonAsciiCharacters")
public class OrderAcceptanceTest extends AcceptanceShoppingCartTest {

    private Long cartId1;
    private Long cartId2;
    private String accessToken;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        CustomerRequest signUpRequest = new CustomerRequest("giron", plainBasicPassword);
        회원가입_요청(signUpRequest);

        TokenRequest tokenRequest = new TokenRequest("giron", plainBasicPassword);
        TokenResponse tokenResponse = 로그인_요청_토큰_생성됨(tokenRequest);
        accessToken = tokenResponse.getAccessToken();

        cartId1 = 장바구니_아이템_추가되어_있음(accessToken, productId1);
        cartId2 = 장바구니_아이템_추가되어_있음(accessToken, productId2);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        List<OrderRequest> orderRequests = Stream.of(cartId1, cartId2)
                .map(cartId -> new OrderRequest(cartId, 1))
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(accessToken, orderRequests);

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공되어_있음(accessToken, Collections.singletonList(new OrderRequest(cartId1, 1)));
        Long orderId2 = 주문하기_요청_성공되어_있음(accessToken, Collections.singletonList(new OrderRequest(cartId2, 1)));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(accessToken);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공되어_있음(accessToken, Arrays.asList(
                new OrderRequest(cartId1, 1),
                new OrderRequest(cartId2, 1)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(accessToken, orderId);

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

    public static ExtractableResponse<Response> 주문하기_요청(String token, List<OrderRequest> orderRequests) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .body(orderRequests)
                .when().post("/api/customers/me/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .when().get("/api/customers/me/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String token, Long orderId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .when().get("/api/customers/me/orders/{orderId}", orderId)
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(String token, List<OrderRequest> orderRequests) {
        ExtractableResponse<Response> response = 주문하기_요청(token, orderRequests);
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
        OrdersResponse ordersResponse = response.as(OrdersResponse.class);
        assertThat(ordersResponse.getId()).isEqualTo(orderId);
    }
}
