package woowacourse.shoppingcart.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.SignInRequest;
import woowacourse.shoppingcart.dto.SignInResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CartAcceptanceTest extends AcceptanceTest {
    private static final String EMAIL = "crew01@naver.com";
    private static final String VALID_PASSWORD = "a12345";
    private static final SignInRequest SIGN_IN_REQUEST = new SignInRequest(EMAIL, VALID_PASSWORD);

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void 존재하는_고객의_장바구니를_조회하는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        CartResponse cartResponse = findCustomerCart(accessToken, HttpStatus.OK).as(CartResponse.class);

        assertAll(
                () -> assertThat(cartResponse.getProducts().get(0)
                        .getId()).isEqualTo(1),
                () -> assertThat(cartResponse.getProducts().get(0)
                        .getName()).isEqualTo("맛있는 치킨"),
                () -> assertThat(cartResponse.getProducts().get(0)
                        .getPrice()).isEqualTo(100),
                () -> assertThat(cartResponse.getProducts().get(0)
                        .getQuantity()).isEqualTo(1),
                () -> assertThat(cartResponse.getProducts().get(0)
                        .getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponse.getProducts().get(0)
                        .getCheck()).isTrue(),
                () -> assertThat(cartResponse.getProducts().get(1)
                        .getId()).isEqualTo(3),
                () -> assertThat(cartResponse.getProducts().get(1)
                        .getName()).isEqualTo("맛있는 짜장면"),
                () -> assertThat(cartResponse.getProducts().get(1)
                        .getPrice()).isEqualTo(300),
                () -> assertThat(cartResponse.getProducts().get(1)
                        .getQuantity()).isEqualTo(2),
                () -> assertThat(cartResponse.getProducts().get(1)
                        .getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponse.getProducts().get(1)
                        .getCheck()).isFalse(),
                () -> assertThat(cartResponse.getProducts().get(2)
                        .getId()).isEqualTo(5),
                () -> assertThat(cartResponse.getProducts().get(2)
                        .getName()).isEqualTo("맛있는 짬뽕"),
                () -> assertThat(cartResponse.getProducts().get(2)
                        .getPrice()).isEqualTo(500),
                () -> assertThat(cartResponse.getProducts().get(2)
                        .getQuantity()).isEqualTo(1),
                () -> assertThat(cartResponse.getProducts().get(2)
                        .getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponse.getProducts().get(2)
                        .getCheck()).isTrue()
        );
    }

    @Test
    void 없는_사용자의_장바구니를_조회하는_경우() {
        String accessToken = jwtTokenProvider.createToken("alpha");

        var extract = findCustomerCart(accessToken, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 존재하지 않는 장바구니입니다.");
    }
}
