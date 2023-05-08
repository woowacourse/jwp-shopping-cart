package cart.controller.api;

import cart.controller.auth.AuthenticationPrincipal;
import cart.domain.user.User;
import cart.dto.CartRequest;
import cart.dto.CartResponse;
import cart.service.CartCreateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartCreateController {

    private final CartCreateService cartCreateService;

    public CartCreateController(final CartCreateService cartCreateService) {
        this.cartCreateService = cartCreateService;
    }

    @PostMapping("/carts")
    public ResponseEntity<CartResponse> createCart(
            @RequestBody final CartRequest cartRequest,
            @AuthenticationPrincipal final User user
    ) {
        final String email = user.getEmail().getValue();
        final Long productId = cartRequest.getProductId();
        final CartResponse cartResponse = cartCreateService.create(email, productId);
        return ResponseEntity.ok(cartResponse);
    }
}
