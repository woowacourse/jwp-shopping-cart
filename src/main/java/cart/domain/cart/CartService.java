package cart.domain.cart;

import cart.domain.product.Product;
import cart.domain.user.User;
import cart.domain.user.UserRepository;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
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
        final User user = userOptional.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        return cartRepository.insert(user, productId);
    }

    @Transactional(readOnly = true)
    public List<Product> getProducts(final UserRequest userRequest) {
        final Optional<User> userOptional = userRepository.findUserByEmail(userRequest.getEmail());
        final User user = userOptional.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        return cartRepository.findAllByUser(user);
    }

    public void delete(final UserRequest userRequest, final Long productId) {
        final Optional<User> userOptional = userRepository.findUserByEmail(userRequest.getEmail());
        final User user = userOptional.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        cartRepository.delete(user, productId);
    }
}
