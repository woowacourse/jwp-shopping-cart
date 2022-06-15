package woowacourse.shoppingcart.product.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.product.acceptance.ProductRestHandler.assertThatProductException;
import static woowacourse.shoppingcart.product.acceptance.ProductRestHandler.상품목록;
import static woowacourse.shoppingcart.product.acceptance.ProductRestHandler.상품조회;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.product.support.exception.ProductExceptionCode;
import woowacourse.support.acceptance.AcceptanceTest;

@DisplayName("상품 관련 기능")
class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        final ExtractableResponse<Response> response = 상품목록();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        final ExtractableResponse<Response> response = 상품조회(1);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("존재하지 않는 상품을 조회한다")
    @Test
    void getNonExistentProduct() {
        final ExtractableResponse<Response> response = 상품조회(0);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThatProductException(response, ProductExceptionCode.NO_SUCH_PRODUCT_EXIST);
    }
}
