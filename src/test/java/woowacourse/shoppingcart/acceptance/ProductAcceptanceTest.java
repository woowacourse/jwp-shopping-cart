package woowacourse.shoppingcart.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.application.dto.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.ResponseCreator.*;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {
    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        // given & when
        ExtractableResponse<Response> response = postProduct("치킨", 10_000, "http://example.com/chicken.jpg");

        // then
        상품_추가됨(response);
    }

    @DisplayName("상품을 ID로 조회한다")
    @Test
    void findById() {
        // given
        postProduct("치킨", 10_000, "http://example.com/chicken.jpg");

        // when
        ExtractableResponse<Response> response = getProductById(1L);

        // then
        조회_응답됨(response);
        상품_조회됨(response, 1L);
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void findAll() {
        // given
        postProduct("치킨", 10_000, "http://example.com/chicken.jpg");
        postProduct("맥주", 20_000, "http://example.com/beer.jpg");

        // when
        ExtractableResponse<Response> 상품_조회 = getProducts();

        // then
        조회_응답됨(상품_조회);
        상품_목록_포함됨(1L, 2L, 상품_조회);
    }

    public static void 상품_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 상품_목록_포함됨(Long productId1, Long productId2, ExtractableResponse<Response> response) {
        List<Long> resultProductIds = response.jsonPath().getList(".", ProductResponse.class).stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productId1, productId2);
    }

    public static void 상품_조회됨(ExtractableResponse<Response> response, Long productId) {
        ProductResponse resultProduct = response.as(ProductResponse.class);
        assertThat(resultProduct.getId()).isEqualTo(productId);
    }
}
