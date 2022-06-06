package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;

import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.fixture.SimpleResponse;
import woowacourse.fixture.SimpleRestAssured;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemsResponse;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    private static final String USERNAME = "puterism";
    private static final String PASSWORD = "password123!";
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
        SimpleResponse response = 장바구니_아이템_추가_요청(productId1);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음(productId1);
        장바구니_아이템_추가되어_있음(productId2);

        SimpleResponse response = 장바구니_아이템_목록_조회_요청();

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        장바구니_아이템_추가되어_있음(productId1);

        SimpleResponse response = 장바구니_상품_삭제_요청(productId1);

        장바구니_삭제됨(response);
    }

    @DisplayName("장바구니 비우기")
    @Test
    void deleteCart() {
        장바구니_아이템_추가되어_있음(productId1);

        SimpleResponse response = 장바구니_비우기_요청();

        장바구니_삭제됨(response);
    }

    public static SimpleResponse 장바구니_아이템_추가_요청(Long productId) {
        return SimpleRestAssured.postWithToken("/cart/" + productId, getTokenByLogin());
    }

    public static SimpleResponse 장바구니_아이템_목록_조회_요청() {
        return SimpleRestAssured.getWithToken("/cart", getTokenByLogin());
    }

    public static SimpleResponse 장바구니_상품_삭제_요청(Long productId) {
        Map<String, List<Long>> params = Map.of("productIds", List.of(productId));
        return SimpleRestAssured.deleteWithToken("/cart/products", getTokenByLogin(), params);
    }

    public static SimpleResponse 장바구니_비우기_요청() {
        return SimpleRestAssured.deleteWithToken("/cart", getTokenByLogin());
    }

    public static void 장바구니_아이템_추가됨(SimpleResponse response) {
        response.assertStatus(HttpStatus.OK);
    }

    public static void 장바구니_아이템_추가되어_있음(Long productId) {
        장바구니_아이템_추가_요청(productId);
    }

    public static void 장바구니_아이템_목록_응답됨(SimpleResponse response) {
        response.assertStatus(HttpStatus.OK);
    }

    public static void 장바구니_아이템_목록_포함됨(SimpleResponse response, Long... productIds) {
        List<Long> resultProductIds = response.toObject(CartItemsResponse.class)
                .getCartItems().stream()
                .map(CartItemResponse::getProduct)
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(SimpleResponse response) {
        response.assertStatus(HttpStatus.NO_CONTENT);
    }

    private static String getTokenByLogin() {
        TokenRequest tokenRequest = new TokenRequest(USERNAME, PASSWORD);
        return SimpleRestAssured.post("/login", tokenRequest)
                .toObject(TokenResponse.class)
                .getAccessToken();
    }
}
