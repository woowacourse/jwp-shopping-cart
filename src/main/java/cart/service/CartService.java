package cart.service;

import cart.domain.Cart;
import cart.domain.CartItem;
import cart.domain.Product;
import cart.domain.User;
import cart.exception.ProductNotFoundException;
import cart.exception.UserNotFoundException;
import cart.repository.CartRepository;
import cart.repository.ProductRepository;
import cart.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(UserRepository userRepository, CartRepository cartRepository,
                       ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<CartItem> getCartItems(String userEmail) {
        User user = getUser(userEmail);
        Cart cart = cartRepository.findByUser(user);
        return cart.getCartItems();
    }

    private User getUser(String userEmail) {
        return userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException());
    }

    @Transactional
    public void addCartItem(String userEmail, Long productId) {
        User user = getUser(userEmail);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException());
        Cart cart = cartRepository.findByUser(user);
        cartRepository.addCartItem(cart, new CartItem(product));
    }

    @Transactional
    public void deleteCartItem(Long cartItemId) {
        cartRepository.removeCartItem(cartItemId);
    }
}
