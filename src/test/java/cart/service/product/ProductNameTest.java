package cart.service.product;

import cart.service.product.domain.ProductName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ProductNameTest {

    @Test
    void 상품_이름이_50자가_넘어가면_예외() {
        String invalidName = "a".repeat(51);
        Assertions.assertThatThrownBy(() -> new ProductName(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 이름은 최대 50자 입니다.");
    }

}
