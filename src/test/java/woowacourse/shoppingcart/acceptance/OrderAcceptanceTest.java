package woowacourse.shoppingcart.acceptance;

import static woowacourse.fixture.RequestFixture.로그인_및_토큰_발급;
import static woowacourse.fixture.RequestFixture.상품_등록되어_있음;
import static woowacourse.fixture.RequestFixture.장바구니_아이템_추가되어_있음;
import static woowacourse.fixture.RequestFixture.주문_내역_조회_요청;
import static woowacourse.fixture.RequestFixture.주문_단일_조회_요청;
import static woowacourse.fixture.RequestFixture.주문하기_요청;
import static woowacourse.fixture.RequestFixture.주문하기_요청_성공되어_있음;
import static woowacourse.fixture.ResponseFixture.주문_내역_포함됨;
import static woowacourse.fixture.ResponseFixture.주문_조회_응답됨;
import static woowacourse.fixture.ResponseFixture.주문_조회됨;
import static woowacourse.fixture.ResponseFixture.주문하기_성공함;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.dto.request.CreateOrderDetailRequest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private static final String USER_NAME = "puterism";
    private static final String PASSWORD = "Shopping123!";
    private String accessToken;
    private Long cartId1;
    private Long cartId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        accessToken = 로그인_및_토큰_발급(USER_NAME, PASSWORD);

        cartId1 = 장바구니_아이템_추가되어_있음(accessToken, productId1);
        cartId2 = 장바구니_아이템_추가되어_있음(accessToken, productId2);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        // given
        List<CreateOrderDetailRequest> orderRequests = Stream.of(cartId1, cartId2)
                .map(cartId -> new CreateOrderDetailRequest(cartId, 10))
                .collect(Collectors.toList());

        // when
        ExtractableResponse<Response> response = 주문하기_요청(orderRequests, accessToken);

        // then
        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        // given
        Long orderId1 = 주문하기_요청_성공되어_있음(Collections.singletonList(new CreateOrderDetailRequest(cartId1, 2)),
                accessToken);
        Long orderId2 = 주문하기_요청_성공되어_있음(Collections.singletonList(new CreateOrderDetailRequest(cartId2, 5)),
                accessToken);

        // when
        ExtractableResponse<Response> response = 주문_내역_조회_요청(accessToken);

        // then
        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        // given
        Long orderId = 주문하기_요청_성공되어_있음(Arrays.asList(
                new CreateOrderDetailRequest(cartId1, 2),
                new CreateOrderDetailRequest(cartId2, 4)
        ), accessToken);

        // when
        ExtractableResponse<Response> response = 주문_단일_조회_요청(orderId, accessToken);

        // then
        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }
}
