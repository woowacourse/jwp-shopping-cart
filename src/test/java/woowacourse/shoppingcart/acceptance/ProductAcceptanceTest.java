package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.fixture.PasswordFixture.plainBasicPassword;
import static woowacourse.fixture.TokenFixture.BEARER;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.request.ProductAddRequest;
import woowacourse.shoppingcart.dto.response.ProductResponse;

@DisplayName("상품 관련 기능")
@SuppressWarnings("NonAsciiCharacters")
public class ProductAcceptanceTest extends AcceptanceShoppingCartTest {

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        ExtractableResponse<Response> response = 상품_등록_요청("치킨", 10_000, "http://example.com/chicken.jpg");

        상품_추가됨(response);
    }

    @DisplayName("로그인이 안된 상태로 상품 목록을 조회하면, 카트 정보가 cartId: null, quantity: 0이 포함된다.")
    @Test
    void getProductsWithOutLogin() {
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        ExtractableResponse<Response> response = 비로그인_상품_목록_조회_요청();

        조회_응답됨(response);
        상품_목록_포함됨(productId1, productId2, response);
        상품_목록_카트_아이디_포함됨(null, null, response);
        상품_목록_카트_수량_포함됨(0, 0, response);
    }

    @DisplayName("로그인이 된 상태로 상품 목록을 조회하면, 카트 정보가 cartId, quantity 정보가 포함된다.")
    @Test
    void getProducts() {
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        CustomerRequest signUpRequest = new CustomerRequest("giron", plainBasicPassword);
        회원가입_요청(signUpRequest);

        TokenRequest tokenRequest = new TokenRequest("giron", plainBasicPassword);
        TokenResponse tokenResponse = 로그인_요청_토큰_생성됨(tokenRequest);
        final String accessToken = tokenResponse.getAccessToken();

        final Long cartId = 장바구니_아이템_추가되어_있음(accessToken, productId1);

        ExtractableResponse<Response> response = 로그인된_상품_목록_조회_요청(accessToken);

        조회_응답됨(response);
        상품_목록_포함됨(productId1, productId2, response);
        상품_목록_카트_아이디_포함됨(cartId, null, response);
        상품_목록_카트_수량_포함됨(1, 0, response);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        ExtractableResponse<Response> response = 상품_조회_요청(productId);

        조회_응답됨(response);
        상품_조회됨(response, productId);
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        ExtractableResponse<Response> response = 상품_삭제_요청(productId);

        상품_삭제됨(response);
    }

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl) {
        ProductAddRequest productAddRequest = new ProductAddRequest(name, price, imageUrl);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productAddRequest)
                .when().post("/api/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 비로그인_상품_목록_조회_요청() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인된_상품_목록_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .when().get("/api/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_조회_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_삭제_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static void 상품_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 상품_등록되어_있음(String name, int price, String imageUrl) {
        ExtractableResponse<Response> response = 상품_등록_요청(name, price, imageUrl);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static void 조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 상품_목록_포함됨(Long productId1, Long productId2, ExtractableResponse<Response> response) {
        List<Long> resultProductIds = response.jsonPath().getList(".", ProductResponse.class).stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productId1, productId2);
    }

    public static void 상품_목록_카트_아이디_포함됨(Long cartId1, Long cartId2, ExtractableResponse<Response> response) {
        List<Long> resultCartIds = response.jsonPath().getList(".", ProductResponse.class).stream()
                .map(ProductResponse::getCartId)
                .collect(Collectors.toList());
        assertThat(resultCartIds).contains(cartId1, cartId2);
    }

    public static void 상품_목록_카트_수량_포함됨(int quantity1, int quantity2, ExtractableResponse<Response> response) {
        List<Integer> resultCartQuantities = response.jsonPath().getList(".", ProductResponse.class).stream()
                .map(ProductResponse::getQuantity)
                .collect(Collectors.toList());
        assertThat(resultCartQuantities).contains(quantity1, quantity2);
    }

    public static void 상품_조회됨(ExtractableResponse<Response> response, Long productId) {
        ProductResponse productResponse = response.as(ProductResponse.class);
        assertThat(productResponse.getId()).isEqualTo(productId);
    }

    public static void 상품_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
