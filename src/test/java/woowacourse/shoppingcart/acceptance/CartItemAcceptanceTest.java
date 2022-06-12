package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.AcceptanceTestFixture.deleteMethodRequestWithBearerAuthAndBody;
import static woowacourse.AcceptanceTestFixture.getMethodRequestWithBearerAuth;
import static woowacourse.AcceptanceTestFixture.postMethodRequest;
import static woowacourse.AcceptanceTestFixture.postMethodRequestWithBearerAuth;

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
import woowacourse.auth.dto.LoginRequest;
import woowacourse.common.AcceptanceTest;
import woowacourse.shoppingcart.domain.ThumbnailImage;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemAddRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemDeleteRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemResponse;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;

@DisplayName("장바구니 관련 기능")
public class CartItemAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String userName, Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/api/mycarts")
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 장바구니_아이템_추가되어_있음(String userName, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(userName, productId);
        return Long.parseLong(response.header("Location").split("/cartItems/")[1]);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", CartItemResponse.class).stream()
                .map(CartItemResponse::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        ThumbnailImage chickenThumbnailImage = new ThumbnailImage("http://example.com/chicken.jpg", "chicken");
        ThumbnailImage beerThumbnailImage = new ThumbnailImage("http://example.com/beer.jpg", "chicken");

        ProductRequest chickenRequest = new ProductRequest("치킨", 1000, 10, chickenThumbnailImage);
        ProductRequest beerRequest = new ProductRequest("치킨", 1000, 10, beerThumbnailImage);

        postMethodRequest(chickenRequest, "/api/products");
        postMethodRequest(beerRequest, "/api/products");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        String token = loginAndGetToken("test@gmail.com", "password0!", "name");

        ExtractableResponse<Response> response = addCartItem(token, 1L, 10);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        String token = loginAndGetToken("test@gmail.com", "password0!", "name");

        addCartItem(token, 1L, 10);
        addCartItem(token, 2L, 10);

        ExtractableResponse<Response> response = getMethodRequestWithBearerAuth(token, "/api/mycarts");

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, 1L, 2L);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        String token = loginAndGetToken("test@gmail.com", "password0!", "name");

        addCartItem(token, 1L, 10);

        CartItemDeleteRequest cartItemDeleteRequest = new CartItemDeleteRequest(List.of(1L));
        ExtractableResponse<Response> response = deleteMethodRequestWithBearerAuthAndBody(cartItemDeleteRequest, token,
                "/api/mycarts");

        장바구니_삭제됨(response);
    }

    private String loginAndGetToken(String email, String password, String username) {
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest,
                "/api/auth/login");

        return tokenResponse.jsonPath().getString("accessToken");
    }

    private ExtractableResponse<Response> addCartItem(String token, Long productId, int quantity) {
        CartItemAddRequest firstCartItemRequest = new CartItemAddRequest(productId, quantity);
        return postMethodRequestWithBearerAuth(firstCartItemRequest, token, "/api/mycarts");
    }
}
