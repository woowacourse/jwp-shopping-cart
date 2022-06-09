package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.ShoppingCartAcceptanceTestFixture.PRODUCTS_FIND_URI;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import woowacourse.member.dto.response.ErrorResponse;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.util.HttpRequestUtil;

@DisplayName("상품 관련 기능")
class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품 목록을 페이징 조회한다")
    @Test
    void getProducts() {
        ExtractableResponse<Response> response = HttpRequestUtil.get(PRODUCTS_FIND_URI + "?page=1&limit=5");
        List<Long> productIds = response.jsonPath()
                .getList(".", ProductResponse.class)
                .stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toUnmodifiableList());
        String xTotalCount = response.header("x-total-count");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(productIds).containsExactly(1L, 2L, 3L, 4L, 5L),
                () -> assertThat(xTotalCount).isEqualTo("19")
        );
    }

    @ParameterizedTest
    @DisplayName("상품 목록을 페이징 조회할 때, 페이지 값 혹은 한 페이지에 보여줄 상품의 개수가 1보다 작으면 400을 응답한다.")
    @CsvSource({"?page=0&limit=5, page 값은 양수여야 합니다.", "?page=2&limit=0, 한 페이지에 보여줄 상품의 개수는 양수여야합니다."})
    void getProducts_BadRequest_InvalidPaginationValue(String paginationValue, String expectedMessage) {
        ExtractableResponse<Response> response = HttpRequestUtil.get(PRODUCTS_FIND_URI + paginationValue);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(message).isEqualTo(expectedMessage)
        );
    }
}
