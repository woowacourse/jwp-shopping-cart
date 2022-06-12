package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.AcceptanceTestFixture.getMethodRequestWithBearerAuth;
import static woowacourse.AcceptanceTestFixture.postMethodRequest;
import static woowacourse.AcceptanceTestFixture.postMethodRequestWithBearerAuth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.common.AcceptanceTest;
import woowacourse.shoppingcart.domain.ThumbnailImage;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemAddRequest;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;
import woowacourse.shoppingcart.dto.order.OrderCreateRequest;
import woowacourse.shoppingcart.dto.order.OrderResponse;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
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
        String token = loginAndGetToken("test@gmail.com", "password0!", "name");
        addCartItem(token, 1L, 1);
        addCartItem(token, 2L, 1);

        OrderCreateRequest firstOrder = new OrderCreateRequest(List.of(1L));
        OrderCreateRequest secondOrder = new OrderCreateRequest(List.of(2L));
        postMethodRequestWithBearerAuth(firstOrder, token, "/api/myorders");
        postMethodRequestWithBearerAuth(secondOrder, token, "/api/myorders");

        ExtractableResponse<Response> response = getMethodRequestWithBearerAuth(token, "/api/myorders");

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, 1L, 2L);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        String token = loginAndGetToken("test@gmail.com", "password0!", "name");
        addCartItem(token, 1L, 1);
        addCartItem(token, 2L, 1);

        OrderCreateRequest firstOrder = new OrderCreateRequest(List.of(1L, 2L));
        postMethodRequestWithBearerAuth(firstOrder, token, "/api/myorders");

        ExtractableResponse<Response> response = getMethodRequestWithBearerAuth(token, "/api/myorders/1");

        주문_조회_응답됨(response);
        주문_조회됨(response, 1L);
    }

    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        OrderResponse resultOrder = response.as(OrderResponse.class);
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
