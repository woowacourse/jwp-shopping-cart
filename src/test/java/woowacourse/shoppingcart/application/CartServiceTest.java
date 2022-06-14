package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.NotFoundCustomerCartItemException;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartItemDao cartItemDao;

    @Test
    @DisplayName("장바구니에 상품을 담다.")
    void addCart() {
        //given
        final long expected = 1L;
        when(cartItemDao.addCartItem(any(Long.class), any(Long.class)))
                .thenReturn(expected);

        //when
        final Long actual = cartService.addCart(1L, 1L);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("장바구니에 상품을 담믈 때 고객이나 상품이 존재하지 않을 경우 예외를 던진다.")
    void addCart_invalidCustomerOrProduct_throwsException() {
        //given
        when(cartItemDao.addCartItem(any(Long.class), any(Long.class)))
                .thenThrow(DataIntegrityViolationException.class);

        //when, then
        assertThatThrownBy(() -> cartService.addCart(1L, 1L))
                .isInstanceOf(NotFoundProductException.class);
    }

    @Test
    @DisplayName("고객 id 에 해당하는 장바구니 상품들을 조회한다.")
    void findCartsByCustomerId() {
        //given
        final long customerId = 1L;
        final List<Cart> expected = List.of(new Cart(1L, 2L, "카레", 1000, 3, "www.na/e"));
        when(cartItemDao.findCartItemsByCustomerId(customerId))
                .thenReturn(expected);

        //when
        final List<Cart> actual = cartService.findCartsByCustomerId(customerId);

        //then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("장바구니 상품의 수량을 수정한다.")
    void updateCartItemQuantity() {
        //given
        final long cartItemId = 1L;
        when(cartItemDao.findProductIdsByCustomerId(any(Long.class)))
                .thenReturn(List.of(1L, 2L));

        //when
        assertThatCode(() -> cartService.updateCartItemQuantity(1L, cartItemId, 10))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("장바구니 상품 수량 수정시 존재하지 않는 장바구니 상품이면 예외를 던진다.")
    void updateCartItemQuantity_invalidCartItem_throwsException() {
        //given
        final long cartItemId = 51L;
        when(cartItemDao.findProductIdsByCustomerId(any(Long.class)))
                .thenReturn(List.of(1L, 2L));

        //when, then
        assertThatThrownBy(() -> cartService.updateCartItemQuantity(1L, cartItemId, 10))
                .isInstanceOf(NotFoundCustomerCartItemException.class);
    }

    @Test
    @DisplayName("고객 id 에 해당하는 장바구니에서 상품 id 들에 해당하는 상품을 삭제한다.")
    void deleteCart() {
        //given
        final long customerId = 1L;
        when(cartItemDao.findIdsByCustomerId(customerId))
                .thenReturn(List.of(1L, 2L, 3L));
        final List<Long> cartItemIds = List.of(1L, 2L);

        //when, then
        assertThatCode(() -> cartService.deleteCart(customerId, cartItemIds))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("장바구니 상품 삭제시 존재하지 않는 상품일 경우 예외를 던진다.")
    void deleteCart_invalidProduct_throwsException() {
        //given
        final long customerId = 1L;
        when(cartItemDao.findIdsByCustomerId(customerId))
                .thenReturn(List.of(1L, 2L, 3L));
        final List<Long> cartItemIds = List.of(5L, 6L);

        //when, then
        assertThatThrownBy(() -> cartService.deleteCart(customerId, cartItemIds))
                .isInstanceOf(NotFoundCustomerCartItemException.class);
    }
}
