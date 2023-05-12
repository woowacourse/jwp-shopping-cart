package cart.service;

import cart.domain.Cart;
import cart.domain.CartProduct;
import cart.dto.AddCartRequestDto;
import cart.dto.auth.AuthInfo;
import cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional
    public void addProductToCart(final AddCartRequestDto addCartRequestDto, final AuthInfo authInfo) {
        final Long memberId = cartRepository.findIdByAuthInfo(authInfo);

        cartRepository.addProductToCart(addCartRequestDto.getProductId(), memberId);
    }

    @Transactional
    public void deleteProductFromCart(final Long cartId, final AuthInfo authInfo) {
        final Long memberId = cartRepository.findIdByAuthInfo(authInfo);
        final Cart cart = cartRepository.getCartProductsByMemberId(memberId);
        final CartProduct productToDelete = cartRepository.getCartProductByCartId(cartId);
        cart.deleteProduct(productToDelete);

        cartRepository.deleteProductFromCart(cartId);
    }

    public Cart findCartProductsByMember(AuthInfo authInfo) {
        final Long memberId = cartRepository.findIdByAuthInfo(authInfo);
        return cartRepository.getCartProductsByMemberId(memberId);
    }
}
