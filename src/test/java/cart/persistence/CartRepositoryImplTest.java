package cart.persistence;

import cart.domain.cart.Cart;
import cart.domain.cart.CartItem;
import cart.domain.cart.Product;
import cart.persistence.dao.CartDao;
import cart.persistence.dao.CartItemDao;
import cart.persistence.dao.CartItemWithProductDao;
import cart.persistence.entity.CartItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartRepositoryImplTest {

    @Mock
    private CartDao cartDao;
    @Mock
    private CartItemDao cartItemDao;
    @Mock
    private CartItemWithProductDao cartItemWithProductDao;
    @InjectMocks
    private CartRepositoryImpl cartRepository;

    private Cart cart;
    private List<CartItemEntity> cartItemEntities;
    private List<Product> products;
    private Long cartId;
    private Long memberId;

    @BeforeEach
    void setUp() {
        cartId = 1L;
        memberId = 1L;
        Product p1 = new Product(1L, "p1", "test-image-url", new BigDecimal(1000));
        Product p2 = new Product(2L, "p2", "test-image-url", new BigDecimal(2000));

        products = List.of(p1, p2);

        CartItem item1 = new CartItem(p1);
        CartItem item2 = new CartItem(p2);

        cart = new Cart(cartId, memberId, List.of(item1, item2));

        cartItemEntities = List.of(
                new CartItemEntity(cartId, p1.getId()),
                new CartItemEntity(cartId, p2.getId())
        );
    }

    @Test
    @DisplayName("cart가 있으면 update하고 있으면 insert한다. 이후에 저장한 cart를 반환한다.")
    void saveTest() {
        when(cartDao.existsByCartId(cartId)).thenReturn(true);
        doNothing().when(cartItemDao).deleteByCartId(cartId);
        doNothing().when(cartItemDao).insertCartItems(cartItemEntities);

        Cart save = cartRepository.save(cart);

        assertAll(
                () -> assertThat(cart.getCartId()).isEqualTo(save.getCartId()),
                () -> assertThat(cart.getCartItems().containsAll(save.getCartItems())).isTrue(),
                () -> assertThat(cart.getMemberId()).isEqualTo(save.getMemberId())
        );
        verify(cartDao, times(1)).existsByCartId(cartId);
        verify(cartItemDao, times(1)).insertCartItems(cartItemEntities);
        verify(cartItemDao, times(1)).deleteByCartId(cartId);
    }

    @Test
    @DisplayName("memberId에 해당하는 cart를 꺼내온다.")
    void getCartByMemberIdTest() {
        when(cartDao.findCartIdByMemberId(memberId)).thenReturn(Optional.of(cartId));
        when(cartItemWithProductDao.findProductsByCartId(cartId)).thenReturn(products);

        Cart foundCart = cartRepository.getCartByMemberId(memberId);

        assertAll(
                () -> assertThat(foundCart.getCartId()).isEqualTo(cart.getCartId()),
                () -> assertThat(foundCart.getMemberId()).isEqualTo(cart.getMemberId()),
                () -> assertThat(foundCart.getCartItems().containsAll(cart.getCartItems())).isTrue()
        );
        verify(cartDao, times(1)).findCartIdByMemberId(memberId);
        verify(cartItemWithProductDao, times(1)).findProductsByCartId(cartId);
    }
}
