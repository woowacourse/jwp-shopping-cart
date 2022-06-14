package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartItemDao cartItemDao;

    @Mock
    private ProductService productService;

    @Test
    @DisplayName("장바구니에 상품 추가하는 기능")
    void addCart() {
        // given
        when(cartItemDao.addCartItem(1L, 1L, 1))
                .thenReturn(1L);

        // when
        Long addedItemId = cartService.enrollCartItem(1L, 1L);

        // then
        assertThat(addedItemId).isOne();
    }

    @Test
    @DisplayName("장바구니에 존재하는 상품 조회하는 기능")
    void getCartItems() {
        // given

        when(cartItemDao.findIdsByCustomerId(1L))
                .thenReturn(List.of(1L, 2L));
        when(cartItemDao.findProductIdById(1L))
                .thenReturn(1L);
        when(cartItemDao.findProductIdById(2L))
                .thenReturn(2L);

        final Product rice = new Product(1L, "밥", 1000, "www.naver.com");
        final Product bread = new Product(2L, "빵", 1000, "www.naver.com");
        when(productService.findProductById(1L))
                .thenReturn(rice);
        when(productService.findProductById(2L))
                .thenReturn(bread);

        List<CartItem> expected = List.of(new CartItem(1L, rice), new CartItem(2L, bread));

        // when
        final List<CartItem> cartItems = cartService.findCartItemsByCustomerId(1L);

        // then
        assertThat(cartItems).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("장바구니 수량을 하나 추가하는 기능")
    void updateCartItem() {
        // given
        final CartItem rice = new CartItem(1L, 1L, "밥", 1000, "www.naver.com", 1);
        when(cartItemDao.findByCustomerIdAndProductId(1L, 1L))
                .thenReturn(rice);
        when(cartItemDao.update(any(CartItem.class)))
                .thenReturn(2);

        // when, then
        assertThatCode(() -> cartService.update(1L, 1L, 2))
                .doesNotThrowAnyException();
    }
}
