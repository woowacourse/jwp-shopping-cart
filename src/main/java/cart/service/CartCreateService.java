package cart.service;

import cart.controller.dto.CartResponse;
import cart.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartCreateService {

    private final CartRepository cartRepository;

    public CartCreateService(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartResponse create(final String email, final Long productId) {
        return cartRepository.save(email, productId);
    }
}
