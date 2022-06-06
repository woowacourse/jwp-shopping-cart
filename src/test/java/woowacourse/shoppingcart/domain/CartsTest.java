package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_IMAGE;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_NAME;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_PRICE;
import static woowacourse.helper.fixture.ProductFixture.createProduct;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CartsTest {

    @DisplayName("총 비용을 계산한다.")
    @Test
    void calculateTotalPrice() {
        final Carts carts = new Carts(1L, 1L, createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE), 10);
        assertThat(carts.calculateTotalPrice()).isEqualTo(10_000);
    }

}
