package cart.domain.cart;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class CartProductTest {

    @Test
    void 멤버_ID가_없으면_예외를_발생한다() {
        assertThatThrownBy(() -> new CartProduct(null, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("맴버 ID는 필수입니다.");
    }

    @Test
    void 상품_ID가_없으면_예외를_발생한다() {
        assertThatThrownBy(() -> new CartProduct(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 ID는 필수입니다.");
    }
}
