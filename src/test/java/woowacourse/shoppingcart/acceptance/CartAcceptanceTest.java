package woowacourse.shoppingcart.acceptance;

import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Sql(scripts = {"classpath:testSchema.sql", "classpath:testCartData.sql"})
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

        List<CartResponseElement> cartResponseElements = extract.body().jsonPath().getList("cartItems", CartResponseElement.class);
        assertAll(() -> assertThat(cartResponseElements.get(0).getId()).isEqualTo(1L),
                () -> assertThat(cartResponseElements.get(0).getName()).isEqualTo("맛있는 치킨"),
                () -> assertThat(cartResponseElements.get(0).getPrice()).isEqualTo(100),
                () -> assertThat(cartResponseElements.get(0).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(0).getQuantity()).isEqualTo(1),
                () -> assertThat(cartResponseElements.get(0).getChecked()).isTrue(),

                () -> assertThat(cartResponseElements.get(1).getId()).isEqualTo(2L),
                () -> assertThat(cartResponseElements.get(1).getName()).isEqualTo("맛있는 짜장면"),
                () -> assertThat(cartResponseElements.get(1).getPrice()).isEqualTo(300),
                () -> assertThat(cartResponseElements.get(1).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(1).getQuantity()).isEqualTo(2),
                () -> assertThat(cartResponseElements.get(1).getChecked()).isFalse(),

                () -> assertThat(cartResponseElements.get(2).getId()).isEqualTo(3L),
                () -> assertThat(cartResponseElements.get(2).getName()).isEqualTo("맛있는 짬뽕"),
                () -> assertThat(cartResponseElements.get(2).getPrice()).isEqualTo(500),
                () -> assertThat(cartResponseElements.get(2).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(2).getQuantity()).isEqualTo(1),
                () -> assertThat(cartResponseElements.get(2).getChecked()).isTrue()
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

        List<CartResponseElement> cartResponseElements = extract.body().jsonPath().getList("cartItems", CartResponseElement.class);
        assertAll(() -> assertThat(cartResponseElements.get(0).getId()).isEqualTo(1L),
                () -> assertThat(cartResponseElements.get(0).getName()).isEqualTo("맛있는 치킨"),
                () -> assertThat(cartResponseElements.get(0).getPrice()).isEqualTo(100),
                () -> assertThat(cartResponseElements.get(0).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(0).getQuantity()).isEqualTo(4),
                () -> assertThat(cartResponseElements.get(0).getChecked()).isTrue(),

                () -> assertThat(cartResponseElements.get(1).getId()).isEqualTo(2L),
                () -> assertThat(cartResponseElements.get(1).getName()).isEqualTo("맛있는 짜장면"),
                () -> assertThat(cartResponseElements.get(1).getPrice()).isEqualTo(300),
                () -> assertThat(cartResponseElements.get(1).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(1).getQuantity()).isEqualTo(2),
                () -> assertThat(cartResponseElements.get(1).getChecked()).isFalse(),

                () -> assertThat(cartResponseElements.get(2).getId()).isEqualTo(3L),
                () -> assertThat(cartResponseElements.get(2).getName()).isEqualTo("맛있는 짬뽕"),
                () -> assertThat(cartResponseElements.get(2).getPrice()).isEqualTo(500),
                () -> assertThat(cartResponseElements.get(2).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(2).getQuantity()).isEqualTo(1),
                () -> assertThat(cartResponseElements.get(2).getChecked()).isTrue()
        );
    }

    @Test
    void 사용자의_장바구니에_존재하지_않는_상품을_담는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest(2L, 3, true);

        createCartItem(accessToken, addCartItemRequest, HttpStatus.CREATED);
        ExtractableResponse extract = findCustomerCart(accessToken, HttpStatus.OK);

        List<CartResponseElement> cartResponseElements = extract.body().jsonPath().getList("cartItems", CartResponseElement.class);
        assertAll(() -> assertThat(cartResponseElements.get(0).getId()).isEqualTo(1L),
                () -> assertThat(cartResponseElements.get(0).getName()).isEqualTo("맛있는 치킨"),
                () -> assertThat(cartResponseElements.get(0).getPrice()).isEqualTo(100),
                () -> assertThat(cartResponseElements.get(0).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(0).getQuantity()).isEqualTo(1),
                () -> assertThat(cartResponseElements.get(0).getChecked()).isTrue(),

                () -> assertThat(cartResponseElements.get(1).getId()).isEqualTo(2L),
                () -> assertThat(cartResponseElements.get(1).getName()).isEqualTo("맛있는 짜장면"),
                () -> assertThat(cartResponseElements.get(1).getPrice()).isEqualTo(300),
                () -> assertThat(cartResponseElements.get(1).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(1).getQuantity()).isEqualTo(2),
                () -> assertThat(cartResponseElements.get(1).getChecked()).isFalse(),

                () -> assertThat(cartResponseElements.get(2).getId()).isEqualTo(3L),
                () -> assertThat(cartResponseElements.get(2).getName()).isEqualTo("맛있는 짬뽕"),
                () -> assertThat(cartResponseElements.get(2).getPrice()).isEqualTo(500),
                () -> assertThat(cartResponseElements.get(2).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(2).getQuantity()).isEqualTo(1),
                () -> assertThat(cartResponseElements.get(2).getChecked()).isTrue(),

                () -> assertThat(cartResponseElements.get(3).getId()).isEqualTo(4L),
                () -> assertThat(cartResponseElements.get(3).getName()).isEqualTo("맛있는 족발"),
                () -> assertThat(cartResponseElements.get(3).getPrice()).isEqualTo(200),
                () -> assertThat(cartResponseElements.get(3).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(3).getQuantity()).isEqualTo(3),
                () -> assertThat(cartResponseElements.get(3).getChecked()).isTrue()
        );
    }

    @Test
    void 존재하지_않는_사용자의_장바구니에_상품을_추가하는_경우() {
        String accessToken = jwtTokenProvider.createToken("alpha");
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest(2L, 3, true);

        ExtractableResponse extract = createCartItem(accessToken, addCartItemRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 존재하지 않는 장바구니입니다.");
    }

    @Test
    void 자연수가_아닌_ID로_상품을_추가하는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest(0L, 3, true);

        ExtractableResponse extract = createCartItem(accessToken, addCartItemRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 상품 ID는 자연수여야 합니다.");
    }

    @Test
    void 자연수가_아닌_수량으로_상품을_추가하는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest(1L, 0, true);

        ExtractableResponse extract = createCartItem(accessToken, addCartItemRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 상품 수는 자연수여야 합니다.");
    }

    @Test
    void 존재하지_않는_상품으로_장바구니에_상품을_추가하는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest(100L, 0, true);

        ExtractableResponse extract = createCartItem(accessToken, addCartItemRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 존재하는 상품이 아닙니다.");
    }

    @Test
    void 사용자의_장바구니_중_일부_항목을_수정하는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();
        UpdateCartItemRequest updateCartItemRequest = new UpdateCartItemRequest(List.of(new UpdateCartItemElement(3L, 10, false)));

        ExtractableResponse extract = updateCartItem(accessToken, updateCartItemRequest, HttpStatus.OK);

        List<CartResponseElement> cartResponseElements = extract.body().jsonPath().getList("cartItems", CartResponseElement.class);
        assertAll(() -> assertThat(cartResponseElements.get(0).getId()).isEqualTo(3L),
                () -> assertThat(cartResponseElements.get(0).getName()).isEqualTo("맛있는 짬뽕"),
                () -> assertThat(cartResponseElements.get(0).getPrice()).isEqualTo(500),
                () -> assertThat(cartResponseElements.get(0).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(0).getQuantity()).isEqualTo(10),
                () -> assertThat(cartResponseElements.get(0).getChecked()).isFalse()
        );
    }

    @Test
    void 자연수가_아닌_항목_ID로_일부_항목을_수정하는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();
        UpdateCartItemRequest updateCartItemRequest = new UpdateCartItemRequest(List.of(new UpdateCartItemElement(0L, 10, false)));

        ExtractableResponse extract = updateCartItem(accessToken, updateCartItemRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 항목 ID는 자연수여야 합니다.");
    }

    @Test
    void 장바구니에_없는_항목을_수정하는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();
        UpdateCartItemRequest updateCartItemRequest = new UpdateCartItemRequest(List.of(new UpdateCartItemElement(5L, 10, false)));

        ExtractableResponse extract = updateCartItem(accessToken, updateCartItemRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 장바구니에 없는 상품이 있습니다.");
    }

    @Test
    void 자연수가_아닌_수량으로_수정하는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();
        UpdateCartItemRequest updateCartItemRequest = new UpdateCartItemRequest(List.of(new UpdateCartItemElement(1L, 0, false)));

        ExtractableResponse extract = updateCartItem(accessToken, updateCartItemRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 상품 수는 자연수여야 합니다.");
    }

    @Test
    void 존재하지_않는_사용자의_장바구니로_수정하는_경우() {
        String accessToken = jwtTokenProvider.createToken("alpha");
        UpdateCartItemRequest updateCartItemRequest = new UpdateCartItemRequest(List.of(new UpdateCartItemElement(1L, 0, false)));

        ExtractableResponse extract = updateCartItem(accessToken, updateCartItemRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 존재하지 않는 장바구니입니다.");
    }

    @Test
    void 사용자의_장바구니_중_일부_항목을_삭제하는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();
        DeleteCartItemRequest deleteCartItemRequest = new DeleteCartItemRequest(List.of(new DeleteCartItemElement(1L), new DeleteCartItemElement(3L)));

        deleteCartItem(accessToken, deleteCartItemRequest, HttpStatus.NO_CONTENT);
        ExtractableResponse extract = findCustomerCart(accessToken, HttpStatus.OK);

        List<CartResponseElement> cartResponseElements = extract.body().jsonPath().getList("cartItems", CartResponseElement.class);
        assertAll(() -> assertThat(cartResponseElements.get(0).getId()).isEqualTo(2L),
                () -> assertThat(cartResponseElements.get(0).getName()).isEqualTo("맛있는 짜장면"),
                () -> assertThat(cartResponseElements.get(0).getPrice()).isEqualTo(300),
                () -> assertThat(cartResponseElements.get(0).getImageUrl()).isEqualTo("https://www.naver.com"),
                () -> assertThat(cartResponseElements.get(0).getQuantity()).isEqualTo(2),
                () -> assertThat(cartResponseElements.get(0).getChecked()).isFalse()
        );
    }

    @Test
    void 자연수가_아닌_항목_ID로_일부_항목을_삭제하는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();
        DeleteCartItemRequest deleteCartItemRequest = new DeleteCartItemRequest(List.of(new DeleteCartItemElement(0L), new DeleteCartItemElement(3L)));

        ExtractableResponse extract = deleteCartItem(accessToken, deleteCartItemRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 항목 ID는 자연수여야 합니다.");
    }

    @Test
    void 장바구니에_존재하지_않는_일부_항목을_삭제하는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();
        DeleteCartItemRequest deleteCartItemRequest = new DeleteCartItemRequest(List.of(new DeleteCartItemElement(7L), new DeleteCartItemElement(3L)));

        ExtractableResponse extract = deleteCartItem(accessToken, deleteCartItemRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 장바구니에 없는 상품이 있습니다.");
    }

    @Test
    void 존재하지_않는_사용자의_장바구니에_일부_항목을_삭제하는_경우() {
        String accessToken = jwtTokenProvider.createToken("alpha");
        DeleteCartItemRequest deleteCartItemRequest = new DeleteCartItemRequest(List.of(new DeleteCartItemElement(7L), new DeleteCartItemElement(3L)));

        ExtractableResponse extract = deleteCartItem(accessToken, deleteCartItemRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 존재하지 않는 장바구니입니다.");
    }

    @Test
    void 사용자의_장바구니를_삭제하는_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        deleteCart(accessToken, HttpStatus.NO_CONTENT);
        ExtractableResponse extract = findCustomerCart(accessToken, HttpStatus.OK);

        List<CartResponseElement> cartResponseElements = extract.body().jsonPath().getList("cartItems", CartResponseElement.class);
        assertThat(cartResponseElements.size()).isEqualTo(0);
    }

    @Test
    void 장바구니에_상품을_넣은_후_회원탈퇴() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();
        createCartItem(accessToken, new AddCartItemRequest(1L, 10, true), HttpStatus.CREATED);
        var deleteCustomerRequest = new DeleteCustomerRequest(VALID_PASSWORD);

        createDeleteCustomerResult(accessToken, deleteCustomerRequest, HttpStatus.NO_CONTENT);
        var response = createSignInResult(SIGN_IN_REQUEST, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 존재하지 않는 이메일 입니다.");
    }
}
