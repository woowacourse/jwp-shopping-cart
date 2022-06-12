package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.createCustomer;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.getTokenResponse;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;
import static woowacourse.shoppingcart.fixture.CartItemFixtures.CART_REQUEST_1;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_REQUEST_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_2;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_REQUEST_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_REQUEST_2;
import static woowacourse.shoppingcart.fixture.ProductFixtures.getProductRequestParam;

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
import woowacourse.AcceptanceTest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.ProductExistenceResponse;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    public static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjU0MzUyNzMwLCJleHAiOjE2NTQzNTI3MzB9.OvlNgJk_dG30BL_JWj_DQRPmepqLMLl6Djwtlp2hBWw";
    public static final String INVALID_TOKEN = "invalidToken";
    public static final int PRODUCT_QUANTITY_1 = 3;
    public static final int PRODUCT_QUANTITY_2 = 100;

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

    public static Long 장바구니_아이템_추가되어_있음(Long productId, int quantity, String token) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(productId, quantity, token);
        return Long.parseLong(response.header("Location").split("/cart/")[1]);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer" + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/cart")
                .then().log().all()
                .extract();
    }


    public static ExtractableResponse<Response> 장바구니_삭제_요청(Long cartId, String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer" + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/cart/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 장바구니_아이템_확인_요청(Long productId, String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer" + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/cart/existence/{productId}", productId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 장바구니_아이템_수정_요청(Long cartItemId, String token,
                                                         CartRequest cartRequest) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer" + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequest)
                .when().put("/api/customers/cart/{cartItemId}", cartItemId)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    private void 만료된_토큰으로_요청시_확인(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    private void 유효하지_않은_토큰으로_요청시_확인(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

    }

    private void 장바구니_아이템_추가_안됨_없는_물건(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 장바구니_아이템_목록_확인(ExtractableResponse<Response> response, Long... cartItemId) {
        final List<CartItemResponse> cartItemResponses = response.jsonPath().getList(".", CartItemResponse.class);

        final List<Long> cartItemIds = cartItemResponses.stream()
                .map(CartItemResponse::getCartItemId)
                .collect(Collectors.toList());

        final List<ProductResponse> productResponses = cartItemResponses
                .stream()
                .map(CartItemResponse::getProduct).collect(
                        Collectors.toList());
        final List<Integer> quantities = cartItemResponses.stream().map(CartItemResponse::getQuantity)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(cartItemIds).containsExactly(cartItemId[0], cartItemId[1]),
                () -> assertThat(productResponses).extracting("name", "price", "imageUrl", "description", "stock")
                        .containsExactly(
                                tuple(PRODUCT_1.getName(), PRODUCT_1.getPrice().getValue(),
                                        PRODUCT_1.getImageUrl(), PRODUCT_1.getDescription(),
                                        PRODUCT_1.getStock().getValue()),
                                tuple(PRODUCT_2.getName(), PRODUCT_2.getPrice().getValue(),
                                        PRODUCT_2.getImageUrl(), PRODUCT_2.getDescription(),
                                        PRODUCT_2.getStock().getValue())
                        ),
                () -> assertThat(quantities).hasSize(2).containsExactly(PRODUCT_QUANTITY_1, PRODUCT_QUANTITY_2)

        );
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private void 장바구니_삭제_안됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }

    private void 장바구니_아이템_확인됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getObject(".", ProductExistenceResponse.class).getExists()).isTrue();
    }

    private void 장바구니__확인(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getObject(".", ProductExistenceResponse.class).getExists()).isFalse();
    }

    private void 장바구니_수정_확인(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private void 장바구니_수정_실패_확인(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

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
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(productId1, PRODUCT_QUANTITY_1, token);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("만료된 토큰으로 장바구니 아이템 추가 시 403 Forbidden")
    @Test
    void addCartItemByExpiredToken() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(productId1, PRODUCT_QUANTITY_1, EXPIRED_TOKEN);

        만료된_토큰으로_요청시_확인(response);
    }

    @DisplayName("유효하지 않은 토큰으로 장바구니 아이템 추가 시 401 Unauthorized")
    @Test
    void addCartItemByInvalidToken() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(productId1, PRODUCT_QUANTITY_1, INVALID_TOKEN);

        유효하지_않은_토큰으로_요청시_확인(response);
    }

    @DisplayName("없는 물건을 장바구니 아이템으로추가 시 400 Bad request")
    @Test
    void addCartItemByNotExistProduct() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(99999999L, PRODUCT_QUANTITY_1, token);

        장바구니_아이템_추가_안됨_없는_물건(response);
    }


    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        final Long cartItemId1 = 장바구니_아이템_추가되어_있음(productId1, PRODUCT_QUANTITY_1, token);
        final Long cartItemId2 = 장바구니_아이템_추가되어_있음(productId2, PRODUCT_QUANTITY_2, token);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_확인(response, cartItemId1, cartItemId2);
    }

    @DisplayName("유효하지 않은 토큰으로 장바구니 아이템 목록 조회시 401 Unauthorized")
    @Test
    void getCartItemsByInvalidToken() {
        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(INVALID_TOKEN);

        유효하지_않은_토큰으로_요청시_확인(response);
    }

    @DisplayName("만료된 토큰으로 장바구니 아이템 목록 조회시 403 Forbidden")
    @Test
    void getCartItemsByExpiredToken() {
        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(EXPIRED_TOKEN);

        만료된_토큰으로_요청시_확인(response);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        Long cartId = 장바구니_아이템_추가되어_있음(productId1, PRODUCT_QUANTITY_1, token);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(cartId, token);

        장바구니_삭제됨(response);
    }

    @DisplayName("장바구니 삭제 안됨")
    @Test
    void deleteCartItemByInvalidCartId() {
        Long cartId = 장바구니_아이템_추가되어_있음(productId1, PRODUCT_QUANTITY_1, token);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(cartId + 1, token);

        장바구니_삭제_안됨(response);
    }

    @DisplayName("장바구니에 해당 상품이 존재하는 지 조회한다.")
    @Test
    public void hasExistProduct() {
        // given
        장바구니_아이템_추가되어_있음(productId1, PRODUCT_QUANTITY_1, token);

        // when
        final ExtractableResponse<Response> response = 장바구니_아이템_확인_요청(productId1, token);

        // then
        장바구니_아이템_확인됨(response);
    }

    @DisplayName("장바구니에 해당 상품이 존재하지 않는 지 조회한다.")
    @Test
    public void hasNotExistProduct() {
        // given
        장바구니_아이템_추가되어_있음(productId1, PRODUCT_QUANTITY_1, token);

        // when
        final ExtractableResponse<Response> response = 장바구니_아이템_확인_요청(productId2, token);

        // then
        장바구니__확인(response);
    }

    @DisplayName("장바구니를 수정한다.")
    @Test
    public void updateCart() {
        // given
        Long cartId = 장바구니_아이템_추가되어_있음(productId1, PRODUCT_QUANTITY_1, token);

        // when
        final ExtractableResponse<Response> response = 장바구니_아이템_수정_요청(cartId, token, new CartRequest(productId1, 3));

        // then
        장바구니_수정_확인(response);
    }

    @DisplayName("잘못된 상품이 전달될 시 400 Bad Request")
    @Test
    public void updateCartByInvalidProduct() {
        // given
        Long cartId = 장바구니_아이템_추가되어_있음(productId1, PRODUCT_QUANTITY_1, token);

        // when
        final CartRequest invalidCartRequest = new CartRequest(productId2, 99);

        final ExtractableResponse<Response> response = 장바구니_아이템_수정_요청(cartId, token, invalidCartRequest);

        // then
        장바구니_수정_실패_확인(response);
    }
}
