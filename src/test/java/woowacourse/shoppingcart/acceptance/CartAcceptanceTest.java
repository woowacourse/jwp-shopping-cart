package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.acceptance.AuthAcceptanceTestFixture.LOGIN_URI;
import static woowacourse.auth.acceptance.AuthAcceptanceTestFixture.MEMBER_CREATE_REQUEST;
import static woowacourse.auth.acceptance.AuthAcceptanceTestFixture.SIGN_UP_URI;
import static woowacourse.auth.acceptance.AuthAcceptanceTestFixture.VALID_LOGIN_REQUEST;
import static woowacourse.util.HttpRequestUtil.deleteWithAuthorization;
import static woowacourse.util.HttpRequestUtil.getWithAuthorization;
import static woowacourse.util.HttpRequestUtil.patchWithAuthorization;
import static woowacourse.util.HttpRequestUtil.post;
import static woowacourse.util.HttpRequestUtil.postWithAuthorization;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.response.ErrorResponse;
import woowacourse.auth.dto.response.LoginResponse;
import woowacourse.shoppingcart.dto.CartItemAddRequest;
import woowacourse.shoppingcart.dto.CartItemQuantityUpdateRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;

@DisplayName("장바구니 관련 기능")
@Sql(scripts = {"classpath:schema.sql", "classpath:import.sql"})
class CartAcceptanceTest extends AcceptanceTest {

    private static final String CART_URI = "/api/carts/products";
    private static final CartItemAddRequest VALID_CART_ITEM_ADD_REQUEST1 =
            new CartItemAddRequest(1L, 5);
    private static final CartItemAddRequest VALID_CART_ITEM_ADD_REQUEST2 =
            new CartItemAddRequest(2L, 8);
    private static final CartItemAddRequest INVALID_PRODUCT_ID_CART_ITEM_ADD_REQUEST =
            new CartItemAddRequest(4L, 5);
    private static final CartItemQuantityUpdateRequest VALID_CART_ITEM_QUANTITY_UPDATE_REQUEST =
            new CartItemQuantityUpdateRequest(1L, 8);

    private String token;

    @BeforeEach
    void login() {
        post(SIGN_UP_URI, MEMBER_CREATE_REQUEST);
        token = post(LOGIN_URI, VALID_LOGIN_REQUEST).as(LoginResponse.class)
                .getToken();
    }

    @DisplayName("장바구니에 상품 추가에 성공하면 201을 응답한다.")
    @Test
    void addCartItem_created() {
        ExtractableResponse<Response> response = postWithAuthorization(CART_URI, token, VALID_CART_ITEM_ADD_REQUEST1);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("존재하지 않는 상품을 추가하려 하면 400을 응답한다.")
    @Test
    void addCartItem_badRequest_InvalidProductId() {
        ExtractableResponse<Response> response =
                postWithAuthorization(CART_URI, token, INVALID_PRODUCT_ID_CART_ITEM_ADD_REQUEST);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(message).isEqualTo("존재하지 않는 상품입니다.")
        );
    }

    @DisplayName("null인 상품 id 혹은 null인 상품 수량으로 장바구니에 상품을 추가하려 하면 400을 응답한다.")
    @ParameterizedTest
    @MethodSource("provideNullProductIdAndQuantity")
    void addCartItem_badRequest_Null(Long productId, Integer quantity) {
        ExtractableResponse<Response> response =
                postWithAuthorization(CART_URI, token, new CartItemAddRequest(productId, quantity));
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(message).isEqualTo("입력하지 않은 정보가 있습니다.")
        );
    }

    @DisplayName("상품의 재고보다 많은 양을 구매하려고 하면 400을 응답한다.")
    @Test
    void addCartItem_badRequest_InvalidQuantity() {
        int invalidQuantity = 101;
        ExtractableResponse<Response> response =
                postWithAuthorization(CART_URI, token, new CartItemAddRequest(1L, invalidQuantity));
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(message).isEqualTo("상품 재고가 부족합니다.")
        );
    }

    @DisplayName("장바구니 목록을 반환한다.")
    @Test
    void findAllCartItems_OK() {
        postWithAuthorization(CART_URI, token, VALID_CART_ITEM_ADD_REQUEST1);
        postWithAuthorization(CART_URI, token, VALID_CART_ITEM_ADD_REQUEST2);

        ExtractableResponse<Response> response = getWithAuthorization(CART_URI, token);
        List<CartItemResponse> cartItems = response.jsonPath()
                .getList(".", CartItemResponse.class);
        CartItemResponse first = cartItems.get(0);
        CartItemResponse second = cartItems.get(1);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(cartItems).hasSize(2),
                () -> assertThat(first.getProduct().getId()).isEqualTo(1L),
                () -> assertThat(first.getQuantity()).isEqualTo(5),
                () -> assertThat(second.getProduct().getId()).isEqualTo(2L),
                () -> assertThat(second.getQuantity()).isEqualTo(8)
        );
    }

    @DisplayName("상품의 수량을 변경하고 성공하면 200과 변경된 장바구니 목록을 반환한다.")
    @Test
    void updateQuantity_OK() {
        postWithAuthorization(CART_URI, token, VALID_CART_ITEM_ADD_REQUEST1);

        ExtractableResponse<Response> response = patchWithAuthorization(CART_URI, token,
                VALID_CART_ITEM_QUANTITY_UPDATE_REQUEST);

        CartItemResponse updatedCartItem = response.jsonPath()
                .getList(".", CartItemResponse.class)
                .get(0);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(updatedCartItem.getQuantity()).isEqualTo(8)
        );
    }

    @DisplayName("상품의 재고보다 많은 양으로 수량을 변경하려고 하면 400을 응답한다.")
    @Test
    void updateQuantity_badRequest_InvalidQuantity() {
        postWithAuthorization(CART_URI, token, VALID_CART_ITEM_ADD_REQUEST1);
        int invalidQuantity = 101;
        ExtractableResponse<Response> response =
                patchWithAuthorization(CART_URI, token, new CartItemQuantityUpdateRequest(1L, invalidQuantity));
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(message).isEqualTo("상품 재고가 부족합니다.")
        );
    }

    @DisplayName("null인 상품 id 혹은 null인 상품 수량으로 장바구니에 상품 수량을 변경하려하면 400을 응답한다.")
    @ParameterizedTest
    @MethodSource("provideNullProductIdAndQuantity")
    void updateQuantity_badRequest_Null(Long productId, Integer quantity) {
        ExtractableResponse<Response> response =
                patchWithAuthorization(CART_URI, token, new CartItemQuantityUpdateRequest(productId, quantity));
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(message).isEqualTo("입력하지 않은 정보가 있습니다.")
        );
    }

    @DisplayName("상품을 삭제하고 성공하면 200과 삭제된 장바구니 목록을 응답한다.")
    @Test
    void deleteCartItem_OK() {
        postWithAuthorization(CART_URI, token, VALID_CART_ITEM_ADD_REQUEST1);

        ExtractableResponse<Response> response = deleteWithAuthorization(CART_URI + "?id=1", token);
        List<CartItemResponse> cartItems = response.jsonPath()
                .getList(".", CartItemResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(cartItems).isEmpty()
        );
    }

    private static Stream<Arguments> provideNullProductIdAndQuantity() {
        return Stream.of(
                Arguments.of(null, 3),
                Arguments.of(1L, null),
                Arguments.of(null, null)
        );
    }
}
