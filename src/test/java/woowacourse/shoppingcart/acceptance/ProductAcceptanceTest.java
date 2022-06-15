package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.ui.product.dto.request.ProductRegisterRequest;
import woowacourse.shoppingcart.ui.product.dto.response.ProductResponse;
import woowacourse.shoppingcart.ui.product.dto.response.ProductsResponse;

@DisplayName("상품 관련 기능")
class ProductAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 상품_등록_요청(final String name, final int price, final String imageUrl) {
        final ProductRegisterRequest productRequest = new ProductRegisterRequest(name, price, imageUrl);

        return RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).body(productRequest).when()
                .post("/api/products").then().log().all().extract();
    }

    public static ExtractableResponse<Response> 상품_조회_요청(final Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_목록_조회_요청(final int page, int limit) {
        final Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("limit", 10);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .params(params)
                .when().get("/api/products")
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

    public static void 상품_추가됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 상품_등록되어_있음(final String name, final int price, final String imageUrl) {
        final ExtractableResponse<Response> response = 상품_등록_요청(name, price, imageUrl);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static void 조회_응답됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 상품_조회됨(final ExtractableResponse<Response> response, final Long productId) {
        final Product resultProduct = response.as(Product.class);
        assertThat(resultProduct.getId()).isEqualTo(productId);
    }

    public static void 상품_목록_포함됨(Long productId1, Long productId2, ExtractableResponse<Response> response) {
        final ProductsResponse productsResponse = response.as(ProductsResponse.class);
        final List<Long> productIds = productsResponse.getProducts().stream().map(ProductResponse::getId)
                .collect(Collectors.toList());
        assertThat(productIds).contains(productId1, productId2);
    }

    public static void 상품_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void addProduct() {
        // given, when 상품을 등록하면
        final ExtractableResponse<Response> response = 상품_등록_요청("치킨", 10_000, "http://example.com/chicken.jpg");

        // then 정상적으로 상품이 등록된다.
        상품_추가됨(response);
    }

    @Test
    @DisplayName("상품을 조회한다.")
    void getProduct() {
        // given 상품을 등록하고
        final Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        // when 해당 상품을 조회하면
        final ExtractableResponse<Response> response = 상품_조회_요청(productId);

        // then 정상적으로 상품이 조회된다.
        조회_응답됨(response);
        상품_조회됨(response, productId);
    }

    @Test
    @DisplayName("상품 조회시 존재하지 않는 상품일 경우 404 응답을 반환한다.")
    void getProduct_invalidProduct_statusCode404() {
        //when
        final ExtractableResponse<Response> response = 상품_조회_요청(100L);

        //then
        요청이_NOT_FOUND_응답함(response);
    }

    @Test
    @DisplayName("상품 목록을 조회한다.")
    void getProducts() {
        // given 상품을 등록하고
        final Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        final Long productId2 = 상품_등록되어_있음("사과", 10_200, "http://example.com/apple.jpg");

        // when 해당 상품을 조회하면
        final ExtractableResponse<Response> response = 상품_목록_조회_요청(1, 10);

        // then 정상적으로 상품이 조회된다.
        조회_응답됨(response);
        상품_목록_포함됨(productId1, productId2, response);
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void delete() {
        // given 상품을 등록하고
        final Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        // when 상품 삭제를 하면
        final ExtractableResponse<Response> response = 상품_삭제_요청(productId);

        // then 상품이 정상적으로 삭제된다.
        상품_삭제됨(response);
    }

    @Test
    @DisplayName("상품 삭제시 존재하지 않는 상품일 경우 404 응답을 반환한다.")
    void delete_invalidProduct_statusCode404() {
        //when
        final ExtractableResponse<Response> response = 상품_조회_요청(100L);

        //then
        요청이_NOT_FOUND_응답함(response);
    }
}
