package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.로그인;
import static woowacourse.Fixtures.조조그린_로그인_요청;
import static woowacourse.Fixtures.조조그린_요청;
import static woowacourse.Fixtures.치킨;
import static woowacourse.Fixtures.피자;
import static woowacourse.Fixtures.회원가입;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartIdRequest;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.ProductIdRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
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

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        List<ProductIdRequest> 물품추가요청 = List.of(new ProductIdRequest(치킨아이디));
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(토큰, 물품추가요청);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음(토큰, 치킨아이디);
        장바구니_아이템_추가되어_있음(토큰, 피자아이디);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(토큰);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, 치킨아이디, 피자아이디);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        Long cartId = 장바구니_아이템_추가되어_있음(토큰, 치킨아이디);
        ExtractableResponse<Response> response = 장바구니_삭제_요청(토큰, List.of(new CartIdRequest(cartId)));
        장바구니_삭제됨(response);
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

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().get("/auth/customer/cartItems")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String accessToken, List<CartIdRequest> cartIdRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(cartIdRequest)
                .when().delete("/auth/customer/cartItems")
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body()).isNotNull();
    }

    public static Long 장바구니_아이템_추가되어_있음(String token, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, List.of(new ProductIdRequest(productId)));
        return 장바구니목록_변환(response).get(0).getId();
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = 장바구니목록_변환(response).stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private static List<Cart> 장바구니목록_변환(ExtractableResponse<Response> response) {
        return response.body().jsonPath().getList(".", Cart.class);
    }
}
