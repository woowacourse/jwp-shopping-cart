package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.fixture.ProductSimpleAssured.상품_등록;
import static woowacourse.shoppingcart.acceptance.fixture.ProductSimpleAssured.상품_등록되어_있음;
import static woowacourse.shoppingcart.acceptance.fixture.ProductSimpleAssured.상품_목록_조회_요청;
import static woowacourse.shoppingcart.acceptance.fixture.ProductSimpleAssured.상품_삭제_요청;
import static woowacourse.shoppingcart.acceptance.fixture.ProductSimpleAssured.상품_삭제됨;
import static woowacourse.shoppingcart.acceptance.fixture.ProductSimpleAssured.상품_조회_요청;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.ProductRequest;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        ExtractableResponse<Response> response = 상품_등록(new ProductRequest("치킨", 10_000,
                "http://example.com/chicken.jpg"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        ExtractableResponse<Response> response = 상품_목록_조회_요청();
        List<Long> resultProductIds = response.jsonPath().getList("productList", Product.class).stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(resultProductIds).contains(productId1, productId2)
        );
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        ExtractableResponse<Response> response = 상품_조회_요청(productId);
        Product resultProduct = response.as(Product.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(resultProduct.getId()).isEqualTo(productId)
        );
    }

    @DisplayName("상품을 조회할 대상이 없다면 404를 응답한다.")
    @Test
    void getProductResponse404() {
        ExtractableResponse<Response> response = 상품_조회_요청(1L);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        ExtractableResponse<Response> response = 상품_삭제_요청(productId);

        상품_삭제됨(response);
    }
}
