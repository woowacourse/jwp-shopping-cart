package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.utils.Fixture.맥주;
import static woowacourse.utils.Fixture.치킨;
import static woowacourse.utils.RestAssuredUtils.httpDelete;
import static woowacourse.utils.RestAssuredUtils.httpGet;
import static woowacourse.utils.RestAssuredUtils.httpPost;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.product.ProductFindResponse;
import woowacourse.shoppingcart.dto.product.ProductSaveRequest;
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
        ExtractableResponse<Response> getResponse = httpGet("/products/" + productId);

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
        ExtractableResponse<Response> getResponse = httpGet("/products/" + "1");

        // then
        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("상품 목록을 경우 조회한다.")
    void find_products() {
        // given
        ProductSaveRequest request1 = new ProductSaveRequest(치킨.getName(), 치킨.getPrice(), 치킨.getImage());
        httpPost("/products", request1);
        ProductSaveRequest request2 = new ProductSaveRequest(맥주.getName(), 맥주.getPrice(), 맥주.getImage());
        httpPost("/products", request2);

        // when
        ExtractableResponse<Response> getResponse = httpGet("/products");

        // then
        List<ProductFindResponse> list = getResponse.jsonPath().getList(".", ProductFindResponse.class);
        assertThat(list).hasSize(2)
                .extracting("name", "price", "image")
                .containsExactly(
                        tuple(치킨.getName(), 치킨.getPrice(), 치킨.getImage()),
                        tuple(맥주.getName(), 맥주.getPrice(), 맥주.getImage())
                );
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void delete_product() {
        // given
        ProductSaveRequest request = new ProductSaveRequest(치킨.getName(), 치킨.getPrice(), 치킨.getImage());
        ExtractableResponse<Response> response = httpPost("/products", request);
        String productId = response.jsonPath().getString("productId");

        // when
        httpDelete("/products/" + productId);

        // then
        ExtractableResponse<Response> response1 = httpGet("/products/" + productId);
        assertThat(response1.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
