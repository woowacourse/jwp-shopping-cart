package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.product.domain.Product;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {
    public static ExtractableResponse<Response> 상품_등록_요청(final String name, final int price, final String imageUrl) {
        final Product productRequest = new Product(name, price, imageUrl);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when().post("/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_조회_요청(final Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/products/{productId}", productId)
                .then().log().all()
                .extract();
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

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        // given

        // when
        final ValidatableResponse response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/products")
                .then().log().all();

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("products.id", contains(1, 2, 3, 4, 5))
                .body("products.name", contains("사과", "포도", "복숭아", "딸기", "오렌지"))
                .body("products.price", contains(1600, 700, 3100, 2100, 540))
                .body("products.imageUrl",
                        contains("apple.co.kr", "podo.do", "pea.ch", "strawberry.org", "orange.org"));
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        final Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        final ExtractableResponse<Response> response = 상품_조회_요청(productId);

        조회_응답됨(response);
        상품_조회됨(response, productId);
    }
}
