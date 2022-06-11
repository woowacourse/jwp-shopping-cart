package woowacourse.shoppingcart.cart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.auth.acceptance.AuthRestHandler.회원가입_로그인;
import static woowacourse.shoppingcart.cart.acceptance.CartRestHandler.장바구니담기;
import static woowacourse.shoppingcart.cart.acceptance.CartRestHandler.장바구니삭제;
import static woowacourse.shoppingcart.cart.acceptance.CartRestHandler.장바구니조회;
import static woowacourse.shoppingcart.product.acceptance.ProductRestHandler.assertThatProductException;
import static woowacourse.support.acceptance.RestHandler.extractResponse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.auth.application.dto.response.TokenResponse;
import woowacourse.shoppingcart.cart.application.dto.request.CartDeleteRequest;
import woowacourse.shoppingcart.cart.application.dto.request.CartPutRequest;
import woowacourse.shoppingcart.cart.application.dto.response.CartItemResponse;
import woowacourse.shoppingcart.product.support.exception.ProductExceptionCode;
import woowacourse.support.acceptance.AcceptanceTest;

@DisplayName("장바구니 관련 기능")
class CartAcceptanceTest extends AcceptanceTest {

    private String accessToken;

    @BeforeEach
    void setUp() {
        final TokenResponse tokenResponse = extractResponse(회원가입_로그인(), TokenResponse.class);
        this.accessToken = tokenResponse.getAccessToken();
    }

    @DisplayName("장바구니에 상품을 추가한다")
    @ParameterizedTest
    @CsvSource(value = {"1,1", "2,2"})
    void addCartItem(final long productId, final long quantity) {
        final ExtractableResponse<Response> response = 장바구니담기(productId, new CartPutRequest(quantity), accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        final CartItemResponse cartItemResponse =extractResponse(response, CartItemResponse.class);
        assertAll(
                () -> assertThat(cartItemResponse.getProductId()).isEqualTo(productId),
                () -> assertThat(cartItemResponse.getQuantity()).isEqualTo(quantity)
        );
    }

    @DisplayName("장바구니에 담긴 상품의 수량을 변경한다")
    @ParameterizedTest
    @CsvSource(value = {"1,1", "2,2"})
    void updateQuantityOfCartItem(final long productId, final long quantity) {
        장바구니담기(productId, new CartPutRequest(quantity), accessToken);
        final ExtractableResponse<Response> response = 장바구니담기(productId, new CartPutRequest(quantity), accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        final CartItemResponse cartItemResponse =extractResponse(response, CartItemResponse.class);
        assertAll(
                () -> assertThat(cartItemResponse.getProductId()).isEqualTo(productId),
                () -> assertThat(cartItemResponse.getQuantity()).isEqualTo(quantity)
        );
    }

    @DisplayName("존재하지 않는 상품을 장바구니에 추가한다")
    @Test
    void addCartItemWithNonExistentProduct() {
        final ExtractableResponse<Response> response = 장바구니담기(0, new CartPutRequest(10L), accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThatProductException(response, ProductExceptionCode.NO_SUCH_PRODUCT_EXIST);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니담기(1, new CartPutRequest(3L), accessToken);
        장바구니담기(2, new CartPutRequest(3L), accessToken);
        final ExtractableResponse<Response> response = 장바구니조회(accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(extractResponse(response, List.class)).hasSize(2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        장바구니담기(1, new CartPutRequest(3L), accessToken);

        final ExtractableResponse<Response> response = 장바구니삭제(new CartDeleteRequest(1L), accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
