package cart.service;

import cart.domain.cart.Cart;
import cart.domain.cart.CartProduct;
import cart.domain.product.Product;
import cart.domain.user.User;
import cart.exception.ProductNotFoundException;
import cart.exception.UserNotFoundException;
import cart.repository.cart.CartRepository;
import cart.repository.product.ProductRepository;
import cart.repository.user.UserRepository;
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
    public List<CartProduct> getCartItems(String userEmail) {
        User user = getUser(userEmail);
        Cart cart = cartRepository.findByNo(user.getCartNo());
        return cart.getCartProducts();
    }

    private User getUser(String userEmail) {
        return userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void addCartItem(String userEmail, Long productId) {
        User user = getUser(userEmail);
        Cart cart = cartRepository.findByNo(user.getCartNo());
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        cartRepository.addCartItem(cart, new CartProduct(product));
    }

    @Transactional
    public void deleteCartItem(Long cartItemId) {
        cartRepository.removeCartItem(cartItemId);
    }
}
