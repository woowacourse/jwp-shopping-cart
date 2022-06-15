package woowacourse.shoppingcart.order.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.auth.acceptance.AuthRestHandler.회원가입_로그인;
import static woowacourse.shoppingcart.cart.acceptance.CartRestHandler.assertThatCartException;
import static woowacourse.shoppingcart.cart.acceptance.CartRestHandler.장바구니담기;
import static woowacourse.shoppingcart.order.acceptance.OrderRestHandler.assertThatOrderException;
import static woowacourse.shoppingcart.order.acceptance.OrderRestHandler.주문;
import static woowacourse.shoppingcart.order.acceptance.OrderRestHandler.주문조회;
import static woowacourse.shoppingcart.product.acceptance.ProductRestHandler.assertThatProductException;
import static woowacourse.support.acceptance.RestHandler.extractResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.auth.application.dto.response.TokenResponse;
import woowacourse.shoppingcart.cart.application.dto.request.CartPutRequest;
import woowacourse.shoppingcart.cart.support.exception.CartExceptionCode;
import woowacourse.shoppingcart.order.application.dto.request.OrderRequest;
import woowacourse.shoppingcart.order.application.dto.response.OrderResponse;
import woowacourse.shoppingcart.order.support.exception.OrderExceptionCode;
import woowacourse.shoppingcart.product.support.exception.ProductExceptionCode;
import woowacourse.support.acceptance.AcceptanceTest;

@DisplayName("주문 관련 기능")
class OrderAcceptanceTest extends AcceptanceTest {

    private String accessToken;

    @BeforeEach
    void setUp() {
        final TokenResponse tokenResponse = extractResponse(회원가입_로그인(), TokenResponse.class);
        this.accessToken = tokenResponse.getAccessToken();

        장바구니담기(1, new CartPutRequest(2L), accessToken);
    }

    @DisplayName("장바구니에 담긴 상품을 주문한다")
    @Test
    void addOrder() {
        final ExtractableResponse<Response> response = 주문(new OrderRequest(1L), accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(extractResponse(response, OrderResponse.class).getOrderDetails()).hasSize(1);
    }

    @DisplayName("전체 상품 목록에 존재하지 않는 상품을 주문한다")
    @Test
    void orderNonExistentProductInAllList() {
        final ExtractableResponse<Response> response = 주문(new OrderRequest(0L), accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThatProductException(response, ProductExceptionCode.NO_SUCH_PRODUCT_EXIST);
    }

    @DisplayName("장바구니에 존재하지 않는 상품을 주문한다")
    @Test
    void orderNonExistentProductInCart() {
        final ExtractableResponse<Response> response = 주문(new OrderRequest(2L), accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThatCartException(response, CartExceptionCode.NO_SUCH_PRODUCT_EXIST);
    }

    @DisplayName("단일 주문을 조회한다")
    @Test
    void getOrder() {
        final ExtractableResponse<Response> orderResponse = 주문(new OrderRequest(1L), accessToken);
        final ExtractableResponse<Response> response =
                주문조회(extractResponse(orderResponse, OrderResponse.class).getId(), accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(extractResponse(response, OrderResponse.class).getOrderDetails()).hasSize(1);
    }

    @DisplayName("존재하지 않는 단일 주문을 조회한다")
    @Test
    void getNonExistentOrder() {
        final ExtractableResponse<Response> response = 주문조회(1, accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThatOrderException(response, OrderExceptionCode.NO_SUCH_ORDER_EXIST);
    }
}
