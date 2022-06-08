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
import woowacourse.shoppingcart.dto.product.ProductResponse;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 상품_목록_조회_요청(int page, int limit) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products" + "?page=" + page + "&limit=" + limit)
                .then().log().all()
                .extract();
    }

    public static void 조회_응답됨(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.header("x-total-count")).isEqualTo("19")
        );
    }

    public static void 상품_목록_포함됨(List<Long> productIds, ExtractableResponse<Response> response) {
        List<Long> resultProductIds = response.jsonPath().getList(".", ProductResponse.class).stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).containsAll(productIds);
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        ExtractableResponse<Response> response = 상품_목록_조회_요청(1, 3);

        조회_응답됨(response);
        상품_목록_포함됨(List.of(1L, 2L, 3L), response);
    }
}
