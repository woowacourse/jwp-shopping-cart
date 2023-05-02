package cart.controller;

import cart.domain.auth.service.AuthService;
import cart.domain.cart.service.CartService;
import cart.dto.AuthInfo;
import cart.dto.CartResponse;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final AuthService authService;
    private final CartService cartService;

    public CartController(final AuthService authService, final CartService cartService) {
        this.authService = authService;
        this.cartService = cartService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartResponse>> getCarts(final HttpServletRequest request) {
        final AuthInfo authInfo = authService.checkAuthenticationHeader(
            request.getHeader("Authorization"));
        final List<CartResponse> responses = cartService.findByEmail(authInfo.getEmail());
        return ResponseEntity.ok().body(responses);
    }
}
