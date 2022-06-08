package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.fixture.SimpleResponse;
import woowacourse.fixture.SimpleRestAssured;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.dto.response.ProductsResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.blankString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {
    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        SimpleResponse response = 상품_등록_요청("치킨", 10_000, "http://example.com/chicken.jpg");

        상품_추가됨(response);
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        SimpleResponse response = 상품_목록_조회_요청();

        조회_응답됨(response);
        상품_목록_포함됨(productId1, productId2, response);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        SimpleResponse response = 상품_조회_요청(productId);

        조회_응답됨(response);
        상품_조회됨(response, productId);
    }

    @DisplayName("존재하지 않는 상품을 조회하면 예외가 발생한다")
    @Test
    void getProduct_not_found() {
        SimpleResponse response = 상품_조회_요청(100L);

        response.assertStatus(HttpStatus.NOT_FOUND);
        response.containsExceptionMessage("상품 아이디");
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");

        ExtractableResponse<Response> response = 상품_삭제_요청(productId);

        상품_삭제됨(response);
    }

    public static SimpleResponse 상품_등록_요청(String name, int price, String imageUrl) {
        Product productRequest = new Product(name, price, imageUrl);
        return SimpleRestAssured.post("/products", productRequest);
    }

    public static SimpleResponse 상품_목록_조회_요청() {
        return SimpleRestAssured.get("products");
    }

    public static SimpleResponse 상품_조회_요청(Long productId) {
        return SimpleRestAssured.get("/products/" + productId);
    }

    public static ExtractableResponse<Response> 상품_삭제_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static void 상품_추가됨(SimpleResponse response) {
        response.assertStatus(HttpStatus.CREATED);
        response.assertHeader("Location", not(blankString()));
    }

    public static Long 상품_등록되어_있음(String name, int price, String imageUrl) {
        SimpleResponse response = 상품_등록_요청(name, price, imageUrl);
        return response.getIdFromLocation("/products/");
    }

    public static void 조회_응답됨(SimpleResponse response) {
        response.assertStatus(HttpStatus.OK);
    }

    public static void 상품_목록_포함됨(Long productId1, Long productId2, SimpleResponse response) {
        List<Long> resultProductIds = response.toObject(ProductsResponse.class)
                .getProducts()
                .stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productId1, productId2);
    }

    public static void 상품_조회됨(SimpleResponse response, Long productId) {
        ProductResponse resultProduct = response.toObject(ProductResponse.class);
        assertThat(resultProduct.getId()).isEqualTo(productId);
    }

    public static void 상품_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
