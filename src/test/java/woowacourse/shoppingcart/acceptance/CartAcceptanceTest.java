package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.AcceptanceFixture.createCustomer;
import static woowacourse.AcceptanceFixture.login;
import static woowacourse.Fixture.페퍼;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.cart.CartResponse;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    private static final String USER = "puterism";
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        createCustomer(페퍼);
        ExtractableResponse<Response> login = login(페퍼_아이디, 페퍼_비밀번호);
        String accessToken = login.as(TokenResponse.class).getAccessToken();

        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId1);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        createCustomer(페퍼);
        ExtractableResponse<Response> login = login(페퍼_아이디, 페퍼_비밀번호);
        String accessToken = login.as(TokenResponse.class).getAccessToken();

        장바구니_아이템_추가되어_있음(accessToken, productId1);
        장바구니_아이템_추가되어_있음(accessToken, productId2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        createCustomer(페퍼);
        ExtractableResponse<Response> login = login(페퍼_아이디, 페퍼_비밀번호);
        String accessToken = login.as(TokenResponse.class).getAccessToken();

        Long cartId = 장바구니_아이템_추가되어_있음(accessToken, productId1);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(accessToken, cartId);

        장바구니_삭제됨(response);
    }

    @DisplayName("장바구니 수량 변경")
    @Test
    void updateCartItem() {
        createCustomer(페퍼);
        ExtractableResponse<Response> login = login(페퍼_아이디, 페퍼_비밀번호);
        String accessToken = login.as(TokenResponse.class).getAccessToken();

        Long cartId = 장바구니_아이템_추가되어_있음(accessToken, productId1);

        ExtractableResponse<Response> response = 장바구니_아이템_수량_변경_요청(accessToken, cartId, 5);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().jsonPath().getInt("quantity")).isEqualTo(5);
    }

    @DisplayName("장바구니 전체 삭제")
    @Test
    void deleteAllCartItems() {
        createCustomer(페퍼);
        ExtractableResponse<Response> login = login(페퍼_아이디, 페퍼_비밀번호);
        String accessToken = login.as(TokenResponse.class).getAccessToken();

        Long cartId1 = 장바구니_아이템_추가되어_있음(accessToken, productId1);
        Long cartId2 = 장바구니_아이템_추가되어_있음(accessToken, productId2);

        ExtractableResponse<Response> response = 장바구니_전체_삭제_요청(accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String accessToken, Long cartId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/customers/carts/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_전체_삭제_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/customers/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_수량_변경_요청(String accessToken, Long cartId, int quantity) {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("quantity", quantity);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().put("/customers/carts/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static Long 장바구니_아이템_추가되어_있음(String accessToken, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId);
        return response.as(Cart.class).getId();
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList("carts", CartResponse.class).stream()
                .map(CartResponse::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
