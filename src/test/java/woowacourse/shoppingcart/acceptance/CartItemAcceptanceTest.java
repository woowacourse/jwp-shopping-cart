package woowacourse.shoppingcart.acceptance;

import static Fixture.CustomerFixtures.*;
import static Fixture.ProductFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import Fixture.SimpleRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.dto.CartItemSaveRequest;
import woowacourse.shoppingcart.dto.product.JsonToProduct;

@DisplayName("장바구니 관련 기능")
public class CartItemAcceptanceTest extends AcceptanceTest {

    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        SimpleRestAssured.saveCustomer(YAHO_SAVE_REQUEST);

        productId1 = 상품_등록되어_있음(CHICKEN_REQUEST);
        productId2 = 상품_등록되어_있음(BEER_REQUEST);
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        String accessToken = SimpleRestAssured.getAccessToken(YAHO_TOKEN_REQUEST);

        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId1, 10);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 추가 - 같은 상품을 반복해서 추가하면 quantity 가 추가된다.")
    @Test
    void addCartItem_duplicate() {
        String accessToken = SimpleRestAssured.getAccessToken(YAHO_TOKEN_REQUEST);

        장바구니_아이템_추가_요청(accessToken, productId1, 10);
        장바구니_아이템_추가_요청(accessToken, productId1, 10);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);
        List<Integer> quantities = response.jsonPath().getList("quantity", Integer.class);

        assertAll(
                () -> assertThat(quantities.size()).isEqualTo(1),
                () -> assertThat(quantities.get(0)).isEqualTo(20)
        );

    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        String accessToken = SimpleRestAssured.getAccessToken(YAHO_TOKEN_REQUEST);

        장바구니_아이템_추가되어_있음(accessToken, productId1, 10);
        장바구니_아이템_추가되어_있음(accessToken, productId2, 10);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        String accessToken = SimpleRestAssured.getAccessToken(YAHO_TOKEN_REQUEST);
        Long cartId = 장바구니_아이템_추가되어_있음(accessToken, productId1, 10);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(accessToken, cartId);

        장바구니_삭제됨(response);
    }

    @DisplayName("장바구니 수량 수정")
    @Test
    void updateCartItem() {
        String accessToken = SimpleRestAssured.getAccessToken(YAHO_TOKEN_REQUEST);
        Long cartId = 장바구니_아이템_추가되어_있음(accessToken, productId1, 10);

        ExtractableResponse<Response> response = 장바구니_수량_수정_요청(accessToken, cartId, 5);

        장바구니_수량_수정됨(response);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, Long productId, int quantity) {
        CartItemSaveRequest request = new CartItemSaveRequest(productId, quantity);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers/me/cart-items")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me/cart-items")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String accessToken, Long cartItemId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/me/cart-items/{cartItemId}", cartItemId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_수량_수정_요청(String accessToken, Long cartItemId, int quantity) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(quantity)
                .when().patch("/api/customers/me/cart-items/{cartItemId}", cartItemId)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 장바구니_아이템_추가되어_있음(String accessToken, Long productId, int quantity) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId, quantity);
        return Long.parseLong(response.header("Location").split("/cart-items/")[1]);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList("product", JsonToProduct.class).stream()
                .map(JsonToProduct::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 장바구니_수량_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
