package woowacourse.shoppingcart.acceptance;

import static java.lang.Long.parseLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.CartSaveRequest;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import java.util.List;

@DisplayName("주문 관련 기능")
@Sql("/testData.sql")
public class OrderAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "sun@gmail.com";
    private static final String PASSWORD = "12345678";

    private Long cartId1;
    private Long cartId2;
    private String accessToken;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        // 토큰 발급
        accessToken = requestPostWithBody("/api/login", new TokenRequest(EMAIL, PASSWORD))
                .as(TokenResponse.class)
                .getAccessToken();

        // 카트 담기
        final ExtractableResponse<Response> cartResponse1 =
                requestPostWithTokenAndBody("/api/customer/carts", accessToken, new CartSaveRequest(1L, 10));
        final ExtractableResponse<Response> cartResponse2 =
                requestPostWithTokenAndBody("/api/customer/carts", accessToken, new CartSaveRequest(2L, 10));

        cartId1 = parseLong(cartResponse1.header("Location").split("/carts/")[1]);
        cartId2 = parseLong(cartResponse2.header("Location").split("/carts/")[1]);
    }

    @DisplayName("주문하기")
    @Test
    void add() {
        // given
        final OrderRequest orderRequest1 = new OrderRequest(cartId1, 10);
        final OrderRequest orderRequest2 = new OrderRequest(cartId2, 10);

        // when
        final ExtractableResponse<Response> response =
                requestPostWithTokenAndBody("/api/customer/orders", accessToken, List.of(orderRequest1, orderRequest2));
        final long orderId = parseLong(response.header("Location").split("/orders/")[1]);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(orderId).isOne()
        );
    }

    @DisplayName("단일 주문 조회")
    @Test
    void getOne() {
        // given
        final OrderRequest orderRequest1 = new OrderRequest(cartId1, 10);
        final OrderRequest orderRequest2 = new OrderRequest(cartId2, 10);
        final ExtractableResponse<Response> createResponse =
                requestPostWithTokenAndBody("/api/customer/orders", accessToken, List.of(orderRequest1, orderRequest2));
        final long orderId = parseLong(createResponse.header("Location").split("/orders/")[1]);
        final List<OrderDetail> orderDetails = List.of(
                new OrderDetail(1L, 10000, "치킨", "http://example.com/chicken.jpg", 10),
                new OrderDetail(2L, 20000, "맥주", "http://example.com/beer.jpg", 10)
        );

        // when
        final ExtractableResponse<Response> response =
                requestGetWithTokenAndBody("/api/customer/orders/" + orderId, accessToken);
        final OrderResponse orderResponseDto = response.as(OrderResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(orderResponseDto).usingRecursiveComparison()
                        .isEqualTo(OrderResponse.from(new Orders(1L, orderDetails))));
    }

    @DisplayName("주문 전체 조회")
    @Test
    void getAll() {
        // given
        final OrderRequest orderRequest1 = new OrderRequest(cartId1, 10);
        final OrderRequest orderRequest2 = new OrderRequest(cartId2, 10);
        final ExtractableResponse<Response> createResponse1 =
                requestPostWithTokenAndBody("/api/customer/orders", accessToken, List.of(orderRequest1));
        final ExtractableResponse<Response> createResponse2 =
                requestPostWithTokenAndBody("/api/customer/orders", accessToken, List.of(orderRequest2));
        final long orderId1 = parseLong(createResponse1.header("Location").split("/orders/")[1]);
        final long orderId2 = parseLong(createResponse2.header("Location").split("/orders/")[1]);

        final List<OrderDetail> orderDetails1 = List.of(
                new OrderDetail(1L, 10000, "치킨", "http://example.com/chicken.jpg", 10));
        final List<OrderDetail> orderDetails2 = List.of(
                new OrderDetail(2L, 20000, "맥주", "http://example.com/beer.jpg", 10));

        // when
        final ExtractableResponse<Response> response =
                requestGetWithTokenAndBody("/api/customer/orders", accessToken);
        final List<OrderResponse> orderResponses = response.jsonPath().getList(".", OrderResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(orderResponses).usingRecursiveComparison()
                        .isEqualTo(List.of(
                                OrderResponse.from(new Orders(orderId1, orderDetails1)),
                                OrderResponse.from(new Orders(orderId2, orderDetails2))
                        ))
        );
    }
}
