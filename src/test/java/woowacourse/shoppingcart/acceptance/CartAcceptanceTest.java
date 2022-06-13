package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.BAD_REQUEST;
import static woowacourse.Fixtures.FORBIDDEN;
import static woowacourse.Fixtures.로그인;
import static woowacourse.Fixtures.예외메세지_검증;
import static woowacourse.Fixtures.조조그린_로그인_요청;
import static woowacourse.Fixtures.조조그린_요청;
import static woowacourse.Fixtures.치킨;
import static woowacourse.Fixtures.피자;
import static woowacourse.Fixtures.헌치_로그인_요청;
import static woowacourse.Fixtures.헌치_요청;
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
import woowacourse.shoppingcart.dto.CartIdRequest;
import woowacourse.shoppingcart.dto.CartProductInfoRequest;
import woowacourse.shoppingcart.dto.CartProductInfoResponse;
import woowacourse.shoppingcart.dto.CartResponse;
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

    @DisplayName("로그인한 회원의 장바구니 목록을 조회한다.")
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음(토큰, 치킨아이디);
        장바구니_아이템_추가되어_있음(토큰, 피자아이디);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(토큰);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, 치킨아이디, 피자아이디);
    }

    @DisplayName("토큰이 잘못될 경우 요청할 수 없다.")
    @Test
    void getCartItems_tokenError() {
        장바구니_아이템_추가되어_있음(토큰, 피자아이디);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청("12345");

        FORBIDDEN(response);
        예외메세지_검증(response, "토큰이 유효하지 않습니다.");
    }

    @DisplayName("로그인한 회원의 장바구니에 해당 상품이 있는 경우 상품 개수를 1 증가시킨다.")
    @Test
    void addCartItem_PlusOne() {
        List<ProductIdRequest> 물품추가요청 = List.of(new ProductIdRequest(치킨아이디));
        장바구니_아이템_추가_요청(토큰, 물품추가요청);
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(토큰, 물품추가요청);

        장바구니_아이템_추가됨(response);
        List<CartProductInfoResponse> cartProductInfoResponses = response.body().jsonPath()
                .getList(".", CartProductInfoResponse.class);
        assertThat(cartProductInfoResponses.get(0).getQuantity())
                .isEqualTo(2);
    }

    @DisplayName("로그인한 회원의 장바구니에 해당 상품이 없는 경우 상품을 추가한다.")
    @Test
    void addCartItem() {
        List<ProductIdRequest> 물품추가요청 = List.of(new ProductIdRequest(치킨아이디));
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(토큰, 물품추가요청);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("토큰이 잘못될 경우 요청할 수 없다.")
    @Test
    void addCartItem_tokenError() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청("1234", List.of(new ProductIdRequest(치킨아이디)));

        FORBIDDEN(response);
        예외메세지_검증(response, "토큰이 유효하지 않습니다.");
    }

    @DisplayName("로그인 한 회원의 장바구니 정보(각 아이디 별 물품 개수)를 수정한다.")
    @Test
    void updateCartItems() {
        장바구니_아이템_추가_요청(토큰, List.of(new ProductIdRequest(치킨아이디)));
        int quantity = 2;

        장바구니_아이템_수정됨(장바구니_아이템_수정_요청(토큰, new CartProductInfoRequest(1L, quantity)));
    }

    @DisplayName("없는 카트 아이디로 수정할 수 없다.")
    @Test
    void updateCartItems_cartIdError() {
        장바구니_아이템_추가_요청(토큰, List.of(new ProductIdRequest(치킨아이디)));
        int quantity = 2;
        ExtractableResponse<Response> response = 장바구니_아이템_수정_요청(토큰, new CartProductInfoRequest(105L, quantity));

        BAD_REQUEST(response);
        예외메세지_검증(response, "올바르지 않은 사용자 이름이거나 상품 아이디 입니다.");
    }

    @DisplayName("토큰이 잘못될 경우 요청할 수 없다.")
    @Test
    void updateCartItems_tokenError() {
        장바구니_아이템_추가_요청(토큰, List.of(new ProductIdRequest(치킨아이디)));

        ExtractableResponse<Response> response = 장바구니_아이템_수정_요청("1234",
                new CartProductInfoRequest(1L, 2));

        FORBIDDEN(response);
        예외메세지_검증(response, "토큰이 유효하지 않습니다.");
    }

    @DisplayName("로그인 한 회원의 장바구니에서 해당 아이디의 물품을 제거한다.")
    @Test
    void deleteCartItem() {
        Long cartId = 장바구니_아이템_추가되어_있음(토큰, 치킨아이디);
        ExtractableResponse<Response> response = 장바구니_삭제_요청(토큰, List.of(new CartIdRequest(cartId)));
        장바구니_삭제됨(response);
    }

    @DisplayName("해당 유저에게 없는 카트 아이디로 제거할 수 없다.")
    @Test
    void deleteCartItem_cartIdError() {
        회원가입(헌치_요청);
        String 헌치토큰 = 로그인(헌치_로그인_요청)
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        Long cartId = 장바구니_아이템_추가되어_있음(토큰, 치킨아이디);
        ExtractableResponse<Response> response = 장바구니_삭제_요청(헌치토큰, List.of(new CartIdRequest(cartId)));
        BAD_REQUEST(response);
        예외메세지_검증(response, "장바구니 아이템이 없습니다.");
    }

    @DisplayName("토큰이 잘못될 경우 요청할 수 없다.")
    @Test
    void deleteCartItem_tokenError() {
        Long cartId = 장바구니_아이템_추가되어_있음(토큰, 치킨아이디);
        ExtractableResponse<Response> response = 장바구니_삭제_요청("1234", List.of(new CartIdRequest(cartId)));

        FORBIDDEN(response);
        예외메세지_검증(response, "토큰이 유효하지 않습니다.");
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

    public static ExtractableResponse<Response> 장바구니_아이템_수정_요청(String accessToken,
                                                               CartProductInfoRequest cartProductInfoRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(cartProductInfoRequest)
                .when().patch("/auth/customer/cartItems")
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

    public static void 장바구니_아이템_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().as(CartProductInfoResponse.class)).isNotNull();
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
                .map(CartResponse::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private static List<CartResponse> 장바구니목록_변환(ExtractableResponse<Response> response) {
        return response.body().jsonPath().getList(".", CartResponse.class);
    }
}
