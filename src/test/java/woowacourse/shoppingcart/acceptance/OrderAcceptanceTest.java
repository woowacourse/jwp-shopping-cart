package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.acceptance.AcceptanceTestFixture.에덴;
import static woowacourse.shoppingcart.application.ProductFixture.바나나_사과_주문_요청;
import static woowacourse.shoppingcart.application.ProductFixture.바나나_요청;
import static woowacourse.shoppingcart.application.ProductFixture.바나나_주문_요청;
import static woowacourse.shoppingcart.application.ProductFixture.사과_요청;
import static woowacourse.shoppingcart.application.ProductFixture.장바구니_바나나_요청;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderResponse;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private String token;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        post("/signup", 에덴);
        post("/products", 바나나_요청);
        post("/products", 사과_요청);
        final ExtractableResponse<Response> tokenResponse = post("/signin",
                new TokenRequest("leo0842", "Password123!"));
        token = tokenResponse.jsonPath().getObject(".", TokenResponse.class).getAccessToken();
        post("/customers/cart", token, 장바구니_바나나_요청);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        ExtractableResponse<Response> response = post("/customers/orders", token, 바나나_사과_주문_요청);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrders() {
        post("/customers/orders", token, 바나나_사과_주문_요청);
        ExtractableResponse<Response> response = get("/customers/orders/" + 1, token);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getLong("orderId")).isEqualTo(1),
                () -> assertThat(response.jsonPath().getInt("totalCost")).isEqualTo(40_000),
                () -> {
                    List<OrderDetailResponse> orderDetailResponse = response.jsonPath()
                            .getList("orderDetails", OrderDetailResponse.class);
                    assertAll(
                            () -> assertThat(orderDetailResponse.size()).isEqualTo(2),
                            () -> assertThat(orderDetailResponse.get(0).getCost()).isEqualTo(10_000)
                    );
                }
        );
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrder() {
        post("/customers/orders", token, 바나나_사과_주문_요청);
        post("/customers/orders", token, 바나나_주문_요청);

        ExtractableResponse<Response> response = get("/customers/orders", token);
        assertAll(

                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> {
                    List<OrderResponse> ordersResponse = response.jsonPath().getList("orders", OrderResponse.class);
                    assertAll(
                            () -> assertThat(ordersResponse.size()).isEqualTo(2),
                            () -> assertThat(ordersResponse.get(0).getOrderDetails().size()).isEqualTo(2),
                            () -> assertThat(ordersResponse.get(1).getOrderDetails().size()).isEqualTo(1)
                    );
                }
        );
    }
}
