package woowacourse.shoppingcart.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.acceptance.AcceptanceTest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.member.dto.request.LoginRequest;
import woowacourse.shoppingcart.dto.OrderRequest;

import java.util.List;

import static woowacourse.acceptance.RestAssuredConvenienceMethod.*;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private static final String URI = "/api/members/me/orders";

    @DisplayName("주문하기 - 성공한 경우 201 Created가 반환된다.")
    @Test
    void addOrder() {
        OrderRequest request = new OrderRequest(1L, 1);
        postRequestWithToken(token(), List.of(request), URI)
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("주문하기 - 토큰 없이 접근한 경우 401 Unauthorized가 반환된다.")
    @Test
    void addOrderWithoutToken() {
        OrderRequest request = new OrderRequest(1L, 1);
        postRequestWithoutToken(List.of(request), URI)
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("주문하기 - 잘못된 입력의 경우 400 Bad Request가 반환된다.")
    @Test
    void addBad() {
        OrderRequest request = new OrderRequest(5L, 1);
        postRequestWithToken(token(), List.of(request), URI)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("주문 내역 조회 - 성공한 경우 200 ok가 반환된다.")
    @Test
    void getOrders() {
        postRequestWithToken(token(), List.of(new OrderRequest(1L, 1)), URI);

        getRequestWithToken(token(), URI)
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("주문 내역 조회 - 토큰 없이 접근한 경우 401 Unauthorized가 반환된다.")
    @Test
    void getOrdersWithoutToken() {
        postRequestWithToken(token(), List.of(new OrderRequest(1L, 1)), URI);

        getRequestWithoutToken(URI)
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("주문 단일 조회 - 성공한 경우 200 ok가 반환된다.")
    @Test
    void getOrder() {
        postRequestWithToken(token(), List.of(new OrderRequest(1L, 1)), URI);

        getRequestWithToken(token(), URI + "/1")
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("주문 딘일 조회 - 토큰 없이 접근한 경우 401 Unauthorized가 반환된다.")
    @Test
    void getOrderWithoutToken() {
        postRequestWithToken(token(), List.of(new OrderRequest(1L, 1)), URI);

        getRequestWithoutToken(URI + "/1")
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("주문 단일 조회 - 잘못된 입력의 경우 400 Bad Request가 반환된다.")
    @Test
    void getBadOrder() {
        getRequestWithToken(token(), URI + "/200")
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private String token() {
        LoginRequest loginRequest = new LoginRequest("ari@wooteco.com", "Wooteco1!");
        return postRequestWithoutToken(loginRequest, "/api/auth")
                .extract().as(TokenResponse.class).getAccessToken();
    }

}
