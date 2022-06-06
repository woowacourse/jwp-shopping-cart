package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.utils.Fixture.치킨;
import static woowacourse.utils.RestAssuredUtils.httpPost;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.ProductSaveRequest;
import woowacourse.utils.AcceptanceTest;

class ProductControllerTest extends AcceptanceTest {

    @Test
    @DisplayName("상품을 저장한다.")
    void test() {
        // given
        ProductSaveRequest request = new ProductSaveRequest(치킨.getName(), 치킨.getPrice(), 치킨.getImage());

        // when
        ExtractableResponse<Response> response = httpPost("/products", request);

        // then
        assertAll(
                () -> assertThat(response.jsonPath().getString("name")).isEqualTo(치킨.getName()),
                () -> assertThat(response.jsonPath().getInt("price")).isEqualTo(치킨.getPrice()),
                () -> assertThat(response.jsonPath().getString("image")).isEqualTo(치킨.getImage()),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        );
    }
}
