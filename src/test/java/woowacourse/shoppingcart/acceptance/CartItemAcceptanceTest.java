package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.AcceptanceTestFixture.getMethodRequestWithBearerAuth;
import static woowacourse.AcceptanceTestFixture.postMethodRequest;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
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
import woowacourse.shoppingcart.dto.CartItemDto;
import woowacourse.shoppingcart.dto.CustomerRequest;

@DisplayName("장바구니 관련 기능")
public class CartItemAcceptanceTest extends AcceptanceTest {

    private Long productId1;
    private Long productId2;
    private String token;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, 20, "http://example.com/chicken.jpg", "imageAlt");
        productId2 = 상품_등록되어_있음("맥주", 20_000, 20, "http://example.com/beer.jpg", "imageAlt");

        final String email = "test@gmail.com";
        final String password = "password0!";
        final String username = "루나";

        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");
        final LoginRequest loginRequest = new LoginRequest(email, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest, "/api/auth/login");
        token = tokenResponse.jsonPath().getString("accessToken");

        getMethodRequestWithBearerAuth(token, "/api/customers/me");
    }

    @DisplayName("장바구니 아이템 생성")
    @Test
    void addCartItem() {
        // when
        ExtractableResponse<Response> response = 장바구니_아이템_생성_요청(token, productId1, 1);

        // then
        final Long cartItemId = Long.parseLong(response.header("Location").split("/mycarts/")[1]);
        장바구니_아이템_생성됨(response);
        장바구니_아이템_개수_일치(token, cartItemId, 1);
    }

    @DisplayName("장바구니 아이템 개수 추가")
    @Test
    void updateQuantity() {
        // given
        ExtractableResponse<Response> createResponse = 장바구니_아이템_생성_요청(token, productId1, 1);
        final Long cartItemId = Long.parseLong(createResponse.header("Location").split("/mycarts/")[1]);

        // when
        final int newQuantity = 2;
        ExtractableResponse<Response> response = 장바구니_아이템_개수_추가_요청(token, cartItemId, newQuantity);

        // then
        장바구니_아이템_개수_추가_응답됨(response);
        장바구니_아이템_개수_일치(token, cartItemId, newQuantity);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음(token, productId1);
        장바구니_아이템_추가되어_있음(token, productId2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 아이템 단건 조회")
    @Test
    void getCartItem() {
        final Long cartItemId = 장바구니_아이템_추가되어_있음(token, productId1);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_단건_조회_요청(token, cartItemId);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_일치(response, productId1);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        // given
        final Long cartItemId1 = 장바구니_아이템_추가되어_있음(token, productId1);
        final Long cartItemId2 = 장바구니_아이템_추가되어_있음(token, productId2);
        final List<Long> cartItemIds = Arrays.asList(cartItemId1, cartItemId2);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(token, cartItemIds);

        장바구니_삭제됨(response);
        장바구니_아이템_없음(token, cartItemId1);
        장바구니_아이템_없음(token, cartItemId2);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_생성_요청(final String token, final Long productId, final int quantity) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);
        requestBody.put("quantity", quantity);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/api/mycarts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_개수_추가_요청(final String token, final Long cartItemId, final int quantity) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("cartItemId", cartItemId);
        requestBody.put("quantity", quantity);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().patch("/api/mycarts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(final String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/mycarts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_단건_조회_요청(final String token, Long cartItemId) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/mycarts/" + cartItemId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(final String token, final List<Long> cartItemIds) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("cartItemIds", cartItemIds);

        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().delete("/api/mycarts")
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 장바구니_아이템_추가되어_있음(String token, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_생성_요청(token, productId, 1);
        return Long.parseLong(response.header("Location").split("/mycarts/")[1]);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_개수_추가_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", CartItemDto.class).stream()
                .map(CartItemDto::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_아이템_일치(ExtractableResponse<Response> response, Long productId) {
        final Long actual = response.jsonPath().getLong("productId");
        assertThat(actual).isEqualTo(productId);
    }

    public static void 장바구니_아이템_개수_일치(String token, Long cartItemId, int quantity) {
        ExtractableResponse<Response> response = 장바구니_아이템_목록_단건_조회_요청(token, cartItemId);
        final Long actual = response.jsonPath().getLong("quantity");
        assertThat(actual).isEqualTo(quantity);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 장바구니_아이템_없음(String token, Long cartItemId) {
        ExtractableResponse<Response> response = 장바구니_아이템_목록_단건_조회_요청(token, cartItemId);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
