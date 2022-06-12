package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.AcceptanceTestFixture.deleteMethodRequest;
import static woowacourse.AcceptanceTestFixture.getMethodRequest;
import static woowacourse.AcceptanceTestFixture.postMethodRequest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.common.AcceptanceTest;
import woowacourse.shoppingcart.domain.ThumbnailImage;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {
    public static void 상품_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
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

    public static void 상품_조회됨(ExtractableResponse<Response> response, Long productId) {
        ProductResponse resultProduct = response.as(ProductResponse.class);
        assertThat(resultProduct.getId()).isEqualTo(productId);
    }

    public static void 상품_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        ThumbnailImage thumbnailImage = new ThumbnailImage("http://example.com/chicken.jpg", "chicken");
        ProductRequest productRequest = new ProductRequest("치킨", 1000, 10, thumbnailImage);

        ExtractableResponse<Response> response = postMethodRequest(productRequest, "/api/products");

        상품_추가됨(response);
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        ThumbnailImage chickenThumbnailImage = new ThumbnailImage("http://example.com/chicken.jpg", "chicken");
        ThumbnailImage beerThumbnailImage = new ThumbnailImage("http://example.com/beer.jpg", "chicken");

        ProductRequest chickenRequest = new ProductRequest("치킨", 1000, 10, chickenThumbnailImage);
        ProductRequest beerRequest = new ProductRequest("치킨", 1000, 10, beerThumbnailImage);

        postMethodRequest(chickenRequest, "/api/products");
        postMethodRequest(beerRequest, "/api/products");

        ExtractableResponse<Response> response = getMethodRequest("/api/products");

        조회_응답됨(response);
        상품_목록_포함됨(1L, 2L, response);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        ThumbnailImage chickenThumbnailImage = new ThumbnailImage("http://example.com/chicken.jpg", "chicken");
        ProductRequest chickenRequest = new ProductRequest("치킨", 1000, 10, chickenThumbnailImage);

        postMethodRequest(chickenRequest, "/api/products");

        ExtractableResponse<Response> response = getMethodRequest("/api/products/1");

        조회_응답됨(response);
        상품_조회됨(response, 1L);
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        ThumbnailImage chickenThumbnailImage = new ThumbnailImage("http://example.com/chicken.jpg", "chicken");
        ProductRequest chickenRequest = new ProductRequest("치킨", 1000, 10, chickenThumbnailImage);

        postMethodRequest(chickenRequest, "/api/products");

        ExtractableResponse<Response> response = deleteMethodRequest("/api/products/1");

        상품_삭제됨(response);
    }
}
