package cart.domain.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductIdTest {
    @DisplayName("Null 객체의 값에 접근하면 예외가 발생한다.")
    @Test
    void getNullProductIdValueFailure() {
        ProductId emptyId = ProductId.getEmptyId();

        assertThatThrownBy(emptyId::getId)
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
