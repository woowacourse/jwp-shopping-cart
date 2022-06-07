package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.CartItemAddRequest.ProductResponse;
import woowacourse.util.HttpRequestUtil;

@DisplayName("상품 관련 기능")
@Sql("classpath:import.sql")
class ProductAcceptanceTest extends AcceptanceTest {

    private static final String PRODUCTS_FIND_URI = "/api/products";

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        ExtractableResponse<Response> response = HttpRequestUtil.get(PRODUCTS_FIND_URI);
        List<ProductResponse> productResponses = response.jsonPath()
                .getList(".", ProductResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(productResponses).hasSize(19)
        );
    }
}
