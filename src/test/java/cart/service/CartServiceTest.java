package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;

import cart.domain.Cart;
import cart.domain.CartItem;
import cart.domain.CartItems;
import cart.domain.Product;
import cart.domain.User;
import cart.factory.UserFactory;
import cart.repository.CartRepository;
import cart.repository.ProductRepository;
import cart.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("회원의 카트 물건을 가져올 수 있다.")
    void get_cart_items_success() {
        //given
        String email = "rosie@wooteco.com";
        User user = UserFactory.createUser(email);
        Product product = Product.from(1L, "라면", "imgimg", 10000);
        CartItem cartItem = new CartItem(product);

        BDDMockito.given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        BDDMockito.given(cartRepository.findByUser(user)).willReturn(new Cart(user, new CartItems(List.of(cartItem))));
        //when
        List<CartItem> cartItems = cartService.getCartItems(email);
        //then
        assertThat(cartItems).contains(cartItem);
    }

    @Test
    @DisplayName("카트에 물건을 추가할 수 있다.")
    void add_cart_item_success() {
        // given
        String email = "rosie@wooteco.com";
        Long productId = 1L;
        Product product = Product.from(productId, "피자", "imgurl", 10000);
        User user = UserFactory.createUser(email);
        Cart cart = Cart.of(user, List.of());
        BDDMockito.given(productRepository.findById(productId)).willReturn(Optional.of(product));
        BDDMockito.given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        BDDMockito.given(cartRepository.findByUser(user)).willReturn(cart);

        // when
        cartService.addCartItem(email, productId);

        //then
        ArgumentCaptor<CartItem> cartItemCaptor = ArgumentCaptor.forClass(CartItem.class);
        Mockito.verify(cartRepository).addCartItem(eq(cart), cartItemCaptor.capture());
        Product expected = cartItemCaptor.getValue().getProduct();
        assertThat(expected).isEqualTo(product);
    }

    @Test
    @DisplayName("카트의 물건을 지울 수 있다.")
    void remove_cart_item_success() {
        //given
        Long cartItemId = 1L;

        //when
        cartService.deleteCartItem(cartItemId);

        //then
        Mockito.verify(cartRepository).removeCartItem(eq(cartItemId));
    }
}