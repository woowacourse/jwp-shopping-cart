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
@Transactional(readOnly = true)
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public CartService(final UserRepository userRepository, final CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public List<CartProduct> getCartProducts1(final User userRequest) {
        final Optional<User> userOptional = userRepository.findUserByEmail(userRequest.getUserEmailValue());
        final User user = userOptional.orElseThrow(() -> new UserNotFoundException(userRequest.getUserEmailValue()));

        return cartRepository.findAllByUser1(user);
    }

    public List<CartProduct> getCartProducts2(final User userRequest) {
        final Optional<User> userOptional = userRepository.findUserByEmail(userRequest.getUserEmailValue());
        final User user = userOptional.orElseThrow(() -> new UserNotFoundException(userRequest.getUserEmailValue()));

        return cartRepository.findAllByUser2(user);
    }

    @Transactional
    public Long add(final User userRequest, final Long productId) {
        return cartRepository.insert(userRequest.getId(), productId);
    }

    @Transactional
    public void delete(final Long cartProductId) {
        cartRepository.delete(cartProductId);
    }
}
