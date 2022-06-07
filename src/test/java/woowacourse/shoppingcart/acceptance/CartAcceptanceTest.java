package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static woowacourse.auth.acceptance.AuthAcceptanceTest.*;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ThumbnailImageDto;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    private static final String USER = "puterism";
    private Header header;
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        ProductRequest productRequest1 = new ProductRequest("치킨", 10_000, 10,
            new ThumbnailImageDto("http://example.com/chicken.jpg", "이미지입니다."));
        ProductRequest productRequest2 = new ProductRequest("맥주", 20_000, 10,
            new ThumbnailImageDto("http://example.com/beer.jpg", "이미지입니다."));

        header = getTokenHeader();
        productId1 = getAddedProductId(productRequest1);
        productId2 = getAddedProductId(productRequest2);
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        // given
        CartItemRequest cartItemRequest = new CartItemRequest(productId1, 10);

        // when
        ExtractableResponse<Response> responseAddedCartItem = AcceptanceFixture.post(cartItemRequest, "/api/mycarts",
            header);
        CartItemResponse cartItemResponse = responseAddedCartItem.jsonPath().getObject(".", CartItemResponse.class);

        // then
        assertThat(responseAddedCartItem.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(responseAddedCartItem.header("Location")).isEqualTo("/api/mycarts/" + cartItemResponse.getId());
        assertThat(cartItemResponse)
            .extracting("productId", "price", "name", "quantity")
            .containsExactly(cartItemRequest.getProductId(), 10_000, "치킨",
                cartItemRequest.getQuantity());
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        getAddedCartItemId(USER, productId1);
        getAddedCartItemId(USER, productId2);

        ExtractableResponse<Response> response = AcceptanceFixture.get("/api/customers/" + USER + "/carts");
        List<Long> resultProductIds = response.jsonPath().getList(".", Cart.class).stream()
            .map(Cart::getProductId)
            .collect(Collectors.toList());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(resultProductIds).contains(productId1, productId2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        Long cartId = getAddedCartItemId(USER, productId1);

        ExtractableResponse<Response> response = AcceptanceFixture.delete(
            "/api/customers/" + USER + "/carts/" + cartId);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static ExtractableResponse<Response> requestToAddCartItem(String userName, Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);

        return AcceptanceFixture.post(requestBody, "/api/customers/" + userName + "/carts");
    }

    public static Long getAddedCartItemId(String userName, Long productId) {
        ExtractableResponse<Response> response = requestToAddCartItem(userName, productId);
        return Long.parseLong(response.header("Location").split("/carts/")[1]);
    }

    private Header getTokenHeader() {
        final CustomerRequest customerRequest =
            new CustomerRequest("email@email.com", "password1!", "dwoo");
        AcceptanceFixture.post(customerRequest, "/api/customers");

        final TokenRequest tokenRequest = new TokenRequest(customerRequest.getEmail(), customerRequest.getPassword());
        final ExtractableResponse<Response> loginResponse = AcceptanceFixture.post(tokenRequest, "/api/auth/login");

        // when
        final String accessToken = extractAccessToken(loginResponse);

        return new Header("Authorization", BEARER + accessToken);
    }
}
