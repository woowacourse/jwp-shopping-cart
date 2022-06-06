package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.utils.Fixture.치킨;
import static woowacourse.utils.RestAssuredUtils.httpGet;
import static woowacourse.utils.RestAssuredUtils.httpPost;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.ProductSaveRequest;
import woowacourse.utils.AcceptanceTest;

class ProductControllerTest extends AcceptanceTest {

    @Test
    @DisplayName("상품을 저장한다.")
    void save_product() {
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

    @Test
    @DisplayName("상품을 있을 경우 조회한다.")
    void find_product_exist() {
        // given
        ProductSaveRequest request = new ProductSaveRequest(치킨.getName(), 치킨.getPrice(), 치킨.getImage());
        ExtractableResponse<Response> response = httpPost("/products", request);
        String productId = response.jsonPath().getString("productId");

        // when
        ExtractableResponse<Response> getResponse = httpGet("/products",  productId);

        // then
        assertAll(
                () -> assertThat(getResponse.jsonPath().getString("name")).isEqualTo(치킨.getName()),
                () -> assertThat(getResponse.jsonPath().getInt("price")).isEqualTo(치킨.getPrice()),
                () -> assertThat(getResponse.jsonPath().getString("image")).isEqualTo(치킨.getImage()),
                () -> assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @Test
    @DisplayName("상품이 없을 경우 404를 반환한다.")
    void find_product_not_exist() {
        // when
        ExtractableResponse<Response> getResponse = httpGet("/products",  "1");

        // then
        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
