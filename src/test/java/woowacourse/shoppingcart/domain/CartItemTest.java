package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.common.exception.InvalidRequestException;

@SuppressWarnings("NonAsciiCharacters")
class CartItemTest {

    private static final Product 유효한_상품 = new Product(1L, "상품명", 1000, "이미지 주소");

    @DisplayName("생성자 테스트")
    @Nested
    class ConstructorTest {

        @Test
        void 수량을_입력하지_않은_경우_1로_초기화() {
            CartItem actual = new CartItem(유효한_상품);
            CartItem expected = new CartItem(유효한_상품, 1);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 수량이_음수인_경우_예외발생() {
            int 음수_수량 = -1;
            assertThatThrownBy(() -> new CartItem(유효한_상품, 음수_수량))
                    .isInstanceOf(InvalidRequestException.class);
        }
    }
}
