package cart.service;

import cart.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartCreateService {

    private final CartRepository cartRepository;

    public CartCreateService(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void create(final String email, final Long productId) {
        cartRepository.save(email, productId);
    }
}
