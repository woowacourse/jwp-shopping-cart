package woowacourse.shoppingcart.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.acceptance.AcceptanceTest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.member.dto.request.LoginRequest;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.PutCartItemRequest;

import java.util.List;

import static woowacourse.acceptance.RestAssuredConvenienceMethod.*;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private static final String URL = "/api/members/me/carts";


    @DisplayName("아이템 조회 - 성공한 경우 200 ok가 반환된다.")
    @Test
    void getCarts() {
        getRequestWithToken(token(), URL)
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("아이템 조회 - 토큰 없이 접근한 경우 401 Unauthorized가 반환된다.")
    @Test
    void getCartsWithoutToken() {
        getRequestWithoutToken(URL)
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("아이템 추가 - 성공한 경우 201 Created가 반환된다.")
    @Test
    void addCartItem() {
        AddCartItemRequest request = new AddCartItemRequest(3L);

        postRequestWithToken(token(), request, URL)
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("아이템 추가 - 잘못된 입력의 경우 400 Bad Request가 반환된다.")
    @Test
    void addBad() {
        AddCartItemRequest request = new AddCartItemRequest(99L);

        postRequestWithToken(token(), request, URL)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("아이템 추가 - 토큰 없이 접근한 경우 401 Unauthorized가 반환된다.")
    @Test
    void addWithoutToken() {
        AddCartItemRequest request = new AddCartItemRequest(3L);

        postRequestWithoutToken(request, URL)
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }


    @DisplayName("수량 변경 - 성공한 경우 200 ok가 반환된다.")
    @Test
    void putCartItem() {
        PutCartItemRequest request = new PutCartItemRequest(10);
        putRequestWithToken(token(), request, URL + "/1")
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("수량 변경 - 잘못된 입력의 경우 400 Bad Request가 반환된다.")
    @Test
    void putBad() {
        PutCartItemRequest request = new PutCartItemRequest(10);

        putRequestWithToken(token(), request, URL + "/99")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("수량 변경 - 토큰 없이 접근한 경우 401 Unauthorized가 반환된다.")
    @Test
    void putWithoutToken() {
        PutCartItemRequest request = new PutCartItemRequest(10);

        putRequestWithoutToken(request, URL + "/1")
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }


    @DisplayName("삭제 - 성공한 경우 204 No Content가 반환된다.")
    @Test
    void deleteCartItem() {
        deleteRequestWithToken(token(), List.of(), URL + "/1")
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("삭제 - 잘못된 입력의 경우 400 Bad Request가 반환된다.")
    @Test
    void deleteBad() {
        deleteRequestWithToken(token(), List.of(), URL + "/99")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("삭제 - 토큰 없이 접근한 경우 401 Unauthorized가 반환된다.")
    @Test
    void deleteWithoutToken() {
        deleteRequestWithoutToken(URL + "/1")
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private String token() {
        LoginRequest loginRequest = new LoginRequest("ari@wooteco.com", "Wooteco1!");
        return postRequestWithoutToken(loginRequest, "/api/auth")
                .extract().as(TokenResponse.class).getAccessToken();
    }

}
