package woowacourse.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.Fixture.covertTypeList;
import static woowacourse.fixture.Fixture.delete;
import static woowacourse.fixture.Fixture.post;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.product.ProductResponse;

public class ProductFixture {

    private ProductFixture() {
    }

    public static ExtractableResponse<Response> 상품_등록_요청(
            String token,
            String name,
            int price,
            String imageUrl,
            long quantity
    ) {
        Product productRequest = new Product(name, price, imageUrl, quantity);
        String path = "/api/products";
        return post(path, token, productRequest);
    }


    public static ExtractableResponse<Response> 상품_목록_조회_요청() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
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

    public static ExtractableResponse<Response> 상품_삭제_요청(String token, Long productId) {
        return delete("/api/products/" + productId, token);
    }


    public static void 상품_추가_검증(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 상품_등록후_ID반환(String token, String name, int price, String imageUrl, long quantity) {
        Product productRequest = new Product(name, price, imageUrl, quantity);
        String path = "/api/products";
        ExtractableResponse<Response> response = post(path, token, productRequest);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static Long 상품_등록되어_있음2(String token, Product product) {
        String path = "/api/products";
        ExtractableResponse<Response> response = post(path, token, product);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static void 상품_목록_검증(Long productId1, Long productId, ExtractableResponse<Response> response) {
        List<Long> resultProductIds = covertTypeList(response, ProductResponse.class).stream()
                .map(ProductResponse::getProductId)
                .collect(Collectors.toList());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(resultProductIds).contains(productId1, productId);
    }

    public static void 상품_조회_검증(ExtractableResponse<Response> response, Long productId, Product product) {
        ProductResponse find = response.as(ProductResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(find.getProductId()).isEqualTo(productId),
                () -> assertThat(find.getName()).isEqualTo(product.getName()),
                () -> assertThat(find.getPrice()).isEqualTo(product.getPrice()),
                () -> assertThat(find.getThumbnailUrl()).isEqualTo(product.getImageUrl()),
                () -> assertThat(find.getQuantity()).isEqualTo(product.getQuantity())
        );
    }

    public static void 상품_삭제_검증(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
