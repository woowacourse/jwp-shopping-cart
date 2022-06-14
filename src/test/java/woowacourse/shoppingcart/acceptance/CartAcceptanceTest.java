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
import woowacourse.shoppingcart.dto.CartDeleteRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CartSaveRequest;
import woowacourse.shoppingcart.dto.CartUpdateRequest;
import java.util.List;

@DisplayName("장바구니 관련 기능")
@Sql("/testData.sql")
public class CartAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "sun@gmail.com";
    private static final String PASSWORD = "12345678";
    private static final Long productId1 = 1L;
    private static final Long productId2 = 2L;

    private String accessToken;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        // 토큰 발급
        accessToken = requestPostWithBody("/api/login", new TokenRequest(EMAIL, PASSWORD)).as(TokenResponse.class).getAccessToken();
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        // given, when
        final ExtractableResponse<Response> response = requestPostWithTokenAndBody("/api/customer/carts", accessToken, new CartSaveRequest(productId1, 1));

        // then
        assertAll(() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()), () -> assertThat(response.header("Location")).isNotBlank());
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        // given
        requestPostWithTokenAndBody("/api/customer/carts", accessToken, new CartSaveRequest(productId1, 1));
        requestPostWithTokenAndBody("/api/customer/carts", accessToken, new CartSaveRequest(productId2, 10));

        // when
        final ExtractableResponse<Response> response =
                requestGetWithTokenAndBody("/api/customer/carts", accessToken);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList(".", CartResponse.class)).usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(List.of(
                                new CartResponse(null, productId1, "치킨", 10_000, "http://example.com/chicken.jpg", 1),
                                new CartResponse(null, productId2, "맥주", 20_000, "http://example.com/beer.jpg", 10)
                        ))
        );
    }

    @DisplayName("장바구니 수량 변경")
    @Test
    void update() {
        // given
        requestPostWithTokenAndBody("/api/customer/carts", accessToken, new CartSaveRequest(productId1, 1));

        // when
        final ExtractableResponse<Response> response =
                requestPutWithTokenAndBody("/api/customer/carts", accessToken, new CartUpdateRequest(1L, 100));

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

    }

    @DisplayName("장바구니 단일 품목 삭제")
    @Test
    void deleteSingleCartItem() {
        // given
        final ExtractableResponse<Response> createResponse =
                requestPostWithTokenAndBody("/api/customer/carts", accessToken, new CartSaveRequest(1L, 10));
        final long cartId = parseLong(createResponse.header("Location").split("/carts/")[1]);

        // when
        final ExtractableResponse<Response> response =
                requestDeleteWithTokenAndBody("/api/customer/carts/", accessToken, new CartDeleteRequest(List.of(cartId)));

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("장바구니 여러 품목 삭제")
    @Test
    void deleteSeveralCartItem() {
        // given
        final ExtractableResponse<Response> createResponse1 =
                requestPostWithTokenAndBody("/api/customer/carts", accessToken, new CartSaveRequest(1L, 10));
        final ExtractableResponse<Response> createResponse2 =
                requestPostWithTokenAndBody("/api/customer/carts", accessToken, new CartSaveRequest(2L, 10));
        final long cartId1 = parseLong(createResponse1.header("Location").split("/carts/")[1]);
        final long cartId2 = parseLong(createResponse2.header("Location").split("/carts/")[1]);

        // when
        final ExtractableResponse<Response> response =
                requestDeleteWithTokenAndBody("/api/customer/carts", accessToken, new CartDeleteRequest(List.of(cartId1, cartId2)));
        final ExtractableResponse<Response> getResponse = requestGetWithTokenAndBody("/api/customer/carts", accessToken);
        final int size = getResponse.jsonPath().getList(".", CartResponse.class).size();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(size).isEqualTo(0)
        );
    }
}
