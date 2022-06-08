package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.domain.Product;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl, int stock) {
        Product productRequest = new Product(name, price, imageUrl, stock);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when().post("/api/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_목록_조회_요청(int page, int limit) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products" + "?page=" + page + "&limit=" + limit)
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

    public static ExtractableResponse<Response> 상품_삭제_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static void 상품_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 상품_등록되어_있음(String name, int price, String imageUrl, int stock) {
        ExtractableResponse<Response> response = 상품_등록_요청(name, price, imageUrl, stock);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static void 조회_응답됨(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.header("x-total-count")).isEqualTo("19")
        );
    }

    public static void 상품_목록_포함됨(List<Long> productIds, ExtractableResponse<Response> response) {
        List<Long> resultProductIds = response.jsonPath().getList(".", Product.class).stream()
                .map(Product::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).containsAll(productIds);
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
        ExtractableResponse<Response> response = 상품_등록_요청("치킨", 10_000, "http://example.com/chicken.jpg", 10);

        상품_추가됨(response);
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        ExtractableResponse<Response> response = 상품_목록_조회_요청(1, 3);

        조회_응답됨(response);
        상품_목록_포함됨(List.of(1L, 2L, 3L), response);
    }
}
