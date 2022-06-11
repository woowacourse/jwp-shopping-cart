package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import woowacourse.auth.domain.user.Customer;
import woowacourse.auth.domain.user.EncryptedPassword;
import woowacourse.common.exception.NotFoundException;
import woowacourse.common.exception.CheckCartException;
import woowacourse.setup.SpringBeanTest;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.util.DatabaseFixture;

@SuppressWarnings("NonAsciiCharacters")
class CartServiceTest extends SpringBeanTest {

    private static final Customer 고객 = new Customer(1L, "username", new EncryptedPassword("암호화"), "닉네임", 20);
    private static final Product 호박 = new Product(1L, "호박", 1000, "호박_이미지");
    private static final Product 고구마 = new Product(2L, "고구마", 2000, "고구마_이미지");
    private static final Product 호박고구마 = new Product(3L, "호박고구마", 3000, "호박_고구마_이미지");

    @Autowired
    private CartService cartService;

    @Autowired
    private DatabaseFixture databaseFixture;

    @Test
    void findCart_메서드는_고객의_장바구니에_등록된_상품들을_조회한다() {
        CartItem 호박_3개 = new CartItem(호박, 3);
        CartItem 고구마_0개 = new CartItem(고구마, 0);
        CartItem 호박고구마_10개 = new CartItem(호박고구마, 10);
        databaseFixture.save(호박, 고구마, 호박고구마);
        databaseFixture.save(고객, 호박_3개, 고구마_0개, 호박고구마_10개);

        Cart actual = cartService.findCart(고객);
        Cart expected = new Cart(호박_3개, 고구마_0개, 호박고구마_10개);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("registerNewCartItem 메서드는 새로운 상품을 장바구니에 등록한다.")
    @Nested
    class RegisterNewCartItemTest {

        @Test
        void 등록되지_않은_상품인_경우_1개를_등록하고_성공() {
            Long productId = 호박.getId();
            databaseFixture.save(호박);

            cartService.registerNewCartItem(고객, productId);
            Cart actualCart = cartService.findCart(고객);
            Cart expectedCart = new Cart(new CartItem(호박, 1));

            assertThat(actualCart).isEqualTo(expectedCart);
        }

        @Test
        void 이미_등록된_상품인_경우_redirect_관련_예외발생() {
            Long 호박_ID = 호박.getId();
            databaseFixture.save(호박);
            cartService.registerNewCartItem(고객, 호박_ID);

            assertThatThrownBy(() -> cartService.registerNewCartItem(고객, 호박_ID))
                    .isInstanceOf(CheckCartException.class);
        }

        @Test
        void 수량이_0개여도_이미_등록된_상품인_경우_redirect_관련_예외발생() {
            Long 호박_ID = 호박.getId();
            CartItem 호박_3개 = new CartItem(호박, 3);
            databaseFixture.save(호박);
            databaseFixture.save(고객, 호박_3개);

            assertThatThrownBy(() -> cartService.registerNewCartItem(고객, 호박_ID))
                    .isInstanceOf(CheckCartException.class);
        }

        @Test
        void 존재하지_않는_상품을_등록하려는_경우_예외_발생() {
            Long 존재하지_않는_ID = 999999L;

            assertThatThrownBy(() -> cartService.registerNewCartItem(고객, 존재하지_않는_ID))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    @DisplayName("updateCartItem 메서드는 상품과 수량 정보를 장바구니에 저장한다")
    @Nested
    class UpdateCartItemTest {

        @Test
        void 이미_장바구니에_등록된_상품인_경우_수량_정보만_수정() {
            Long 호박_ID = 호박.getId();
            CartItem 호박_3개 = new CartItem(호박, 3);
            databaseFixture.save(호박);
            databaseFixture.save(고객, 호박_3개);

            cartService.updateCartItem(고객, 호박_ID, 100);
            Cart actual = cartService.findCart(고객);
            Cart expected = new Cart(new CartItem(호박, 100));

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 아직_장바구니에_등록되지_않은_상품인_경우_새로_등록() {
            Long 호박_ID = 호박.getId();
            databaseFixture.save(호박);

            cartService.updateCartItem(고객, 호박_ID, 10);
            Cart actual = cartService.findCart(고객);
            Cart expected = new Cart(new CartItem(호박, 10));

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 존재하지_않는_상품을_등록하려는_경우_예외_발생() {
            Long 존재하지_않는_ID = 999999L;

            assertThatThrownBy(() -> cartService.registerNewCartItem(고객, 존재하지_않는_ID))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    @DisplayName("clearCart 메서드는 고객의 장바구니를 완전히 비운다")
    @Nested
    class ClearCartTest {

        @Test
        void 고객의_장바구니를_완전히_비움() {
            databaseFixture.save(호박, 고구마, 호박고구마);
            databaseFixture.save(고객, new CartItem(호박, 3),
                    new CartItem(고구마, 3), new CartItem(호박고구마, 3));

            cartService.clearCart(고객);
            Cart actual = cartService.findCart(고객);
            Cart expected = new Cart(List.of());

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 이미_비어있는_장바구니를_비우려는_경우_예외_미발생() {
            assertThatNoException()
                    .isThrownBy(() -> cartService.clearCart(고객));
        }
    }

    @DisplayName("removeCartItems 메서드는 고객의 장바구니에 담긴 상품 정보들 중 일부만 삭제")
    @Nested
    class RemoveCartItemsTest {

        private final CartItem 호박3개 = new CartItem(호박, 3);
        private final CartItem 고구마3개 = new CartItem(고구마, 3);
        private final CartItem 호박고구마3개 = new CartItem(호박고구마, 3);

        @Test
        void productIds_목록에_해당되는_상품들만_장바구니에서_제거() {
            databaseFixture.save(호박, 고구마, 호박고구마);
            databaseFixture.save(고객, 호박3개, 고구마3개, 호박고구마3개);
            List<Long> 호박과_호박고구마_정보 = List.of(호박3개.getProductId(), 호박고구마3개.getProductId());

            cartService.removeCartItems(고객, 호박과_호박고구마_정보);
            Cart actual = cartService.findCart(고객);
            Cart expected = new Cart(고구마3개);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 등록되지_않는_장바구니_정보가_포함되어도_예외_미발생하며_등록된_정보들만_전부_제거() {
            databaseFixture.save(호박, 고구마, 호박고구마);
            databaseFixture.save(고객, 호박3개, 고구마3개);
            List<Long> 호박과_호박고구마_정보 = List.of(호박3개.getProductId(), 호박고구마3개.getProductId());

            cartService.removeCartItems(고객, 호박과_호박고구마_정보);
            Cart actual = cartService.findCart(고객);
            Cart expected = new Cart(고구마3개);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 존재하지_않는_상품을_제거하려는_경우_예외_미발생() {
            List<Long> 존재하지_않는_상품_ID = List.of(999999L);

            assertThatNoException()
                    .isThrownBy(() -> cartService.removeCartItems(고객, 존재하지_않는_상품_ID));
        }
    }
}
