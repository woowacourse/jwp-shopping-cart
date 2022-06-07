package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.LOCATION;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.application.dto.OrderResponse;
import woowacourse.shoppingcart.ui.dto.OrderDetailRequest;
import woowacourse.shoppingcart.ui.dto.OrderRequest;

@DisplayName("주문 관련 기능")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class OrderAcceptanceTest extends AcceptanceTest {

    private Long 장바구니_추가_후_id_반환(String token, Long productId) {
        ExtractableResponse<Response> response = 장바구니_추가(token, productId);
        return Long.parseLong(response.header(LOCATION).split("/cart/")[1]);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        String token = 회원_가입_후_토큰_발급("yeonlog", "11aaAA!!");

        Long productId1 = 상품_등록("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록("맥주", 20_000, "http://example.com/beer.jpg");

        Long cartId1 = 장바구니_추가_후_id_반환(token, productId1);
        Long cartId2 = 장바구니_추가_후_id_반환(token, productId2);

        List<OrderDetailRequest> orderRequests = Stream.of(cartId1, cartId2)
                .map(cartId -> new OrderDetailRequest(cartId, 10))
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(token, new OrderRequest(orderRequests));

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        String token = 회원_가입_후_토큰_발급("yeonlog", "11aaAA!!");

        Long productId1 = 상품_등록("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록("맥주", 20_000, "http://example.com/beer.jpg");

        Long cartId1 = 장바구니_추가_후_id_반환(token, productId1);
        Long cartId2 = 장바구니_추가_후_id_반환(token, productId2);

        Long orderId1 = 주문하기_요청_성공되어_있음(token, Collections.singletonList(new OrderDetailRequest(cartId1, 2)));
        Long orderId2 = 주문하기_요청_성공되어_있음(token, Collections.singletonList(new OrderDetailRequest(cartId2, 5)));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(token);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        String token = 회원_가입_후_토큰_발급("yeonlog", "11aaAA!!");

        Long productId1 = 상품_등록("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록("맥주", 20_000, "http://example.com/beer.jpg");

        Long cartId1 = 장바구니_추가_후_id_반환(token, productId1);
        Long cartId2 = 장바구니_추가_후_id_반환(token, productId2);

        Long orderId = 주문하기_요청_성공되어_있음(token, Arrays.asList(
                new OrderDetailRequest(cartId1, 2),
                new OrderDetailRequest(cartId2, 4)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(token, orderId);

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

    public static ExtractableResponse<Response> 주문하기_요청(String token, OrderRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + token)
                .body(request)
                .when().post("/customers/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + token)
                .when().get("/customers/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String token, Long orderId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + token)
                .when().get("/customers/orders/{orderId}", orderId)
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(String token, List<OrderDetailRequest> orderRequests) {
        ExtractableResponse<Response> response = 주문하기_요청(token, new OrderRequest(orderRequests));
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
        List<Long> resultOrderIds = response.jsonPath().getList(".", OrderResponse.class).stream()
                .map(OrderResponse::getOrderId)
                .collect(Collectors.toList());
        assertThat(resultOrderIds).contains(orderIds);
    }

    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        OrderResponse resultOrder = response.as(OrderResponse.class);
        assertThat(resultOrder.getOrderId()).isEqualTo(orderId);
    }

    private Long 상품_등록(String name, int price, String imageUrl) {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(상품_정보(name, price, imageUrl))
                .when().post("/products")
                .then().log().all()
                .extract();

        return Long.parseLong(response.header(LOCATION).split("/products/")[1]);
    }

}
