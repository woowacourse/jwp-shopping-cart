package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.common.exception.InvalidRequestException;

@SuppressWarnings("NonAsciiCharacters")
class CartTest {

    private static final Product 호박 = new Product(1L, "호박", 1000, "이미지_주소1");
    private static final Product 고구마 = new Product(2L, "고구마", 500, "이미지_주소2");
    private static final Product 호박고구마 = new Product(3L, "호박고구마", 1500, "이미지_주소3");

    @Test
    void 비어있는_장바구니도_생성_가능() {
        assertThatNoException()
                .isThrownBy(() -> new Cart(List.of()));
    }

    @DisplayName("hasProduct 메서드는 특정 상품이 장바구니에 등록되었는지의 여부를 반환")
    @Nested
    class HasProductTest {

        @Test
        void 장바구니에_등록된_상품인_경우_참() {
            CartItem 호박_3개 = new CartItem(호박, 3);
            Cart 장바구니 = new Cart(호박_3개);

            boolean actual = 장바구니.hasProduct(호박);

            assertThat(actual).isTrue();
        }

        @Test
        void 장바구니에_등록된_상품인_수량이_0개여도_참() {
            CartItem 고구마_0개 = new CartItem(고구마, 0);
            Cart 장바구니 = new Cart(고구마_0개);

            boolean actual = 장바구니.hasProduct(고구마);

            assertThat(actual).isTrue();
        }

        @Test
        void 장바구니에_등록된_상품이_아닌_경우_거짓() {
            CartItem 호박_1개 = new CartItem(호박, 1);
            CartItem 고구마_2개 = new CartItem(고구마, 2);
            Cart 장바구니 = new Cart(호박_1개, 고구마_2개);

            boolean actual = 장바구니.hasProduct(호박고구마);

            assertThat(actual).isFalse();
        }
    }

    @DisplayName("extractCartItems 메서드는 상품 id들의 목록에 해당되는 상품들의 장바구니 등록 정보를 리스트로 반환")
    @Nested
    class ExtractCartItemsTest {

        private final CartItem 호박_3개 = new CartItem(호박, 3);
        private final CartItem 고구마_0개 = new CartItem(고구마, 0);
        private final CartItem 호박고구마_100개 = new CartItem(호박고구마, 100);
        private final Cart 장바구니 = new Cart(호박_3개, 고구마_0개, 호박고구마_100개);

        @Test
        void 등록된_상품들을_수량_정보까지_포함하여_반환() {
            List<Long> 상품_id_목록 = List.of(호박_3개.getProductId(), 호박고구마_100개.getProductId());

            List<CartItem> actual = 장바구니.extractCartItems(상품_id_목록);
            List<CartItem> expected = List.of(호박_3개, 호박고구마_100개);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 수량이_0개인_상품이어도_등록되었으면_포함하여_반환() {
            List<Long> 상품_id_목록 = List.of(호박_3개.getProductId(), 고구마_0개.getProductId());

            List<CartItem> actual = 장바구니.extractCartItems(상품_id_목록);
            List<CartItem> expected = List.of(호박_3개, 고구마_0개);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 장바구니에_등록되지_않은_상품의_id가_포함된_경우_예외발생() {
            long 등록되지_않은_상품_id = 99999L;
            List<Long> 상품_id_목록 = List.of(등록되지_않은_상품_id, 호박_3개.getProductId());

           assertThatThrownBy(() -> 장바구니.extractCartItems(상품_id_목록))
                   .isInstanceOf(InvalidRequestException.class);
        }
    }
}
