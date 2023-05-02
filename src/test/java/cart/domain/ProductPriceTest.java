package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductPriceTest {

    @ParameterizedTest(name = "상품 가격이 0원 미만, 10,000,000원 초과면 예외가 발생한다.")
    @ValueSource(ints = {-1, 10_000_001})
    void create_withPrice_fail(final int invalidPrice) {
        assertThatThrownBy(() ->
            ProductPrice.create(invalidPrice))
            .isInstanceOf(GlobalException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.PRODUCT_PRICE_RANGE);
    }
}
