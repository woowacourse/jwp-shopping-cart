package woowacourse.shoppingcart.acceptance;

import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.*;

import java.util.List;

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

        ExtractableResponse extract = findCustomerCart(accessToken, HttpStatus.OK);

        List<CartResponseElement> cartResponseElements = extract.body().jsonPath().getList("products", CartResponseElement.class);
        assertAll(() -> assertThat(cartResponseElements.get(0).getId()).isEqualTo(1L),
                () -> assertThat(cartResponseElements.get(0).getName()).isEqualTo("맛있는 치킨"),
                () -> assertThat(cartResponseElements.get(0).getPrice()).isEqualTo(100),
                () -> assertThat(cartResponseElements.get(0).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(0).getQuantity()).isEqualTo(1),
                () -> assertThat(cartResponseElements.get(0).getCheck()).isTrue(),

                () -> assertThat(cartResponseElements.get(1).getId()).isEqualTo(2L),
                () -> assertThat(cartResponseElements.get(1).getName()).isEqualTo("맛있는 짜장면"),
                () -> assertThat(cartResponseElements.get(1).getPrice()).isEqualTo(300),
                () -> assertThat(cartResponseElements.get(1).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(1).getQuantity()).isEqualTo(2),
                () -> assertThat(cartResponseElements.get(1).getCheck()).isFalse(),

                () -> assertThat(cartResponseElements.get(2).getId()).isEqualTo(3L),
                () -> assertThat(cartResponseElements.get(2).getName()).isEqualTo("맛있는 짬뽕"),
                () -> assertThat(cartResponseElements.get(2).getPrice()).isEqualTo(500),
                () -> assertThat(cartResponseElements.get(2).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(2).getQuantity()).isEqualTo(1),
                () -> assertThat(cartResponseElements.get(2).getCheck()).isTrue()
        );
    }

    @Test
    void 없는_사용자의_장바구니를_조회하는_경우() {
        String accessToken = jwtTokenProvider.createToken("alpha");

        var extract = findCustomerCart(accessToken, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 존재하지 않는 장바구니입니다.");
    }

    @Test
    void 사용자의_장바구니에_존재하는_상품을_더_담는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest(1L, 3, true);
        createCartItem(accessToken, addCartItemRequest, HttpStatus.CREATED);
        ExtractableResponse extract = findCustomerCart(accessToken, HttpStatus.OK);

        List<CartResponseElement> cartResponseElements = extract.body().jsonPath().getList("products", CartResponseElement.class);
        assertAll(() -> assertThat(cartResponseElements.get(0).getId()).isEqualTo(1L),
                () -> assertThat(cartResponseElements.get(0).getName()).isEqualTo("맛있는 치킨"),
                () -> assertThat(cartResponseElements.get(0).getPrice()).isEqualTo(100),
                () -> assertThat(cartResponseElements.get(0).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(0).getQuantity()).isEqualTo(4),
                () -> assertThat(cartResponseElements.get(0).getCheck()).isTrue(),

                () -> assertThat(cartResponseElements.get(1).getId()).isEqualTo(2L),
                () -> assertThat(cartResponseElements.get(1).getName()).isEqualTo("맛있는 짜장면"),
                () -> assertThat(cartResponseElements.get(1).getPrice()).isEqualTo(300),
                () -> assertThat(cartResponseElements.get(1).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(1).getQuantity()).isEqualTo(2),
                () -> assertThat(cartResponseElements.get(1).getCheck()).isFalse(),

                () -> assertThat(cartResponseElements.get(2).getId()).isEqualTo(3L),
                () -> assertThat(cartResponseElements.get(2).getName()).isEqualTo("맛있는 짬뽕"),
                () -> assertThat(cartResponseElements.get(2).getPrice()).isEqualTo(500),
                () -> assertThat(cartResponseElements.get(2).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(2).getQuantity()).isEqualTo(1),
                () -> assertThat(cartResponseElements.get(2).getCheck()).isTrue()
        );
    }

    @Test
    void 사용자의_장바구니에_존재하지_않는_상품을_담는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest(2L, 3, true);
        createCartItem(accessToken, addCartItemRequest, HttpStatus.CREATED);
        ExtractableResponse extract = findCustomerCart(accessToken, HttpStatus.OK);
        List<CartResponseElement> cartResponseElements = extract.body().jsonPath().getList("products", CartResponseElement.class);
        assertAll(() -> assertThat(cartResponseElements.get(0).getId()).isEqualTo(1L),
                () -> assertThat(cartResponseElements.get(0).getName()).isEqualTo("맛있는 치킨"),
                () -> assertThat(cartResponseElements.get(0).getPrice()).isEqualTo(100),
                () -> assertThat(cartResponseElements.get(0).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(0).getQuantity()).isEqualTo(1),
                () -> assertThat(cartResponseElements.get(0).getCheck()).isTrue(),

                () -> assertThat(cartResponseElements.get(1).getId()).isEqualTo(2L),
                () -> assertThat(cartResponseElements.get(1).getName()).isEqualTo("맛있는 짜장면"),
                () -> assertThat(cartResponseElements.get(1).getPrice()).isEqualTo(300),
                () -> assertThat(cartResponseElements.get(1).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(1).getQuantity()).isEqualTo(2),
                () -> assertThat(cartResponseElements.get(1).getCheck()).isFalse(),

                () -> assertThat(cartResponseElements.get(2).getId()).isEqualTo(3L),
                () -> assertThat(cartResponseElements.get(2).getName()).isEqualTo("맛있는 짬뽕"),
                () -> assertThat(cartResponseElements.get(2).getPrice()).isEqualTo(500),
                () -> assertThat(cartResponseElements.get(2).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(2).getQuantity()).isEqualTo(1),
                () -> assertThat(cartResponseElements.get(2).getCheck()).isTrue(),

                () -> assertThat(cartResponseElements.get(3).getId()).isEqualTo(4L),
                () -> assertThat(cartResponseElements.get(3).getName()).isEqualTo("맛있는 족발"),
                () -> assertThat(cartResponseElements.get(3).getPrice()).isEqualTo(200),
                () -> assertThat(cartResponseElements.get(3).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(3).getQuantity()).isEqualTo(3),
                () -> assertThat(cartResponseElements.get(3).getCheck()).isTrue()
        );
    }
}
