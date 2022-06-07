package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.util.HttpRequestUtil.get;
import static woowacourse.util.HttpRequestUtil.post;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.response.ProductResponse;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        ExtractableResponse<Response> response = 상품_등록_요청("치킨", 10_000, 1, "http://example.com/chicken.jpg");

        상품_추가됨(response);
    }

    @DisplayName("상품 목록 1페이지를 조회한다")
    @Test
    void getProducts() {
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, 1, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, 1, "http://example.com/beer.jpg");

        ExtractableResponse<Response> response = 상품_목록_조회_요청(1, 10);

        조회_응답됨(response);
        상품_목록_포함됨(productId1, productId2, response);
    }

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, int stock, String imageUrl) {
        Product productRequest = new Product(name, price, stock, imageUrl);

        return post("/api/products", productRequest);
    }

    public static ExtractableResponse<Response> 상품_목록_조회_요청(int page, int limit) {
        return get("/api/products?page=" + page + "&limit=" + limit);
    }

    public static void 상품_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 상품_등록되어_있음(String name, int price, int stock, String imageUrl) {
        ExtractableResponse<Response> response = 상품_등록_요청(name, price, stock, imageUrl);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
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
}
