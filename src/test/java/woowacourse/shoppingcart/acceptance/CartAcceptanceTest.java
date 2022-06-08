package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.cart.CartAdditionRequest;
import woowacourse.shoppingcart.dto.cart.CartResponse;
import woowacourse.shoppingcart.dto.cart.CartUpdateRequest;
import woowacourse.shoppingcart.dto.product.ProductResponse;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private static final Long PRODUCT_ID_1 = 1L;
    private static final Long PRODUCT_ID_2 = 2L;

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, Long productId, int quantity) {
        CartAdditionRequest cartAdditionRequest = new CartAdditionRequest(productId, quantity);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(cartAdditionRequest)
                .when().post("/api/carts/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().get("/api/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String accessToken, Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().delete("/api/carts/products/?productId=" + productId)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", CartResponse.class).stream()
                .map(CartResponse::getProduct)
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private static ExtractableResponse<Response> 장바구니_변경_요청(String accessToken, Long productId, int quantity) {
        CartUpdateRequest cartUpdateRequest = new CartUpdateRequest(productId, quantity);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(cartUpdateRequest)
                .when().patch("/api/carts/products")
                .then().log().all()
                .extract();
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        회원가입(파리채);
        String accessToken = 로그인_후_토큰발급(파리채토큰);
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, PRODUCT_ID_1, 1);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        String accessToken = 회원가입_로그인_장바구니_추가_후_토큰발급();

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, PRODUCT_ID_1, PRODUCT_ID_2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        String accessToken = 회원가입_로그인_장바구니_추가_후_토큰발급();

        ExtractableResponse<Response> response = 장바구니_삭제_요청(accessToken, PRODUCT_ID_1);

        장바구니_삭제됨(response);
        장바구니_아이템_목록_포함됨(response, PRODUCT_ID_2);
    }

    @DisplayName("장바구니 변경")
    @Test
    void updateCartItem() {
        String accessToken = 회원가입_로그인_장바구니_추가_후_토큰발급();

        ExtractableResponse<Response> response = 장바구니_변경_요청(accessToken, PRODUCT_ID_1, 2);

        int quantity = response.jsonPath().getList(".", CartResponse.class).stream()
                .filter(cart -> cart.getProduct().getId().equals(PRODUCT_ID_1))
                .findFirst()
                .orElseThrow()
                .getQuantity();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(quantity).isEqualTo(2)
        );
    }

    private String 회원가입_로그인_장바구니_추가_후_토큰발급() {
        회원가입(파리채);
        String accessToken = 로그인_후_토큰발급(파리채토큰);

        장바구니_아이템_추가_요청(accessToken, PRODUCT_ID_1, 1);
        장바구니_아이템_추가_요청(accessToken, PRODUCT_ID_2, 1);
        return accessToken;
    }
}
