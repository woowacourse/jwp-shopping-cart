package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.acceptance.AuthAcceptanceTest.로그인_후_토큰_획득;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가되어_있음;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.회원_추가되어_있음;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl) {
        Product productRequest = new Product(name, imageUrl, price);
        return requestHttpPost("", productRequest, "/products").extract();
    }

    public static ExtractableResponse<Response> 상품_목록_조회_요청_회원(String token) {
        return requestHttpGet(token, "/products").extract();
    }

    public static ExtractableResponse<Response> 상품_목록_조회_요청_비회원() {
        return requestHttpGet("", "/products").extract();
    }

    public static ExtractableResponse<Response> 상품_조회_요청(Long productId) {
        return requestHttpGet("", "/products/" + productId).extract();
    }

    public static ExtractableResponse<Response> 상품_삭제_요청(Long productId) {
        return requestHttpDelete("", "/products/" + productId).extract();
    }

    public static void 상품_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static Long 상품_등록되어_있음(String name, int price, String imageUrl) {
        ExtractableResponse<Response> response = 상품_등록_요청(name, price, imageUrl);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static void 조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 상품_목록_포함됨(Long productId1, Long productId2, ExtractableResponse<Response> response) {
        ProductsResponse products = response.jsonPath().getObject(".", ProductsResponse.class);
        List<Long> resultProductIds = products.getProducts().stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productId1, productId2);
    }

    public static void 장바구니_아이템별_수량_확인됨(ExtractableResponse<Response> response) {
        ProductsResponse products = response.jsonPath().getObject(".", ProductsResponse.class);
        List<Integer> result = products.getProducts().stream()
                .map(ProductResponse::getQuantity)
                .collect(Collectors.toList());

        assertThat(result).containsExactly(10, 0);
    }

    public static void 장바구니_아이템_수량이_모두_0(ExtractableResponse<Response> response) {
        ProductsResponse products = response.jsonPath().getObject(".", ProductsResponse.class);
        List<Integer> result = products.getProducts().stream()
                .map(ProductResponse::getQuantity)
                .collect(Collectors.toList());

        assertThat(result).containsExactly(0, 0);
    }

    public static void 상품_조회됨(ExtractableResponse<Response> response, Long productId) {
        Product resultProduct = response.as(Product.class);
        assertThat(resultProduct.getId()).isEqualTo(productId);
    }

    public static void 상품_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        ExtractableResponse<Response> response = 상품_등록_요청("치킨", 10_000, "http://example.com/chicken.jpg");

        상품_추가됨(response);
    }

    @DisplayName("상품 목록을 회원이 조회한다")
    @Test
    void getProductsByMember() {
        // given
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        회원_추가되어_있음();
        String accessToken = 로그인_후_토큰_획득();
        장바구니_아이템_추가되어_있음(accessToken, new CartRequest(productId1, 10));

        ExtractableResponse<Response> response = 상품_목록_조회_요청_회원(accessToken);

        조회_응답됨(response);
        상품_목록_포함됨(productId1, productId2, response);
        장바구니_아이템별_수량_확인됨(response);
    }

    @DisplayName("상품 목록을 비회원이 조회한다")
    @Test
    void getProductsByNonMember() {
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        ExtractableResponse<Response> response = 상품_목록_조회_요청_비회원();

        조회_응답됨(response);
        상품_목록_포함됨(productId1, productId2, response);
        장바구니_아이템_수량이_모두_0(response);
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
}
