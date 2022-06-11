package woowacourse.shoppingcart.acceptance;

import static woowacourse.fixture.CartFixture.장바구니_상품_추가_요청후_ID_반환;
import static woowacourse.fixture.CustomerFixture.로그인_요청_및_토큰발급;
import static woowacourse.fixture.CustomerFixture.회원가입_요청_및_ID_추출;
import static woowacourse.fixture.OrderFixture.주문_내역_조회_요청;
import static woowacourse.fixture.OrderFixture.주문_내역_포함_검증;
import static woowacourse.fixture.OrderFixture.주문_단일_조회_요청;
import static woowacourse.fixture.OrderFixture.주문_조회_검증;
import static woowacourse.fixture.OrderFixture.주문_조회_응답_검증;
import static woowacourse.fixture.OrderFixture.주문하기_성공함;
import static woowacourse.fixture.OrderFixture.주문하기_요청;
import static woowacourse.fixture.OrderFixture.주문하기_요청_성공_검증;
import static woowacourse.fixture.ProductFixture.상품_등록후_ID반환;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.global.AcceptanceTest;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;

@Disabled
@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private static final String USER_NAME = "swcho";
    private String token;
    private long customerId;
    private Long cartId1;
    private Long cartId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        토큰_및_회원_ID_초기화();

        Long productId1 = 상품_등록후_ID반환(token, "치킨", 10_000, "http://example.com/chicken.jpg", 20_000);
        Long productId2 = 상품_등록후_ID반환(token, "맥주", 20_000, "http://example.com/beer.jpg", 30_000);

        cartId1 = 장바구니_상품_추가_요청후_ID_반환(token, customerId, productId1, 2);
        cartId2 = 장바구니_상품_추가_요청후_ID_반환(token, customerId, productId2, 2);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        List<OrderRequest> orderRequests = Stream.of(cartId1, cartId2)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(USER_NAME, orderRequests);

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공_검증(USER_NAME, Collections.singletonList(new OrderRequest(cartId1, 2)));
        Long orderId2 = 주문하기_요청_성공_검증(USER_NAME, Collections.singletonList(new OrderRequest(cartId2, 5)));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(USER_NAME);

        주문_조회_응답_검증(response);
        주문_내역_포함_검증(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공_검증(USER_NAME, Arrays.asList(
                new OrderRequest(cartId1, 2),
                new OrderRequest(cartId2, 4)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(USER_NAME, orderId);

        주문_조회_응답_검증(response);
        주문_조회_검증(response, orderId);
    }

    private void 토큰_및_회원_ID_초기화() {
        CustomerCreateRequest customerRequest = new CustomerCreateRequest("philz@gmail.com", "swcho", "1q2w3e4r!");
        this.customerId = 회원가입_요청_및_ID_추출(customerRequest);
        TokenRequest tokenRequest = new TokenRequest("philz@gmail.com", "1q2w3e4r!");
        this.token = 로그인_요청_및_토큰발급(tokenRequest);
    }
}
