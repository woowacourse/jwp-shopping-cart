package cart.domain.cart;

import cart.domain.user.User;
import cart.domain.user.UserRepository;
import cart.exception.UserNotFoundException;
import cart.web.controller.user.dto.UserRequest;
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

    public Long add(final UserRequest userRequest, final Long productId) {
        final Optional<User> userOptional = userRepository.findUserByEmail(userRequest.getEmail());
        final User user = userOptional.orElseThrow(() -> new UserNotFoundException(userRequest.getEmail()));

        return cartRepository.insert(user, productId);
    }

    @Transactional(readOnly = true)
    public List<CartProduct> getCartProducts(final UserRequest userRequest) {
        final Optional<User> userOptional = userRepository.findUserByEmail(userRequest.getEmail());
        final User user = userOptional.orElseThrow(() -> new UserNotFoundException(userRequest.getEmail()));

        return cartRepository.findAllByUser(user);
    }

    public void delete(final UserRequest userRequest, final Long cartProductId) {
        final Optional<User> userOptional = userRepository.findUserByEmail(userRequest.getEmail());
        final User user = userOptional.orElseThrow(() -> new UserNotFoundException(userRequest.getEmail()));

        cartRepository.delete(user, cartProductId);
    }
}
