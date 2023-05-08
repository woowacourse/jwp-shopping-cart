package cart.service;

import cart.dao.CartDao;
import cart.domain.cart.CartItem;
import cart.domain.product.Product;
import cart.entity.CartEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;
    @Mock
    private ProductService productService;
    @Mock
    private UserService userService;
    @Mock
    private CartDao cartDao;

    @Test
    @DisplayName("상품을 저장한다")
    void save() {
        final Long id = 1L;
        doNothing().when(userService).validateUserIdExist(anyLong());
        doNothing().when(productService).validateProductIdExist(anyLong());
        given(cartDao.insert(anyLong(), anyLong())).willReturn(id);

        assertThat(cartService.save(1L, 1L)).isEqualTo(id);
        verify(cartDao, times(1)).insert(anyLong(), anyLong());
    }

    @Test
    @DisplayName("사용자 ID로 장바구니의 물건을 조회한다.")
    void findByUserId() {
        final List<CartEntity> cartEntities = List.of(new CartEntity(1L, 1L, 1L));
        final Product product = new Product("IO", 1000, null);
        final CartItem expected = new CartItem(1L, product);
        given(cartDao.findByUserId(anyLong())).willReturn(cartEntities);
        given(productService.findById(anyLong())).willReturn(product);

        final List<CartItem> actual = cartService.findByUserId(1L);

        assertAll(
                () -> assertThat(actual.size()).isEqualTo(1),
                () -> assertThat(actual.get(0).getId()).isEqualTo(expected.getId()),
                () -> assertThat(actual.get(0).getProductName()).isEqualTo(expected.getProductName()),
                () -> assertThat(actual.get(0).getProductPrice()).isEqualTo(expected.getProductPrice()),
                () -> assertThat(actual.get(0).getProductImage()).isEqualTo(expected.getProductImage())
        );
        verify(cartDao, times(1)).findByUserId(anyLong());
    }

    @Test
    @DisplayName("장바구니의 상품을 삭제한다")
    void delete() {
        doNothing().when(cartDao).deleteById(anyLong());

        assertDoesNotThrow(() -> cartService.delete(1L));
        verify(cartDao, times(1)).deleteById(anyLong());
    }
}
