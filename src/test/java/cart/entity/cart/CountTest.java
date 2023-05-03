package cart.entity.cart;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import cart.exception.common.UnderZeroException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CountTest {

    @ParameterizedTest(name = "{displayName}")
    @ValueSource(ints = {-1, -1000, -200})
    @DisplayName("장바구니의 상품 수가 음수({0})이면 에러를 발생시킨다.")
    void validate_name_null_or_blank(int count) {
        // when + then
        assertThatThrownBy(() -> new Count(count))
            .isInstanceOf(UnderZeroException.class);

    }

}