package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.acceptance.AuthAcceptanceTestFixture.LOGIN_URI;
import static woowacourse.auth.acceptance.AuthAcceptanceTestFixture.MEMBER_CREATE_REQUEST;
import static woowacourse.auth.acceptance.AuthAcceptanceTestFixture.SIGN_UP_URI;
import static woowacourse.auth.acceptance.AuthAcceptanceTestFixture.VALID_LOGIN_REQUEST;
import static woowacourse.util.HttpRequestUtil.post;
import static woowacourse.util.HttpRequestUtil.postWithAuthorization;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.ui.dto.response.ErrorResponse;
import woowacourse.auth.ui.dto.response.LoginResponse;
import woowacourse.shoppingcart.ui.dto.CartItemAddRequest;

@DisplayName("장바구니 관련 기능")
@Sql(scripts = {"classpath:schema.sql", "classpath:import.sql"})
class CartAcceptanceTest extends AcceptanceTest {

    private static final String CART_URI = "/api/carts/products";
    private static final CartItemAddRequest VALID_CART_ITEM_ADD_REQUEST =
            new CartItemAddRequest(1L, 5);
    private static final CartItemAddRequest INVALID_PRODUCT_ID_CART_ITEM_ADD_REQUEST =
            new CartItemAddRequest(4L, 5);

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
        ExtractableResponse<Response> response = postWithAuthorization(CART_URI, token, VALID_CART_ITEM_ADD_REQUEST);

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

    private static Stream<Arguments> provideNullProductIdAndQuantity() {
        return Stream.of(
                Arguments.of(null, 3),
                Arguments.of(1L, null),
                Arguments.of(null, null)
        );
    }
}
