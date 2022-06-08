package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.request.CreateProductRequest;
import woowacourse.shoppingcart.dto.response.ProductResponse;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        ExtractableResponse<Response> response = 상품_등록_요청("치킨", 10_000, "http://example.com/chicken.jpg");

        상품_추가됨(response);
    }

    @Test
    void 로그인하지_않고_상품_목록_조회() {
        // given
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        // when
        ExtractableResponse<Response> response = 로그인하지_않고_상품_목록_조회_요청();

        // then
        List<ProductResponse> productResponses = response.body().jsonPath()
                .getList("$", ProductResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(productResponses.get(0).getId()).isEqualTo(productId1),
                () -> assertThat(productResponses.get(0).getCartId()).isNull(),
                () -> assertThat(productResponses.get(0).getQuantity()).isEqualTo(0),
                () -> assertThat(productResponses.get(1).getId()).isEqualTo(productId2),
                () -> assertThat(productResponses.get(1).getCartId()).isNull(),
                () -> assertThat(productResponses.get(1).getQuantity()).isEqualTo(0)
        );
    }

    @Test
    void 로그인_후_상품_목록_조회() {
        // given
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        String accessToken = 로그인_및_토큰_발급("puterism", "Shopping123!");

        장바구니_아이템_추가_요청(accessToken, productId1, 10);

        // when
        ExtractableResponse<Response> response = 로그인_후_상품_목록_조회_요청(accessToken);

        // then
        List<ProductResponse> productResponses = response.body().jsonPath()
                .getList("$", ProductResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(productResponses.get(0).getId()).isEqualTo(productId1),
                () -> assertThat(productResponses.get(0).getCartId()).isNotNull(),
                () -> assertThat(productResponses.get(0).getQuantity()).isEqualTo(1),
                () -> assertThat(productResponses.get(1).getId()).isEqualTo(productId2),
                () -> assertThat(productResponses.get(1).getCartId()).isNull(),
                () -> assertThat(productResponses.get(1).getQuantity()).isEqualTo(0)
        );
    }

    @Test
    void 로그인하지_않고_상품_조회() {
        // given
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        // when
        ExtractableResponse<Response> response = 로그인하지_않고_상품_조회_요청(productId);

        // then
        ProductResponse productResponse = response.as(ProductResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(productResponse.getId()).isEqualTo(productId),
                () -> assertThat(productResponse.getCartId()).isNull(),
                () -> assertThat(productResponse.getQuantity()).isEqualTo(0)
        );
    }

    @Test
    void 로그인_후_상품_조회() {
        // given
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        String accessToken = 로그인_및_토큰_발급("puterism", "Shopping123!");

        장바구니_아이템_추가_요청(accessToken, productId, 10);

        // when
        ExtractableResponse<Response> response = 로그인_후_상품_조회_요청(accessToken, productId);

        // then
        ProductResponse productResponse = response.as(ProductResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(productResponse.getId()).isEqualTo(productId),
                () -> assertThat(productResponse.getCartId()).isNotEqualTo(0L),
                () -> assertThat(productResponse.getQuantity()).isEqualTo(1)
        );
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        ExtractableResponse<Response> response = 상품_삭제_요청(productId);

        상품_삭제됨(response);
    }

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl) {
        CreateProductRequest request = new CreateProductRequest(name, price, imageUrl);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/products")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 로그인하지_않고_상품_목록_조회_요청() {
        return RestAssured
                .given().log().all()
                .when().get("/api/products")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 로그인_후_상품_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/api/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인하지_않고_상품_조회_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 로그인_후_상품_조회_요청(String accessToken, Long productId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
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

    private String 로그인_및_토큰_발급(String name, String password) {
        return RestAssured
                .given().log().all()
                .body(new TokenRequest(name, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all().extract()
                .as(TokenResponse.class).getAccessToken();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, Long productId, int quantity) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);
        requestBody.put("quantity", quantity);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/api/customers/me/carts")
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

    public static void 상품_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
