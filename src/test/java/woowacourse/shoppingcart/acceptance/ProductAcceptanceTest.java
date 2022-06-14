package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ThumbnailImageDto;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {
    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", 10_000, 10,
            new ThumbnailImageDto("http://example.com/chicken.jpg", "이미지입니다."));

        // when
        ExtractableResponse<Response> response = requestToAddProduct(productRequest);
        ProductResponse productResponse = response.jsonPath().getObject(".", ProductResponse.class);
        ThumbnailImageDto thumbnailImage = productResponse.getThumbnailImage();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isEqualTo("/api/products/" + productResponse.getId());
        assertThat(productResponse)
            .extracting("name", "price", "stockQuantity")
            .containsExactly(productRequest.getName(), productRequest.getPrice(), productRequest.getStockQuantity());
        assertThat(thumbnailImage)
            .extracting("url", "alt")
            .containsExactly(productRequest.getThumbnailImage().getUrl(), productRequest.getThumbnailImage().getAlt());
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        // given
        ProductRequest productRequest1 = new ProductRequest("치킨", 10_000, 10,
            new ThumbnailImageDto("http://example.com/chicken.jpg", "이미지입니다."));
        ProductRequest productRequest2 = new ProductRequest("맥주", 20_000, 10,
            new ThumbnailImageDto("http://example.com/beer.jpg", "이미지입니다."));

        Long productId1 = getAddedProductId(productRequest1);
        Long productId2 = getAddedProductId(productRequest2);

        // when
        ExtractableResponse<Response> productsResponse = AcceptanceFixture.get("/api/products");
        List<Long> resultProductIds = productsResponse.jsonPath()
            .getList(".", ProductResponse.class).stream()
            .map(ProductResponse::getId)
            .collect(Collectors.toList());

        //then
        assertThat(productsResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(resultProductIds).contains(productId1, productId2);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", 10_000, 10,
            new ThumbnailImageDto("http://example.com/chicken.jpg", "이미지입니다."));
        Long productId = getAddedProductId(productRequest);

        // when
        ExtractableResponse<Response> productFoundByIdResponse = AcceptanceFixture.get("/api/products/" + productId);
        ProductResponse productResponse = productFoundByIdResponse.as(ProductResponse.class);
        ThumbnailImageDto thumbnailImage = productResponse.getThumbnailImage();

        // then
        assertThat(productFoundByIdResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(productResponse)
            .extracting("id", "name", "price", "stockQuantity")
            .containsExactly(productId, productRequest.getName(), productRequest.getPrice(),
                productRequest.getStockQuantity());
        assertThat(thumbnailImage)
            .extracting("url", "alt")
            .containsExactly(productRequest.getThumbnailImage().getUrl(), productRequest.getThumbnailImage().getAlt());
    }

    @DisplayName("조회한 상품이 없는 경우")
    @Test
    void getNotExistProduct() {
        ExtractableResponse<Response> productFoundByIdResponse = AcceptanceFixture.get("/api/products/" + 1L);
        assertThat(extractErrorCode(productFoundByIdResponse)).isEqualTo(6001);
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", 10_000, 10,
            new ThumbnailImageDto("http://example.com/chicken.jpg", "이미지입니다."));
        Long productId = getAddedProductId(productRequest);

        // when
        ExtractableResponse<Response> deleteResponse = AcceptanceFixture.delete("/api/products/" + productId);

        // then
        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private static ExtractableResponse<Response> requestToAddProduct(ProductRequest productRequest) {
        return AcceptanceFixture.post(productRequest, "/api/products");
    }

    public static Long getAddedProductId(ProductRequest productRequest) {
        ExtractableResponse<Response> response = requestToAddProduct(productRequest);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    private int extractErrorCode(ExtractableResponse<Response> response) {
        return response.jsonPath().getInt("errorCode");
    }
}
