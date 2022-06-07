package woowacourse.shoppingcart.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.application.dto.TokenResponse;
import woowacourse.shoppingcart.application.dto.CartResponse;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.ResponseCreator.*;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {


    private TokenResponse tokenResponse;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        postCustomers("email@email.com", "password123@Q", "zero");
        ExtractableResponse<Response> 로그인_응답됨 = postLogin("email@email.com", "password123@Q");
        tokenResponse = 로그인_응답됨.as(TokenResponse.class);

        postProduct("치킨", 10_000, "http://example.com/chicken.jpg");
        postProduct("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        // given & when
        ExtractableResponse<Response> response = postCart(tokenResponse, 1L);

        // then
        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        // given
        postCart(tokenResponse, 1L);
        postCart(tokenResponse, 2L);

        // when
        ExtractableResponse<Response> response = ResponseCreator.getCarts(tokenResponse);

        // given
        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, 1L, 2L);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        // given
        postCart(tokenResponse, 1L);

        // when
        ExtractableResponse<Response> response = deleteCart(tokenResponse, 1L);

        // then
        장바구니_삭제됨(response);
    }

    @DisplayName("장바구니 품목 수량 변경")
    @Test
    void updateCartItem() {
        // given
        postCart(tokenResponse, 1L);

        // when
        ExtractableResponse<Response> response = updateCart(tokenResponse, 1L, 2);

        // then
        장바구니_변경됨(response);
    }

    private static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", CartResponse.class).stream()
                .map(CartResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    private static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private static void 장바구니_변경됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
