package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.domain.Product;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {
    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        // given & when
        ExtractableResponse<Response> response = requestToAddProduct("치킨", 10_000, "http://example.com/chicken.jpg");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        // given
        Long productId1 = getAddedProductId("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = getAddedProductId("맥주", 20_000, "http://example.com/beer.jpg");

        // when
        ExtractableResponse<Response> response = AcceptanceFixture.get("/api/products");
        List<Long> resultProductIds = response.jsonPath().getList(".", Product.class).stream()
            .map(Product::getId)
            .collect(Collectors.toList());

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(resultProductIds).contains(productId1, productId2);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        // given
        Long productId = getAddedProductId("치킨", 10_000, "http://example.com/chicken.jpg");

        // when
        ExtractableResponse<Response> response = AcceptanceFixture.get("/api/products/" + productId);
        Product resultProduct = response.as(Product.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(resultProduct.getId()).isEqualTo(productId);
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        // given
        Long productId = getAddedProductId("치킨", 10_000, "http://example.com/chicken.jpg");

        // when
        ExtractableResponse<Response> response = AcceptanceFixture.delete("/api/products/" + productId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private static ExtractableResponse<Response> requestToAddProduct(String name, int price, String imageUrl) {
        Product productRequest = new Product(name, price, imageUrl);

        return AcceptanceFixture.post(productRequest, "/api/products");
    }

    public static Long getAddedProductId(String name, int price, String imageUrl) {
        ExtractableResponse<Response> response = requestToAddProduct(name, price, imageUrl);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }
}
