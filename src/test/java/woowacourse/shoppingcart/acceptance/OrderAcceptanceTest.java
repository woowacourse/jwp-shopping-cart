package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import woowacourse.shoppingcart.domain.order.OrderItem;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.dto.cart.CartItemRequest;
import woowacourse.shoppingcart.dto.product.ProductRequest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private static final Long CUSTOMER_ID = 1L;

    private String token;
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 5);
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg", 5);

        token = 로그인_요청_및_토큰발급(new LoginRequest("puterism@naver.com", "12349053145"));

        장바구니_아이템_추가_요청(token, CUSTOMER_ID, productId1, 5);
        장바구니_아이템_추가_요청(token, CUSTOMER_ID, productId2, 5);
    }

    @DisplayName("주문하기")
    @Test
    void createOrder() {
        //when
        ExtractableResponse<Response> response = 주문하기_요청(token, CUSTOMER_ID);

        //then
        주문하기_성공함(response);
    }

    @Test
    @DisplayName("주문 내역 조회")
    void findOrders() {
        //given
        주문하기_요청(token, CUSTOMER_ID);

        //when
        ExtractableResponse<Response> response = 주문_내역_조회_요청(token, CUSTOMER_ID);

        //then
        주문_조회_응답됨(response);
        주문_내역_포함됨(response, List.of(productId1, productId2));
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

    public ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl, int quantity) {
        ProductRequest productRequest = new ProductRequest(imageUrl, name, price, quantity);

        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .when().post("/api/products")
            .then().log().all()
            .extract();
    }

    public Long 상품_등록되어_있음(String name, int price, String imageUrl, int quantity) {
        ExtractableResponse<Response> response = 상품_등록_요청(name, price, imageUrl, quantity);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public ExtractableResponse<Response> 장바구니_아이템_추가_요청(String token, long customerId, Long productId,
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

    public ExtractableResponse<Response> 주문하기_요청(String token, long customerId) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/customers/{customerId}/orders", customerId)
            .then().log().all()
            .extract();
    }

    public void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public ExtractableResponse<Response> 주문_내역_조회_요청(String token, long customerId) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .when().get("/api/customers/{customerId}/orders", customerId)
            .then().log().all()
            .extract();
    }

    public void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public void 주문_내역_포함됨(ExtractableResponse<Response> response, List<Long> productIds) {
        List<List<Long>> result = response.jsonPath().getList(".", OrderResponse.class)
            .stream().map(convertOrderResponseToProductIds())
            .collect(Collectors.toList());

        assertThat(result).contains(productIds);
    }

    private Function<OrderResponse, List<Long>> convertOrderResponseToProductIds() {
        return orderResponse ->
            orderResponse.getOrderItems().stream().map(OrderItem::getProductId)
                .collect(Collectors.toList());
    }
}
