package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.AcceptanceTestFixture.deleteMethodRequestWithBearerAuth;
import static woowacourse.AcceptanceTestFixture.getMethodRequestWithBearerAuth;
import static woowacourse.AcceptanceTestFixture.join;
import static woowacourse.AcceptanceTestFixture.login;
import static woowacourse.AcceptanceTestFixture.patchMethodRequestWithBearerAuth;
import static woowacourse.AcceptanceTestFixture.postMethodRequest;
import static woowacourse.AcceptanceTestFixture.postMethodRequestWithBearerAuth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.cartitem.dto.CartItemQuantityRequest;
import woowacourse.shoppingcart.cartitem.dto.CartItemRequest;
import woowacourse.shoppingcart.cartitem.dto.CartItemResponse;
import woowacourse.shoppingcart.cartitem.dto.DeleteCartItemRequest;
import woowacourse.shoppingcart.product.dto.ProductRequest;
import woowacourse.shoppingcart.product.dto.ThumbnailImageDto;

@DisplayName("장바구니 관련 기능")
public class CartItemAcceptanceTest extends AcceptanceTest {
    private String token;
    private Long productId1;
    private Long productId2;

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

        productId1 = Long.parseLong(
                postMethodRequest(productRequest1, "/api/products").header("Location").split("/products/")[1]);
        productId2 = Long.parseLong(
                postMethodRequest(productRequest2, "/api/products").header("Location").split("/products/")[1]);
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        final CartItemRequest cartItemRequest = new CartItemRequest(productId1, 1);
        final ExtractableResponse<Response> response = postMethodRequestWithBearerAuth(cartItemRequest, token,
                "/api/mycarts");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("Location")).isNotBlank()
        );
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        final CartItemRequest cartItemRequest1 = new CartItemRequest(productId1, 1);
        final CartItemRequest cartItemRequest2 = new CartItemRequest(productId2, 1);
        postMethodRequestWithBearerAuth(cartItemRequest1, token, "/api/mycarts");
        postMethodRequestWithBearerAuth(cartItemRequest2, token, "/api/mycarts");

        final ExtractableResponse<Response> response = getMethodRequestWithBearerAuth(token,
                "/api/mycarts");

        final List<CartItemResponse> cartItemResponses = response.jsonPath().getList(".", CartItemResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(cartItemResponses.size()).isEqualTo(2)
        );
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        final CartItemRequest cartItemRequest1 = new CartItemRequest(productId1, 1);
        final CartItemRequest cartItemRequest2 = new CartItemRequest(productId2, 1);
        final Long cartItemId1 = Long.parseLong(postMethodRequestWithBearerAuth(cartItemRequest1, token,
                "/api/mycarts").header("Location").split("/mycarts/")[1]);
        final Long cartItemId2 = Long.parseLong(postMethodRequestWithBearerAuth(cartItemRequest2, token,
                "/api/mycarts").header("Location").split("/mycarts/")[1]);

        final DeleteCartItemRequest deleteCartItemRequest = new DeleteCartItemRequest(
                List.of(cartItemId1, cartItemId2));
        final ExtractableResponse<Response> response = deleteMethodRequestWithBearerAuth(deleteCartItemRequest, token,
                "/api/mycarts");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("장바구니 수량 변경")
    @Test
    void updateCartItemQuantity() {
        final CartItemRequest cartItemRequest = new CartItemRequest(productId1, 1);
        final Long cartItemId = Long.parseLong(postMethodRequestWithBearerAuth(cartItemRequest, token,
                "/api/mycarts").header("Location").split("/mycarts/")[1]);

        final CartItemQuantityRequest cartItemQuantityRequest = new CartItemQuantityRequest(cartItemId, 3);
        final ExtractableResponse<Response> response = patchMethodRequestWithBearerAuth(cartItemQuantityRequest, token,
                "/api/mycarts");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
