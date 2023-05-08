package cart.service;

import java.util.Optional;

import cart.domain.user.User;
import cart.entiy.CartEntity;
import cart.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartDeleteService {

    private final CartRepository cartRepository;

    public CartDeleteService(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void delete(final long id, final User user) {
        final Optional<CartEntity> cart = cartRepository.findById(id);
        final String email = cart.orElseThrow(CartNotFoundException::new).getEmail();
        if (user.getEmail().getValue().equals(email)) {
            cartRepository.deleteById(id);
            return;
        }
        throw new CartOwnerMismatchException();
    }
}
