package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.dto.cart.CartAdditionRequest;
import woowacourse.shoppingcart.dto.cart.CartUpdateRequest;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CartServiceTest {

    public static final Email EMAIL = new Email("email@email.com");

    private final CartService cartService;

    public CartServiceTest(CartService cartService) {
        this.cartService = cartService;
    }

    @DisplayName("장바구니를 조회한다.")
    @Test
    void findCartsByEmail() {
        assertThat(cartService.findCartsByEmail(EMAIL)).hasSize(2);
    }

    @DisplayName("장바구니에 새로운 상품을 추가한다.")
    @Test
    void saveCartItem() {
        final CartAdditionRequest cartAdditionRequest = new CartAdditionRequest(10L, 1);
        assertDoesNotThrow(() -> cartService.addCartItem(EMAIL, cartAdditionRequest));
    }

    @DisplayName("장바구니에 새로운 상품을 추가할 때 재고보다 많은 수량을 담으려는 경우 예외가 발생한다.")
    @Test
    void saveExceedQuantity() {
        final CartAdditionRequest cartAdditionRequest = new CartAdditionRequest(1L, 100);
        assertThatThrownBy(() -> cartService.addCartItem(EMAIL, cartAdditionRequest))
                .isInstanceOf(InvalidCartItemException.class);
    }

    @DisplayName("장바구니에 기존에 있던 상품을 추가한다.")
    @Test
    void saveExistingCartItem() {
        final CartAdditionRequest cartAdditionRequest = new CartAdditionRequest(1L, 3);
        assertDoesNotThrow(() -> cartService.addCartItem(EMAIL, cartAdditionRequest));
    }

    @DisplayName("장바구니에 기존에 있던 상품을 추가할 때 합계 수량이 재고보다 많은 경우 예외가 발생한다.")
    @Test
    void saveExistingCartItemExceedQuantity() {
        final CartAdditionRequest cartAdditionRequest = new CartAdditionRequest(1L, 10);
        assertThatThrownBy(() -> cartService.addCartItem(EMAIL, cartAdditionRequest))
                .isInstanceOf(InvalidCartItemException.class);
    }

    @DisplayName("장바구니에 상품 수량을 변경한다.")
    @Test
    void updateCartItem() {
        final CartUpdateRequest cartUpdateRequest = new CartUpdateRequest(1L, 2);
        assertDoesNotThrow(() -> cartService.updateCartItem(EMAIL, cartUpdateRequest));
    }

    @DisplayName("장바구니에 변경할 상품이 존재하지 않는 경우 예외가 발생한다.")
    @Test
    void updateNotExistCartItem() {
        final CartUpdateRequest cartUpdateRequest = new CartUpdateRequest(10L, 2);
        assertThatThrownBy(() -> cartService.updateCartItem(EMAIL, cartUpdateRequest))
                .isInstanceOf(InvalidCartItemException.class)
                .hasMessage("장바구니에 해당 상품이 존재하지 않습니다.");
    }

    @DisplayName("장바구니에 담을 상품의 재고가 부족할 경우 예외가 발생한다.")
    @Test
    void updateShortageOfStockCartItem() {
        final CartUpdateRequest cartUpdateRequest = new CartUpdateRequest(1L, 100);
        assertThatThrownBy(() -> cartService.updateCartItem(EMAIL, cartUpdateRequest))
                .isInstanceOf(InvalidCartItemException.class);
    }

    @DisplayName("장바구니를 삭제한다.")
    @Test
    void deleteCartItem() {
        assertDoesNotThrow(() -> cartService.deleteCartItem(EMAIL, 1L));
    }

    @DisplayName("장바구니에 삭제할 제품이 존재하지 않는 경우 예외가 발생한다.")
    @Test
    void deleteNotExistCartItem() {
        assertThatThrownBy(() -> cartService.deleteCartItem(EMAIL, 10L))
                .isInstanceOf(NotInCustomerCartItemException.class);
    }
}
