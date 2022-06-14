package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static woowacourse.auth.acceptance.AuthAcceptanceTest.*;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.*;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ThumbnailImageDto;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private Long productId1;
    private Long productId2;
    private Header header;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        ProductRequest productRequest1 = new ProductRequest("치킨", 10_000, 10,
            new ThumbnailImageDto("http://example.com/chicken.jpg", "이미지입니다."));
        ProductRequest productRequest2 = new ProductRequest("맥주", 20_000, 10,
            new ThumbnailImageDto("http://example.com/beer.jpg", "이미지입니다."));
        productId1 = getAddedProductId(productRequest1);
        productId2 = getAddedProductId(productRequest2);
        header = getTokenHeader();
    }

    @DisplayName("장바구니에서 선택 주문하기")
    @Test
    void addOrder() {
        // given
        CartItemRequest cartItemRequest1 = new CartItemRequest(productId1, 10);
        CartItemRequest cartItemRequest2 = new CartItemRequest(productId2, 10);
        CartItemResponse cartItemResponse1 = extractCartItem(
            AcceptanceFixture.post(cartItemRequest1, "/api/mycarts", header));
        AcceptanceFixture.post(cartItemRequest2, "/api/mycarts", header);

        // when
        OrderRequest orderRequest = new OrderRequest(List.of(cartItemResponse1.getId()));
        ExtractableResponse<Response> addOrderResponse = AcceptanceFixture.post(orderRequest, "/api/myorders", header);

        // then
        assertThat(addOrderResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(addOrderResponse.header("Location")).isEqualTo("/api/myorders/1");
    }

    @DisplayName("주문 목록을 가져온다.")
    @Test
    void getOrders() {
        // given
        CartItemRequest cartItemRequest1 = new CartItemRequest(productId1, 10);
        CartItemRequest cartItemRequest2 = new CartItemRequest(productId2, 10);
        CartItemResponse cartItemResponse1 = extractCartItem(
            AcceptanceFixture.post(cartItemRequest1, "/api/mycarts", header));
        CartItemResponse cartItemResponse2 = extractCartItem(
            AcceptanceFixture.post(cartItemRequest2, "/api/mycarts", header));

        // when
        OrderRequest orderRequest = new OrderRequest(
            List.of(cartItemResponse1.getId(), cartItemResponse2.getId()));
        AcceptanceFixture.post(orderRequest, "/api/myorders", header);

        // then
        ExtractableResponse<Response> response = AcceptanceFixture.get("/api/myorders", header);
        List<OrderResponse> orders = response.jsonPath().getList(".", OrderResponse.class);
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getOrderedProducts().size()).isEqualTo(2);
    }

    @DisplayName("주문 수량이 재고보다 많을 경우 7001번 에러를 반환한다.")
    @Test
    void addOrderOutOfStock() {
        // given
        CartItemRequest cartItemRequest = new CartItemRequest(productId1, 11);
        CartItemResponse cartItemResponse = extractCartItem(
            AcceptanceFixture.post(cartItemRequest, "/api/mycarts", header));

        // when
        OrderRequest orderRequest = new OrderRequest(
            List.of(cartItemResponse.getId()));
        ExtractableResponse<Response> orderResponse = AcceptanceFixture.post(orderRequest, "/api/myorders", header);

        // then
        assertThat(orderResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(extractErrorCode(orderResponse)).isEqualTo(7001);
    }

    @DisplayName("주문 후에 재고가 차감이 되는 지 확인한다.")
    @Test
    void reduceStock() {
        // given
        CartItemRequest cartItemRequest1 = new CartItemRequest(productId1, 8);
        CartItemRequest cartItemRequest2 = new CartItemRequest(productId2, 10);
        CartItemResponse cartItemResponse1 = extractCartItem(
            AcceptanceFixture.post(cartItemRequest1, "/api/mycarts", header));
        CartItemResponse cartItemResponse2 = extractCartItem(
            AcceptanceFixture.post(cartItemRequest2, "/api/mycarts", header));

        // when
        OrderRequest orderRequest = new OrderRequest(
            List.of(cartItemResponse1.getId()));
        AcceptanceFixture.post(orderRequest, "/api/myorders", header);

        // then
        ProductResponse productResponse = AcceptanceFixture.get("/api/products/" + productId1)
            .jsonPath()
            .getObject(".", ProductResponse.class);
        assertThat(productResponse.getStockQuantity()).isEqualTo(2);
    }

    private Header getTokenHeader() {
        final CustomerRequest customerRequest =
            new CustomerRequest("email@email.com", "password1!", "dwoo");
        AcceptanceFixture.post(customerRequest, "/api/customers");

        final TokenRequest tokenRequest = new TokenRequest(customerRequest.getEmail(), customerRequest.getPassword());
        final ExtractableResponse<Response> loginResponse = AcceptanceFixture.post(tokenRequest, "/api/auth/login");

        final String accessToken = extractAccessToken(loginResponse);

        return new Header("Authorization", BEARER + accessToken);
    }

    private int extractErrorCode(ExtractableResponse<Response> response) {
        return response.jsonPath().getInt("errorCode");
    }
}
