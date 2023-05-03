package cart.business;

import cart.business.domain.cart.CartItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public void addCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void removeCartItem(Integer cartItemId) {
        cartItemRepository.remove(cartItemId);
    }

    @Transactional(readOnly = true)
    public List<CartItem> readAllCartItem(Integer memberId) {
        return cartItemRepository.findAllByMemberId(memberId);
    }
}
