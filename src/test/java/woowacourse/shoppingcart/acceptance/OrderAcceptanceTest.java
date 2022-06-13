package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.CREATED;
import static woowacourse.Fixtures.FORBIDDEN;
import static woowacourse.Fixtures.로그인;
import static woowacourse.Fixtures.예외메세지_검증;
import static woowacourse.Fixtures.조조그린_로그인_요청;
import static woowacourse.Fixtures.조조그린_요청;
import static woowacourse.Fixtures.치킨;
import static woowacourse.Fixtures.피자;
import static woowacourse.Fixtures.회원가입;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.ProductIdRequest;

@DisplayName("장바구니 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {
    private String 토큰;
    private Long 치킨아이디;
    private Long 피자아이디;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        회원가입(조조그린_요청);
        토큰 = 로그인(조조그린_로그인_요청)
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        치킨아이디 = 치킨.getId();
        피자아이디 = 피자.getId();
    }

    @DisplayName("로그인한 회원의 장바구니 목록을 조회한다.")
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음(토큰, 치킨아이디);
        장바구니_아이템_추가되어_있음(토큰, 피자아이디);

        ExtractableResponse<Response> response = 장바구니_주문_요청(토큰, new OrderRequest(치킨아이디, 3));

        CREATED(response);
        assertThat(response.header("Location").matches("/orders/[0-9]+"))
                .isTrue();
    }

    @DisplayName("토큰이 잘못될 경우 요청할 수 없다.")
    @Test
    void getCartItems_tokenError() {
        장바구니_아이템_추가되어_있음(토큰, 피자아이디);

        ExtractableResponse<Response> response = 장바구니_주문_요청("12345", new OrderRequest(치킨아이디, 3));

        FORBIDDEN(response);
        예외메세지_검증(response, "토큰이 유효하지 않습니다.");
    }

    private ExtractableResponse<Response> 장바구니_주문_요청(String accessToken, OrderRequest orderRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(List.of(orderRequest))
                .when().post("/auth/customer/orders")
                .then().log().all()
                .extract();
    }


    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken,
                                                               List<ProductIdRequest> productIdRequests) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(productIdRequests)
                .when().post("/auth/customer/cartItems")
                .then().log().all()
                .extract();
    }

    public static Long 장바구니_아이템_추가되어_있음(String token, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, List.of(new ProductIdRequest(productId)));
        return 장바구니목록_변환(response).get(0).getId();
    }

    private static List<CartResponse> 장바구니목록_변환(ExtractableResponse<Response> response) {
        return response.body().jsonPath().getList(".", CartResponse.class);
    }
}
