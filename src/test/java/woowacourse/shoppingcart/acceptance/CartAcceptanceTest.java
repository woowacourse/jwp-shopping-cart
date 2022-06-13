package woowacourse.shoppingcart.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.acceptance.AcceptanceTestFixture.코린;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품을_등록함;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private Long productId1;
    private Long productId2;
    private String accessToken;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        post("/signup", 코린);
        final ExtractableResponse<Response> signinResponse = post("/signin", new TokenRequest("hamcheeseburger", "Password123!"));
        accessToken = signinResponse.response().jsonPath().getString("accessToken");

        productId1 = 상품을_등록함("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품을_등록함("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId1);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음(accessToken, productId1);
        장바구니_아이템_추가되어_있음(accessToken, productId2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        장바구니_아이템_추가되어_있음(accessToken, productId1);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(accessToken, productId1);

        장바구니_삭제됨(response);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);

        return post("/customers/cart", accessToken, requestBody);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return get("/customers/cart", accessToken);
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String accessToken, long productId) {
        return delete("/customers/cart", accessToken, new CartRequest(productId));
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static void 장바구니_아이템_추가되어_있음(String accessToken, Long productId) {
        장바구니_아이템_추가_요청(accessToken, productId);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList("cart", ProductResponse.class).stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
