package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_REQUEST_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_REQUEST_2;
import static woowacourse.shoppingcart.fixture.ProductFixtures.getProductRequestParam;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.AcceptanceTest;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 상품_등록_요청(Map<String, Object> productRequest) {

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when().post("/api/products")
                .then().log().all()
                .extract();
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

    public static Long 상품_등록되어_있음(Map<String, Object> productRequestParam) {
        ExtractableResponse<Response> response = 상품_등록_요청(productRequestParam);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static void 조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 조회_응답_안됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    public static void 상품_목록_포함됨(Long productId1, Long productId2, ExtractableResponse<Response> response) {
        final List<ProductResponse> productResponses = response.jsonPath().getList(".", ProductResponse.class);

        List<Long> resultProductIds = productResponses.stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());

        assertThat(resultProductIds).contains(productId1, productId2);
    }

    public static void 상품_조회됨(ExtractableResponse<Response> response, Long productId) {
        final ProductResponse productResponse = response.as(ProductResponse.class);
        assertThat(productResponse.getId()).isEqualTo(productId);
    }

    private void 상품_조회_안됨(ExtractableResponse<Response> response, Long productId) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    public static void 상품_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        ExtractableResponse<Response> response = 상품_등록_요청(getProductRequestParam(PRODUCT_REQUEST_1));

        상품_추가됨(response);
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        Long productId1 = 상품_등록되어_있음(getProductRequestParam(PRODUCT_REQUEST_1));
        Long productId2 = 상품_등록되어_있음(getProductRequestParam(PRODUCT_REQUEST_2));

        ExtractableResponse<Response> response = 상품_목록_조회_요청();

        조회_응답됨(response);
        상품_목록_포함됨(productId1, productId2, response);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        Long productId = 상품_등록되어_있음(getProductRequestParam(PRODUCT_REQUEST_1));

        ExtractableResponse<Response> response = 상품_조회_요청(productId);

        조회_응답됨(response);
        상품_조회됨(response, productId);
    }

    @DisplayName("없는 상품을 조회 시 NOT FOUND")
    @Test
    void productNotFound() {
        Long productId = 상품_등록되어_있음(getProductRequestParam(PRODUCT_REQUEST_1));

        ExtractableResponse<Response> response = 상품_조회_요청(productId + 1);

        조회_응답_안됨(response);
        상품_조회_안됨(response, productId);
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        Long productId = 상품_등록되어_있음(getProductRequestParam(PRODUCT_REQUEST_1));

        ExtractableResponse<Response> response = 상품_삭제_요청(productId);

        상품_삭제됨(response);
    }
}
