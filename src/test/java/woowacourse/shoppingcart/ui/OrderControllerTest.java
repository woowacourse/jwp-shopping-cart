package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginTokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.cart.CartItemRequest;

public class OrderControllerTest extends AcceptanceTest {

    private static final long CUSTOMER_ID = 1L;

    @Test
    @DisplayName("사용자의 장바구니가 빈 경우 400 예외를 던진다.")
    void create_customerNotFound() {
        //given
        String token = 로그인_요청_및_토큰발급(new LoginRequest("puterism@naver.com", "12349053145"));

        //when
        ExtractableResponse<Response> response = 주문_요청(token, CUSTOMER_ID);

        //then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
            () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo("해당 고객의 장바구니가 존재하지 않습니다.")
        );
    }

    public static ExtractableResponse<Response> 주문_요청(String token, long customerId) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .when().post("/api/customers/{customerId}/orders", customerId)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String token, long customerId, Long productId,
        int count) {
        CartItemRequest request = new CartItemRequest(productId, count);

        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/customers/{customerId}/carts", customerId)
            .then().log().all()
            .extract();
    }

    private String 로그인_요청_및_토큰발급(LoginRequest request) {
        ExtractableResponse<Response> loginResponse = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/auth/login")
            .then().log().all()
            .extract();

        LoginTokenResponse loginTokenResponse = loginResponse.body().as(LoginTokenResponse.class);
        return loginTokenResponse.getAccessToken();
    }
}
