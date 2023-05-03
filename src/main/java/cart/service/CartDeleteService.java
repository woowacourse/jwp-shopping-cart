package cart.service;

import cart.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartDeleteService {

    private final CartRepository cartRepository;

    public CartDeleteService(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void delete(final long id) {
        cartRepository.deleteById(id);
    }
}
