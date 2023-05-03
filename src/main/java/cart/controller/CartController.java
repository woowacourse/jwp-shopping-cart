package cart.controller;

import cart.domain.auth.service.AuthService;
import cart.domain.cart.service.CartService;
import cart.dto.AuthInfo;
import cart.dto.CartCreateRequest;
import cart.dto.CartResponse;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private static final String AUTHORIZATION = "Authorization";
    private final AuthService authService;
    private final CartService cartService;

    public CartController(final AuthService authService, final CartService cartService) {
        this.authService = authService;
        this.cartService = cartService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartResponse>> getCarts(
        final HttpServletRequest httpServletRequest) {
        final AuthInfo authInfo = authService.checkAuthenticationHeader(
            httpServletRequest.getHeader(AUTHORIZATION));
        final List<CartResponse> responses = cartService.findByEmail(authInfo.getEmail());
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("")
    public ResponseEntity<Void> addCart(@RequestParam final Long productId,
        final HttpServletRequest httpServletRequest) {
        final AuthInfo authInfo = authService.checkAuthenticationHeader(
            httpServletRequest.getHeader(AUTHORIZATION));
        final CartCreateRequest request = new CartCreateRequest(productId, authInfo.getEmail());
        cartService.create(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteCart(@RequestParam final Long id,
        final HttpServletRequest httpServletRequest) {
        authService.checkAuthenticationHeader(httpServletRequest.getHeader(AUTHORIZATION));
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
