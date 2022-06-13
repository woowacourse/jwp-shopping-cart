package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static woowacourse.shoppingcart.acceptance.RequestHandler.deleteRequest;
import static woowacourse.shoppingcart.acceptance.RequestHandler.getRequest;
import static woowacourse.shoppingcart.acceptance.RequestHandler.postRequest;
import static woowacourse.shoppingcart.acceptance.RequestHandler.putRequest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.cartItem.CartItemDeleteRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemResponse;
import woowacourse.shoppingcart.dto.cartItem.CartItemUpdateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerRegisterRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private static final String CUSTOMER_EMAIL = "guest@woowa.com";
    private static final String CUSTOMER_NAME = "guest";
    private static final String CUSTOMER_PASSWORD = "qwer1234!@#$";

    private String token;

    @BeforeEach
    void init() {
        postRequest("/customers", new CustomerRegisterRequest(
                CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD));

        final ExtractableResponse<Response> response = postRequest("/auth/login",
                new TokenRequest(CUSTOMER_EMAIL, CUSTOMER_PASSWORD));
        final TokenResponse tokenResponse = response.jsonPath().getObject(".", TokenResponse.class);
        token = tokenResponse.getAccessToken();
    }

    @DisplayName("해당 고객의 CartItem 목록을 조회한다.")
    @Test
    void getCartItems() {
        putRequest("/cart/products/1", new CartItemUpdateRequest(3), token);
        putRequest("/cart/products/2", new CartItemUpdateRequest(5), token);
        ExtractableResponse<Response> response = getRequest("/cart", token);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<CartItemResponse> cartItemResponses = response.jsonPath().get(".");
        assertThat(cartItemResponses).hasSize(2)
                .extracting("productId", "name", "price", "quantity")
                .containsExactly(tuple(1, "우유", 3000, 3), tuple(2, "바나나", 3000, 5));
    }

    @DisplayName("해당 고객의 장바구니에 CartItem을 추가한다.")
    @Test
    void createCartItem() {
        ExtractableResponse<Response> response = putRequest("/cart/products/1",
                new CartItemUpdateRequest(3), token);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        CartItemResponse cartItemResponses = response.jsonPath().getObject(".", CartItemResponse.class);
        assertThat(cartItemResponses)
                .extracting("productId", "name", "price", "quantity")
                .containsExactly(1L, "우유", 3000, 3);
    }

    @DisplayName("해당 고객의 장바구니에 속한 CartItem을 삭제한다.")
    @Test
    void deleteCartItems() {
        putRequest("/cart/products/1", new CartItemUpdateRequest(3), token);
        putRequest("/cart/products/2", new CartItemUpdateRequest(5), token);
        ExtractableResponse<Response> response = getRequest("/cart", token);
        List<CartItemResponse> cartItemResponses = response.jsonPath().get(".");
        assertThat(cartItemResponses).hasSize(2);

        ExtractableResponse<Response> deleteResponse = deleteRequest("/cart",
                new CartItemDeleteRequest(List.of(1L, 2L)), token);

        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        ExtractableResponse<Response> afterDeleteResponse = getRequest("/cart", token);
        List<CartItemResponse> deleteCartItemResponses = afterDeleteResponse.jsonPath().get(".");
        assertThat(deleteCartItemResponses).hasSize(0);
    }
}
