package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.AcceptanceTestFixture.getMethodRequest;
import static woowacourse.AcceptanceTestFixture.postMethodRequest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.product.dto.ProductRequest;
import woowacourse.shoppingcart.product.dto.ProductResponse;
import woowacourse.shoppingcart.product.dto.ThumbnailImageDto;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        final ProductRequest productRequest = new ProductRequest("치킨", 10_000, 10L,
                new ThumbnailImageDto("http://example.com/chicken.jpg", "이미지입니다."));

        final ExtractableResponse<Response> response = postMethodRequest(productRequest, "/api/products");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("Location")).isNotBlank()
        );
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        final ProductRequest productRequest1 = new ProductRequest("치킨", 10_000, 10L,
                new ThumbnailImageDto("http://example.com/chicken.jpg", "이미지입니다."));
        final ProductRequest productRequest2 = new ProductRequest("피자", 10_000, 10L,
                new ThumbnailImageDto("http://example.com/pizza.jpg", "이미지입니다."));

        final Long chickenId = Long.parseLong(
                postMethodRequest(productRequest1, "/api/products").header("Location").split("/products/")[1]);
        final Long pizzaId = Long.parseLong(
                postMethodRequest(productRequest2, "/api/products").header("Location").split("/products/")[1]);

        final ExtractableResponse<Response> response = getMethodRequest("/api/products");

        final List<ProductResponse> productResponses = response.jsonPath().getList(".", ProductResponse.class);
        final List<Long> productResponseIds = productResponses.stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toUnmodifiableList());

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(productResponseIds).containsExactly(chickenId, pizzaId)
        );
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        final ProductRequest productRequest = new ProductRequest("치킨", 10_000, 10L,
                new ThumbnailImageDto("http://example.com/chicken.jpg", "이미지입니다."));

        final Long chickenId = Long.parseLong(
                postMethodRequest(productRequest, "/api/products").header("Location").split("/products/")[1]);

        final ExtractableResponse<Response> response = getMethodRequest("/api/products/" + chickenId);

        final ProductResponse productResponse = response.jsonPath().getObject(".", ProductResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(productResponse.getId()).isEqualTo(chickenId)
        );
    }
}
