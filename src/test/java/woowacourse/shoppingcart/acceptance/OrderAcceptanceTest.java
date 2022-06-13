package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.AcceptanceTestFixture.getMethodRequestWithBearerAuth;
import static woowacourse.AcceptanceTestFixture.join;
import static woowacourse.AcceptanceTestFixture.login;
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
import woowacourse.shoppingcart.cartitem.ui.dto.CartItemRequest;
import woowacourse.shoppingcart.order.dto.OrderRequest;
import woowacourse.shoppingcart.order.dto.OrderResponse;
import woowacourse.shoppingcart.product.ui.dto.ProductRequest;
import woowacourse.shoppingcart.product.ui.dto.ThumbnailImageDto;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {
    private String token;
    private Long cartId1;
    private Long cartId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        join();
        token = login();

        final ProductRequest productRequest1 = new ProductRequest("치킨", 10_000, 10L,
                new ThumbnailImageDto("http://example.com/chicken.jpg", "이미지입니다."));
        final ProductRequest productRequest2 = new ProductRequest("피자", 10_000, 10L,
                new ThumbnailImageDto("http://example.com/pizza.jpg", "이미지입니다."));

        Long productId1 = Long.parseLong(
                postMethodRequest(productRequest1, "/api/products").header("Location").split("/products/")[1]);
        Long productId2 = Long.parseLong(
                postMethodRequest(productRequest2, "/api/products").header("Location").split("/products/")[1]);

        final CartItemRequest cartItemRequest1 = new CartItemRequest(productId1, 1);
        final CartItemRequest cartItemRequest2 = new CartItemRequest(productId2, 1);
        cartId1 = Long.parseLong(
                postMethodRequestWithBearerAuth(cartItemRequest1, token, "/api/mycarts").header("Location")
                        .split("/mycarts/")[1]);
        cartId2 = Long.parseLong(
                postMethodRequestWithBearerAuth(cartItemRequest2, token, "/api/mycarts").header("Location")
                        .split("/mycarts/")[1]);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        final OrderRequest orderRequest = new OrderRequest(List.of(cartId1, cartId2));

        final ExtractableResponse<Response> response = postMethodRequestWithBearerAuth(orderRequest, token,
                "/api/myorders");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("Location")).isNotBlank()
        );
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        final OrderRequest orderRequest1 = new OrderRequest(List.of(cartId1));
        final OrderRequest orderRequest2 = new OrderRequest(List.of(cartId2));
        final Long orderId1 = Long.parseLong(
                postMethodRequestWithBearerAuth(orderRequest1, token, "/api/myorders").header("Location")
                        .split("/myorders/")[1]);
        final Long orderId2 = Long.parseLong(
                postMethodRequestWithBearerAuth(orderRequest2, token, "/api/myorders").header("Location")
                        .split("/myorders/")[1]);

        final ExtractableResponse<Response> response = getMethodRequestWithBearerAuth(token,
                "/api/myorders");
        final List<OrderResponse> orderResponses = response.jsonPath().getList(".", OrderResponse.class);
        final List<Long> orderResponseIds = orderResponses.stream()
                .map(OrderResponse::getId)
                .collect(Collectors.toUnmodifiableList());

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(orderResponseIds).containsExactly(orderId1, orderId2)
        );
    }
}
