package cart.service.cart;

import cart.domain.cart.Cart;
import cart.event.user.UserRegisteredEvent;
import cart.repository.cart.CartRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartCommandService {

    private final CartRepository cartRepository;

    public CartCommandService(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @EventListener(UserRegisteredEvent.class)
    public void createCart(final UserRegisteredEvent userRegisteredEvent) {
        final Cart cart = new Cart(userRegisteredEvent.getUser());
        cartRepository.save(cart);
    }
}
