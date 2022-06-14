package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.RequestHandler.getRequest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.ExceptionResponse;
import woowacourse.exception.InvalidProductException;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품 목록을 조회한다")
    @Test
    void findProducts() {
        ExtractableResponse<Response> response = getRequest("/products");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<Long> resultProductIds = response.jsonPath().getList(".", ProductResponse.class).stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(1L, 2L);
    }

    @DisplayName("상품을 단건 조회한다")
    @Test
    void findProduct() {
        ExtractableResponse<Response> response = getRequest("/products/1");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getObject(".", ProductResponse.class))
                .extracting("name", "price", "image")
                .containsExactly("우유", 3000, "http://example1.com");
    }

    @DisplayName("존재하지 않는 아이디로 상품을 조회하면 에러가 발생한다.")
    @Test
    void findProductByInvalidId() {
        ExtractableResponse<Response> response = getRequest("/products/3");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.jsonPath().getObject(".", ExceptionResponse.class).getMessage())
                .isEqualTo(new InvalidProductException().getMessage());
    }
}
