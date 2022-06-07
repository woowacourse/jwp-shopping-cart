package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CartItemDao;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    CartService cartService;

    @Mock
    CartItemDao cartItemDao;

    @Test
    @DisplayName("장바구니에 상품 추가하는 기능")
    void addCart() {
        // given
        Mockito.when(cartItemDao.addCartItem(1L, 1L, 0))
                .thenReturn(1L);

        // when
        Long addedItemId  = cartService.addCart(1L, 1L);

        // then
        assertThat(addedItemId).isOne();
    }
}
