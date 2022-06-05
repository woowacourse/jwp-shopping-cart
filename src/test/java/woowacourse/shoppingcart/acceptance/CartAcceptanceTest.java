package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.createCustomer;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.getTokenResponse;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_REQUEST_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_REQUEST_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_REQUEST_2;
import static woowacourse.shoppingcart.fixture.ProductFixtures.getProductRequestParam;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.AcceptanceTest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    public static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjU0MzUyNzMwLCJleHAiOjE2NTQzNTI3MzB9.OvlNgJk_dG30BL_JWj_DQRPmepqLMLl6Djwtlp2hBWw";
    public static final String INVALID_TOKEN = "invalidToken";

    private Long productId1;
    private Long productId2;
    private String token;

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(Long productId, int quantity, String token) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);
        requestBody.put("quantity", quantity);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/api/customers/cart")
                .then().log().all()
                .extract();
    }

//    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String userName) {
//        return RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when().get("/api/customers/{customerName}/carts", userName)
//                .then().log().all()
//                .extract();
//    }
//
//    public static ExtractableResponse<Response> 장바구니_삭제_요청(String userName, Long cartId) {
//        return RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when().delete("/api/customers/{customerName}/carts/{cartId}", userName, cartId)
//                .then().log().all()

//                .extract();
//    }
    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    private void 장바구니_아이템_추가_안됨_만료된_토큰(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    private void 장바구니_아이템_추가_안됨_유효하지_않은_토큰(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

    }

    private void 장바구니_아이템_추가_안됨_없는_물건(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }
//    public static Long 장바구니_아이템_추가되어_있음(String userName, Long productId) {
//        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(userName, productId);
//        return Long.parseLong(response.header("Location").split("/carts/")[1]);
//    }
//    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
//        List<Long> resultProductIds = response.jsonPath().getList(".", Cart.class).stream()
//                .map(Cart::getProductId)
//                .collect(Collectors.toList());
//        assertThat(resultProductIds).contains(productIds);
//    }

//

//    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {

//        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

//    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        createCustomer(CUSTOMER_REQUEST_1);
        token = getTokenResponse(CUSTOMER_REQUEST_1.getEmail(),
                CUSTOMER_REQUEST_1.getPassword()).getAccessToken();
        productId1 = 상품_등록되어_있음(getProductRequestParam(PRODUCT_REQUEST_1));
        productId2 = 상품_등록되어_있음(getProductRequestParam(PRODUCT_REQUEST_2));
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(productId1, 3, token);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("만료된 토큰으로 장바구니 아이템 추가 시 403 Forbidden")
    @Test
    void addCartItemByExpiredToken() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(productId1, 3, EXPIRED_TOKEN);

        장바구니_아이템_추가_안됨_만료된_토큰(response);
    }

    @DisplayName("유효하지 않은 토큰으로 장바구니 아이템 추가 시 401 Unauthorized")
    @Test
    void addCartItemByInvalidToken() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(productId1, 3, INVALID_TOKEN);

        장바구니_아이템_추가_안됨_유효하지_않은_토큰(response);
    }

    @DisplayName("없는 물건을 장바구니 아이템으로추가 시 400 Bad request")
    @Test
    void addCartItemByNotExistProduct() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(99999999L, 3, token);

        장바구니_아이템_추가_안됨_없는_물건(response);
    }

//
//    @DisplayName("장바구니 아이템 목록 조회")
//    @Test
//    void getCartItems() {
//        장바구니_아이템_추가되어_있음(USER, productId1);
//        장바구니_아이템_추가되어_있음(USER, productId2);
//
//        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(USER);
//
//        장바구니_아이템_목록_응답됨(response);
//        장바구니_아이템_목록_포함됨(response, productId1, productId2);
//    }
//
//    @DisplayName("장바구니 삭제")
//    @Test
//    void deleteCartItem() {
//        Long cartId = 장바구니_아이템_추가되어_있음(USER, productId1);
//
//        ExtractableResponse<Response> response = 장바구니_삭제_요청(USER, cartId);
//
//        장바구니_삭제됨(response);
//    }
}
