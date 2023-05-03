package cart.service.cart;

import cart.domain.cart.Cart;
import cart.domain.product.Product;
import cart.event.user.UserRegisteredEvent;
import cart.exception.ProductNotFoundException;
import cart.exception.UserNotFoundException;
import cart.repository.cart.CartRepository;
import cart.service.product.ProductQueryService;
import cart.service.user.UserQueryService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartCommandService {

    private final CartRepository cartRepository;
    private final UserQueryService userQueryService;
    private final ProductQueryService productQueryService;

    public CartCommandService(final CartRepository cartRepository,
            final UserQueryService userQueryService,
            final ProductQueryService productQueryService) {
        this.cartRepository = cartRepository;
        this.userQueryService = userQueryService;
        this.productQueryService = productQueryService;
    }

    @EventListener(UserRegisteredEvent.class)
    public void createCart(final UserRegisteredEvent userRegisteredEvent) {
        final Cart cart = new Cart(userRegisteredEvent.getUser());
        cartRepository.save(cart);
    }

    public Cart addProduct(final Long productId, final String email) {
        final Cart cart = userQueryService.findByEmail(email)
                .flatMap(cartRepository::findByUser)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        final Product product = productQueryService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        cart.addProduct(product);
        return cartRepository.save(cart);
    }

    public void deleteProduct(final Long cartProductId, final String email) {
        final Cart cart = userQueryService.findByEmail(email)
                .flatMap(cartRepository::findByUser)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        cart.deleteProduct(cartProductId);
        cartRepository.save(cart);
    }
}
