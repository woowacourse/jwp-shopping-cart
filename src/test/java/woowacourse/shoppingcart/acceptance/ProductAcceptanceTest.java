package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

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

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl) {
        Product productRequest = new Product(name, price, imageUrl);

        return RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).body(productRequest).when()
                .post("/api/products").then().log().all().extract();
    }

    public static ExtractableResponse<Response> 상품_목록_조회_요청(int page) {
        return RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).when()
                .get("/api/products?page=" + page).then().log().all().extract();
    }

    public static ExtractableResponse<Response> 상품_조회_요청(Long productId) {
        return RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).when()
                .get("/api/products/{productId}", productId).then().log().all().extract();
    }

    public static ExtractableResponse<Response> 상품_삭제_요청(Long productId) {
        return RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).when()
                .delete("/api/products/{productId}", productId).then().log().all().extract();
    }

    public static void 상품_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 상품_등록되어_있음(String name, int price, String imageUrl) {
        ExtractableResponse<Response> response = 상품_등록_요청(name, price, imageUrl);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static void 조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 상품_목록_포함됨(Long productId1, Long productId2, ExtractableResponse<Response> response) {
        List<Long> resultProductIds = response.jsonPath().getList("products", Product.class).stream()
                .map(Product::getId).collect(Collectors.toList());
        assertThat(resultProductIds).containsOnly(productId1, productId2);
    }

    private static void 상품_총_갯수_응답됨(final int totalCount, final ExtractableResponse<Response> response) {
        final int actual = response.jsonPath().getInt("totalCount");
        assertThat(totalCount).isEqualTo(actual);
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
        ExtractableResponse<Response> response = 상품_등록_요청("치킨", 10_000, "http://example.com/chicken.jpg");

        상품_추가됨(response);
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        Long productId1 = 상품_등록되어_있음("맥주1", 20_000, "http://example.com/beer.jpg");
        Long productId2 = 상품_등록되어_있음("맥주2", 20_000, "http://example.com/beer.jpg");
        Long productId3 = 상품_등록되어_있음("맥주3", 20_000, "http://example.com/beer.jpg");
        Long productId4 = 상품_등록되어_있음("맥주4", 20_000, "http://example.com/beer.jpg");
        Long productId5 = 상품_등록되어_있음("맥주5", 20_000, "http://example.com/beer.jpg");
        Long productId6 = 상품_등록되어_있음("맥주6", 20_000, "http://example.com/beer.jpg");
        Long productId7 = 상품_등록되어_있음("맥주7", 20_000, "http://example.com/beer.jpg");
        Long productId8 = 상품_등록되어_있음("맥주8", 20_000, "http://example.com/beer.jpg");
        Long productId9 = 상품_등록되어_있음("맥주9", 20_000, "http://example.com/beer.jpg");
        Long productId10 = 상품_등록되어_있음("맥주10", 20_000, "http://example.com/beer.jpg");
        Long productId11 = 상품_등록되어_있음("맥주11", 20_000, "http://example.com/beer.jpg");
        Long productId12 = 상품_등록되어_있음("맥주12", 20_000, "http://example.com/beer.jpg");

        ExtractableResponse<Response> response = 상품_목록_조회_요청(2);

        조회_응답됨(response);
        상품_총_갯수_응답됨(12, response);
        상품_목록_포함됨(productId11, productId12, response);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        ExtractableResponse<Response> response = 상품_조회_요청(productId);

        조회_응답됨(response);
        상품_조회됨(response, productId);
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        ExtractableResponse<Response> response = 상품_삭제_요청(productId);

        상품_삭제됨(response);
    }
}
