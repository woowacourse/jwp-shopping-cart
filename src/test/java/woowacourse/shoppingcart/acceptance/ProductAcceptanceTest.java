package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.ui.product.dto.request.ProductRegisterRequest;

@DisplayName("상품 관련 기능")
class ProductAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 상품_등록_요청(final String name, final int price, final String imageUrl) {
        final ProductRegisterRequest productRequest = new ProductRegisterRequest(name, price, imageUrl);

        return RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).body(productRequest).when()
                .post("/api/products").then().log().all().extract();
    }

    public static ExtractableResponse<Response> 상품_조회_요청(final Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static void 상품_추가됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 상품_등록되어_있음(final String name, final int price, final String imageUrl) {
        final ExtractableResponse<Response> response = 상품_등록_요청(name, price, imageUrl);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static void 조회_응답됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 상품_조회됨(final ExtractableResponse<Response> response, final Long productId) {
        final Product resultProduct = response.as(Product.class);
        assertThat(resultProduct.getId()).isEqualTo(productId);
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void addProduct() {
        // given, when 상품을 등록하면
        final ExtractableResponse<Response> response = 상품_등록_요청("치킨", 10_000, "http://example.com/chicken.jpg");

        // then 정상적으로 상품이 등록된다.
        상품_추가됨(response);
    }

    @Test
    @DisplayName("상품을 조회한다.")
    void getProduct() {
        // given 상품을 등록하고
        final Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        // when 해당 상품을 조회하면
        final ExtractableResponse<Response> response = 상품_조회_요청(productId);

        // then 정상적으로 상품이 조회된다.
        조회_응답됨(response);
        상품_조회됨(response, productId);
    }
}
