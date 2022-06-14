package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.ShoppingCartFixture.잉_로그인요청;
import static woowacourse.ShoppingCartFixture.주문_URI;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.ui.dto.request.OrderCreateRequest;
import woowacourse.shoppingcart.ui.dto.response.OrderResponse;

@DisplayName("주문 관련 기능")
@Sql({"/truncate.sql", "/auth.sql", "/product.sql", "/cart.sql"})
public class OrderAcceptanceTest extends AcceptanceTest {

    @DisplayName("엑세스 토큰과 장바구니 아이디 목록을 전달해서 주문을 생성할 수 있다")
    @Test
    void addOrder() {
        // given
        final String 엑세스토큰 = getToken(잉_로그인요청);
        final List<OrderCreateRequest> 주문목록 = List.of(new OrderCreateRequest(1L, null),
                new OrderCreateRequest(2L, null));

        // when
        ExtractableResponse<Response> response = 주문하기_요청(주문목록, 엑세스토큰);

        // then
        주문하기_성공함(response);
    }

    @DisplayName("엑세스토큰을 이용해 자신의 전체 주문 목록을 조회할 수 있다")
    @Test
    void getOrders() {
        // given
        final String 엑세스토큰 = getToken(잉_로그인요청);
        final Long orderId1 = 주문하기_요청_성공되어_있음(엑세스토큰, List.of(new OrderCreateRequest(1L, null)));
        final Long orderId2 = 주문하기_요청_성공되어_있음(엑세스토큰, List.of(new OrderCreateRequest(2L, null)));

        // when
        ExtractableResponse<Response> response = 주문_내역_조회_요청(엑세스토큰);

        // then
        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("엑세스토큰과 주문ID를 이용해 주문 한 건을 조회할 수 있다")
    @Test
    void getOrder() {
        // given
        final String 엑세스토큰 = getToken(잉_로그인요청);
        final Long orderId1 = 주문하기_요청_성공되어_있음(엑세스토큰, List.of(new OrderCreateRequest(1L, null)));
        final Long orderId2 = 주문하기_요청_성공되어_있음(엑세스토큰, List.of(new OrderCreateRequest(2L, null)));

        // when
        ExtractableResponse<Response> response = 주문_단일_조회_요청(orderId2, 엑세스토큰);

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId2);
    }

    private ExtractableResponse<Response> 주문하기_요청(List<OrderCreateRequest> orderRequests, String accessToken) {
        return postWithToken(주문_URI, orderRequests, accessToken);
    }

    private ExtractableResponse<Response> 주문_내역_조회_요청(String accessToken) {
        return get(주문_URI, accessToken);
    }

    private ExtractableResponse<Response> 주문_단일_조회_요청(Long orderId, String accessToken) {
        return get(주문_URI + String.format("/%d", orderId), accessToken);
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    private Long 주문하기_요청_성공되어_있음(String accessToken, List<OrderCreateRequest> orderRequests) {
        ExtractableResponse<Response> response = 주문하기_요청(orderRequests, accessToken);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
        final List<Long> resultOrderIds = response.jsonPath().getList(".", OrderResponse.class)
                .stream()
                .map(OrderResponse::getId)
                .collect(Collectors.toList());

        assertThat(resultOrderIds).contains(orderIds);
    }

    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        final OrderResponse orderResponse = response.as(OrderResponse.class);
        assertThat(orderResponse.getId()).isEqualTo(orderId);
    }
}
