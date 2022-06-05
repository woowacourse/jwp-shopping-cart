package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.ui.product.dto.request.ProductRegisterRequest;

@DisplayName("상품 관련 기능")
class ProductAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl) {
        ProductRegisterRequest productRequest = new ProductRegisterRequest(name, price, imageUrl);

        return RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).body(productRequest).when()
            .post("/api/products").then().log().all().extract();
    }

    public static void 상품_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        ExtractableResponse<Response> response = 상품_등록_요청("치킨", 10_000, "http://example.com/chicken.jpg");

        상품_추가됨(response);
    }
}
