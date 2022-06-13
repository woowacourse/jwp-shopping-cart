package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.CUSTOMER_REQUEST_1;
import static woowacourse.Fixtures.EXPIRED_TOKEN;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.AcceptanceTest;
import woowacourse.auth.acceptance.AuthAcceptanceTest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.request.OrdersRequest;

@DisplayName("주문 관련 기능")
@Sql("classpath:schema.sql")
public class OrdersAcceptanceTest extends AcceptanceTest {
    private String accessToken;

    private Long productId1;
    private Long productId2;

    public static ExtractableResponse<Response> 주문하기_요청(String accessToken, List<OrdersRequest> ordersRequests) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(ordersRequests)
                .when().post("/api/customers/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String accessToken, Long orderId) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/orders/{orderId}", orderId)
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(String userName, List<OrdersRequest> ordersRequests) {
        ExtractableResponse<Response> response = 주문하기_요청(userName, ordersRequests);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        AuthAcceptanceTest.회원가입_요청_후_생성된_아이디_반환(CUSTOMER_REQUEST_1);
        TokenResponse tokenResponse = AuthAcceptanceTest.로그인_요청_후_토큰_DTO_반환(CUSTOMER_REQUEST_1.getEmail(),
                CUSTOMER_REQUEST_1.getPassword());
        accessToken = tokenResponse.getAccessToken();

        productId1 = 상품_등록되어_있음("치킨", "치킨 입니다.", 10_000, 10, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", "맥주 입니다.", 20_000, 10, "http://example.com/beer.jpg");
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        List<OrdersRequest> ordersRequests = Stream.of(productId1, productId2)
                .map(productId -> new OrdersRequest(productId, 10))
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(accessToken, ordersRequests);

        주문하기_성공함(response);
    }

    @DisplayName("주문하기 실패 - 유효하지 않은 토큰")
    @ValueSource(strings = {"", "abcd"})
    @ParameterizedTest
    void addOrder_failWithInvalidToken(String accessToken) {
        List<OrdersRequest> ordersRequests = Stream.of(productId1, productId2)
                .map(productId -> new OrdersRequest(productId, 10))
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(accessToken, ordersRequests);
        AuthAcceptanceTest.토큰이_유효하지_않음(response);
    }

    @DisplayName("주문하기 실패 - 만료된 토큰")
    @Test
    void addOrder_failWithExpiredToken() {
        List<OrdersRequest> ordersRequests = Stream.of(productId1, productId2)
                .map(productId -> new OrdersRequest(productId, 10))
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(EXPIRED_TOKEN, ordersRequests);
        AuthAcceptanceTest.토큰이_만료됨(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공되어_있음(accessToken, Collections.singletonList(new OrdersRequest(productId1, 2)));
        Long orderId2 = 주문하기_요청_성공되어_있음(accessToken, Collections.singletonList(new OrdersRequest(productId2, 5)));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(accessToken);

        주문_조회_응답됨(response);
    }

    @DisplayName("주문 내역 조회 실패 - 유효하지 않은 토큰")
    @ValueSource(strings = {"", "abcd"})
    @ParameterizedTest
    void getOrders_failWithInvalidToken(String invalidToken) {
        주문하기_요청_성공되어_있음(accessToken, Collections.singletonList(new OrdersRequest(productId1, 2)));
        주문하기_요청_성공되어_있음(accessToken, Collections.singletonList(new OrdersRequest(productId2, 5)));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(invalidToken);

        AuthAcceptanceTest.토큰이_유효하지_않음(response);
    }

    @DisplayName("주문 내역 조회 - 만료된 토큰")
    @Test
    void getOrders_failWithExpiredToken() {
        주문하기_요청_성공되어_있음(accessToken, Collections.singletonList(new OrdersRequest(productId1, 2)));
        주문하기_요청_성공되어_있음(accessToken, Collections.singletonList(new OrdersRequest(productId2, 5)));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(EXPIRED_TOKEN);

        AuthAcceptanceTest.토큰이_만료됨(response);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공되어_있음(accessToken, Arrays.asList(
                new OrdersRequest(productId1, 2),
                new OrdersRequest(productId2, 4)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(accessToken, orderId);

        주문_조회_응답됨(response);
    }

    @DisplayName("주문 단일 조회 실패 - 유효하지 않은 토큰")
    @ValueSource(strings = {"", "abcd"})
    @ParameterizedTest
    void getOrder_failWithInvalidToken(String invalidToken) {
        Long orderId = 주문하기_요청_성공되어_있음(accessToken, Arrays.asList(
                new OrdersRequest(productId1, 2),
                new OrdersRequest(productId2, 4)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(invalidToken, orderId);

        AuthAcceptanceTest.토큰이_유효하지_않음(response);
    }

    @DisplayName("주문 단일 조회 - 만료된 토큰")
    @Test
    void getOrder_failWithExpiredToken() {
        Long orderId = 주문하기_요청_성공되어_있음(accessToken, Arrays.asList(
                new OrdersRequest(productId1, 2),
                new OrdersRequest(productId2, 4)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(EXPIRED_TOKEN, orderId);

        AuthAcceptanceTest.토큰이_만료됨(response);
    }
}
