package cart.domain.cartitem;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cart.domain.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {

    @Mock
    private CartItemDao cartItemDao;
    @Mock
    private ProductService productService;

    private CartItemService cartItemService;

    @BeforeEach
    void setUp() {
        cartItemService = new CartItemService(cartItemDao, productService);
    }

    @DisplayName("장바구니 아이템 등록 시 DB에 정보를 저장한다")
    @Test
    void add() {
        // given
        CartItem cartItem = new CartItem(1L, 1L);

        // when
        cartItemService.add(cartItem);

        // then
        verify(cartItemDao).insert(cartItem);
    }

    @DisplayName("장바구니 아이템 등록 시 중복 정보가 있으면 예외를 던진다")
    @Test
    void addDuplicatedFail() {
        // given
        CartItem cartItem = new CartItem(1L, 1L);
        when(cartItemDao.isDuplicated(1L, 1L)).thenReturn(true);

        // when
        // then
        assertThatThrownBy(() -> cartItemService.add(cartItem))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("장바구니 아이템 등록 시 상품 정보가 존재하지 않으면 예외를 던진다")
    @Test
    void addNotExistingProductFail() {
        // given
        CartItem cartItem = new CartItem(1L, 1L);
        doThrow(IllegalArgumentException.class)
                .when(productService)
                .validateIdExist(1L);

        // when
        // then
        assertThatThrownBy(() -> cartItemService.add(cartItem))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
