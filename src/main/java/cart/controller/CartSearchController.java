package cart.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import cart.domain.cart.Cart;
import cart.domain.user.User;
import cart.dto.ProductResponse;
import cart.service.AuthService;
import cart.service.CartSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartSearchController {

    private final AuthService authService;
    private final CartSearchService cartSearchService;

    public CartSearchController(final AuthService authService, final CartSearchService cartSearchService) {
        this.authService = authService;
        this.cartSearchService = cartSearchService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<ProductResponse>> getCarts(final HttpServletRequest request) {
        final User user = authService.getUser(request);
        final String email = user.getEmail().getValue();
        final List<Cart> products = cartSearchService.findByEmail(email);
        final List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResponses);
    }
}
