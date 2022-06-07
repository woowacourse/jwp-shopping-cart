package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.AcceptanceTestFixture.postMethodRequest;
import static woowacourse.AcceptanceTestFixture.postMethodRequestWithBearerAuth;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.common.AcceptanceTest;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.ThumbnailImage;
import woowacourse.shoppingcart.dto.OrderCreateRequest;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemAddRequest;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {
    private static final String USER = "puterism";
    private Long cartId1;
    private Long cartId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        ThumbnailImage chickenThumbnailImage = new ThumbnailImage("http://example.com/chicken.jpg", "chicken");
        ThumbnailImage beerThumbnailImage = new ThumbnailImage("http://example.com/beer.jpg", "chicken");

        ProductRequest chickenRequest = new ProductRequest("치킨", 1000, 10, chickenThumbnailImage);
        ProductRequest beerRequest = new ProductRequest("치킨", 1000, 10, beerThumbnailImage);

        postMethodRequest(chickenRequest, "/api/products");
        postMethodRequest(beerRequest, "/api/products");
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        String token = loginAndGetToken("test@gmail.com", "password0!", "name");
        addCartItem(token, 1L, 1);
        addCartItem(token, 2L, 1);

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(List.of(1L, 2L));

        ExtractableResponse<Response> response = postMethodRequestWithBearerAuth(orderCreateRequest,
                token, "/api/myorders");

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공되어_있음(USER, Collections.singletonList(new OrderRequest(cartId1, 2)));
        Long orderId2 = 주문하기_요청_성공되어_있음(USER, Collections.singletonList(new OrderRequest(cartId2, 5)));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(USER);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공되어_있음(USER, Arrays.asList(
                new OrderRequest(cartId1, 2),
                new OrderRequest(cartId2, 4)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(USER, orderId);

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

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

    private String loginAndGetToken(String email, String password, String username) {
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest,
                "/api/auth/login");

        return tokenResponse.jsonPath().getString("accessToken");
    }

    private ExtractableResponse<Response> addCartItem(String token, Long productId, int quantity) {
        CartItemAddRequest firstCartItemRequest = new CartItemAddRequest(productId, quantity);
        return postMethodRequestWithBearerAuth(firstCartItemRequest, token, "/api/mycarts");
    }
}
