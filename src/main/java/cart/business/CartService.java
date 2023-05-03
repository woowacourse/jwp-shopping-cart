package cart.business;

import cart.business.domain.cart.CartItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addCartItem(Integer productId, Integer memberId) {
        CartItem cartItem = new CartItem(null, productId, memberId);
        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void removeCartItem(Integer memberId, Integer cartItemId) {
        cartItemRepository.remove(cartItemId);
    }

    @Transactional(readOnly = true)
    public List<CartItem> readAllCartItem(Integer memberId) {
        return cartItemRepository.findAllById(memberId);
    }
}
