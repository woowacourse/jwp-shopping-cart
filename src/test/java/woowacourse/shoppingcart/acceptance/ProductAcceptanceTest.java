package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "leo@woowahan.com";
    private static final String PASSWORD = "Bunny1234!@";
    private static final String NAME = "leo";
    private static final String PHONE = "010-1234-5678";
    private static final String ADDRESS = "Seoul";

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        ExtractableResponse<Response> response = 상품_등록_요청("치킨", 10_000, "http://example.com/chicken.jpg");

        상품_추가됨(response);
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        ExtractableResponse<Response> response = 상품_목록_조회_요청();

        조회_응답됨(response);
        List<Long> resultProductIds = response.jsonPath().getList("products", ProductResponse.class).stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        List<Integer> resultIsStored = response.jsonPath().getList("products", ProductResponse.class).stream()
                .map(ProductResponse::getQuantity)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productId1, productId2);
        assertThat(resultIsStored).contains(0, 0);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        ExtractableResponse<Response> response = 상품_조회_요청(productId);

        조회_응답됨(response);
        상품_조회됨(response, productId);
    }

    @DisplayName("장바구니에 상품이 담겨있는 경우 상품 저장 여부와 함께 상품 목록을 조회한다")
    @Test
    void getProducts_WithCart() {
        // given
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        postWithBody("/customers", customerRequest);

        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        String accessToken = postWithBody("/auth/login", tokenRequest)
                .as(TokenResponse.class)
                .getAccessToken();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
        CartRequest cartRequestProduct1 = new CartRequest(productId1, 5);
        postWithBodyByToken("/customers/carts", accessToken, cartRequestProduct1);

        ExtractableResponse<Response> response = getByToken("/products", accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<Long> resultProductIds = response.jsonPath().getList("products", ProductResponse.class).stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        List<Integer> resultIsStored = response.jsonPath().getList("products", ProductResponse.class).stream()
                .map(ProductResponse::getQuantity)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productId1, productId2);
        assertThat(resultIsStored).containsExactly(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0);
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        ExtractableResponse<Response> response = 상품_삭제_요청(productId);

        상품_삭제됨(response);
    }

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl) {
        Product productRequest = new Product(name, price, imageUrl);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when().post("/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_목록_조회_요청() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_조회_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_삭제_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/products/{productId}", productId)
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

    public static void 상품_조회됨(ExtractableResponse<Response> response, Long productId) {
        Product resultProduct = response.as(Product.class);
        assertThat(resultProduct.getId()).isEqualTo(productId);
    }

    public static void 상품_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
