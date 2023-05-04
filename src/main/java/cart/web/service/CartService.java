package cart.web.service;

import cart.domain.cart.CartProduct;
import cart.domain.cart.CartRepository;
import cart.domain.user.User;
import cart.domain.user.UserRepository;
import cart.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public CartService(final UserRepository userRepository, final CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public Long add(final User userRequest, final Long productId) {
        final Optional<User> userOptional = userRepository.findUserByEmail(userRequest.getUserEmailValue());
        final User user = userOptional.orElseThrow(() -> new UserNotFoundException(userRequest.getUserEmailValue()));

        return cartRepository.insert(user, productId);
    }

    @Transactional(readOnly = true)
    public List<CartProduct> getCartProducts(final User userRequest) {
        final Optional<User> userOptional = userRepository.findUserByEmail(userRequest.getUserEmailValue());
        final User user = userOptional.orElseThrow(() -> new UserNotFoundException(userRequest.getUserEmailValue()));

        return cartRepository.findAllByUser(user);
    }

    public void delete(final User userRequest, final Long cartProductId) {
        final Optional<User> userOptional = userRepository.findUserByEmail(userRequest.getUserEmailValue());
        final User user = userOptional.orElseThrow(() -> new UserNotFoundException(userRequest.getUserEmailValue()));

        cartRepository.delete(user, cartProductId);
    }
}
