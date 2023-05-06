package cart.repository;

import static cart.fixture.DomainFactory.MAC_BOOK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import cart.domain.cart.Cart;
import cart.domain.user.User;
import cart.repository.dao.CartDao;
import cart.repository.dao.CartItemDao;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartRepositoryTest {

    @Mock
    CartDao cartDao;

    @Mock
    CartItemDao cartItemDao;

    CartRepository cartRepository;

    @BeforeEach
    void setUp() {
        cartRepository = new CartRepository(cartDao, cartItemDao);
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void saveSuccess() {
        Cart cart = new Cart(1L, List.of(MAC_BOOK));

        assertDoesNotThrow(() -> cartRepository.save(cart));
    }
    
    @Test
    @DisplayName("장바구니를 조회한다.")
    void findCartSuccess() {
        given(cartDao.findByUserId(anyLong())).willReturn(1L);
        given(cartItemDao.findAllByCartId(anyLong())).willReturn(List.of(MAC_BOOK));

        Cart cart = cartRepository.findCart(new User(1L, "a@a.com", "a"));
        
        assertAll(
                () -> assertThat(cart.getId()).isEqualTo(1L),
                () -> assertThat(cart.getItems()).hasSize(1)
        );
    }
    
    @Test
    @DisplayName("장바구니에 상품을 삭제한다.")
    void deleteCartItemSuccess() {
        Cart cart = new Cart(1L, List.of(MAC_BOOK));

        assertDoesNotThrow(() -> cartRepository.deleteCartItem(cart));
    }
}
