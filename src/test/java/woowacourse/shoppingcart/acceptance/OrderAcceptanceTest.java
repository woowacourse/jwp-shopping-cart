package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.ShoppingCartFixture.잉_로그인요청;
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
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.ExceptionResponse;
import woowacourse.shoppingcart.dto.response.OrdersResponse;

@DisplayName("주문 관련 기능")
@Sql("/truncate.sql")
public class OrderAcceptanceTest extends AcceptanceTest {

    private String token;
    private Long cartId1;
    private Long cartId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        signUp();
        token = getToken(잉_로그인요청);

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        cartId1 = 장바구니_아이템_추가되어_있음(token, productId1);
        cartId2 = 장바구니_아이템_추가되어_있음(token, productId2);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        List<OrderRequest> orderRequests = Stream.of(cartId1, cartId2)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(token, orderRequests);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공되어_있음(token, Collections.singletonList(new OrderRequest(cartId1, 2)));
        Long orderId2 = 주문하기_요청_성공되어_있음(token, Collections.singletonList(new OrderRequest(cartId2, 5)));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(token);
        final List<OrdersResponse> 주문내역 = response.as(List.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(주문내역.size()).isEqualTo(2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공되어_있음(token, Arrays.asList(
                new OrderRequest(cartId1, 2),
                new OrderRequest(cartId2, 4)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(token, orderId);
        final OrdersResponse 주문_조회_응답 = response.as(OrdersResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertAll(
                () -> assertThat(주문_조회_응답.getId()).isEqualTo(orderId),
                () -> assertThat(주문_조회_응답.getTotalPrice()).isEqualTo(100000),
                () -> assertThat(주문_조회_응답.getOrderDetails().size()).isEqualTo(2)
        );
    }

    @DisplayName("해당 주문이 없는 경우, 주문을 조회할 수 없다.")
    @Test
    void getOrderWith() {
        Long orderId = 주문하기_요청_성공되어_있음(token, Arrays.asList(
                new OrderRequest(cartId1, 2),
                new OrderRequest(cartId2, 4)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(token, orderId + orderId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.as(ExceptionResponse.class).getMessage()).isEqualTo("유저에게는 해당 order_id가 없습니다.");
    }

    public static ExtractableResponse<Response> 주문하기_요청(String token, List<OrderRequest> orderRequests) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequests)
                .when().post("/api/customer/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customer/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String token, Long orderId) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customer/orders/{orderId}", orderId)
                .then().log().all()
                .extract();
    }

    public static Long 주문하기_요청_성공되어_있음(String token, List<OrderRequest> orderRequests) {
        ExtractableResponse<Response> response = 주문하기_요청(token, orderRequests);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }
}
