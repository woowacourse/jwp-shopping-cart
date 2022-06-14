package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.utils.Fixture.signupRequest;
import static woowacourse.utils.Fixture.tokenRequest;
import static woowacourse.utils.Fixture.맥주;
import static woowacourse.utils.Fixture.치킨;
import static woowacourse.utils.RestAssuredUtils.httpPost;
import static woowacourse.utils.RestAssuredUtils.login;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.product.ProductFindResponse;
import woowacourse.shoppingcart.dto.product.ProductSaveRequest;
import woowacourse.utils.AcceptanceTest;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    private String token;
    private Long productId1;
    private Long productId2;

    @BeforeEach
    public void setUp() {
        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);
        token = loginResponse.jsonPath().getString("accessToken");
    }

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        ExtractableResponse<Response> response = 상품_등록_요청(치킨, token);

        상품_추가됨(response);
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        Long productId1 = 상품_등록되어_있음(치킨, token);
        Long productId2 = 상품_등록되어_있음(맥주, token);

        ExtractableResponse<Response> response = 상품_목록_조회_요청(token);

        조회_응답됨(response);
        상품_목록_포함됨(productId1, productId2, response);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        Long productId = 상품_등록되어_있음(치킨, token);

        ExtractableResponse<Response> response = 상품_조회_요청(productId, token);

        조회_응답됨(response);
        상품_조회됨(response, productId);
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        Long productId = 상품_등록되어_있음(치킨, token);

        ExtractableResponse<Response> response = 상품_삭제_요청(productId, token);

        상품_삭제됨(response);
    }

    public static ExtractableResponse<Response> 상품_등록_요청(Product product, String token) {
        ProductSaveRequest productRequest = new ProductSaveRequest(product.getName(), product.getPrice(),
                product.getImage());

        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when().post("/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_목록_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_조회_요청(Long productId, String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_삭제_요청(Long productId, String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static void 상품_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 상품_등록되어_있음(Product product, String token) {
        ExtractableResponse<Response> response = 상품_등록_요청(product, token);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static void 조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 상품_목록_포함됨(Long productId1, Long productId2, ExtractableResponse<Response> response) {
        List<Long> resultProductIds = response.jsonPath().getList(".", ProductFindResponse.class).stream()
                .map(ProductFindResponse::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productId1, productId2);
    }

    public static void 상품_조회됨(ExtractableResponse<Response> response, Long productId) {
        ProductFindResponse resultProduct = response.as(ProductFindResponse.class);
        assertThat(resultProduct.getProductId()).isEqualTo(productId);
    }

    public static void 상품_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
