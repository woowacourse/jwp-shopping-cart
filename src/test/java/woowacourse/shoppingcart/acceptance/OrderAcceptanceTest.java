package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.로그인;
import static woowacourse.Fixtures.조조그린_로그인_요청;
import static woowacourse.Fixtures.조조그린_요청;
import static woowacourse.Fixtures.치킨;
import static woowacourse.Fixtures.피자;
import static woowacourse.Fixtures.회원가입;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.OrderRequest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {
    private String 토큰;
    private Long 치킨카트_아이디;
    private Long 피자카트_아이디;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        회원가입(조조그린_요청);
        토큰 = 로그인(조조그린_로그인_요청)
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        치킨카트_아이디 = 장바구니_아이템_추가되어_있음(토큰, 치킨.getId());
        피자카트_아이디 = 장바구니_아이템_추가되어_있음(토큰, 피자.getId());
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        List<OrderRequest> orderRequests = Stream.of(치킨카트_아이디, 피자카트_아이디)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(토큰, orderRequests);

        주문하기_성공함(response);
    }

    public static ExtractableResponse<Response> 주문하기_요청(String accessToken, List<OrderRequest> orderRequests) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(orderRequests)
                .when().post("/auth/customer/orders")
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotNull();
    }
}
