package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.LOCATION;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.application.dto.OrderResponse;

@DisplayName("주문 관련 기능")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class OrderAcceptanceTest extends AcceptanceTest {

    @Test
    void 생성() {
        // given
        String token = 회원_가입_후_토큰_발급("yeonlog", "11aaAA!!");

        // when
        ExtractableResponse<Response> response = 상품_및_장바구니_생성_후_주문_등록(token, "치킨", 10_000, 1);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header(LOCATION)).isNotBlank();
    }

    @Test
    void 주문_id로_주문_상세_검색() {
        // given
        String token = 회원_가입_후_토큰_발급("yeonlog", "11aaAA!!");
        int price = 10_000;
        int quantity = 2;
        Long orderId = 주문_요청_후_id_반환(token, "치킨", price, quantity);
        int expectTotalCost = price * quantity;

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + token)
                .when().get("/customers/orders/{orderId}", orderId)
                .then().log().all()
                .extract();

        // then
        Long foundOrderId = Long.parseLong(findValue(response, "orderId"));
        Long totalCost = Long.parseLong(findValue(response, "totalCost"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(foundOrderId).isEqualTo(orderId);
        assertThat(totalCost).isEqualTo(expectTotalCost);
    }

    @Test
    void 존재하지_않는_주문_id로_주문_상세_검색() {
        // given
        String token = 회원_가입_후_토큰_발급("yeonlog", "11aaAA!!");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + token)
                .when().get("/customers/orders/{orderId}", 1L)
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(findValue(response, "message")).contains("사용자가 해당 주문을 한 적이 없습니다.");
    }

    @Test
    void 주문_목록_조회() {
        // given
        String token = 회원_가입_후_토큰_발급("yeonlog", "11aaAA!!");
        Long orderId1 = 주문_요청_후_id_반환(token, "치킨", 10_000, 1);
        Long orderId2 = 주문_요청_후_id_반환(token, "피자", 15_000, 1);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + token)
                .when().get("/customers/orders")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(generateOrderIds(response)).containsOnly(orderId1, orderId2);
    }

    private List<Long> generateOrderIds(ExtractableResponse<Response> response) {
        return response.jsonPath().getList("orders", OrderResponse.class).stream()
                .map(OrderResponse::getOrderId)
                .collect(Collectors.toList());
    }

    private Long 주문_요청_후_id_반환(String token, String productName, int productPrice, int quantity) {
        ExtractableResponse<Response> response = 상품_및_장바구니_생성_후_주문_등록(token, productName, productPrice, quantity);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    private ExtractableResponse<Response> 상품_및_장바구니_생성_후_주문_등록(String token, String productName, int productPrice,
                                                               int quantity) {
        Long productId = 상품_등록_후_id_반환(productName, productPrice, "test.png");

        Map<String, Object> request = 주문_등록_요청값(productId, quantity);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + token)
                .body(request)
                .when().post("/customers/orders")
                .then().log().all()
                .extract();
    }

    private Map<String, Object> 주문_등록_요청값(Long productId, int quantity) {
        Map<String, Object> orderDetail = new HashMap<>();
        orderDetail.put("id", productId);
        orderDetail.put("quantity", quantity);

        Map<String, Object> request = new HashMap<>();
        request.put("order", List.of(orderDetail));

        return request;
    }
}
