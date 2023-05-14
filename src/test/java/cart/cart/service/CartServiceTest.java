package cart.cart.service;

import cart.cart.dao.JdbcCartDao;
import cart.cart.domain.Cart;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static cart.DummyData.INITIAL_MEMBER_ONE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CartServiceTest {

    @InjectMocks
    CartService cartService;

    @Mock
    JdbcCartDao cartDao;

    @Test
    void 특정_유저_id에_대한_장바구니용_id를_가져온다() {
        when(cartDao.findById(any())).thenReturn(Cart.of(1L, 1L));

        assertDoesNotThrow(() -> cartService.find(INITIAL_MEMBER_ONE));
    }
}
