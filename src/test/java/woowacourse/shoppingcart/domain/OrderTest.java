package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.common.exception.InvalidRequestException;

@SuppressWarnings("NonAsciiCharacters")
class OrderTest {

    private static final Product 호박 = new Product(1L, "호박", 1000, "이미지_주소1");
    private static final Product 고구마 = new Product(2L, "고구마", 500, "이미지_주소2");

    @DisplayName("생성자 테스트")
    @Nested
    class InitTest {

        @Test
        void 장바구니_상품_중_수량이_0개인_항목은_제외하고_주문_생성() {
            CartItem 호박_3개 = new CartItem(호박, 3);
            CartItem 고구마_0개 = new CartItem(고구마, 0);

            Order actual = new Order(List.of(호박_3개, 고구마_0개));
            Order expected = new Order(List.of(호박_3개));

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 장바구니_상품_목록이_비어있는_경우_예외발생() {
            assertThatThrownBy(() -> new Order(List.of()))
                    .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        void 장바구니_상품_목록이_전부_수량_0개로_구성된_경우_예외발생() {
            CartItem 호박_0개 = new CartItem(호박, 0);
            CartItem 고구마_0개 = new CartItem(고구마, 0);

            assertThatThrownBy(() -> new Order(List.of(호박_0개, 고구마_0개)))
                    .isInstanceOf(InvalidRequestException.class);
        }
    }
}
