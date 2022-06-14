package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.fixture.RequestFixture.상품_등록_요청;
import static woowacourse.fixture.RequestFixture.상품_등록되어_있음;
import static woowacourse.fixture.RequestFixture.상품_목록_조회_요청;
import static woowacourse.fixture.RequestFixture.상품_삭제_요청;
import static woowacourse.fixture.RequestFixture.상품_조회_요청;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.product.ProductResponse;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        // given
        ExtractableResponse<Response> response = 상품_등록_요청("치킨", 10_000, "http://example.com/chicken.jpg", 10);

        // when then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        // given
        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg", 10);

        // when
        ExtractableResponse<Response> response = 상품_목록_조회_요청();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        상품_목록_포함됨(productId1, productId2, response);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        // given
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);

        // when
        ExtractableResponse<Response> response = 상품_조회_요청(productId);
        ProductResponse resultProduct = response.as(ProductResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(resultProduct.getProductId()).isEqualTo(productId);
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        // given
        Long productId = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);

        // when
        ExtractableResponse<Response> response = 상품_삭제_요청(productId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 상품_목록_포함됨(Long productId1, Long productId2, ExtractableResponse<Response> response) {
        List<Long> resultProductIds = response.jsonPath().getList(".", ProductResponse.class).stream()
                .map(ProductResponse::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productId1, productId2);
    }
}
