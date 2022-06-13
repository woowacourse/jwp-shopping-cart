package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginTokenResponse;
import woowacourse.shoppingcart.dto.cart.CartItemRequest;
import woowacourse.shoppingcart.dto.product.ProductRequest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private static final Long CUSTOMER_ID = 1L;

    private String token;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 5);
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg", 5);

        token = 로그인_요청_및_토큰발급(new LoginRequest("puterism@naver.com", "12349053145"));

        장바구니_아이템_추가_요청(token, CUSTOMER_ID, productId1, 5);
        장바구니_아이템_추가_요청(token, CUSTOMER_ID, productId2, 5);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        //when
        ExtractableResponse<Response> response = 주문하기_요청(token, CUSTOMER_ID);

        //then
        주문하기_성공함(response);
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

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl, int quantity) {
        ProductRequest productRequest = new ProductRequest(imageUrl, name, price, quantity);

        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .when().post("/api/products")
            .then().log().all()
            .extract();
    }

    public static Long 상품_등록되어_있음(String name, int price, String imageUrl, int quantity) {
        ExtractableResponse<Response> response = 상품_등록_요청(name, price, imageUrl, quantity);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
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

    public static ExtractableResponse<Response> 주문하기_요청(String token, long customerId) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/customers/{customerId}/orders", customerId)
            .then().log().all()
            .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }
}
