package cart.controller;

import javax.servlet.http.HttpServletRequest;

import cart.dto.CartRequest;
import cart.dto.CartResponse;
import cart.dto.UserResponse;
import cart.infrastructure.BasicAuthorizationExtractor;
import cart.service.AuthService;
import cart.service.CartCreateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartCreateController {

    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
    private final AuthService authService;
    private final CartCreateService cartCreateService;

    public CartCreateController(final AuthService authService, final CartCreateService cartCreateService) {
        this.authService = authService;
        this.cartCreateService = cartCreateService;
    }

    @PostMapping("/carts")
    public ResponseEntity<CartResponse> createCart(@RequestBody final CartRequest cartRequest, final HttpServletRequest request) {
        final UserResponse userResponse = basicAuthorizationExtractor.extract(request);
        final String email = userResponse.getEmail();
        final String password = userResponse.getPassword();
        if (authService.isValidLogin(email, password)) {
            final Long productId = cartRequest.getProductId();
            cartCreateService.create(email, productId);
            return ResponseEntity.ok(new CartResponse(1L, email, productId));
        }
        throw new LoginFailException();
    }
}
