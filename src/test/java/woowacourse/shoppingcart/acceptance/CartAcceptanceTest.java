package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.domain.cartitem.CartItem;
import woowacourse.shoppingcart.dto.CustomerRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private static final String ID = "testx";
    private static final String PASSWORD = "1aB45678!";

    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        회원가입();
        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        String accessToken = 로그인_후_토큰_가져옴();
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId1);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음(productId1);
        장바구니_아이템_추가되어_있음(productId2);

        String accessToken = 로그인_후_토큰_가져옴();
        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        Long cartId = 장바구니_아이템_추가되어_있음(productId1);
        String accessToken = 로그인_후_토큰_가져옴();

        ExtractableResponse<Response> response = 장바구니_삭제_요청(accessToken, cartId);

        장바구니_삭제됨(response);
    }

    @DisplayName("장바구니 수량 변경")
    @Test
    void updateCartItem() {
        Long cartId = 장바구니_아이템_추가되어_있음(productId1);
        String accessToken = 로그인_후_토큰_가져옴();

        ExtractableResponse<Response> response = 장바구니_수량변경_요청(accessToken, cartId, 10);

        ExtractableResponse<Response> getResponse = 장바구니_아이템_목록_조회_요청(accessToken);
        List<CartItem> items = getResponse.body().jsonPath().getList(".", CartItem.class);
        assertThat(items.get(0).getQuantity()).isEqualTo(10);
    }

    @DisplayName("장바구니 수량이 범위를 넘어간 경우 예외를 발생시킨다.")
    @Test
    void updateCartItemExceedRange() {
        Long cartId = 장바구니_아이템_추가되어_있음(productId1);
        String accessToken = 로그인_후_토큰_가져옴();

        ExtractableResponse<Response> response = 장바구니_수량변경_요청(accessToken, cartId, 100);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private ExtractableResponse<Response> 장바구니_수량변경_요청(String accessToken, Long cartId, int quantity) {
        Map<String, Integer> param = new HashMap<>();
        param.put("quantity", quantity);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(param)
                .when().patch("/api/customers/me/carts/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(requestBody)
                .when().post("/api/customers/me/carts")
                .then().log().all()
                .extract();
    }

    public static void 회원가입() {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CustomerRequest(ID, PASSWORD))
                .when().post("/api/customers")
                .then().log().all();
    }

    public static String 로그인_후_토큰_가져옴() {
        return RestAssured
                .given().log().all()
                .body(new TokenRequest(ID, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all().extract().as(TokenResponse.class).getAccessToken();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().get("/api/customers/me/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String accessToken, Long cartId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().delete("/api/customers/me/carts/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 장바구니_아이템_추가되어_있음(Long productId) {
        String accessToken = 로그인_후_토큰_가져옴();
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId);
        return Long.parseLong(response.header("Location").split("/carts/")[1]);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", CartItem.class).stream()
                .map(CartItem::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
