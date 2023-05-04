package cart.controller;

import javax.servlet.http.HttpServletRequest;

import cart.domain.user.User;
import cart.dto.CartRequest;
import cart.dto.CartResponse;
import cart.service.AuthService;
import cart.service.CartCreateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartCreateController {

    private final AuthService authService;
    private final CartCreateService cartCreateService;

    public CartCreateController(final AuthService authService, final CartCreateService cartCreateService) {
        this.authService = authService;
        this.cartCreateService = cartCreateService;
    }

    @PostMapping("/carts")
    public ResponseEntity<CartResponse> createCart(@RequestBody final CartRequest cartRequest, final HttpServletRequest request) {
        final User user = authService.getUser(request);
        final String email = user.getEmail().getValue();
        final Long productId = cartRequest.getProductId();
        final CartResponse cartResponse = cartCreateService.create(email, productId);
        return ResponseEntity.ok(cartResponse);
    }
}
