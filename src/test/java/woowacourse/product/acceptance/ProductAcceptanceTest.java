package woowacourse.product.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.product.dto.ProductRequest;
import woowacourse.product.dto.ProductResponse;
import woowacourse.product.dto.ProductResponses;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    private final String name = "짱구";
    private final int price = 100_000_000;
    private final int stock = 1;
    private final String imageURL = "http://example.com/jjanggu.jpg";
    private final ProductRequest productRequest = new ProductRequest(name, price, stock, imageURL);

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        final ExtractableResponse<Response> response = 상품_등록_요청(productRequest);

        상품_추가됨(response);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        final Long productId = 상품_등록되어_있음(productRequest);

        final ExtractableResponse<Response> response = 상품_조회_요청(productId);

        조회_응답됨(response);
        상품_조회됨(response, productId);
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        final ProductRequest productRequest2 = new ProductRequest("짱아", price, stock, "http://example.com/jjanga.jpg");
        final Long productId1 = 상품_등록되어_있음(productRequest);
        final Long productId2 = 상품_등록되어_있음(productRequest2);

        final ExtractableResponse<Response> response = 상품_목록_조회_요청();

        조회_응답됨(response);
        상품_목록_포함됨(productId1, productId2, response);
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        final Long productId = 상품_등록되어_있음(productRequest);

        final ExtractableResponse<Response> response = 상품_삭제_요청(productId);

        상품_삭제됨(response);
    }

    public static ExtractableResponse<Response> 상품_등록_요청(final ProductRequest productRequest) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .when().post("/api/products")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 상품_목록_조회_요청() {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/products")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 상품_조회_요청(final Long productId) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/products/{productId}", productId)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 상품_삭제_요청(final Long productId) {
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

    public static Long 상품_등록되어_있음(final ProductRequest productRequest) {
        final ExtractableResponse<Response> response = 상품_등록_요청(productRequest);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static void 조회_응답됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 상품_목록_포함됨(final Long productId1, final Long productId2,
        final ExtractableResponse<Response> response) {
        final List<Long> resultProductIds = new ArrayList<>();
        for (ProductResponse.InnerProductResponse innerProductResponse : response.as(ProductResponses.class)
            .getProducts()) {
            Long id = innerProductResponse.getId();
            resultProductIds.add(id);
        }
        assertThat(resultProductIds).contains(productId1, productId2);
    }

    public static void 상품_조회됨(final ExtractableResponse<Response> response, final Long productId) {
        final ProductResponse resultProduct = response.as(ProductResponse.class);
        assertThat(resultProduct.getProduct().getId()).isEqualTo(productId);
    }

    public static void 상품_삭제됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
