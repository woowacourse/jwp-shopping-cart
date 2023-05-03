package cart.service.cart;

import cart.domain.cart.Cart;
import cart.exception.UserNotFoundException;
import cart.repository.cart.CartRepository;
import cart.service.user.UserQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartQueryService {

    private final UserQueryService userQueryService;
    private final CartRepository cartRepository;

    public CartQueryService(final UserQueryService userQueryService, final CartRepository cartRepository) {
        this.userQueryService = userQueryService;
        this.cartRepository = cartRepository;
    }

    public Cart findByEmail(final String email) {
        return userQueryService.findByEmail(email)
                .flatMap(cartRepository::findByUser)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
