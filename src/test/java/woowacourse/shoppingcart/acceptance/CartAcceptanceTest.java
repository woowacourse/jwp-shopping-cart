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
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CreateCustomerRequest;
import woowacourse.shoppingcart.dto.UpdateQuantityRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    private static final String EMAIL = "awesomeo@gmail.com";
    private static final String NICKNAME = "awesome";
    private static final String PASSWORD = "Password123!";

    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        createCustomer(new CreateCustomerRequest(EMAIL, NICKNAME, PASSWORD));
        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        String accessToken = loginAndGetAccessToken(new TokenRequest(EMAIL, PASSWORD));

        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId1);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        String accessToken = loginAndGetAccessToken(new TokenRequest(EMAIL, PASSWORD));

        장바구니_아이템_추가_요청(accessToken, productId1);
        장바구니_아이템_추가_요청(accessToken, productId2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);

        List<CartItemResponse> list = response.body().jsonPath().getList(".", CartItemResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(list).usingRecursiveComparison().isEqualTo(
                        List.of(new CartItemResponse(productId1, "치킨", 10_000, 1, "http://example.com/chicken.jpg"),
                                new CartItemResponse(productId2, "맥주", 20_000, 1, "http://example.com/beer.jpg"))
                )
        );
    }

    @DisplayName("장바구니에 담긴 상품의 수량을 변경한다.")
    @Test
    void addCartItemQuantity() {
        String accessToken = loginAndGetAccessToken(new TokenRequest(EMAIL, PASSWORD));
        장바구니_아이템_추가_요청(accessToken, productId1);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UpdateQuantityRequest(2))
                .when().log().all()
                .patch("/api/carts/products/{productId}", productId1)
                .then().log().all()
                .extract();

        ExtractableResponse<Response> cartResponse = 장바구니_아이템_목록_조회_요청(accessToken);
        List<CartItemResponse> list = cartResponse.body().jsonPath().getList(".", CartItemResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(list).usingRecursiveComparison().isEqualTo(
                        List.of(new CartItemResponse(productId1, "치킨", 10_000, 2, "http://example.com/chicken.jpg"))
                )
        );
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
//        String accessToken = loginAndGetAccessToken(new TokenRequest(EMAIL, PASSWORD));
//        Long cartId = 장바구니_아이템_추가되어_있음(USER, productId1);
//
//        ExtractableResponse<Response> response = 장바구니_삭제_요청(USER, cartId);
//
//        장바구니_삭제됨(response);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().post("/api/carts/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/carts")
                .then().log().all()
                .extract();
    }

    public static Long 장바구니_아이템_추가되어_있음(String userName, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(userName, productId);
        return Long.parseLong(response.header("Location").split("/carts/")[1]);
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String userName, Long cartId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/{customerName}/carts/{cartId}", userName, cartId)
                .then().log().all()
                .extract();
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
