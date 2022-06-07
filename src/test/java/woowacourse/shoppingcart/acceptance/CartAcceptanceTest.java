package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.acceptance.AcceptanceTest;
import woowacourse.acceptance.RestAssuredFixture;
import woowacourse.auth.dto.LogInRequest;
import woowacourse.shoppingcart.domain.Id;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.*;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.ArrayList;
import java.util.List;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    private static final String USER = "puterism";

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        //given
        ProductRequest productRequest = new ProductRequest("치킨", 10000, "http://example.com/chicken.jpg");
        ProductRequest productRequest2 = new ProductRequest("피자", 20000, "http://example.com/pizza.jpg");
        ProductRequest productRequest3 = new ProductRequest("빙수", 5000, "http://example.com/bingsu.jpg");


        RestAssuredFixture.post(productRequest, "/products", HttpStatus.CREATED.value());
        RestAssuredFixture.post(productRequest2, "/products", HttpStatus.CREATED.value());
        RestAssuredFixture.post(productRequest3, "/products", HttpStatus.CREATED.value());
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        RestAssuredFixture.post(signUpRequest, "/users", HttpStatus.CREATED.value());

        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123456");
        String token = RestAssuredFixture.getSignInResponse(logInRequest, "/login").getToken();

        //when & then
        RestAssuredFixture.postCart(new CartProductRequest(1L, 1L, true),
                token, "/cart", HttpStatus.CREATED.value());
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        RestAssuredFixture.post(signUpRequest, "/users", HttpStatus.CREATED.value());

        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123456");
        String token = RestAssuredFixture.getSignInResponse(logInRequest, "/login").getToken();

        RestAssuredFixture.postCart(new CartProductRequest(1L, 1L, true),
                token, "/cart", HttpStatus.CREATED.value());
        RestAssuredFixture.postCart(new CartProductRequest(2L, 1L, true),
                token, "/cart", HttpStatus.CREATED.value());

        RestAssuredFixture.get(token, "/cart", HttpStatus.OK.value())
                .body("products.size()", is(2));
    }

    @DisplayName("장바구니 전체 삭제")
    @Test
    void deleteAllCartItem() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        RestAssuredFixture.post(signUpRequest, "/users", HttpStatus.CREATED.value());

        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123456");
        String token = RestAssuredFixture.getSignInResponse(logInRequest, "/login").getToken();

        RestAssuredFixture.postCart(new CartProductRequest(1L, 1L, true),
                token, "/cart", HttpStatus.CREATED.value());
        RestAssuredFixture.postCart(new CartProductRequest(2L, 1L, true),
                token, "/cart", HttpStatus.CREATED.value());

        //when & then
        RestAssuredFixture.deleteAllCart(token, "/cart/all", HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("장바구니 복수 삭제")
    @Test
    void deleteCartItems() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        RestAssuredFixture.post(signUpRequest, "/users", HttpStatus.CREATED.value());

        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123456");
        String token = RestAssuredFixture.getSignInResponse(logInRequest, "/login").getToken();

        RestAssuredFixture.postCart(new CartProductRequest(1L, 1L, true),
                token, "/cart", HttpStatus.CREATED.value());
        RestAssuredFixture.postCart(new CartProductRequest(2L, 1L, true),
                token, "/cart", HttpStatus.CREATED.value());
        RestAssuredFixture.postCart(new CartProductRequest(3L, 1L, true),
                token, "/cart", HttpStatus.CREATED.value());

        //when & then
        DeleteProductRequest request = new DeleteProductRequest(List.of(new Id(1L), new Id(3L)));


        RestAssuredFixture.delete(request, token, "/cart", HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("장바구니 아이템 복수 수정 ")
    @Test
    void modifyCartItem() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        RestAssuredFixture.post(signUpRequest, "/users", HttpStatus.CREATED.value());

        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123456");
        String token = RestAssuredFixture.getSignInResponse(logInRequest, "/login").getToken();

        RestAssuredFixture.postCart(new CartProductRequest(1L, 1L, true),
                token, "/cart", HttpStatus.CREATED.value());
        RestAssuredFixture.postCart(new CartProductRequest(2L, 1L, true),
                token, "/cart", HttpStatus.CREATED.value());
        RestAssuredFixture.postCart(new CartProductRequest(3L, 1L, true),
                token, "/cart", HttpStatus.CREATED.value());

        //when & then
        ModifyProductRequest request = new ModifyProductRequest(1L, 2L, false);
        RestAssuredFixture.patch(new ModifyProductRequests(List.of(request)), token, "/cart", HttpStatus.OK.value());
    }
}
