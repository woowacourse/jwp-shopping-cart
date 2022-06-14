package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.AcceptanceTestFixture.getMethodRequestWithBearerAuth;
import static woowacourse.AcceptanceTestFixture.postMethodRequest;
import static woowacourse.shoppingcart.acceptance.CartItemAcceptanceTest.장바구니_아이템_추가되어_있음;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

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
import woowacourse.auth.dto.LoginRequest;
import woowacourse.shoppingcart.dto.CartItemIds;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.OrdersDto;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private Long cartId1;
    private Long cartId2;
    private Long cartId3;
    private Long cartId4;
    private String token;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        final Long productId1 = 상품_등록되어_있음("치킨", 10_000, 20, "http://example.com/chicken.jpg", "imageAlt");
        final Long productId2 = 상품_등록되어_있음("맥주", 20_000, 20, "http://example.com/beer.jpg", "imageAlt");
        final Long productId3 = 상품_등록되어_있음("피자", 15_000, 20, "http://example.com/pizza.jpg", "imageAlt");
        final Long productId4 = 상품_등록되어_있음("콜라", 5_000, 20, "http://example.com/coke.jpg", "imageAlt");

        final String email = "test@gmail.com";
        final String password = "password0!";
        final String username = "루나";

        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");
        final LoginRequest loginRequest = new LoginRequest(email, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest, "/api/auth/login");
        token = tokenResponse.jsonPath().getString("accessToken");

        getMethodRequestWithBearerAuth(token, "/api/customers/me");

        cartId1 = 장바구니_아이템_추가되어_있음(token, productId1);
        cartId2 = 장바구니_아이템_추가되어_있음(token, productId2);
        cartId3 = 장바구니_아이템_추가되어_있음(token, productId3);
        cartId4 = 장바구니_아이템_추가되어_있음(token, productId4);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        // given
        List<Long> cartItemIds = List.of(this.cartId1, cartId2);

        // when
        ExtractableResponse<Response> response = 주문하기_요청(token, new CartItemIds(cartItemIds));

        // then
        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공되어_있음(token, new CartItemIds(List.of(cartId1, cartId2)));
        Long orderId2 = 주문하기_요청_성공되어_있음(token, new CartItemIds(List.of(cartId3, cartId4)));

        ExtractableResponse<Response> response = 주문_목록_조회_요청(token);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공되어_있음(token, new CartItemIds(List.of(cartId1, cartId2)));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(token, orderId);

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

    public static ExtractableResponse<Response> 주문하기_요청(String token, CartItemIds cartItemIds) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemIds)
                .when().post("/api/myorders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_목록_조회_요청(String token) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/myorders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String token, Long orderId) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/myorders/{orderId}", orderId)
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(String userName, CartItemIds cartItemIds) {
        ExtractableResponse<Response> response = 주문하기_요청(userName, cartItemIds);
        return Long.parseLong(response.header("Location").split("/myorders/")[1]);
    }

    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
        List<Long> resultOrderIds = response.jsonPath().getList(".", OrdersDto.class).stream()
                .map(OrdersDto::getId)
                .collect(Collectors.toList());
        assertThat(resultOrderIds).contains(orderIds);
    }

    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        OrdersDto resultOrder = response.as(OrdersDto.class);
        assertThat(resultOrder.getId()).isEqualTo(orderId);
    }
}
