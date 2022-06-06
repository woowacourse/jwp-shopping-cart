package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import woowacourse.shoppingcart.ui.product.ProductController;
import woowacourse.shoppingcart.ui.product.dto.request.ProductRegisterRequest;
import woowacourse.shoppingcart.ui.product.dto.response.ProductResponse;

@SpringBootTest
class ProductControllerTest {

    private static final String PRODUCT_NAME = "치즈돈까스~";
    private static final int PRODUCT_PRICE = 7_000;
    private static final String PRODUCT_IMAGE_URL = "www.naver.com/dongas.png";
    @Autowired
    private ProductController productController;

    @Test
    @DisplayName("상품을 등록한다.")
    void register() {
        //given
        final ProductRegisterRequest productRequest = new ProductRegisterRequest("치즈돈까스~", 7_000,
                "www.naver.com/dongas.png");

        //when
        final ResponseEntity<Void> actual = productController.register(productRequest);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("상품에 대한 정보를 조회한다.")
    void showProduct() {
        //given
        final ProductRegisterRequest productRequest = new ProductRegisterRequest(PRODUCT_NAME, PRODUCT_PRICE,
                PRODUCT_IMAGE_URL);
        productController.register(productRequest);

        //when
        final ResponseEntity<ProductResponse> actual = productController.showProduct(1L);

        //then
        assertAll(
                () -> assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(actual.getBody()).usingRecursiveComparison()
                        .isEqualTo(new ProductResponse(1L, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE_URL))
        );
    }
}
