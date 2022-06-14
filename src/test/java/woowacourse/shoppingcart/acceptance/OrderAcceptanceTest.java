package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.OrderRequest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "leo@woowahan.com";
    private static final String PASSWORD = "Bunny1234!@";
    private static final String NAME = "leo";
    private static final String PHONE = "010-1234-5678";
    private static final String ADDRESS = "Seoul";

    private Long productId1;
    private Long productId2;

    private String accessToken;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        ExtractableResponse<Response> registerCustomerResponse = postWithBody("/customers", customerRequest);

        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        accessToken = postWithBody("/auth/login", tokenRequest)
                .as(TokenResponse.class)
                .getAccessToken();

        CartRequest cartRequestProduct1 = new CartRequest(productId1, 10);
        CartRequest cartRequestProduct2 = new CartRequest(productId2, 10);
        postWithBodyByToken("/customers/carts", accessToken, cartRequestProduct1);
        postWithBodyByToken("/customers/carts", accessToken, cartRequestProduct2);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        List<OrderRequest> orderRequests = Stream.of(productId1, productId2)
                .map(productId -> new OrderRequest(productId, 10))
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = postWithBodyByToken("/customers/orders", accessToken, orderRequests);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        OrderRequest orderRequest1 = new OrderRequest(productId1, 10);
        OrderRequest orderRequest2 = new OrderRequest(productId2, 10);
        ExtractableResponse<Response> orderResponse1 = postWithBodyByToken("/customers/orders", accessToken,
                List.of(orderRequest1));
        ExtractableResponse<Response> orderResponse2 = postWithBodyByToken("/customers/orders", accessToken,
                List.of(orderRequest2));
        Long orderId1 = Long.parseLong(orderResponse1.header("Location").split("/orders/")[1]);
        Long orderId2 = Long.parseLong(orderResponse2.header("Location").split("/orders/")[1]);

        ExtractableResponse<Response> getOrdersResponse = getByToken("/customers/orders", accessToken);

        assertThat(getOrdersResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<Orders> orders = getOrdersResponse.jsonPath().getList(".", Orders.class);
        List<Long> orderIds = orders.stream().map(Orders::getId).collect(Collectors.toList());
        assertThat(orderIds).contains(orderId1, orderId2);
    }


    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        List<OrderRequest> orderRequests = Stream.of(productId1, productId2)
                .map(productId -> new OrderRequest(productId, 10))
                .collect(Collectors.toList());
        ExtractableResponse<Response> orderResponse = postWithBodyByToken("/customers/orders", accessToken,
                orderRequests);
        Long orderId = Long.parseLong(orderResponse.header("Location").split("/orders/")[1]);

        ExtractableResponse<Response> getOrdersResponse = getByToken("/customers/orders/" + orderId, accessToken);

        assertThat(getOrdersResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        Orders order = getOrdersResponse.jsonPath().getObject(".", Orders.class);
        assertThat(order.getId()).isEqualTo(orderId);
    }
}
