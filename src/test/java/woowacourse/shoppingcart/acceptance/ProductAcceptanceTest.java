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
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductResponses;
import woowacourse.shoppingcart.dto.ProductsPerPageRequest;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {
    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        ExtractableResponse<Response> response = 상품_등록_요청("치킨", 10_000, "http://example.com/chicken.jpg");

        상품_추가됨(response);
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
        Long productId3 = 상품_등록되어_있음("피자", 20_000, "http://example.com/pizza.jpg");
        Long productId4 = 상품_등록되어_있음("떡볶이", 20_000, "http://example.com/ddukbokki.jpg");
        Long productId5 = 상품_등록되어_있음("보쌈", 20_000, "http://example.com/bossam.jpg");
        Long productId6 = 상품_등록되어_있음("족발", 20_000, "http://example.com/jokbal.jpg");
        Long productId7 = 상품_등록되어_있음("김치", 20_000, "http://example.com/kimchi.jpg");

        ExtractableResponse<Response> response = 상품_목록_조회_요청(new ProductsPerPageRequest(3, 2));

        조회_응답됨(response);
        상품_목록_포함됨(List.of(productId4, productId5, productId6), response);
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

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl) {
        ProductRequest productRequest = new ProductRequest(name, price, imageUrl);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when().post("/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_목록_조회_요청(ProductsPerPageRequest productsPerPageRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("size", productsPerPageRequest.getSize())
                .param("page", productsPerPageRequest.getPage())
                .when().get("/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_조회_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_삭제_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/products/{productId}", productId)
                .then().log().all()
                .extract();
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

    public static void 상품_목록_포함됨(List<Long> productIds, ExtractableResponse<Response> response) {
        List<Long> resultProductIds = response
                .as(ProductResponses.class)
                .getProducts()
                .stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).containsExactlyElementsOf(productIds);
    }

    public static void 상품_조회됨(ExtractableResponse<Response> response, Long productId) {
        ProductResponse resultProduct = response.as(ProductResponse.class);
        assertThat(resultProduct.getId()).isEqualTo(productId);
    }

    public static void 상품_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
