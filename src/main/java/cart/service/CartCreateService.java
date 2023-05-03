package cart.service;

import cart.repository.CartRepository;
import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CartCreateService {

    private final CartRepository cartRepository;

    public CartCreateService(final CartRepository cartRepository, final ProductRepository productRepository) {
        this.cartRepository = cartRepository;
    }

    public void create(final String email, final Long productId) {
        cartRepository.save(email, productId);
    }
}
