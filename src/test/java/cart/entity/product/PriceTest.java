package cart.entity.product;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import cart.exception.common.NullOrBlankException;
import cart.exception.product.PriceNotUnderZeroException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @ParameterizedTest(name = "{displayName}")
    @ValueSource(ints = {-1, -1000, -200})
    @DisplayName("상품 가격이 음수({0})이면 에러를 발생시킨다.")
    void validate_name_null_or_blank(int price) {
        // when + then
        assertThatThrownBy(() -> new Price(price))
            .isInstanceOf(PriceNotUnderZeroException.class);

    }
}