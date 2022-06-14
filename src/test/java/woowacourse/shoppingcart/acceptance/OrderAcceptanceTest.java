package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가_요청;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;
import static woowacourse.utils.Fixture.signupRequest;
import static woowacourse.utils.Fixture.tokenRequest;
import static woowacourse.utils.Fixture.맥주;
import static woowacourse.utils.Fixture.치킨;
import static woowacourse.utils.RestAssuredUtils.getWithToken;
import static woowacourse.utils.RestAssuredUtils.httpPost;
import static woowacourse.utils.RestAssuredUtils.login;
import static woowacourse.utils.RestAssuredUtils.postWithToken;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.cart.CartSetRequest;
import woowacourse.shoppingcart.dto.order.OrderRequest;
import woowacourse.shoppingcart.dto.order.OrderSaveRequest;
import woowacourse.utils.AcceptanceTest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private Long productId1;
    private Long productId2;
    private String token;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);
        token = loginResponse.jsonPath().getString("accessToken");

        productId1 = 상품_등록되어_있음(치킨, token);
        productId2 = 상품_등록되어_있음(맥주, token);

        장바구니_아이템_추가_요청(token, productId1, new CartSetRequest(100));
        장바구니_아이템_추가_요청(token, productId2, new CartSetRequest(100));
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        OrderSaveRequest orderSaveRequest = new OrderSaveRequest(List.of(productId1, productId2));
        ExtractableResponse<Response> response = postWithToken("/orders", token, orderSaveRequest);

        주문하기_성공함(response);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        // given
        OrderSaveRequest orderSaveRequest = new OrderSaveRequest(List.of(productId1, productId2));
        ExtractableResponse<Response> orderResponse = postWithToken("/orders", token, orderSaveRequest);
        Long orderId = Long.valueOf(orderResponse.jsonPath().getString("id"));

        // when
        ExtractableResponse<Response> response = getWithToken("/orders/" + orderId, token);

        // then
        assertThat(response.jsonPath().getString("totalPrice")).isNotNull();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }
}
